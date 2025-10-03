package com.example.barcodescanner.utils

import android.content.Context
import android.os.PowerManager
import android.util.Log
import android.view.WindowManager

/**
 * Utility class to manage wake locks and screen management
 * Prevents device from sleeping during scanning operations
 */
class WakeLockManager(private val context: Context) {

    companion object {
        private const val TAG = "WakeLockManager"
        private const val WAKE_LOCK_TAG = "BarcodeScanner:WakeLock"
        private const val DEFAULT_TIMEOUT = 30 * 60 * 1000L // 30 minutes
    }

    private var wakeLock: PowerManager.WakeLock? = null
    private val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager

    /**
     * Acquire wake lock to prevent device from sleeping
     */
    fun acquireWakeLock(timeout: Long = DEFAULT_TIMEOUT, tag: String = WAKE_LOCK_TAG) {
        try {
            // Release existing wake lock if any
            releaseWakeLock()

            wakeLock = powerManager.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                tag
            )
            wakeLock?.acquire(timeout)
            Log.d(TAG, "Wake lock acquired with timeout: ${timeout}ms")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to acquire wake lock", e)
        }
    }

    /**
     * Acquire partial wake lock (CPU only, screen can turn off)
     */
    fun acquirePartialWakeLock(timeout: Long = DEFAULT_TIMEOUT, tag: String = "${WAKE_LOCK_TAG}_PARTIAL") {
        try {
            releaseWakeLock()

            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, tag)
            wakeLock?.acquire(timeout)
            Log.d(TAG, "Partial wake lock acquired with timeout: ${timeout}ms")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to acquire partial wake lock", e)
        }
    }

    /**
     * Release wake lock - COMPLETELY FIXED VERSION
     */
    fun releaseWakeLock() {
        val currentWakeLock = wakeLock
        if (currentWakeLock != null) {
            try {
                if (currentWakeLock.isHeld) {
                    currentWakeLock.release()
                    Log.d(TAG, "Wake lock released")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error releasing wake lock", e)
            }
        }
        wakeLock = null
    }

    /**
     * Check if wake lock is currently held
     */
    fun isWakeLockHeld(): Boolean {
        val currentWakeLock = wakeLock
        return if (currentWakeLock != null) {
            currentWakeLock.isHeld
        } else {
            false
        }
    }

    /**
     * Configure window to keep screen on
     */
    fun configureScreenOn(window: android.view.Window, keepOn: Boolean = true) {
        try {
            if (keepOn) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
                Log.d(TAG, "Screen keep-on flags added")
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                window.clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
                Log.d(TAG, "Screen keep-on flags removed")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error configuring screen flags", e)
        }
    }

    /**
     * Set screen brightness
     */
    fun setScreenBrightness(window: android.view.Window, brightness: Float): Float {
        return try {
            val layoutParams = window.attributes
            val originalBrightness = layoutParams.screenBrightness
            layoutParams.screenBrightness = brightness
            window.attributes = layoutParams
            Log.d(TAG, "Screen brightness set to: $brightness")
            originalBrightness
        } catch (e: Exception) {
            Log.e(TAG, "Error setting screen brightness", e)
            -1f
        }
    }

    /**
     * Get current battery optimization status
     */
    fun isBatteryOptimizationDisabled(): Boolean {
        return try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                powerManager.isIgnoringBatteryOptimizations(context.packageName)
            } else {
                true // Not applicable for older versions
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking battery optimization", e)
            false
        }
    }

    /**
     * Request to disable battery optimization for this app
     */
    fun requestDisableBatteryOptimization(): android.content.Intent? {
        return try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                val intent = android.content.Intent()
                intent.action = android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                intent.data = android.net.Uri.parse("package:${context.packageName}")
                Log.d(TAG, "Battery optimization disable intent created")
                intent
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creating battery optimization intent", e)
            null
        }
    }

    /**
     * Get wake lock info for debugging
     */
    fun getWakeLockInfo(): String {
        val currentWakeLock = wakeLock
        return if (currentWakeLock != null) {
            "Wake lock held: ${currentWakeLock.isHeld}"
        } else {
            "No wake lock"
        }
    }

    /**
     * Cleanup method - call this when done with the manager
     */
    fun cleanup() {
        releaseWakeLock()
        Log.d(TAG, "WakeLockManager cleaned up")
    }
}

/**
 * Extension function for activities to easily manage wake locks
 */
fun androidx.appcompat.app.AppCompatActivity.setupScreenManagement(keepScreenOn: Boolean = true): WakeLockManager {
    val wakeLockManager = WakeLockManager(this)

    if (keepScreenOn) {
        wakeLockManager.configureScreenOn(window, true)
        wakeLockManager.acquireWakeLock()
        wakeLockManager.setScreenBrightness(window, 1.0f)
    }

    return wakeLockManager
}