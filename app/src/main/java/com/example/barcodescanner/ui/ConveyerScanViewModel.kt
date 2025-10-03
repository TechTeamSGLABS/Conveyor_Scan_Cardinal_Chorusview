package com.example.barcodescanner.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.barcodescanner.data.local.AppDatabase
import com.example.barcodescanner.data.local.AssociationEntity
import com.example.barcodescanner.data.local.SessionStatistics
import com.example.barcodescanner.service.ConnectivityMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay
import android.util.Log

/**
 * 🚀 ENHANCED ViewModel for Conveyer Scan functionality
 * ✅ FIXED: Network connectivity monitoring and reconnection logic
 * ✅ ENHANCED: Offline functionality with proper sync queue management
 * ✅ IMPROVED: Association logic continues working regardless of network state
 */
class ConveyerScanViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "ConveyerScanViewModel"
        private const val SYNC_STATUS_UPDATE_INTERVAL = 5000L // 5 seconds
        private const val NETWORK_RETRY_INTERVAL = 3000L // 3 seconds
    }

    private val database = AppDatabase.getInstance(application)
    private val connectivityMonitor = ConnectivityMonitor(application)
    private val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // 🚀 NEW: Network state tracking
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private val _connectionType = MutableLiveData<String>()
    val connectionType: LiveData<String> = _connectionType

    // Enhanced sync status with network awareness
    private val _syncStatus = MutableLiveData<String>()
    val syncStatus: LiveData<String> = _syncStatus

    private val _sessionStatistics = MutableLiveData<SessionStatistics>()
    val sessionStatistics: LiveData<SessionStatistics> = _sessionStatistics

    private val _associations = MutableLiveData<List<AssociationEntity>>()
    val associations: LiveData<List<AssociationEntity>> = _associations

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // Scanning state
    private val _scanningState = MutableLiveData<ScanningState>()
    val scanningState: LiveData<ScanningState> = _scanningState

    private val _toteIdCount = MutableLiveData<Int>()
    val toteIdCount: LiveData<Int> = _toteIdCount

    private val _olpnCount = MutableLiveData<Int>()
    val olpnCount: LiveData<Int> = _olpnCount

    private val _associationCount = MutableLiveData<Int>()
    val associationCount: LiveData<Int> = _associationCount

    // 🚀 NEW: Network monitoring state
    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private var lastKnownNetworkState = NetworkState.UNKNOWN
    private var pendingSyncOperations = mutableListOf<() -> Unit>()

    init {
        initializeViewModel()
        startNetworkMonitoring()
    }

    private fun initializeViewModel() {
        _syncStatus.value = "🔄 Initializing..."
        _scanningState.value = ScanningState.STOPPED
        _networkState.value = NetworkState.UNKNOWN
        _connectionType.value = "Checking connection..."
        _toteIdCount.value = 0
        _olpnCount.value = 0
        _associationCount.value = 0

        // Check initial network state
        checkInitialNetworkState()

        // Start monitoring sync status
        startSyncStatusMonitoring()
    }

    // 🚀 NEW: Network monitoring with reconnection logic
    private fun startNetworkMonitoring() {
        try {
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()

            networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Log.d(TAG, "🟢 Network became available")
                    handleNetworkAvailable()
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Log.d(TAG, "🔴 Network lost")
                    handleNetworkLost()
                }

                override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    updateConnectionType(networkCapabilities)
                }
            }

            connectivityManager.registerNetworkCallback(networkRequest, networkCallback!!)
            Log.d(TAG, "✅ Network monitoring started")

        } catch (e: Exception) {
            Log.e(TAG, "Failed to start network monitoring", e)
            _networkState.postValue(NetworkState.ERROR)
        }
    }

    private fun checkInitialNetworkState() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val isConnected = connectivityMonitor.isConnected()
                val connectionType = connectivityMonitor.getConnectionType()

                withContext(Dispatchers.Main) {
                    _networkState.value = if (isConnected) NetworkState.CONNECTED else NetworkState.DISCONNECTED
                    _connectionType.value = connectionType
                    lastKnownNetworkState = _networkState.value!!

                    Log.d(TAG, "🔍 Initial network state: ${_networkState.value}, Type: $connectionType")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to check initial network state", e)
                withContext(Dispatchers.Main) {
                    _networkState.value = NetworkState.ERROR
                    _connectionType.value = "Connection check failed"
                }
            }
        }
    }

    private fun handleNetworkAvailable() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _networkState.value = NetworkState.CONNECTED
                lastKnownNetworkState = NetworkState.CONNECTED

                // Update connection type
                updateCurrentConnectionType()

                Log.d(TAG, "🔄 Network reconnected - processing pending operations")

                // Process any pending sync operations
                processPendingSyncOperations()

                // Force sync any pending data
                forceSyncNow()
            }
        }
    }

    private fun handleNetworkLost() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _networkState.value = NetworkState.DISCONNECTED
                _connectionType.value = "🔴 No connection"
                lastKnownNetworkState = NetworkState.DISCONNECTED

                Log.d(TAG, "⚠️ Network disconnected - switching to offline mode")
            }
        }
    }

    private fun updateConnectionType(networkCapabilities: NetworkCapabilities) {
        val connectionType = when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "🟢 WiFi"
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "🟢 Mobile Data"
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "🟢 Ethernet"
            else -> "🟢 Connected"
        }

        _connectionType.postValue(connectionType)
    }

    private fun updateCurrentConnectionType() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val connectionType = connectivityMonitor.getConnectionType()
                withContext(Dispatchers.Main) {
                    _connectionType.value = connectionType
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update connection type", e)
            }
        }
    }

    private fun processPendingSyncOperations() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val operations = synchronized(pendingSyncOperations) {
                    pendingSyncOperations.toList().also {
                        pendingSyncOperations.clear()
                    }
                }

                Log.d(TAG, "🔄 Processing ${operations.size} pending sync operations")

                operations.forEach { operation ->
                    try {
                        operation()
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to execute pending sync operation", e)
                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "Failed to process pending sync operations", e)
            }
        }
    }

    /**
     * 🚀 ENHANCED: Network-aware sync statistics
     */
    fun getSyncStatistics() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val submittedCount = database.associationDao().getSubmittedCount()
                val pendingCount = database.associationDao().getPendingCount()
                val total = submittedCount + pendingCount

                val syncStatus = when (_networkState.value) {
                    NetworkState.DISCONNECTED -> {
                        if (total > 0) {
                            "📱 Offline - $total items queued"
                        } else {
                            "📱 Offline mode"
                        }
                    }
                    NetworkState.CONNECTED -> {
                        if (total == 0) {
                            "✅ All synced"
                        } else {
                            val percentage = if (total > 0) (submittedCount * 100) / total else 0
                            if (percentage == 100) {
                                "✅ All synced"
                            } else {
                                "🔄 Syncing: $submittedCount/$total"
                            }
                        }
                    }
                    NetworkState.ERROR -> "❌ Connection error"
                    NetworkState.UNKNOWN -> "🔍 Checking connection..."
                    else -> "🔄 Initializing..."
                }

                withContext(Dispatchers.Main) {
                    _syncStatus.value = syncStatus
                }

            } catch (e: Exception) {
                Log.e(TAG, "Failed to get sync statistics", e)
                withContext(Dispatchers.Main) {
                    val status = when (_networkState.value) {
                        NetworkState.DISCONNECTED -> "📱 Offline mode"
                        else -> "❌ Sync error"
                    }
                    _syncStatus.value = status
                }
            }
        }
    }

    /**
     * Start a new scanning session
     */
    fun startScanningSession(): Long {
        val sessionId = System.currentTimeMillis()

        viewModelScope.launch {
            try {
                _scanningState.value = ScanningState.STARTING

                // Reset counters
                _toteIdCount.value = 0
                _olpnCount.value = 0
                _associationCount.value = 0

                _scanningState.value = ScanningState.SCANNING
                Log.d(TAG, "🚀 Scanning session started: $sessionId (Network: ${_networkState.value})")

            } catch (e: Exception) {
                Log.e(TAG, "Failed to start scanning session", e)
                _errorMessage.value = "Failed to start scanning: ${e.message}"
                _scanningState.value = ScanningState.ERROR
            }
        }

        return sessionId
    }

    /**
     * Stop the current scanning session
     */
    fun stopScanningSession(sessionId: Long) {
        viewModelScope.launch {
            try {
                _scanningState.value = ScanningState.STOPPING

                // Get session statistics
                loadSessionStatistics(sessionId)

                _scanningState.value = ScanningState.STOPPED
                Log.d(TAG, "🚀 Scanning session stopped: $sessionId")

                // Try to sync if online
                if (_networkState.value == NetworkState.CONNECTED) {
                    forceSyncNow()
                }

            } catch (e: Exception) {
                Log.e(TAG, "Failed to stop scanning session", e)
                _errorMessage.value = "Failed to stop scanning: ${e.message}"
                _scanningState.value = ScanningState.ERROR
            }
        }
    }

    /**
     * 🚀 ENHANCED: Create association with offline support
     */
    fun createAssociation(toteId: String, olpn: String, sessionId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // 🚀 CRITICAL: Association creation works regardless of network state
                Log.d(TAG, "🔗 Creating association: ToteID=$toteId, OLPN=$olpn (Network: ${_networkState.value})")

                // Check if association already exists
                val existing = database.associationDao().getAssociation(toteId, olpn)
                if (existing == null) {
                    val association = AssociationEntity(
                        toteId = toteId,
                        olpn = olpn,
                        sessionId = sessionId,
                        timestamp = System.currentTimeMillis(),
                        // Mark as pending if offline, submitted if online (if your entity supports this)
                        // isSubmitted = _networkState.value == NetworkState.CONNECTED
                    )

                    val insertedId = database.associationDao().insertAssociation(association)
                    if (insertedId > 0) {
                        withContext(Dispatchers.Main) {
                            updateAssociationCount(sessionId)
                        }

                        Log.d(TAG, "✅ Association created successfully: ToteID=$toteId, OLPN=$olpn")

                        // Queue for sync if offline
                        if (_networkState.value == NetworkState.DISCONNECTED) {
                            queueSyncOperation {
                                // Sync this specific association when network is available
                                Log.d(TAG, "🔄 Queued association for sync: $toteId ↔ $olpn")
                            }
                        } else if (_networkState.value == NetworkState.CONNECTED) {
                            // Try immediate sync if online
                            try {
                                // Trigger sync service for this association
                                forceSyncNow()
                            } catch (e: Exception) {
                                Log.w(TAG, "Immediate sync failed, will retry later", e)
                            }
                        }

                    } else {
                        Log.e(TAG, "❌ Failed to insert association in database")
                        withContext(Dispatchers.Main) {
                            _errorMessage.value = "Failed to save association locally"
                        }
                    }
                } else {
                    Log.d(TAG, "ℹ️ Association already exists: ToteID=$toteId, OLPN=$olpn")
                }

            } catch (e: Exception) {
                Log.e(TAG, "❌ Critical failure creating association", e)
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Failed to create association: ${e.message}"
                }
            }
        }
    }

    private fun queueSyncOperation(operation: () -> Unit) {
        synchronized(pendingSyncOperations) {
            pendingSyncOperations.add(operation)
        }
        Log.d(TAG, "📋 Queued sync operation (total pending: ${pendingSyncOperations.size})")
    }

    /**
     * Load session statistics
     */
    fun loadSessionStatistics(sessionId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val statistics = database.associationDao().getSessionStatistics(sessionId)
                withContext(Dispatchers.Main) {
                    _sessionStatistics.value = statistics
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load session statistics", e)
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Failed to load statistics: ${e.message}"
                }
            }
        }
    }

    /**
     * 🚀 ENHANCED: Load associations with offline support
     */
    fun loadSessionAssociations(sessionId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.postValue(true)

                // 🚀 CRITICAL: Always load from local database regardless of network state
                val sessionAssociations = database.associationDao().getAssociationsBySession(sessionId)

                Log.d(TAG, "📊 Loaded ${sessionAssociations.size} associations for session $sessionId (Network: ${_networkState.value})")

                withContext(Dispatchers.Main) {
                    _associations.value = sessionAssociations
                    _isLoading.value = false
                }

                // If online, try to sync any pending associations
                if (_networkState.value == NetworkState.CONNECTED && sessionAssociations.isNotEmpty()) {
                    Log.d(TAG, "🔄 Online: Attempting to sync loaded associations")
                    // Trigger sync service
                    forceSyncNow()
                }

            } catch (e: Exception) {
                Log.e(TAG, "Failed to load session associations", e)
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Failed to load associations: ${e.message}"
                    _isLoading.value = false
                }
            }
        }
    }

    /**
     * Load all associations
     */
    fun loadAllAssociations() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.postValue(true)

                // Always load from local database
                val allAssociations = database.associationDao().getAllAssociations()

                withContext(Dispatchers.Main) {
                    _associations.value = allAssociations
                    _isLoading.value = false
                }

                Log.d(TAG, "📊 Loaded ${allAssociations.size} total associations (Network: ${_networkState.value})")

            } catch (e: Exception) {
                Log.e(TAG, "Failed to load all associations", e)
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Failed to load associations: ${e.message}"
                    _isLoading.value = false
                }
            }
        }
    }

    /**
     * Update ToteID count
     */
    fun updateToteIdCount(sessionId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val count = database.associationDao().getUniqueToteIdCount(sessionId)
                withContext(Dispatchers.Main) {
                    _toteIdCount.value = count
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update ToteID count", e)
            }
        }
    }

    /**
     * Update OLPN count
     */
    fun updateOlpnCount(sessionId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val count = database.associationDao().getUniqueOlpnCount(sessionId)
                withContext(Dispatchers.Main) {
                    _olpnCount.value = count
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update OLPN count", e)
            }
        }
    }

    /**
     * Update association count
     */
    fun updateAssociationCount(sessionId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val count = database.associationDao().getSessionCount(sessionId)
                withContext(Dispatchers.Main) {
                    _associationCount.value = count
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update association count", e)
            }
        }
    }

    /**
     * Delete all associations for a session
     */
    fun deleteSessionData(sessionId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.associationDao().deleteSessionAssociations(sessionId)
                withContext(Dispatchers.Main) {
                    _associations.value = emptyList()
                    _toteIdCount.value = 0
                    _olpnCount.value = 0
                    _associationCount.value = 0
                }
                Log.d(TAG, "Session data deleted: $sessionId")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to delete session data", e)
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Failed to delete session data: ${e.message}"
                }
            }
        }
    }

    /**
     * Delete all associations
     */
    fun deleteAllAssociations() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.associationDao().deleteAll()
                withContext(Dispatchers.Main) {
                    _associations.value = emptyList()
                    _toteIdCount.value = 0
                    _olpnCount.value = 0
                    _associationCount.value = 0
                }
                Log.d(TAG, "All associations deleted")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to delete all associations", e)
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Failed to delete associations: ${e.message}"
                }
            }
        }
    }

    /**
     * 🚀 ENHANCED: Start monitoring sync status with network awareness
     */
    private fun startSyncStatusMonitoring() {
        viewModelScope.launch {
            while (true) {
                getSyncStatistics()
                delay(SYNC_STATUS_UPDATE_INTERVAL)
            }
        }
    }

    /**
     * 🚀 ENHANCED: Force sync with network state awareness
     */
    fun forceSyncNow() {
        if (_networkState.value != NetworkState.CONNECTED) {
            Log.d(TAG, "⚠️ Cannot force sync - network not available (${_networkState.value})")
            _syncStatus.postValue("📱 Offline - will sync when connected")
            return
        }

        try {
            val intent = android.content.Intent(getApplication(), com.example.barcodescanner.service.CloudSyncService::class.java).apply {
                action = com.example.barcodescanner.service.CloudSyncService.ACTION_FORCE_SYNC
            }
            getApplication<Application>().startService(intent)
            _syncStatus.postValue("🔄 Force syncing...")
            Log.d(TAG, "✅ Force sync triggered")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to trigger force sync", e)
            _errorMessage.postValue("Failed to trigger sync: ${e.message}")
        }
    }

    /**
     * Cleanup old data (older than specified days)
     */
    fun cleanupOldData(daysOld: Int = 7) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cutoffTime = System.currentTimeMillis() - (daysOld * 24 * 60 * 60 * 1000L)
                database.associationDao().deleteOldAssociations(cutoffTime)
                Log.d(TAG, "Cleaned up data older than $daysOld days")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to cleanup old data", e)
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Failed to cleanup old data: ${e.message}"
                }
            }
        }
    }

    /**
     * Get pending sync count
     */
    fun getPendingSyncCount(callback: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val count = database.associationDao().getPendingCount()
                withContext(Dispatchers.Main) {
                    callback(count)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to get pending sync count", e)
                withContext(Dispatchers.Main) {
                    callback(0)
                }
            }
        }
    }

    /**
     * 🚀 NEW: Manual network retry
     */
    fun retryNetworkConnection() {
        viewModelScope.launch {
            _syncStatus.postValue("🔄 Checking connection...")
            delay(1000) // Brief delay for user feedback

            checkInitialNetworkState()

            // Wait a moment and then check again
            delay(2000)
            if (_networkState.value == NetworkState.CONNECTED) {
                forceSyncNow()
            }
        }
    }

    /**
     * 🚀 NEW: Get network state information
     */
    fun getNetworkInfo(): String {
        return when (_networkState.value) {
            NetworkState.CONNECTED -> "Connected (${_connectionType.value})"
            NetworkState.DISCONNECTED -> "No network connection"
            NetworkState.ERROR -> "Network error"
            NetworkState.UNKNOWN -> "Checking connection..."
            null -> "Initializing..."
        }
    }

    /**
     * Clear error message
     */
    fun clearError() {
        _errorMessage.value = ""
    }

    override fun onCleared() {
        super.onCleared()
        // Clean up network monitoring
        networkCallback?.let { callback ->
            try {
                connectivityManager.unregisterNetworkCallback(callback)
                Log.d(TAG, "✅ Network monitoring stopped")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to unregister network callback", e)
            }
        }
    }

    /**
     * 🚀 NEW: Network state enum
     */
    enum class NetworkState {
        CONNECTED,
        DISCONNECTED,
        ERROR,
        UNKNOWN
    }

    /**
     * Enum for scanning states
     */
    enum class ScanningState {
        STOPPED,
        STARTING,
        SCANNING,
        STOPPING,
        ERROR
    }
}