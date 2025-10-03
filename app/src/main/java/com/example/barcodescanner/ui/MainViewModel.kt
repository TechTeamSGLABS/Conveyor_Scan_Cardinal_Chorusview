package com.example.barcodescanner.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.barcodescanner.data.local.AppDatabase
import com.example.barcodescanner.data.local.BarcodeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log

/**
 * ViewModel for MainActivity
 * Manages barcode scanning state and database operations
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val database = AppDatabase.getInstance(application)
    private var currentSessionId = System.currentTimeMillis()

    // LiveData for UI observations
    private val _barcodeCount = MutableLiveData<Int>()
    val barcodeCount: LiveData<Int> = _barcodeCount

    private val _isScanning = MutableLiveData<Boolean>()
    val isScanning: LiveData<Boolean> = _isScanning

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _lastScannedBarcode = MutableLiveData<String>()
    val lastScannedBarcode: LiveData<String> = _lastScannedBarcode

    init {
        _barcodeCount.value = 0
        _isScanning.value = false
        _errorMessage.value = ""
        updateBarcodeCount()
    }

    /**
     * Add a new barcode to the database
     */
    fun addBarcode(value: String, format: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Check if barcode already exists in current session
                val existingBarcode = database.barcodeDao().getBarcodeByValueAndSession(value, currentSessionId)

                if (existingBarcode == null) {
                    val barcode = BarcodeEntity(
                        value = value,
                        format = format,
                        timestamp = System.currentTimeMillis(),
                        sessionId = currentSessionId
                    )

                    val insertedId = database.barcodeDao().insertBarcode(barcode)
                    if (insertedId > 0) {
                        withContext(Dispatchers.Main) {
                            _lastScannedBarcode.value = value
                            updateBarcodeCount()
                        }
                        Log.d(TAG, "Barcode added: $value")
                    }
                } else {
                    Log.d(TAG, "Barcode already exists: $value")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to add barcode", e)
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Failed to save barcode: ${e.message}"
                }
            }
        }
    }

    /**
     * Update barcode count from database
     */
    private fun updateBarcodeCount() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val count = database.barcodeDao().getSessionBarcodeCount(currentSessionId)
                withContext(Dispatchers.Main) {
                    _barcodeCount.value = count
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update barcode count", e)
            }
        }
    }

    /**
     * Start scanning session
     */
    fun startScanning() {
        _isScanning.value = true
        currentSessionId = System.currentTimeMillis()
        _barcodeCount.value = 0
        Log.d(TAG, "Scanning started, session: $currentSessionId")
    }

    /**
     * Stop scanning session
     */
    fun stopScanning() {
        _isScanning.value = false
        Log.d(TAG, "Scanning stopped")
    }

    /**
     * Get current session ID
     */
    fun getCurrentSessionId(): Long {
        return currentSessionId
    }

    /**
     * Clear error message
     */
    fun clearError() {
        _errorMessage.value = ""
    }

    /**
     * Get total barcode count across all sessions
     */
    fun getTotalBarcodeCount(callback: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val count = database.barcodeDao().getTotalBarcodeCount()
                withContext(Dispatchers.Main) {
                    callback(count)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to get total barcode count", e)
                withContext(Dispatchers.Main) {
                    callback(0)
                }
            }
        }
    }

    /**
     * Delete all barcodes for current session
     */
    fun clearCurrentSession() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.barcodeDao().deleteSessionBarcodes(currentSessionId)
                withContext(Dispatchers.Main) {
                    _barcodeCount.value = 0
                }
                Log.d(TAG, "Current session cleared")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to clear current session", e)
                withContext(Dispatchers.Main) {
                    _errorMessage.value = "Failed to clear session: ${e.message}"
                }
            }
        }
    }
}