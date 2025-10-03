package com.example.barcodescanner.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcodescanner.data.local.AppDatabase
import com.example.barcodescanner.data.local.BarcodeEntity
import com.example.barcodescanner.data.remote.ChorusApiService
import com.example.barcodescanner.data.remote.BarcodePayload
import kotlinx.coroutines.launch
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScanViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val dao = db.barcodeDao()

    // Use ChorusApiService instead of direct API calls
    private val chorusApiService = ChorusApiService()

    fun onBarcodeScanned(value: String, format: Int, sessionId: Long) {
        viewModelScope.launch {
            try {
                if (value.isBlank() || sessionId == 0L) {
                    Log.e("ScanViewModel", "Invalid barcode or sessionId. Skipping insert.")
                    return@launch
                }

                // ✅ Check for uniqueness within the session only
                val alreadyScanned = withContext(Dispatchers.IO) {
                    dao.getBarcodeByValueAndSession(value, sessionId)
                }

                if (alreadyScanned != null) {
                    Log.d("ScanViewModel", "Barcode '$value' already exists in session $sessionId, skipping insert")
                    return@launch
                }

                val timestamp = System.currentTimeMillis()
                val barcode = BarcodeEntity(
                    value = value,
                    format = format,
                    timestamp = timestamp,
                    sessionId = sessionId
                )

                val id = withContext(Dispatchers.IO) {
                    dao.insertBarcode(barcode)
                }

                if (id != -1L) {
                    Log.d("ScanViewModel", "Successfully inserted barcode: $value")
                    try {
                        // Use ChorusApiService for posting barcode data
                        val barcodePayload = BarcodePayload(
                            value = value,
                            format = format.toString(), // Convert Int to String
                            timestamp = timestamp
                        )

                        val result = withContext(Dispatchers.IO) {
                            chorusApiService.postBarcode(barcodePayload)
                        }

                        if (result.isSuccess) {
                            withContext(Dispatchers.IO) {
                                dao.updateSyncStatus(id.toInt(), true)
                            }
                            Log.d("ScanViewModel", "Successfully synced barcode to server: ${result.getOrNull()}")
                        } else {
                            Log.e("ScanViewModel", "Failed to sync barcode: ${result.exceptionOrNull()?.message}")
                        }
                    } catch (e: Exception) {
                        Log.e("ScanViewModel", "Network sync failed", e)
                    }
                } else {
                    Log.w("ScanViewModel", "Failed to insert barcode (duplicate constraint)")
                }

            } catch (e: Exception) {
                Log.e("ScanViewModel", "DB insert failed", e)
            }
        }
    }
}