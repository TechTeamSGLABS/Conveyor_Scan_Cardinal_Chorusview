package com.example.barcodescanner.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PointF
import android.graphics.RectF
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.util.Size
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.barcodescanner.R
import com.example.barcodescanner.data.local.AppDatabase
import com.example.barcodescanner.data.local.AssociationEntity
import com.example.barcodescanner.model.DetectedBarcode
import com.example.barcodescanner.service.CloudSyncService
import com.example.barcodescanner.utils.BarcodeFormatReference
import com.example.barcodescanner.utils.MovingBarcodeAssociationManager
import com.example.barcodescanner.view.OverlayView
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.math.max
import kotlin.math.min

/**
 * 🚀 COMPLETE ENHANCED Conveyer Scan Activity - The Ultimate Solution!
 *
 * ✅ NETWORK-AWARE: Full offline/online functionality with automatic reconnection
 * ✅ MOVING BARCODES: Advanced 5-strategy association system for conveyor scanning
 * ✅ PERFORMANCE OPTIMIZED: 4K resolution with enhanced processing
 * ✅ ROBUST OFFLINE: Works completely offline with sync when connected
 * ✅ SMART ASSOCIATIONS: Handles timing gaps, spatial relationships, and movement prediction
 * ✅ COMPREHENSIVE LOGGING: Detailed CSV logs with association strategy tracking
 * ✅ BARCODE DATA CLEANING: Removes ]C1 prefix from 1D barcodes before backend processing
 * ✅ SCREEN LOCK PREVENTION: Keeps device awake during active scanning sessions
 */
class ConveyerScanActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ConveyerScanActivity"
        private const val CAMERA_PERMISSION_REQUEST = 1001

        // 🚀 OPTIMIZED: Proper timeout values for moving barcodes
        private const val BARCODE_TIMEOUT_MS = 3000L // 3 seconds for UI persistence
        private const val STATS_UPDATE_INTERVAL_MS = 5000L // Update stats every 5 seconds
        private const val ASSOCIATION_FEEDBACK_ENABLED = true // Enhanced user feedback

        // 🆕 BARCODE DATA CLEANING: Constants for data preprocessing
        private const val BARCODE_PREFIX_C1 = "]C1"
    }

    // UI Components
    private lateinit var previewView: PreviewView
    private lateinit var overlayView: OverlayView
    private lateinit var startStopButton: Button
    private lateinit var statusTextView: TextView
    private lateinit var zoomSeekBar: SeekBar
    private lateinit var connectionStatusTextView: TextView
    private lateinit var syncStatusTextView: TextView
    private lateinit var resolutionButton: Button

    // 🚀 CAMERA: Enhanced camera components
    private var camera: Camera? = null
    private lateinit var cameraExecutor: ExecutorService
    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var currentZoomRatio = 1f
    private var maxZoomRatio = 10f
    private var useHighResolution = true

    // Enhanced zoom persistence
    private val PREFS_NAME = "ConveyerScanner"
    private val ZOOM_PREF_KEY = "last_zoom_ratio"
    private var hasAppliedSavedZoom = false

    // Enhanced Feedback Components
    private lateinit var vibrator: Vibrator

    // 🆕 SCREEN MANAGEMENT: Screen lock prevention components
    private lateinit var powerManager: PowerManager
    private var wakeLock: PowerManager.WakeLock? = null
    private var originalScreenTimeout: Int = 0
    private var isScreenManagementActive = false

    // 🚀 SCANNING STATE: Core scanning variables
    private var isScanning = false
    private var sessionId: Long = 0L
    private val detectedBarcodes = mutableMapOf<String, DetectedBarcode>()
    private val scannedSet = mutableSetOf<String>() // Track scanned barcodes for immediate UI
    private val barcodeLastSeen = mutableMapOf<String, Long>() // For persistent tick marks
    private val recentToteIds = mutableSetOf<String>()
    private val recentOlpns = mutableSetOf<String>()

    // 🚀 LEGACY TRACKING: Keep for fallback support
    private val associatedToteIds = mutableSetOf<String>()
    private val associatedOlpns = mutableSetOf<String>()
    private val pendingToteIds = mutableMapOf<String, Long>() // toteId -> detection time
    private val pendingOlpns = mutableMapOf<String, Long>() // olpn -> detection time
    private val missingScanTimeout = 10000L // 10 seconds timeout for missing scans

    // CSV logging variables
    private lateinit var scanLogFile: File
    private var csvRowCounter = 1

    // 🚀 PERFORMANCE: Monitoring variables
    private var lastFrameTime = 0L
    private var frameCount = 0
    private var averageFps = 0.0

    // Counters
    private var toteIdCount = 0
    private var olpnCount = 0
    private var associationCount = 0

    // 🚀 NETWORK: Network state tracking
    private var wasOffline = false
    private var currentNetworkState: ConveyerScanViewModel.NetworkState? = null

    // 🚀 MOVING BARCODES: Advanced association manager
    private lateinit var movingBarcodeManager: MovingBarcodeAssociationManager
    private var associationSuccessRate = 0f
    private var lastStatsUpdate = 0L

    // Database and enhanced ViewModel
    private lateinit var database: AppDatabase
    private val conveyerViewModel: ConveyerScanViewModel by viewModels()

    // 🆕 BARCODE DATA CLEANING: Helper functions for preprocessing barcode data

    /**
     * Cleans barcode data by removing common prefixes that should not be sent to backend
     * Currently handles the ]C1 prefix for 1D barcodes
     *
     * @param rawValue The raw barcode value from scanner
     * @param format The barcode format type
     * @return Cleaned barcode value ready for backend processing
     */
    private fun cleanBarcodeData(rawValue: String, format: Int): String {
        var cleanedValue = rawValue

        // Check if this is a 1D barcode format
        val is1DBarcode = when (format) {
            Barcode.FORMAT_CODE_128,
            Barcode.FORMAT_CODE_39,
            Barcode.FORMAT_CODE_93,
            Barcode.FORMAT_CODABAR,
            Barcode.FORMAT_EAN_13,
            Barcode.FORMAT_EAN_8,
            Barcode.FORMAT_UPC_A,
            Barcode.FORMAT_UPC_E,
            Barcode.FORMAT_ITF -> true
            else -> false
        }

        // Apply cleaning rules for 1D barcodes
        if (is1DBarcode) {
            // Remove ]C1 prefix if present
            if (cleanedValue.startsWith(BARCODE_PREFIX_C1)) {
                val originalValue = cleanedValue
                cleanedValue = cleanedValue.removePrefix(BARCODE_PREFIX_C1)
                Log.d(TAG, "🧹 CLEANED BARCODE DATA:")
                Log.d(TAG, "   Original: '$originalValue'")
                Log.d(TAG, "   Cleaned:  '$cleanedValue'")
                Log.d(TAG, "   Format:   ${getBarcodeFormatName(format)} (1D)")
            }

            // Add additional cleaning rules here if needed in the future
            // Example: cleanedValue = cleanedValue.removePrefix("]C0")
        }

        return cleanedValue
    }

    /**
     * Validates that the cleaned barcode data is acceptable
     *
     * @param cleanedValue The cleaned barcode value
     * @param originalValue The original barcode value
     * @return true if the cleaned value is valid, false otherwise
     */
    private fun isValidCleanedBarcodeData(cleanedValue: String, originalValue: String): Boolean {
        // Basic validation rules
        if (cleanedValue.isEmpty()) {
            Log.w(TAG, "⚠️ BARCODE CLEANING WARNING: Cleaned value is empty")
            Log.w(TAG, "   Original: '$originalValue'")
            return false
        }

        // Ensure we didn't remove too much data (sanity check)
        if (cleanedValue.length < originalValue.length * 0.5) {
            Log.w(TAG, "⚠️ BARCODE CLEANING WARNING: Cleaned value too short")
            Log.w(TAG, "   Original: '$originalValue' (${originalValue.length} chars)")
            Log.w(TAG, "   Cleaned:  '$cleanedValue' (${cleanedValue.length} chars)")
            return false
        }

        return true
    }

    // 🆕 SCREEN MANAGEMENT: Screen lock prevention functions

    /**
     * Initialize screen management components for keeping device awake during scanning
     */
    private fun initializeScreenManagement() {
        try {
            // Initialize PowerManager for wake lock
            powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager

            // Store original screen timeout setting
            originalScreenTimeout = try {
                android.provider.Settings.System.getInt(
                    contentResolver,
                    android.provider.Settings.System.SCREEN_OFF_TIMEOUT
                )
            } catch (e: Exception) {
                15000 // Default 15 seconds if can't read
            }

            Log.d(TAG, "📱 Screen management initialized - Original timeout: ${originalScreenTimeout}ms")

        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize screen management", e)
        }
    }

    /**
     * Enable screen keep-awake during scanning to prevent device lock
     */
    private fun enableScreenKeepAwake() {
        try {
            if (isScreenManagementActive) {
                Log.d(TAG, "📱 Screen keep-awake already active")
                return
            }

            // Method 1: Window flags to keep screen on
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

            // Method 2: Wake lock as backup
            if (wakeLock?.isHeld != true) {
                wakeLock = powerManager.newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                    "ConveyerScanner::ScreenAwake"
                ).apply {
                    acquire(10 * 60 * 1000L) // 10 minutes max
                }
                Log.d(TAG, "🔒 Wake lock acquired for scanning session")
            }

            // Method 3: Set screen brightness to ensure visibility
            val layoutParams = window.attributes
            layoutParams.screenBrightness = 0.8f // 80% brightness
            window.attributes = layoutParams

            isScreenManagementActive = true

            Log.d(TAG, "📱✅ SCREEN KEEP-AWAKE ENABLED for scanning session: $sessionId")

            // Show user feedback
            runOnUiThread {
                Toast.makeText(this, "📱 Screen lock disabled during scanning", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Log.e(TAG, "❌ Failed to enable screen keep-awake", e)
            // Fallback - at least try window flag
            try {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            } catch (ex: Exception) {
                Log.e(TAG, "❌ Fallback screen keep-awake also failed", ex)
            }
        }
    }

    /**
     * Disable screen keep-awake and restore normal screen behavior
     */
    private fun disableScreenKeepAwake() {
        try {
            if (!isScreenManagementActive) {
                Log.d(TAG, "📱 Screen keep-awake already disabled")
                return
            }

            // Remove window flags
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

            // Release wake lock
            wakeLock?.let { lock ->
                if (lock.isHeld) {
                    lock.release()
                    Log.d(TAG, "🔓 Wake lock released")
                }
            }
            wakeLock = null

            // Restore normal screen brightness
            val layoutParams = window.attributes
            layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
            window.attributes = layoutParams

            isScreenManagementActive = false

            Log.d(TAG, "📱✅ SCREEN KEEP-AWAKE DISABLED - Normal screen behavior restored")

            // Show user feedback
            runOnUiThread {
                Toast.makeText(this, "📱 Screen lock re-enabled", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Log.e(TAG, "❌ Failed to disable screen keep-awake", e)
        }
    }

    /**
     * Monitor screen state during scanning and reacquire locks if needed
     */
    private fun monitorScreenStateDuringScanning() {
        lifecycleScope.launch {
            while (isScanning && isScreenManagementActive) {
                try {
                    // Check if screen is actually on
                    val isScreenOn = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                        powerManager.isInteractive
                    } else {
                        @Suppress("DEPRECATION")
                        powerManager.isScreenOn
                    }

                    if (!isScreenOn) {
                        Log.w(TAG, "⚠️ Screen turned off during scanning - attempting to wake")
                        // Try to re-enable screen
                        enableScreenKeepAwake()
                    }

                    // Check wake lock status
                    if (wakeLock?.isHeld != true && isScanning) {
                        Log.w(TAG, "⚠️ Wake lock lost during scanning - reacquiring")
                        enableScreenKeepAwake()
                    }

                } catch (e: Exception) {
                    Log.e(TAG, "Error monitoring screen state", e)
                }

                delay(5000) // Check every 5 seconds
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conveyer_scan)

        // Setup action bar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Conveyer Scan"
            subtitle = "Network + Moving Barcodes + Data Cleaning + Screen Management"
        }

        // Reset zoom application flag
        hasAppliedSavedZoom = false

        initializeComponents()

        // 🆕 CRITICAL: Initialize screen management
        initializeScreenManagement()

        setupUI()
        setupCamera()
        startCloudSyncService()

        // 🚀 ENHANCED: Complete integration setup
        setupEnhancedNetworkIntegration()
        initializeMovingBarcodeManager()

        // Load saved zoom level
        loadSavedZoomLevel()

        // Debug format mappings
        debugFormatMappings()

        // Test format detection
        testFormatDetection()

        // Initialize file logging system
        initializeSessionLogging()

        // 🚀 START: Performance monitoring
        startPerformanceMonitoring()

        // 🆕 Test barcode data cleaning
        testBarcodeDataCleaning()
    }

    // 🆕 BARCODE DATA CLEANING: Test function to verify cleaning functionality
    private fun testBarcodeDataCleaning() {
        Log.d(TAG, "=== TESTING BARCODE DATA CLEANING ===")

        // Test cases for ]C1 prefix removal
        val testCases = listOf(
            "]C100400962958743356665" to "00400962958743356665",
            "]C1123456789" to "123456789",
            "00400962958743356665" to "00400962958743356665", // No prefix
            "]C1" to "", // Edge case - only prefix
            "" to "" // Edge case - empty string
        )

        testCases.forEach { (input, expected) ->
            val cleaned = cleanBarcodeData(input, Barcode.FORMAT_CODE_128)
            val isValid = isValidCleanedBarcodeData(cleaned, input)
            Log.d(TAG, "   Input: '$input' -> Cleaned: '$cleaned' (Expected: '$expected') Valid: $isValid")

            if (cleaned != expected) {
                Log.w(TAG, "   ⚠️ Test case failed!")
            }
        }

        Log.d(TAG, "=== END BARCODE CLEANING TEST ===")
    }

    // 🚀 NETWORK: Enhanced network-aware initialization
    private fun setupEnhancedNetworkIntegration() {
        initializeNetworkAwareComponents()
        observeSyncStatusEnhanced()
        Log.d(TAG, "🚀 Enhanced network integration setup complete")
    }

    // 🚀 NETWORK: Initialize network-aware components
    private fun initializeNetworkAwareComponents() {
        // Observe network state changes
        conveyerViewModel.networkState.observe(this) { networkState ->
            handleNetworkStateChange(networkState)
        }

        // Observe connection type changes
        conveyerViewModel.connectionType.observe(this) { connectionType ->
            updateConnectionStatusDisplay(connectionType)
        }

        // Enhanced sync status observation
        conveyerViewModel.syncStatus.observe(this) { status ->
            updateSyncStatusDisplay(status)
        }

        // Observe association count from ViewModel
        conveyerViewModel.associationCount.observe(this) { count ->
            // Update local counter to match ViewModel
            associationCount = count
            updateCounterDisplays()
        }

        Log.d(TAG, "🚀 Network-aware components initialized")
    }

    // 🚀 MOVING BARCODES: Initialize the advanced association manager
    private fun initializeMovingBarcodeManager() {
        movingBarcodeManager = MovingBarcodeAssociationManager()

        // Set up callbacks for association events
        movingBarcodeManager.setCallbacks(
            onAssociation = { toteId, olpn, type, confidence, reason ->
                handleSuccessfulAssociation(toteId, olpn, type, confidence, reason)
            },
            onMissed = { detection, reason ->
                handleMissedAssociation(detection, reason)
            }
        )

        Log.d(TAG, "🚀 Moving barcode association manager initialized")
    }

    // 🚀 NETWORK: Handle network state changes with proper UI feedback
    private fun handleNetworkStateChange(networkState: ConveyerScanViewModel.NetworkState?) {
        currentNetworkState = networkState

        when (networkState) {
            ConveyerScanViewModel.NetworkState.CONNECTED -> {
                Log.d(TAG, "🟢 Network connected - enabling online features")

                runOnUiThread {
                    if (wasOffline) {
                        Toast.makeText(this, "✅ Back online - syncing data...", Toast.LENGTH_SHORT).show()
                        wasOffline = false
                    }
                    enableOnlineFeatures()
                }
            }

            ConveyerScanViewModel.NetworkState.DISCONNECTED -> {
                Log.d(TAG, "🔴 Network disconnected - switching to offline mode")
                wasOffline = true

                runOnUiThread {
                    Toast.makeText(this, "📱 Offline mode - data will sync when connected", Toast.LENGTH_LONG).show()
                    enableOfflineMode()
                }
            }

            ConveyerScanViewModel.NetworkState.ERROR -> {
                Log.e(TAG, "❌ Network error")
                runOnUiThread {
                    showNetworkErrorDialog()
                }
            }

            ConveyerScanViewModel.NetworkState.UNKNOWN -> {
                Log.d(TAG, "🔍 Network state unknown - checking...")
            }

            null -> {
                Log.d(TAG, "🔄 Network state initializing...")
            }
        }
    }

    // 🚀 NETWORK: Update connection status display with better visual feedback
    private fun updateConnectionStatusDisplay(connectionType: String) {
        runOnUiThread {
            connectionStatusTextView.text = connectionType

            // Update status text color based on connection
            val color = when {
                connectionType.contains("🟢") -> ContextCompat.getColor(this, android.R.color.holo_green_dark)
                connectionType.contains("🔴") -> ContextCompat.getColor(this, android.R.color.holo_red_dark)
                connectionType.contains("🔄") -> ContextCompat.getColor(this, android.R.color.holo_orange_dark)
                else -> ContextCompat.getColor(this, android.R.color.primary_text_light)
            }
            connectionStatusTextView.setTextColor(color)
        }
    }

    // 🚀 NETWORK: Update sync status with network awareness
    private fun updateSyncStatusDisplay(status: String) {
        runOnUiThread {
            syncStatusTextView.text = status
            updateSyncButtonState(status)
        }
    }

    // 🚀 NETWORK: Enable online features
    private fun enableOnlineFeatures() {
        Log.d(TAG, "✅ Online features enabled")
    }

    // 🚀 NETWORK: Enable offline mode - keep scanning working
    private fun enableOfflineMode() {
        Log.d(TAG, "📱 Offline mode enabled - scanning continues locally")
    }

    // 🚀 NETWORK: Show network error dialog with retry option
    private fun showNetworkErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle("Network Error")
            .setMessage("Network connection error detected. Scanning will continue in offline mode and data will sync when connection is restored.")
            .setPositiveButton("Retry Connection") { _, _ ->
                conveyerViewModel.retryNetworkConnection()
            }
            .setNegativeButton("Continue Offline") { _, _ ->
                // Continue in offline mode
            }
            .setIcon(R.drawable.ic_info)
            .show()
    }

    // 🚀 NETWORK: Update sync button state based on network status
    private fun updateSyncButtonState(status: String) {
        when {
            status.contains("Offline") -> {
                Log.d(TAG, "Sync status: Offline mode")
            }
            status.contains("Syncing") -> {
                Log.d(TAG, "Sync status: Syncing in progress")
            }
            status.contains("All synced") -> {
                Log.d(TAG, "Sync status: All synced")
            }
            else -> {
                Log.d(TAG, "Sync status: Default state")
            }
        }
    }

    // 🚀 NETWORK: Enhanced sync status observation
    private fun observeSyncStatusEnhanced() {
        conveyerViewModel.syncStatus.observe(this) { status ->
            updateSyncStatusDisplay(status)
        }

        conveyerViewModel.networkState.observe(this) { networkState ->
            handleNetworkStateChange(networkState)
        }

        conveyerViewModel.connectionType.observe(this) { connectionType ->
            updateConnectionStatusDisplay(connectionType)
        }
    }

    // Enhanced zoom persistence
    private fun saveZoomLevel(zoomRatio: Float) {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putFloat(ZOOM_PREF_KEY, zoomRatio).apply()
        Log.d(TAG, "Saved zoom level: $zoomRatio")
    }

    private fun loadSavedZoomLevel() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedZoom = prefs.getFloat(ZOOM_PREF_KEY, 1f)
        currentZoomRatio = savedZoom
        Log.d(TAG, "Loaded saved zoom level: $savedZoom")
    }

    private fun applySavedZoomLevel() {
        if (currentZoomRatio > 1f && camera != null) {
            try {
                val clampedZoom = currentZoomRatio.coerceIn(1f, maxZoomRatio)
                camera?.cameraControl?.setZoomRatio(clampedZoom)
                updateZoomUI()
                Log.d(TAG, "Applied saved zoom level: $clampedZoom")

                if (!hasAppliedSavedZoom) {
                    hasAppliedSavedZoom = true
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to apply saved zoom level", e)
                currentZoomRatio = 1f
                updateZoomUI()
            }
        }
    }

    private fun initializeComponents() {
        // Initialize UI components
        previewView = findViewById(R.id.previewView)
        overlayView = findViewById(R.id.overlayView)
        startStopButton = findViewById(R.id.startStopButton)
        statusTextView = findViewById(R.id.statusTextView)
        zoomSeekBar = findViewById(R.id.zoomSeekBar)
        connectionStatusTextView = findViewById(R.id.connectionStatusTextView)
        syncStatusTextView = findViewById(R.id.syncStatusTextView)

        // Add resolution button if it exists
        try {
            resolutionButton = findViewById(R.id.resolutionButton)
        } catch (e: Exception) {
            // Create resolution button if it doesn't exist
            resolutionButton = Button(this).apply {
                text = "4K RES"
            }
        }

        // Initialize camera executor
        cameraExecutor = Executors.newSingleThreadExecutor()

        // Initialize database
        database = AppDatabase.getInstance(this)

        // Enhanced feedback initialization
        initializeFeedback()

        // Setup gesture detector for zoom
        setupGestureDetector()

        Log.d(TAG, "🚀 COMPLETE components initialized with all enhancements")
    }

    private fun initializeFeedback() {
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        Log.d(TAG, "Enhanced feedback systems initialized")
    }

    private fun setupUI() {
        startStopButton.setOnClickListener {
            toggleScanning()
        }

        setupZoomControls()
        setupResolutionButton()
        updateCounterDisplays()
        updateConnectionStatus()
    }

    private fun setupZoomControls() {
        zoomSeekBar.max = 100
        zoomSeekBar.progress = 0
        zoomSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && camera != null) {
                    try {
                        val zoomRatio = 1f + (maxZoomRatio - 1f) * (progress / 100f)
                        val clampedZoom = zoomRatio.coerceIn(1f, maxZoomRatio)
                        camera?.cameraControl?.setZoomRatio(clampedZoom)
                        currentZoomRatio = clampedZoom
                        saveZoomLevel(currentZoomRatio)
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to set zoom via seekbar", e)
                    }
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setupResolutionButton() {
        updateResolutionButtonText()
        resolutionButton.setOnClickListener {
            useHighResolution = !useHighResolution
            updateResolutionButtonText()
            hasAppliedSavedZoom = false
            startCamera()
        }
    }

    private fun updateResolutionButtonText() {
        resolutionButton.text = if (useHighResolution) "4K RES" else "STANDARD RES"
    }

    private fun setupGestureDetector() {
        scaleGestureDetector = ScaleGestureDetector(this,
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    try {
                        val scale = detector.scaleFactor
                        val newZoomRatio = (currentZoomRatio * scale).coerceIn(1f, maxZoomRatio)

                        if (camera != null && kotlin.math.abs(newZoomRatio - currentZoomRatio) > 0.01f) {
                            camera?.cameraControl?.setZoomRatio(newZoomRatio)
                            currentZoomRatio = newZoomRatio
                            updateZoomUI()
                            saveZoomLevel(currentZoomRatio)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to handle zoom gesture", e)
                    }
                    return true
                }
            })
    }

    private fun setupCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST)
        } else {
            startCamera()
        }
    }

    // 🚀 CAMERA: Enhanced camera setup with 4K resolution
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            cameraProvider.unbindAll()

            // Setup preview with aspect ratio
            val preview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .build()
            preview.setSurfaceProvider(previewView.surfaceProvider)

            // Setup camera selector - use back camera
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // 🚀 4K resolution for optimal performance
            val imageAnalysisBuilder = ImageAnalysis.Builder()
                .setTargetResolution(
                    if (useHighResolution)
                        Size(3840, 2160)  // 4K resolution
                    else
                        Size(1920, 1080)  // Standard resolution
                )
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)

            Log.d(TAG, "🚀 Using ${if (useHighResolution) "4K (3840x2160)" else "Standard (1920x1080)"} resolution")

            val imageAnalysis = imageAnalysisBuilder.build()

            // Multi-format barcode scanner
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    // 1D Barcodes
                    Barcode.FORMAT_CODE_128,
                    Barcode.FORMAT_CODE_39,
                    Barcode.FORMAT_CODE_93,
                    Barcode.FORMAT_CODABAR,
                    Barcode.FORMAT_EAN_13,
                    Barcode.FORMAT_EAN_8,
                    Barcode.FORMAT_UPC_A,
                    Barcode.FORMAT_UPC_E,
                    Barcode.FORMAT_ITF,
                    // QR Codes
                    Barcode.FORMAT_QR_CODE,
                    // 2D Barcodes
                    Barcode.FORMAT_DATA_MATRIX,
                    Barcode.FORMAT_PDF417,
                    Barcode.FORMAT_AZTEC
                )
                .build()

            val scanner = BarcodeScanning.getClient(options)
            setupOptimizedBarcodeAnalyzer(imageAnalysis, scanner)

            // Bind to lifecycle
            camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)

            // Enhanced zoom state observation
            camera?.cameraInfo?.zoomState?.observe(this) { zoomState ->
                maxZoomRatio = zoomState.maxZoomRatio

                if (!hasAppliedSavedZoom) {
                    currentZoomRatio = zoomState.zoomRatio
                    updateZoomUI()

                    if (maxZoomRatio > 1f) {
                        loadSavedZoomLevel()
                        applySavedZoomLevel()
                    }
                } else {
                    updateZoomUI()
                }
            }

            Log.d(TAG, "🚀 ULTIMATE camera started successfully with all enhancements")

        }, ContextCompat.getMainExecutor(this))
    }

    // 🚀 CAMERA: Enhanced barcode analyzer
    private fun setupOptimizedBarcodeAnalyzer(imageAnalysis: ImageAnalysis, scanner: com.google.mlkit.vision.barcode.BarcodeScanner) {
        imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
            if (!isScanning) {
                imageProxy.close()
                return@setAnalyzer
            }

            // Performance monitoring
            val frameStartTime = System.currentTimeMillis()
            frameCount++

            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

                scanner.process(inputImage)
                    .addOnSuccessListener { barcodes ->
                        // 🚀 ENHANCED: Process with moving barcode support
                        processBarcodesWithMovingSupport(barcodes)

                        // Performance tracking
                        val frameEndTime = System.currentTimeMillis()
                        val processingTime = frameEndTime - frameStartTime
                        if (frameCount % 30 == 0) { // Log every 30 frames
                            Log.d(TAG, "🚀 Frame processing: ${processingTime}ms | FPS: ${1000.0 / processingTime}")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Barcode analysis failed", e)
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }
            } else {
                imageProxy.close()
            }
        }
    }

    // 🚀 ENHANCED: Process barcodes with full moving barcode support and data cleaning
    private fun processBarcodesWithMovingSupport(barcodes: List<Barcode>) {
        val currentResults = mutableListOf<DetectedBarcode>()
        val newlyDetected = mutableListOf<Triple<String, Int, Long>>()
        val currentTime = System.currentTimeMillis()

        // Enhanced barcode type tracking
        val frame1DBarcodes = mutableListOf<String>()
        val frameQRCodes = mutableListOf<String>()
        val frame2DBarcodes = mutableListOf<String>()

        // STEP 1: IMMEDIATE DETECTION PROCESSING WITH DATA CLEANING
        for (barcode in barcodes) {
            val rawValue = barcode.rawValue ?: continue
            val box = barcode.boundingBox ?: continue
            val rectF = RectF(box)

            // 🆕 BARCODE DATA CLEANING: Clean the barcode data before processing
            val cleanedValue = cleanBarcodeData(rawValue, barcode.format)

            // Validate cleaned data
            if (!isValidCleanedBarcodeData(cleanedValue, rawValue)) {
                Log.e(TAG, "❌ INVALID CLEANED BARCODE DATA: Skipping '$rawValue'")
                continue
            }

            // Log cleaning operation if data was modified
            if (cleanedValue != rawValue) {
                Log.d(TAG, "🧹 BARCODE DATA CLEANED:")
                Log.d(TAG, "   Raw:     '$rawValue'")
                Log.d(TAG, "   Cleaned: '$cleanedValue'")
                Log.d(TAG, "   Format:  ${getBarcodeFormatName(barcode.format)}")
            }

            // Use cleaned value for all subsequent processing
            val value = cleanedValue

            barcodeLastSeen[value] = currentTime

            val isAlreadyScanned = scannedSet.contains(value)
            val detected = DetectedBarcode(rectF, value, barcode.format, isAlreadyScanned, "Near")
            currentResults.add(detected)

            // Enhanced barcode type classification
            when (barcode.format) {
                Barcode.FORMAT_CODE_128, Barcode.FORMAT_CODE_39, Barcode.FORMAT_CODE_93,
                Barcode.FORMAT_CODABAR, Barcode.FORMAT_EAN_13, Barcode.FORMAT_EAN_8,
                Barcode.FORMAT_UPC_A, Barcode.FORMAT_UPC_E, Barcode.FORMAT_ITF -> {
                    frame1DBarcodes.add(value)
                }
                Barcode.FORMAT_QR_CODE -> {
                    frameQRCodes.add(value)
                }
                Barcode.FORMAT_DATA_MATRIX, Barcode.FORMAT_PDF417, Barcode.FORMAT_AZTEC -> {
                    frame2DBarcodes.add(value)
                }
            }

            // Track new detections
            if (!isAlreadyScanned) {
                scannedSet.add(value)
                newlyDetected.add(Triple(value, barcode.format, currentTime))
                provideScanFeedback()

                // 🚀 MOVING BARCODES: Process with advanced association manager (using cleaned data)
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        movingBarcodeManager.processDetection(value, barcode.format, rectF, currentTime)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error processing moving barcode detection", e)
                    }
                }
            }
        }

        // Enhanced multi-type detection logging
        if (frame1DBarcodes.isNotEmpty() || frameQRCodes.isNotEmpty() || frame2DBarcodes.isNotEmpty()) {
            Log.d(TAG, "🚀 MULTI-FORMAT FRAME:")
            Log.d(TAG, "   📊 1D (${frame1DBarcodes.size}): $frame1DBarcodes")
            Log.d(TAG, "   📱 QR (${frameQRCodes.size}): $frameQRCodes")
            Log.d(TAG, "   🔲 2D (${frame2DBarcodes.size}): $frame2DBarcodes")

            if (frame1DBarcodes.isNotEmpty() && frameQRCodes.isNotEmpty()) {
                Log.d(TAG, "🎯 IMMEDIATE OPPORTUNITY: 1D + QR in same frame!")
            }
        }

        // STEP 2: IMMEDIATE UI UPDATE
        updatePersistentBarcodes(currentResults, currentTime)

        if (newlyDetected.isNotEmpty()) {
            statusTextView.text = "Scanning... New: ${newlyDetected.size}"
        }

        // STEP 3: BACKGROUND PROCESSING
        if (newlyDetected.isNotEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {
                processBarcodesInBackgroundEnhanced(newlyDetected)
            }
        }

        // STEP 4: UPDATE STATISTICS
        if (currentTime - lastStatsUpdate > STATS_UPDATE_INTERVAL_MS) {
            updateAssociationStatistics()
            lastStatsUpdate = currentTime
        }
    }

    // 🚀 ENHANCED: Background processing with reduced traditional association dependency
    private suspend fun processBarcodesInBackgroundEnhanced(newBarcodes: List<Triple<String, Int, Long>>) {
        val db = AppDatabase.getInstance(this@ConveyerScanActivity)
        val dao = db.barcodeDao()

        var newToteIds = 0
        var newOlpns = 0

        // Batch process barcodes for storage
        val barcodeEntities = mutableListOf<com.example.barcodescanner.data.local.BarcodeEntity>()

        for ((value, format, timestamp) in newBarcodes) {
            try {
                // 🆕 NOTE: The 'value' here is already cleaned from processBarcodesWithMovingSupport
                barcodeEntities.add(
                    com.example.barcodescanner.data.local.BarcodeEntity(
                        value = value, // This is the cleaned value ready for backend
                        format = format,
                        timestamp = timestamp,
                        sessionId = sessionId,
                        imagePath = null
                    )
                )

                // Update counters
                when {
                    BarcodeFormatReference.isOlpnFormat(format) -> {
                        if (!recentOlpns.contains(value)) {
                            recentOlpns.add(value)
                            newOlpns++
                        }
                    }
                    BarcodeFormatReference.isToteIdFormat(format) -> {
                        if (!recentToteIds.contains(value)) {
                            recentToteIds.add(value)
                            newToteIds++
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error preparing barcode $value for batch processing", e)
            }
        }

        // Batch database operations
        try {
            if (barcodeEntities.isNotEmpty()) {
                var successCount = 0
                for (entity in barcodeEntities) {
                    try {
                        dao.insertBarcode(entity)
                        successCount++

                        // Log successful backend-ready data insertion
                        Log.d(TAG, "✅ BACKEND-READY DATA STORED: '${entity.value}' (${getBarcodeFormatName(entity.format)})")
                    } catch (ex: Exception) {
                        Log.e(TAG, "Barcode insert failed for ${entity.value}", ex)
                    }
                }
                Log.d(TAG, "🚀 Inserted $successCount/${barcodeEntities.size} cleaned barcodes")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Barcode database operations failed", e)
        }

        // Update counters
        toteIdCount += newToteIds
        olpnCount += newOlpns

        // Legacy association check (now supplementary to MovingBarcodeAssociationManager)
        checkAndLogMissingScanPairs(System.currentTimeMillis())

        // Update UI
        withContext(Dispatchers.Main) {
            val countsDisplay = "T:$toteIdCount,O:$olpnCount,A:$associationCount"
            statusTextView.text = "Scanning... ($countsDisplay)"
            updateCounterDisplays()
        }
    }

    // 🚀 MOVING BARCODES: Handle successful associations from the manager
    private fun handleSuccessfulAssociation(
        toteId: String,
        olpn: String,
        type: MovingBarcodeAssociationManager.AssociationType,
        confidence: Float,
        reason: String
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // 🆕 NOTE: toteId and olpn here are already cleaned values from the manager
                // Use ViewModel to create association (handles network state)
                withContext(Dispatchers.Main) {
                    conveyerViewModel.createAssociation(toteId, olpn, sessionId)
                }

                // Update local tracking
                associatedToteIds.add(toteId)
                associatedOlpns.add(olpn)
                pendingToteIds.remove(toteId)
                pendingOlpns.remove(olpn)

                associationCount++

                // Log with strategy type
                logToCSV(toteId, olpn, "association-complete-${type.name.lowercase()}")

                Log.d(TAG, "✅ MOVING BARCODE ASSOCIATION (CLEANED DATA): $toteId ↔ $olpn")
                Log.d(TAG, "   Type: $type | Confidence: $confidence | Reason: $reason")

                // Enhanced UI feedback
                withContext(Dispatchers.Main) {
                    if (ASSOCIATION_FEEDBACK_ENABLED) {
                        val typeDisplay = when (type) {
                            MovingBarcodeAssociationManager.AssociationType.IMMEDIATE_SAME_FRAME -> "Immediate"
                            MovingBarcodeAssociationManager.AssociationType.TEMPORAL_SEQUENCE -> "Sequence"
                            MovingBarcodeAssociationManager.AssociationType.SPATIAL_PROXIMITY -> "Spatial"
                            MovingBarcodeAssociationManager.AssociationType.MOVEMENT_PREDICTION -> "Movement"
                            MovingBarcodeAssociationManager.AssociationType.PATTERN_BASED -> "Pattern"
                        }

                        val confidenceEmoji = when {
                            confidence >= 0.8f -> "🟢"
                            confidence >= 0.6f -> "🟡"
                            else -> "🟠"
                        }

                        Toast.makeText(this@ConveyerScanActivity,
                            "$confidenceEmoji $typeDisplay: ${toteId.take(8)}... ↔ ${olpn.take(8)}...",
                            Toast.LENGTH_SHORT).show()
                    }

                    updateCounterDisplays()
                }

            } catch (e: Exception) {
                Log.e(TAG, "Failed to execute association from moving barcode manager", e)
            }
        }
    }

    // 🚀 MOVING BARCODES: Handle missed associations for analysis
    private fun handleMissedAssociation(
        detection: MovingBarcodeAssociationManager.BarcodeDetection,
        reason: String
    ) {
        Log.w(TAG, "⚠️ MISSED ASSOCIATION: ${detection.value} (${getBarcodeTypeName(detection.format)}) - $reason")

        // Log missed association for analysis
        val missedType = if (BarcodeFormatReference.isOlpnFormat(detection.format)) "OLPN" else "ToteID"
        logToCSV(
            if (missedType == "ToteID") detection.value else "missing-toteid",
            if (missedType == "OLPN") detection.value else "missing-olpn",
            "association-missed-$reason"
        )
    }

    // 🚀 MOVING BARCODES: Update association statistics
    private fun updateAssociationStatistics() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val stats = movingBarcodeManager.getAssociationStats()
                associationSuccessRate = stats.associationRate

                Log.d(TAG, "📊 MOVING BARCODE STATS:")
                Log.d(TAG, "   Total detections: ${stats.totalDetections}")
                Log.d(TAG, "   Associated: ${stats.associatedCount}")
                Log.d(TAG, "   Pending: ${stats.pendingCount}")
                Log.d(TAG, "   Success rate: ${(stats.associationRate * 100).toInt()}%")

            } catch (e: Exception) {
                Log.e(TAG, "Failed to update association statistics", e)
            }
        }
    }

    // Handle persistent barcodes and overlay updates
    private fun updatePersistentBarcodes(currentResults: MutableList<DetectedBarcode>, currentTime: Long) {
        val currentBarcodeValues = currentResults.map { it.value }.toSet()

        // Add persistent detected barcodes still within timeout
        val persistentBarcodes = mutableListOf<DetectedBarcode>()
        val iterator = barcodeLastSeen.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            val value = entry.key
            val lastSeen = entry.value

            if (!currentBarcodeValues.contains(value) && (currentTime - lastSeen) < BARCODE_TIMEOUT_MS) {
                val lastKnownRect = RectF(
                    previewView.width / 2f - 50f,
                    previewView.height / 2f - 25f,
                    previewView.width / 2f + 50f,
                    previewView.height / 2f + 25f
                )

                val persistentDetected = DetectedBarcode(
                    lastKnownRect,
                    value,
                    1,
                    true,
                    "Recent"
                )
                persistentBarcodes.add(persistentDetected)
            } else if ((currentTime - lastSeen) >= BARCODE_TIMEOUT_MS) {
                iterator.remove()
            }
        }

        // Combine current and persistent results
        val allResults = currentResults + persistentBarcodes

        try {
            overlayView.drawBarcodes(allResults)
        } catch (e: Exception) {
            Log.e(TAG, "Error drawing barcodes on overlay", e)
        }
    }

    // Check for missing scan pairs (legacy fallback)
    private fun checkAndLogMissingScanPairs(currentTime: Long) {
        val expiredToteIds = pendingToteIds.filter { (_, detectionTime) ->
            (currentTime - detectionTime) > missingScanTimeout
        }

        expiredToteIds.forEach { (toteId, detectionTime) ->
            val waitTime = (currentTime - detectionTime) / 1000.0
            Log.w(TAG, "⚠️ MISSING OLPN: ToteID $toteId waited ${waitTime}s without OLPN match")
            logToCSV(toteId, "OLPN-missing", "association-not-done")
            pendingToteIds.remove(toteId)
        }

        val expiredOlpns = pendingOlpns.filter { (_, detectionTime) ->
            (currentTime - detectionTime) > missingScanTimeout
        }

        expiredOlpns.forEach { (olpn, detectionTime) ->
            val waitTime = (currentTime - detectionTime) / 1000.0
            Log.w(TAG, "⚠️ MISSING TOTEID: OLPN $olpn waited ${waitTime}s without ToteID match")
            logToCSV("toteid-missing", olpn, "association-not-done")
            pendingOlpns.remove(olpn)
        }
    }

    // Performance monitoring
    private fun startPerformanceMonitoring() {
        lifecycleScope.launch {
            while (isScanning) {
                val memoryInfo = android.app.ActivityManager.MemoryInfo()
                val activityManager = getSystemService(ACTIVITY_SERVICE) as android.app.ActivityManager
                activityManager.getMemoryInfo(memoryInfo)

                val availableMemoryMB = memoryInfo.availMem / 1024 / 1024
                if (availableMemoryMB < 100) {
                    Log.w(TAG, "⚠️ Low memory warning: ${availableMemoryMB}MB available")
                }

                delay(5000)
            }
        }
    }

    // Initialize logging system
    private fun initializeSessionLogging() {
        try {
            val logsDir = File(getExternalFilesDir(null), "scan_logs")
            if (!logsDir.exists()) {
                logsDir.mkdirs()
            }

            val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            val today = dateFormat.format(java.util.Date())
            scanLogFile = File(logsDir, "conveyer_scan_$today.csv")

            Log.d(TAG, "CSV logging system initialized: ${scanLogFile.absolutePath}")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize CSV logging system", e)
        }
    }

    // Start session logging with CSV headers
    private fun startSessionLogging() {
        try {
            if (!scanLogFile.exists() || scanLogFile.length() == 0L) {
                val csvHeaders = "Row,ToteID,OLPN,Timestamp,AssociationStatus\n"
                scanLogFile.writeText(csvHeaders)
                csvRowCounter = 1
            } else {
                csvRowCounter = scanLogFile.readLines().size
            }

            logSessionStart()
            Log.d(TAG, "CSV session logging started for session: $sessionId")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start CSV session logging", e)
        }
    }

    // Log session start marker
    private fun logSessionStart() {
        try {
            val timestamp = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
            val sessionStartEntry = "${csvRowCounter++},SESSION-START-${sessionId},SESSION-START,${timestamp},session-initialized\n"
            scanLogFile.appendText(sessionStartEntry)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to log session start", e)
        }
    }

    // Log to CSV with association strategy
    private fun logToCSV(toteId: String, olpn: String, associationStatus: String) {
        try {
            val timestamp = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", java.util.Locale.getDefault()).format(java.util.Date())
            val csvEntry = "${csvRowCounter++},${toteId},${olpn},${timestamp},${associationStatus}\n"
            scanLogFile.appendText(csvEntry)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to write to CSV log file", e)
        }
    }

    // Load existing associations for deduplication
    private fun loadExistingAssociations() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val existingAssociations = database.associationDao().getAllAssociations()

                withContext(Dispatchers.Main) {
                    existingAssociations.forEach { association ->
                        associatedToteIds.add(association.toteId)
                        associatedOlpns.add(association.olpn)
                    }

                    Log.d(TAG, "🔍 LOADED EXISTING ASSOCIATIONS:")
                    Log.d(TAG, "   Total: ${existingAssociations.size}")
                    Log.d(TAG, "   Session: $sessionId")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load existing associations", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ConveyerScanActivity,
                        "Warning: Failed to load existing associations",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Debug format mappings
    private fun debugFormatMappings() {
        Log.d(TAG, "=== BARCODE FORMAT DEBUG ===")
        Log.d(TAG, "QR_CODE: ${BarcodeFormatReference.QR_CODE}")
        Log.d(TAG, "DATA_MATRIX: ${BarcodeFormatReference.DATA_MATRIX}")
        Log.d(TAG, "CODE_128: ${BarcodeFormatReference.CODE_128}")
        BarcodeFormatReference.printFormatMappings()
        Log.d(TAG, "=== END FORMAT DEBUG ===")
    }

    private fun provideScanFeedback() {
        addHapticFeedback()
    }

    private fun addHapticFeedback() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(50)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to provide haptic feedback", e)
        }
    }

    private fun getBarcodeFormatName(format: Int): String {
        return when (format) {
            1 -> "Code 128"
            2 -> "Code 39"
            4 -> "Code 93"
            8 -> "Codabar"
            16 -> "Data Matrix"
            32 -> "EAN-13"
            64 -> "EAN-8"
            256 -> "QR Code"
            512 -> "UPC-A"
            1024 -> "UPC-E"
            2048 -> "PDF417"
            4096 -> "Aztec"
            8192 -> "ITF"
            else -> "Unknown"
        }
    }

    private fun getBarcodeTypeName(format: Int): String {
        return when {
            BarcodeFormatReference.isOlpnFormat(format) -> "OLPN"
            BarcodeFormatReference.isToteIdFormat(format) -> "ToteID"
            else -> "Unknown"
        }
    }

    private fun toggleScanning() {
        if (isScanning) {
            stopScanningEnhanced()
        } else {
            startScanningEnhanced()
        }
    }

    // 🚀 ENHANCED: Complete scanning start with all features including screen management
    private fun startScanningEnhanced() {
        // Get session ID from ViewModel
        sessionId = conveyerViewModel.startScanningSession()

        isScanning = true

        // Reset all counters and state
        toteIdCount = 0
        olpnCount = 0
        associationCount = 0
        associationSuccessRate = 0f
        lastStatsUpdate = 0L
        scannedSet.clear()
        barcodeLastSeen.clear()
        recentToteIds.clear()
        recentOlpns.clear()

        // Clear tracking sets
        associatedToteIds.clear()
        associatedOlpns.clear()
        pendingToteIds.clear()
        pendingOlpns.clear()

        // 🚀 MOVING BARCODES: Reset association manager
        movingBarcodeManager.reset()

        overlayView.clear()
        setFocusForDistanceScan()
        loadExistingAssociations()
        startSessionLogging()

        // 🆕 CRITICAL: Enable screen keep-awake for scanning
        enableScreenKeepAwake()
        monitorScreenStateDuringScanning()

        // Update UI
        startStopButton.text = "Stop Scan"
        startStopButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))

        updateStatus()

        val networkInfo = conveyerViewModel.getNetworkInfo()
        Log.d(TAG, "🚀 ULTIMATE ENHANCED scanning started: $sessionId")
        Log.d(TAG, "   Features: Network-Aware + Moving Barcodes + 4K + Offline Support + Data Cleaning + Screen Lock Prevention")
        Log.d(TAG, "   Network: $networkInfo")
        Log.d(TAG, "   Data Cleaning: ]C1 prefix removal for 1D barcodes ENABLED")
        Log.d(TAG, "   Screen Management: KEEP-AWAKE ENABLED")
    }

    // 🚀 ENHANCED: Complete scanning stop with screen management cleanup
    private fun stopScanningEnhanced() {
        conveyerViewModel.stopScanningSession(sessionId)
        isScanning = false

        // 🆕 CRITICAL: Restore normal screen behavior
        disableScreenKeepAwake()

        // Update UI
        startStopButton.text = "Start Scan"
        startStopButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))

        statusTextView.text = "Scanning stopped"
        showScanSummaryEnhanced()

        Log.d(TAG, "🚀 ULTIMATE ENHANCED scanning stopped")
        Log.d(TAG, "   Screen Management: KEEP-AWAKE DISABLED")
    }

    private fun setFocusForDistanceScan() {
        camera?.cameraControl?.let { control ->
            control.cancelFocusAndMetering()
            val factory = previewView.meteringPointFactory
            val point = factory.createPoint(previewView.width / 2f, previewView.height / 2f)
            val action = FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
                .setAutoCancelDuration(5, TimeUnit.SECONDS)
                .build()
            control.startFocusAndMetering(action)
        }
    }

    // 🚀 ENHANCED: Complete scan summary with all statistics
    private fun showScanSummaryEnhanced() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Get actual database counts
                val actualToteIds = database.barcodeDao().getBarcodesBySession(sessionId)
                    .count { BarcodeFormatReference.isToteIdFormat(it.format) }
                val actualOlpns = database.barcodeDao().getBarcodesBySession(sessionId)
                    .count { BarcodeFormatReference.isOlpnFormat(it.format) }
                val actualAssociations = database.associationDao().getSessionCount(sessionId)

                val networkInfo = conveyerViewModel.getNetworkInfo()
                val stats = movingBarcodeManager.getAssociationStats()

                Log.d(TAG, "🔍 COMPLETE VERIFICATION:")
                Log.d(TAG, "   UI Counts - T:$toteIdCount, O:$olpnCount, A:$associationCount")
                Log.d(TAG, "   DB Counts - T:$actualToteIds, O:$actualOlpns, A:$actualAssociations")
                Log.d(TAG, "   Moving Success Rate: ${(stats.associationRate * 100).toInt()}%")
                Log.d(TAG, "   Network: $networkInfo")
                Log.d(TAG, "   Data Cleaning: ENABLED (]C1 prefix removal)")
                Log.d(TAG, "   Screen Management: DISABLED (normal behavior restored)")

                withContext(Dispatchers.Main) {
                    val finalToteCount = maxOf(toteIdCount, actualToteIds)
                    val finalOlpnCount = maxOf(olpnCount, actualOlpns)
                    val finalAssociationCount = maxOf(associationCount, actualAssociations)

                    val syncMessage = when {
                        networkInfo.contains("Connected") -> "✅ Data will be synced automatically"
                        networkInfo.contains("No network") -> "📱 Data saved locally - will sync when connected"
                        else -> "🔄 Connection status: $networkInfo"
                    }

                    val successRate = (stats.associationRate * 100).toInt()
                    val associationAnalysis = when {
                        successRate >= 90 -> "🟢 Excellent association rate"
                        successRate >= 75 -> "🟡 Good association rate"
                        successRate >= 60 -> "🟠 Fair association rate - check conveyor speed"
                        else -> "🔴 Low association rate - check setup"
                    }

                    val message = """
                        📊 ULTIMATE SCAN RESULTS (T:$finalToteCount,O:$finalOlpnCount,A:$finalAssociationCount):
                        • ToteIDs detected: $finalToteCount
                        • OLPNs detected: $finalOlpnCount
                        • Associations created: $finalAssociationCount
                        • Data cleaning: ]C1 prefixes removed ✅
                        • Screen lock: Re-enabled ✅
                    """.trimIndent()

                    AlertDialog.Builder(this@ConveyerScanActivity)
                        .setTitle("🚀 Ultimate Scan Complete")
                        .setMessage(message)
                        .setPositiveButton("View Results") { _, _ ->
                            Handler(Looper.getMainLooper()).postDelayed({
                                navigateToResults()
                            }, 500)
                        }
                        .setNegativeButton("Continue Scanning") { _, _ ->
                            // Continue scanning
                        }
                        .setNeutralButton("View CSV Log") { _, _ ->
                            showCSVLogFile()
                        }
                        .setIcon(R.drawable.ic_info)
                        .show()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to verify database counts", e)
                // Fallback with basic counts
                withContext(Dispatchers.Main) {
                    val networkInfo = conveyerViewModel.getNetworkInfo()
                    val message = """
                        📊 Scan Results (T:$toteIdCount,O:$olpnCount,A:$associationCount):
                        • ToteIDs: $toteIdCount | OLPNs: $olpnCount | Associations: $associationCount
                        • Network: $networkInfo
                        • Session: $sessionId
                        • Data cleaning: ]C1 prefixes removed ✅
                        • Screen lock: Re-enabled ✅
                    """.trimIndent()

                    AlertDialog.Builder(this@ConveyerScanActivity)
                        .setTitle("Scan Complete")
                        .setMessage(message)
                        .setPositiveButton("View Results") { _, _ -> navigateToResults() }
                        .setNegativeButton("Continue", null)
                        .setNeutralButton("CSV Log") { _, _ -> showCSVLogFile() }
                        .show()
                }
            }
        }
    }

    // Show CSV log file contents
    private fun showCSVLogFile() {
        try {
            val csvContent = scanLogFile.readText()
            val lines = csvContent.lines()
            val truncatedContent = if (lines.size > 50) {
                lines.take(5).joinToString("\n") + "\n...\n" + lines.takeLast(20).joinToString("\n")
            } else {
                csvContent
            }

            AlertDialog.Builder(this)
                .setTitle("CSV Scan Log (${lines.size - 1} entries)")
                .setMessage(truncatedContent)
                .setPositiveButton("OK", null)
                .setNeutralButton("Share CSV") { _, _ ->
                    shareCSVLogFile()
                }
                .show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to read CSV file: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Share CSV log file
    private fun shareCSVLogFile() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_TEXT, scanLogFile.readText())
                putExtra(Intent.EXTRA_SUBJECT, "Ultimate Conveyer Scan CSV Log - Session $sessionId")
            }
            startActivity(Intent.createChooser(shareIntent, "Share CSV Log"))
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to share CSV: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // Navigate to results with complete information
    private fun navigateToResults() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val sessionAssociations = database.associationDao().getAssociationsBySession(sessionId)
                val sessionBarcodes = try {
                    database.barcodeDao().getBarcodesBySession(sessionId)
                } catch (e: Exception) {
                    database.barcodeDao().getAllBarcodes().filter { it.sessionId == sessionId }
                }

                val networkInfo = conveyerViewModel.getNetworkInfo()
                val stats = movingBarcodeManager.getAssociationStats()

                Log.d(TAG, "🔍 FINAL NAVIGATION VERIFICATION:")
                Log.d(TAG, "   Session: $sessionId")
                Log.d(TAG, "   Associations: ${sessionAssociations.size}")
                Log.d(TAG, "   Barcodes: ${sessionBarcodes.size}")
                Log.d(TAG, "   Success Rate: ${(stats.associationRate * 100).toInt()}%")
                Log.d(TAG, "   Network: $networkInfo")
                Log.d(TAG, "   Data Cleaning: ]C1 prefixes removed")
                Log.d(TAG, "   Screen Management: Normal behavior restored")

                withContext(Dispatchers.Main) {
                    if (sessionAssociations.isNotEmpty() || sessionBarcodes.isNotEmpty()) {
                        val intent = Intent(this@ConveyerScanActivity, ConveyerResultsActivity::class.java).apply {
                            putExtra("session_id", sessionId)
                            putExtra("association_count", sessionAssociations.size)
                            putExtra("barcode_count", sessionBarcodes.size)
                            putExtra("network_info", networkInfo)
                            putExtra("success_rate", (stats.associationRate * 100).toInt())
                            putExtra("enhanced_features", true)
                            putExtra("data_cleaning_enabled", true)
                            putExtra("screen_management_enabled", true)
                        }
                        startActivity(intent)
                    } else {
                        AlertDialog.Builder(this@ConveyerScanActivity)
                            .setTitle("No Data Found")
                            .setMessage("""
                                No scan data available for results page.
                                
                                Network: $networkInfo
                                Session: $sessionId
                                Data cleaning: ✅ Enabled
                                Screen management: ✅ Enabled
                                
                                Data is saved locally and will sync when connected.
                            """.trimIndent())
                            .setPositiveButton("Start New Scan") { _, _ -> finish() }
                            .setNegativeButton("Dashboard") { _, _ -> navigateToMainDashboard() }
                            .setNeutralButton("CSV Log") { _, _ -> showCSVLogFile() }
                            .show()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to verify data before navigation", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ConveyerScanActivity,
                        "Warning: Data verification failed, navigating anyway",
                        Toast.LENGTH_LONG).show()

                    val intent = Intent(this@ConveyerScanActivity, ConveyerResultsActivity::class.java).apply {
                        putExtra("session_id", sessionId)
                        putExtra("verification_failed", true)
                        putExtra("network_info", conveyerViewModel.getNetworkInfo())
                        putExtra("data_cleaning_enabled", true)
                        putExtra("screen_management_enabled", true)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    private fun updateCounterDisplays() {
        Log.d(TAG, "📊 Current counts - T:$toteIdCount, O:$olpnCount, A:$associationCount")
    }

    private fun navigateToMainDashboard() {
        try {
            val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(launchIntent)
                finish()
                return
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAndRemoveTask()
            } else {
                finish()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to navigate to main dashboard", e)
            finish()
        }
    }

    private fun updateStatus() {
        val status = if (isScanning) {
            "Scanning... (T:$toteIdCount,O:$olpnCount,A:$associationCount)"
        } else {
            "Ready to scan"
        }
        statusTextView.text = status
    }

    private fun updateZoomUI() {
        try {
            val progress = ((currentZoomRatio - 1f) / (maxZoomRatio - 1f) * 100).toInt().coerceIn(0, 100)
            if (zoomSeekBar.progress != progress) {
                zoomSeekBar.progress = progress
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to update zoom UI", e)
            zoomSeekBar.progress = 0
        }
    }

    private fun updateConnectionStatus() {
        lifecycleScope.launch(Dispatchers.IO) {
            val connectivityMonitor = com.example.barcodescanner.service.ConnectivityMonitor(this@ConveyerScanActivity)
            val isConnected = connectivityMonitor.isConnected()
            val connectionType = connectivityMonitor.getConnectionType()

            withContext(Dispatchers.Main) {
                val status = if (isConnected) "🟢 $connectionType" else "🔴 Offline"
                connectionStatusTextView.text = status
            }
        }
    }

    private fun startCloudSyncService() {
        val intent = Intent(this, CloudSyncService::class.java).apply {
            action = CloudSyncService.ACTION_START_SYNC
        }
        startService(intent)
        Log.d(TAG, "Enhanced cloud sync service started")
    }

    // Test function to verify format detection
    private fun testFormatDetection() {
        Log.d(TAG, "=== TESTING FORMAT DETECTION ===")
        val qrFormat = 256
        Log.d(TAG, "QR Code (256): isToteId=${BarcodeFormatReference.isToteIdFormat(qrFormat)}")
        val code128Format = 1
        Log.d(TAG, "Code 128 (1): isOlpn=${BarcodeFormatReference.isOlpnFormat(code128Format)}")
        val dataMatrixFormat = 16
        Log.d(TAG, "Data Matrix (16): isToteId=${BarcodeFormatReference.isToteIdFormat(dataMatrixFormat)}")
        Log.d(TAG, "=== END FORMAT TEST ===")
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector?.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_DOWN) {
            val factory = previewView.meteringPointFactory
            val point = factory.createPoint(event.x, event.y)
            val action = FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF).build()
            camera?.cameraControl?.startFocusAndMetering(action)
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    // 🆕 ENHANCED: onPause with screen management awareness
    override fun onPause() {
        super.onPause()
        // Don't disable screen keep-awake on pause if still scanning
        // This allows scanning to continue even if user briefly switches apps
        Log.d(TAG, "📱 Activity paused - maintaining screen keep-awake during scanning: $isScanning")
    }

    // 🆕 ENHANCED: onResume with screen management restoration
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called - restoring zoom level and screen management")

        // Restore screen keep-awake if we were scanning
        if (isScanning && !isScreenManagementActive) {
            Log.d(TAG, "📱 Restoring screen keep-awake on resume")
            enableScreenKeepAwake()
            monitorScreenStateDuringScanning()
        }

        if (camera != null) {
            hasAppliedSavedZoom = false
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    loadSavedZoomLevel()
                    val clampedZoom = currentZoomRatio.coerceIn(1f, maxZoomRatio)
                    camera?.cameraControl?.setZoomRatio(clampedZoom)
                    updateZoomUI()
                    Log.d(TAG, "Refreshed zoom to: $clampedZoom")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to refresh zoom", e)
                }
            }, 300)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (isScanning) {
            toggleScanning()
        } else {
            super.onBackPressed()
        }
    }

    // 🆕 ENHANCED: onDestroy with complete screen management cleanup
    override fun onDestroy() {
        super.onDestroy()

        // 🆕 CRITICAL: Clean up screen management
        disableScreenKeepAwake()

        cameraExecutor.shutdown()

        val intent = Intent(this, CloudSyncService::class.java).apply {
            action = CloudSyncService.ACTION_STOP_SYNC
        }
        startService(intent)

        Log.d(TAG, "🧹 Screen management cleaned up on destroy")
    }

    // Extension function for coroutine support
    private suspend fun <T> com.google.android.gms.tasks.Task<T>.await(): T {
        return suspendCancellableCoroutine { cont ->
            addOnSuccessListener { cont.resume(it) }
            addOnFailureListener { cont.resumeWithException(it) }
        }
    }
}