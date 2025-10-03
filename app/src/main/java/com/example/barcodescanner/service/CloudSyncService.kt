package com.example.barcodescanner.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.barcodescanner.data.local.AppDatabase
import com.example.barcodescanner.data.local.AssociationEntity
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Response
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Background service for syncing associations to cloud API
 * Runs continuously and handles offline/online scenarios
 */
class CloudSyncService : Service() {

    companion object {
        private const val TAG = "CloudSyncService"
        private const val API_BASE_URL = "http://3.91.8.133:3000/"
        private const val SYNC_INTERVAL_MS = 10000L // 10 seconds
        private const val RETRY_DELAY_MS = 30000L // 30 seconds
        const val ACTION_START_SYNC = "START_SYNC"
        const val ACTION_STOP_SYNC = "STOP_SYNC"
        const val ACTION_FORCE_SYNC = "FORCE_SYNC"
    }

    private var syncJob: Job? = null
    private var isRunning = false
    private lateinit var database: AppDatabase
    private lateinit var apiService: AssociationApiService
    private lateinit var connectivityMonitor: ConnectivityMonitor

    // API interface
    interface AssociationApiService {
        @POST("api/associations")
        @Headers(
            "Content-Type: application/json",
            "User-Agent: Chorus-API-Tester/1.0"
        )
        suspend fun submitAssociations(@Body associations: List<AssociationRequest>): Response<AssociationResponse>

        // 🔧 NEW: Single association endpoint (matches Python script)
        @POST("api/associations")
        @Headers(
            "Content-Type: application/json",
            "User-Agent: Chorus-API-Tester/1.0"
        )
        suspend fun submitSingleAssociation(@Body association: AssociationRequest): Response<AssociationResponse>
    }

    // Request/Response data classes
    data class AssociationRequest(
        val toteId: String,
        val olpn: String
    )

    data class AssociationResponse(
        val success: Boolean? = null,
        val message: String? = null,
        val processed: Int? = null,
        val error: String? = null,
        val status: String? = null
    )

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "CloudSyncService created")

        database = AppDatabase.getInstance(this)
        connectivityMonitor = ConnectivityMonitor(this)

        // Setup Retrofit with logging
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(AssociationApiService::class.java)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service started with action: ${intent?.action}")

        when (intent?.action) {
            ACTION_START_SYNC -> startSync()
            ACTION_STOP_SYNC -> stopSync()
            ACTION_FORCE_SYNC -> forceSyncNow()
        }

        return START_STICKY // Restart if killed
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startSync() {
        if (isRunning) {
            Log.d(TAG, "Sync already running")
            return
        }

        isRunning = true
        syncJob = CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            Log.d(TAG, "Starting continuous sync loop")

            while (isActive && isRunning) {
                try {
                    if (connectivityMonitor.isConnected() && connectivityMonitor.hasGoodBandwidth()) {
                        performSync()
                    } else {
                        Log.d(TAG, "No connectivity or poor bandwidth, skipping sync")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error in sync loop", e)
                }

                delay(SYNC_INTERVAL_MS)
            }

            Log.d(TAG, "Sync loop ended")
        }
    }

    private fun stopSync() {
        Log.d(TAG, "Stopping sync service")
        isRunning = false
        syncJob?.cancel()
        syncJob = null
    }

    private fun forceSyncNow() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                performSync()
            } catch (e: Exception) {
                Log.e(TAG, "Force sync failed", e)
            }
        }
    }

    private suspend fun performSync() {
        val pendingAssociations = database.associationDao().getPendingAssociations()

        if (pendingAssociations.isEmpty()) {
            Log.d(TAG, "No pending associations to sync")
            return
        }

        Log.d(TAG, "Found ${pendingAssociations.size} pending associations")

        // Process in smaller batches (1 at a time to match Python script)
        val batchSize = 1
        val batches = pendingAssociations.chunked(batchSize)

        for (batch in batches) {
            try {
                val success = submitBatch(batch)
                if (!success) {
                    // If batch fails, mark individual items for retry
                    markBatchForRetry(batch, "Batch submission failed")
                    delay(RETRY_DELAY_MS)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error submitting batch", e)
                markBatchForRetry(batch, e.message ?: "Unknown error")
                delay(RETRY_DELAY_MS)
            }
        }
    }

    private suspend fun submitBatch(associations: List<AssociationEntity>): Boolean {
        return try {
            // 🔧 FIX: Submit associations one by one, not as array
            var allSuccessful = true

            for (association in associations) {
                try {
                    val request = AssociationRequest(
                        toteId = association.toteId,
                        olpn = association.olpn
                    )

                    Log.d(TAG, "Submitting single association: ToteId=${association.toteId}, OLPN=${association.olpn}")
                    val response = apiService.submitSingleAssociation(request)

                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d(TAG, "Association submitted successfully: ${responseBody?.message}")

                        // Mark this association as submitted
                        val timestamp = System.currentTimeMillis()
                        database.associationDao().markAsSubmitted(
                            id = association.id,
                            timestamp = timestamp,
                            responseData = responseBody?.message
                        )
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e(TAG, "Association submission failed: ${response.code()} - $errorBody")
                        allSuccessful = false

                        // Mark for retry
                        database.associationDao().incrementRetryCount(association.id, "HTTP ${response.code()}: $errorBody")
                    }

                    // Small delay between requests to avoid overwhelming server
                    delay(200)

                } catch (e: Exception) {
                    Log.e(TAG, "Exception submitting association ${association.id}", e)
                    allSuccessful = false
                    database.associationDao().incrementRetryCount(association.id, e.message)
                }
            }

            allSuccessful
        } catch (e: Exception) {
            Log.e(TAG, "Exception during batch submission", e)
            false
        }
    }

    private suspend fun markBatchForRetry(associations: List<AssociationEntity>, errorMessage: String) {
        associations.forEach { association ->
            if (association.retryCount < 3) {
                database.associationDao().incrementRetryCount(association.id, errorMessage)
                Log.d(TAG, "Marked association ${association.id} for retry (attempt ${association.retryCount + 1})")
            } else {
                Log.w(TAG, "Association ${association.id} exceeded max retry attempts")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "CloudSyncService destroyed")
        stopSync()
    }
}

/**
 * Monitor network connectivity and bandwidth
 */
class ConnectivityMonitor(private val context: android.content.Context) {

    private val TAG = "ConnectivityMonitor"

    fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE)
                as android.net.ConnectivityManager

        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities?.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected == true
        }
    }

    fun hasGoodBandwidth(): Boolean {
        // Simple heuristic - in production, you might want more sophisticated bandwidth testing
        val connectivityManager = context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE)
                as android.net.ConnectivityManager

        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities?.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_NOT_METERED) == true ||
                    capabilities?.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            true // Assume good bandwidth for older devices
        }
    }

    fun getConnectionType(): String {
        val connectivityManager = context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE)
                as android.net.ConnectivityManager

        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            when {
                capabilities?.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) == true -> "WiFi"
                capabilities?.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR) == true -> "Mobile"
                capabilities?.hasTransport(android.net.NetworkCapabilities.TRANSPORT_ETHERNET) == true -> "Ethernet"
                else -> "Unknown"
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo?.typeName ?: "Unknown"
        }
    }
}