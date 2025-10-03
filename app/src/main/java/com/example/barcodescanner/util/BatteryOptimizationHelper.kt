package com.example.barcodescanner.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

/**
 * Helper class to manage battery optimization settings
 * Helps ensure the app can run continuously without being killed by the system
 */
class BatteryOptimizationHelper(private val context: Context) {

    companion object {
        private const val REQUEST_CODE_BATTERY_OPTIMIZATION = 1000
    }

    /**
     * Check if battery optimization is disabled for this app
     */
    fun isBatteryOptimizationDisabled(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            powerManager.isIgnoringBatteryOptimizations(context.packageName)
        } else {
            true // Not applicable for older versions
        }
    }

    /**
     * Show dialog asking user to disable battery optimization
     */
    fun showBatteryOptimizationDialog(activity: Activity, onDismiss: (() -> Unit)? = null) {
        if (isBatteryOptimizationDisabled()) {
            onDismiss?.invoke()
            return
        }

        AlertDialog.Builder(activity)
            .setTitle("Battery Optimization")
            .setMessage(
                "For continuous barcode scanning, please disable battery optimization for this app. " +
                        "This prevents the system from putting the app to sleep.\n\n" +
                        "Steps:\n" +
                        "1. Tap 'Open Settings'\n" +
                        "2. Find '${activity.packageManager.getApplicationLabel(activity.applicationInfo)}'\n" +
                        "3. Select 'Don't optimize'\n" +
                        "4. Tap 'Done'"
            )
            .setPositiveButton("Open Settings") { _, _ ->
                openBatteryOptimizationSettings(activity)
            }
            .setNegativeButton("Skip") { _, _ ->
                onDismiss?.invoke()
            }
            .setCancelable(false)
            .show()
    }

    /**
     * Open battery optimization settings for this app
     */
    private fun openBatteryOptimizationSettings(activity: Activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                    data = Uri.parse("package:${context.packageName}")
                }
                activity.startActivityForResult(intent, REQUEST_CODE_BATTERY_OPTIMIZATION)
            }
        } catch (e: Exception) {
            // Fallback to general battery settings
            openGeneralBatterySettings(activity)
        }
    }

    /**
     * Open general battery optimization settings as fallback
     */
    private fun openGeneralBatterySettings(activity: Activity) {
        try {
            val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
            } else {
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:${context.packageName}")
                }
            }
            activity.startActivity(intent)
        } catch (e: Exception) {
            // Final fallback to app settings
            openAppSettings(activity)
        }
    }

    /**
     * Open app settings as final fallback
     */
    private fun openAppSettings(activity: Activity) {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            activity.startActivity(intent)
        } catch (e: Exception) {
            // Can't open settings, show manual instructions
            showManualInstructions(activity)
        }
    }

    /**
     * Show manual instructions as last resort
     */
    private fun showManualInstructions(activity: Activity) {
        AlertDialog.Builder(activity)
            .setTitle("Manual Setup Required")
            .setMessage(
                "Please manually configure battery settings:\n\n" +
                        "Samsung devices:\n" +
                        "Settings → Apps → [App Name] → Battery → Allow background activity\n\n" +
                        "General Android:\n" +
                        "Settings → Battery → Battery Optimization → [App Name] → Don't optimize"
            )
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }

    /**
     * Show Samsung-specific optimization dialog
     */
    fun showSamsungOptimizationDialog(activity: Activity) {
        AlertDialog.Builder(activity)
            .setTitle("Samsung Device Optimization")
            .setMessage(
                "For Samsung devices, additional steps may be needed:\n\n" +
                        "1. Settings → Apps → [App Name] → Battery\n" +
                        "   • Turn OFF 'Put app to sleep'\n" +
                        "   • Turn ON 'Allow background activity'\n\n" +
                        "2. Settings → Device care → Battery → More battery settings\n" +
                        "   • Turn OFF 'Adaptive battery'\n" +
                        "   • Turn OFF 'Put unused apps to sleep'\n\n" +
                        "3. Settings → Developer options\n" +
                        "   • Turn ON 'Stay awake' (keeps screen on while charging)"
            )
            .setPositiveButton("Open App Settings") { _, _ ->
                openAppSettings(activity)
            }
            .setNegativeButton("OK") { _, _ -> }
            .show()
    }

    /**
     * Check if device is Samsung
     */
    fun isSamsungDevice(): Boolean {
        return Build.MANUFACTURER.equals("samsung", ignoreCase = true)
    }

    /**
     * Show appropriate optimization dialog based on device
     */
    fun showOptimizationDialog(activity: Activity, onDismiss: (() -> Unit)? = null) {
        when {
            isSamsungDevice() -> showSamsungOptimizationDialog(activity)
            else -> showBatteryOptimizationDialog(activity, onDismiss)
        }
    }
}