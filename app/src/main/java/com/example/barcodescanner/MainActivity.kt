package com.example.barcodescanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.barcodescanner.databinding.ActivityMainBinding
import com.example.barcodescanner.ui.BarcodeAnalyzer
import com.example.barcodescanner.ui.MainViewModel
import com.example.barcodescanner.utils.WakeLockManager
import com.example.barcodescanner.utils.setupScreenManagement
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * MainActivity with enhanced screen management and wake lock support
 * Prevents device from sleeping during live scanning
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var wakeLockManager: WakeLockManager

    private var camera: Camera? = null
    private var preview: Preview? = null
    private var imageAnalyzer: ImageAnalysis? = null

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA
        ).toTypedArray()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Setup screen management with wake lock
        wakeLockManager = setupScreenManagement(keepScreenOn = true)

        // Initialize camera executor
        cameraExecutor = Executors.newSingleThreadExecutor()

        // Check permissions and start camera
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Setup UI
        setupUI()

        // Observe ViewModel
        observeViewModel()
    }

    /**
     * Setup UI components and listeners
     */
    private fun setupUI() {
        // Start scanning automatically
        viewModel.startScanning()

        // Add any UI setup here if buttons exist in your layout
        // For example, if you have these buttons in your layout:

        // binding.btnFlashlight?.setOnClickListener {
        //     toggleFlashlight()
        // }

        // binding.btnViewResults?.setOnClickListener {
        //     val intent = Intent(this, ResultsActivity::class.java)
        //     startActivity(intent)
        // }

        // Manual focus (tap to focus) - only if viewFinder exists
        binding.root.setOnTouchListener { _, event ->
            val meteringPointFactory = SurfaceOrientedMeteringPointFactory(
                binding.root.width.toFloat(),
                binding.root.height.toFloat()
            )
            val meteringPoint = meteringPointFactory.createPoint(event.x, event.y)
            val action = FocusMeteringAction.Builder(meteringPoint).build()
            camera?.cameraControl?.startFocusAndMetering(action)
            true
        }
    }

    /**
     * Observe ViewModel state changes
     */
    private fun observeViewModel() {
        viewModel.barcodeCount.observe(this) { count ->
            // Update UI with barcode count
            Log.d(TAG, "Barcodes scanned: $count")
            // If you have a TextView for showing count:
            // binding.tvBarcodeCount?.text = "Scanned: $count"
        }

        viewModel.isScanning.observe(this) { isScanning ->
            if (isScanning) {
                // Ensure wake lock is active during scanning
                if (!wakeLockManager.isWakeLockHeld()) {
                    wakeLockManager.acquireWakeLock()
                }
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                viewModel.clearError()
            }
        }

        viewModel.lastScannedBarcode.observe(this) { barcode ->
            if (barcode.isNotEmpty()) {
                Log.d(TAG, "Last scanned: $barcode")
                // Show toast or update UI with last scanned barcode
                Toast.makeText(this, "Scanned: $barcode", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Start camera with enhanced configuration
     */
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview configuration
            preview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .build()

            // Only set surface provider if viewFinder exists
            try {
                // Try to get viewFinder from binding
                val viewFinderField = binding::class.java.getDeclaredField("viewFinder")
                val viewFinder = viewFinderField.get(binding) as androidx.camera.view.PreviewView
                preview?.setSurfaceProvider(viewFinder.surfaceProvider)
            } catch (e: Exception) {
                Log.w(TAG, "ViewFinder not found in layout, running in headless mode")
            }

            // Image analysis configuration for barcode scanning
            imageAnalyzer = ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarcodeAnalyzer { barcodes ->
                        // Process detected barcodes
                        barcodes.forEach { barcode ->
                            barcode.rawValue?.let { value ->
                                runOnUiThread {
                                    viewModel.addBarcode(value, barcode.format)
                                }
                            }
                        }
                    })
                }

            // Select back camera as default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = if (preview != null) {
                    cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageAnalyzer
                    )
                } else {
                    cameraProvider.bindToLifecycle(
                        this, cameraSelector, imageAnalyzer
                    )
                }

                Log.d(TAG, "Camera started successfully")

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
                Toast.makeText(this, "Camera initialization failed", Toast.LENGTH_SHORT).show()
            }

        }, ContextCompat.getMainExecutor(this))
    }

    /**
     * Toggle flashlight on/off
     */
    private fun toggleFlashlight() {
        camera?.let { cam ->
            val currentTorchState = cam.cameraInfo.torchState.value
            val newTorchState = currentTorchState != TorchState.ON
            cam.cameraControl.enableTorch(newTorchState)

            Log.d(TAG, "Flashlight ${if (newTorchState) "ON" else "OFF"}")
        }
    }

    /**
     * Check if all required permissions are granted
     */
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Re-acquire wake lock when activity resumes
        if (!wakeLockManager.isWakeLockHeld()) {
            wakeLockManager.acquireWakeLock()
        }

        // Re-configure screen settings
        wakeLockManager.configureScreenOn(window, true)
    }

    override fun onPause() {
        super.onPause()
        // Keep wake lock active for quick resume
        // Don't release here unless you want the screen to turn off
    }

    override fun onDestroy() {
        super.onDestroy()

        // Clean up resources
        cameraExecutor.shutdown()
        wakeLockManager.cleanup()

        Log.d(TAG, "MainActivity destroyed and resources cleaned up")
    }

    /**
     * Handle back press with confirmation if actively scanning
     */
    override fun onBackPressed() {
        if (viewModel.isScanning.value == true) {
            // Show confirmation dialog or just finish
            Toast.makeText(this, "Stopping scan session...", Toast.LENGTH_SHORT).show()
            viewModel.stopScanning()
        }
        super.onBackPressed()
    }
}