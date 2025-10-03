package com.example.barcodescanner.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.barcodescanner.R
import com.example.barcodescanner.data.local.AppDatabase
import com.example.barcodescanner.data.local.BarcodeEntity
import com.example.barcodescanner.util.ImageUtils
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.max
import kotlin.math.min
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import android.hardware.camera2.CaptureRequest
import androidx.camera.camera2.interop.Camera2Interop
import androidx.camera.core.FocusMeteringAction
import java.util.concurrent.TimeUnit
import androidx.appcompat.app.AlertDialog
import java.io.ByteArrayOutputStream
import kotlin.math.sqrt



// 🔗 Data class for OLPN/TOTEID association
data class BarcodeAssociation(
    val olpn: Barcode? = null,
    val toteId: Barcode? = null,
    val otherBarcodes: List<Barcode> = emptyList(),
    val isComplete: Boolean = false
) {
    fun getAllBarcodes(): List<Barcode> {
        val result = mutableListOf<Barcode>()
        olpn?.let { result.add(it) }
        toteId?.let { result.add(it) }
        result.addAll(otherBarcodes)
        return result
    }

    fun getDisplayText(): String = when {
        isComplete -> "OLPN + TOTE SET"
        olpn != null && toteId == null -> "OLPN "
        olpn == null && toteId != null -> "TOTE "
        else -> "Other Barcodes (${otherBarcodes.size})"
    }
}

class PhotoCaptureActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var captureButton: Button
    private lateinit var readBarcodesButton: Button
    private lateinit var capturedImageView: ImageView
    private lateinit var backButton: Button
    private lateinit var zoomInButton: Button
    private lateinit var zoomOutButton: Button
    private lateinit var zoomResetButton: Button
    private lateinit var zoomLevelText: TextView
    private lateinit var focusButton: Button

    private var imageCapture: ImageCapture? = null
    private var cameraInstance: androidx.camera.core.Camera? = null
    private lateinit var cameraExecutor: ExecutorService

    // ⚡ PERFORMANCE: Simplified single-image system
    private var capturedOptimizedBitmap: Bitmap? = null
    private var sessionId: Long = 0L

    // ⚡ PERFORMANCE: Optimized camera properties
    private var currentZoomRatio = 1.5f // Start with moderate zoom
    private var minZoomRatio = 1.0f
    private var maxZoomRatio = 1.0f
    private val zoomStep = 0.3f

    // ⚡ PERFORMANCE: Streamlined compression
    companion object {
        private const val TAG = "FastPhotoCapture"
        private const val REQUEST_CODE_PERMISSIONS = 1001
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val MAX_IMAGE_SIZE_KB = 512 // Increased from 256KB for better quality
        private const val OPTIMAL_WIDTH = 2048 // Reduced from 8000 for speed
        private const val OPTIMAL_HEIGHT = 1536 // Reduced from 6000 for speed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_capture)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "QIC Scan - Fast Capture (< 5s)"
            subtitle = "OLPN + TOTE Association"
        }

        sessionId = System.currentTimeMillis()

        initializeViews()
        setupClickListeners()

        cameraExecutor = Executors.newSingleThreadExecutor()

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun initializeViews() {
        previewView = findViewById(R.id.previewView)
        captureButton = findViewById(R.id.captureButton)
        readBarcodesButton = findViewById(R.id.readBarcodesButton)
        capturedImageView = findViewById(R.id.capturedImageView)
        backButton = findViewById(R.id.backButton)
        zoomInButton = findViewById(R.id.zoomInButton)
        zoomOutButton = findViewById(R.id.zoomOutButton)
        zoomResetButton = findViewById(R.id.zoomResetButton)
        zoomLevelText = findViewById(R.id.zoomLevelText)
        focusButton = findViewById(R.id.focusButton) ?: Button(this).apply { text = "FOCUS" }
    }

    private fun setupClickListeners() {
        captureButton.setOnClickListener {
            fastPhotoCapture()
        }

        readBarcodesButton.setOnClickListener {
            capturedOptimizedBitmap?.let { bitmap ->
                fastBarcodeDetection(bitmap)
            } ?: run {
                Toast.makeText(this, "Please take a photo first", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener { finish() }
        zoomInButton.setOnClickListener { zoomIn() }
        zoomOutButton.setOnClickListener { zoomOut() }
        zoomResetButton.setOnClickListener { resetZoom() }
        focusButton.setOnClickListener { quickFocus() }

        // ⚡ PERFORMANCE: Simplified tap-to-focus
        previewView.setOnTouchListener { _, event ->
            if (event.action == android.view.MotionEvent.ACTION_DOWN) {
                val factory = previewView.meteringPointFactory
                val point = factory.createPoint(event.x, event.y)
                val action = FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
                    .setAutoCancelDuration(2, TimeUnit.SECONDS) // Reduced from 5 seconds
                    .build()
                cameraInstance?.cameraControl?.startFocusAndMetering(action)
                true
            } else false
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // ⚡ PERFORMANCE: Optimized preview settings
            val preview = Preview.Builder()
                .setTargetResolution(Size(1280, 960)) // Moderate resolution for smooth preview
                .build()
                .also { it.setSurfaceProvider(previewView.surfaceProvider) }

            // ⚡ PERFORMANCE: Balanced ImageCapture settings
            val imageCaptureBuilder = ImageCapture.Builder()
                .setTargetResolution(Size(OPTIMAL_WIDTH, OPTIMAL_HEIGHT)) // Balanced resolution
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY) // Speed over max quality
                .setJpegQuality(85) // Good quality, faster processing
                .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                .setTargetRotation(this.windowManager.defaultDisplay.rotation)

            // ⚡ PERFORMANCE: Essential Camera2 optimizations only
            Camera2Interop.Extender(imageCaptureBuilder)
                .setCaptureRequestOption(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                .setCaptureRequestOption(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON)

            imageCapture = imageCaptureBuilder.build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraInstance = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

                setupZoomInfo()
                applyInitialZoom()

            } catch (exc: Exception) {
                Log.e(TAG, "Camera binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun applyInitialZoom() {
        cameraInstance?.let { camera ->
            val initialZoom = min(1.5f, maxZoomRatio)
            camera.cameraControl.setZoomRatio(initialZoom)
            currentZoomRatio = initialZoom
        }
    }

    private fun quickFocus() {
        cameraInstance?.cameraControl?.let { control ->
            val factory = previewView.meteringPointFactory
            val centerPoint = factory.createPoint(previewView.width / 2f, previewView.height / 2f)

            val action = FocusMeteringAction.Builder(centerPoint, FocusMeteringAction.FLAG_AF)
                .setAutoCancelDuration(2, TimeUnit.SECONDS)
                .build()

            control.startFocusAndMetering(action)
            Toast.makeText(this, "Quick focus applied", Toast.LENGTH_SHORT).show()
        }
    }

    // ⚡ PERFORMANCE: Streamlined fast photo capture
    private fun fastPhotoCapture() {
        val imageCapture = imageCapture ?: return
        val startTime = System.currentTimeMillis()

        // ⚡ PERFORMANCE: No delay, immediate capture
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    val captureTime = System.currentTimeMillis() - startTime

                    // ⚡ PERFORMANCE: Single optimized image processing
                    val optimizedBitmap = imageProxyToOptimizedBitmap(image)
                    capturedOptimizedBitmap = optimizedBitmap

                    runOnUiThread {
                        capturedImageView.setImageBitmap(optimizedBitmap)
                        capturedImageView.visibility = View.VISIBLE
                        previewView.visibility = View.GONE
                        captureButton.visibility = View.GONE
                        readBarcodesButton.visibility = View.VISIBLE
                        hideZoomControls()

                        val info = "${optimizedBitmap.width}x${optimizedBitmap.height}"
                        val sizeKB = getImageSizeKB(optimizedBitmap)

                        Toast.makeText(
                            this@PhotoCaptureActivity,
                            "⚡ Fast capture: $info (${sizeKB}KB) in ${captureTime}ms",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    image.close()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "Fast photo capture failed: ${exception.message}", exception)
                    runOnUiThread {
                        Toast.makeText(this@PhotoCaptureActivity, "Capture failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }

    // ⚡ PERFORMANCE: Single optimized image processing pipeline
    private fun imageProxyToOptimizedBitmap(image: ImageProxy): Bitmap {
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        val originalBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

        // Correct rotation
        val rotatedBitmap = correctImageOrientation(originalBitmap, image.imageInfo.rotationDegrees)

        // Optimize size and quality in one step
        return optimizeBitmap(rotatedBitmap)
    }

    // ⚡ PERFORMANCE: Single-step bitmap optimization
    private fun optimizeBitmap(bitmap: Bitmap): Bitmap {
        // Step 1: Resize if too large
        val scaledBitmap = if (bitmap.width > OPTIMAL_WIDTH || bitmap.height > OPTIMAL_HEIGHT) {
            val scale = min(
                OPTIMAL_WIDTH.toFloat() / bitmap.width,
                OPTIMAL_HEIGHT.toFloat() / bitmap.height
            )
            val newWidth = (bitmap.width * scale).toInt()
            val newHeight = (bitmap.height * scale).toInt()
            Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
        } else {
            bitmap
        }

        // Step 2: Compress if needed
        return if (getImageSizeKB(scaledBitmap) > MAX_IMAGE_SIZE_KB) {
            compressToTargetSize(scaledBitmap)
        } else {
            scaledBitmap
        }
    }

    private fun compressToTargetSize(bitmap: Bitmap): Bitmap {
        val stream = ByteArrayOutputStream()
        var quality = 85

        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)

        while (stream.size() > MAX_IMAGE_SIZE_KB * 1024 && quality > 30) {
            stream.reset()
            quality -= 10
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        }

        val compressedBytes = stream.toByteArray()
        return BitmapFactory.decodeByteArray(compressedBytes, 0, compressedBytes.size)
    }

    // ⚡ PERFORMANCE: Streamlined fast barcode detection
    private fun fastBarcodeDetection(bitmap: Bitmap) {
        readBarcodesButton.isEnabled = false
        readBarcodesButton.text = "Fast Detection..."
        val startTime = System.currentTimeMillis()

        lifecycleScope.launch {
            try {
                // ⚡ PERFORMANCE: Single detection strategy
                val detectedBarcodes = performFastDetection(bitmap)
                val detectionTime = System.currentTimeMillis() - startTime

                // 🔗 ASSOCIATION: Group OLPN and TOTE barcodes
                val associations = associateOLPNAndTOTE(detectedBarcodes)

                withContext(Dispatchers.Main) {
                    if (associations.isNotEmpty()) {
                        processBarcodeAssociations(bitmap, associations, detectionTime)
                    } else {
                        showNoBarcodesDetectedDialog()
                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "Fast barcode detection failed", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@PhotoCaptureActivity, "Detection failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    readBarcodesButton.isEnabled = true
                    readBarcodesButton.text = "Try Again"
                }
            }
        }
    }

    // ⚡ PERFORMANCE: Single optimized detection method
    private suspend fun performFastDetection(bitmap: Bitmap): List<Barcode> = withContext(Dispatchers.IO) {
        val inputImage = InputImage.fromBitmap(bitmap, 0)

        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                // All barcode formats
                Barcode.FORMAT_CODE_128, Barcode.FORMAT_CODE_39, Barcode.FORMAT_CODE_93,
                Barcode.FORMAT_EAN_13, Barcode.FORMAT_EAN_8, Barcode.FORMAT_UPC_A, Barcode.FORMAT_UPC_E,
                Barcode.FORMAT_ITF, Barcode.FORMAT_CODABAR,
                Barcode.FORMAT_QR_CODE, Barcode.FORMAT_DATA_MATRIX, Barcode.FORMAT_PDF417, Barcode.FORMAT_AZTEC
            )
            .build()

        val scanner = BarcodeScanning.getClient(options)
        val results = scanner.process(inputImage).await()

        Log.d(TAG, "Fast detection found ${results.size} barcodes")
        results
    }

    // 🔗 ASSOCIATION LOGIC: Group OLPN and TOTE barcodes
    private fun associateOLPNAndTOTE(barcodes: List<Barcode>): List<BarcodeAssociation> {
        val associations = mutableListOf<BarcodeAssociation>()
        val processedBarcodes = mutableSetOf<Barcode>()

        // Identify OLPN and TOTE patterns
        val olpnBarcodes = barcodes.filter { isOLPN(it) }
        val toteBarcodes = barcodes.filter { isTOTE(it) }
        val otherBarcodes = barcodes.filter { !isOLPN(it) && !isTOTE(it) }

        Log.d(TAG, "Association: OLPN=${olpnBarcodes.size}, TOTE=${toteBarcodes.size}, Other=${otherBarcodes.size}")

        // Strategy 1: Direct pairing (if counts match)
        if (olpnBarcodes.size == toteBarcodes.size && olpnBarcodes.isNotEmpty()) {
            for (i in olpnBarcodes.indices) {
                associations.add(
                    BarcodeAssociation(
                        olpn = olpnBarcodes[i],
                        toteId = toteBarcodes[i],
                        isComplete = true
                    )
                )
                processedBarcodes.add(olpnBarcodes[i])
                processedBarcodes.add(toteBarcodes[i])
            }
        } else {
            // Strategy 2: Best match pairing based on proximity
            val pairedOLPN = mutableSetOf<Barcode>()
            val pairedTOTE = mutableSetOf<Barcode>()

            for (olpn in olpnBarcodes) {
                val closestTote = toteBarcodes
                    .filter { !pairedTOTE.contains(it) }
                    .minByOrNull { calculateDistance(olpn, it) }

                if (closestTote != null) {
                    associations.add(
                        BarcodeAssociation(
                            olpn = olpn,
                            toteId = closestTote,
                            isComplete = true
                        )
                    )
                    pairedOLPN.add(olpn)
                    pairedTOTE.add(closestTote)
                    processedBarcodes.add(olpn)
                    processedBarcodes.add(closestTote)
                }
            }

            // Add unpaired OLPN
            olpnBarcodes.filter { !pairedOLPN.contains(it) }.forEach {
                associations.add(BarcodeAssociation(olpn = it, isComplete = false))
                processedBarcodes.add(it)
            }

            // Add unpaired TOTE
            toteBarcodes.filter { !pairedTOTE.contains(it) }.forEach {
                associations.add(BarcodeAssociation(toteId = it, isComplete = false))
                processedBarcodes.add(it)
            }
        }

        // Add other barcodes
        if (otherBarcodes.isNotEmpty()) {
            associations.add(
                BarcodeAssociation(otherBarcodes = otherBarcodes, isComplete = false)
             )
            processedBarcodes.addAll(otherBarcodes)
        }

        Log.d(TAG, "Created ${associations.size} associations, ${associations.count { it.isComplete }} complete pairs")
        return associations
    }

    // 🔗 Pattern recognition for OLPN
    private fun isOLPN(barcode: Barcode): Boolean {
        val value = barcode.rawValue?.uppercase() ?: return false
        return value.contains("OLPN") ||
                value.matches(Regex("^[A-Z0-9]{8,12}$")) || // Typical OLPN pattern
                value.startsWith("P") && value.length >= 8
    }

    // 🔗 Pattern recognition for TOTE
    private fun isTOTE(barcode: Barcode): Boolean {
        val value = barcode.rawValue?.uppercase() ?: return false
        return value.contains("TOTE") ||
                value.contains("TOT") ||
                value.startsWith("T") && value.length >= 6 ||
                value.matches(Regex("^\\d{6,10}$")) // Numeric TOTE ID pattern
    }

    // 🔗 Calculate distance between two barcodes for proximity pairing
    private fun calculateDistance(barcode1: Barcode, barcode2: Barcode): Double {
        val box1 = barcode1.boundingBox
        val box2 = barcode2.boundingBox

        if (box1 == null || box2 == null) return Double.MAX_VALUE

        val centerX1 = box1.left + box1.width() / 2f
        val centerY1 = box1.top + box1.height() / 2f
        val centerX2 = box2.left + box2.width() / 2f
        val centerY2 = box2.top + box2.height() / 2f

        val dx = centerX1 - centerX2
        val dy = centerY1 - centerY2

        return sqrt((dx * dx + dy * dy).toDouble())
    }

    private suspend fun processBarcodeAssociations(
        originalBitmap: Bitmap,
        associations: List<BarcodeAssociation>,
        detectionTime: Long
    ) {
        Log.d(TAG, "Processing ${associations.size} barcode associations")

        // Create overlay with association groupings
        val overlayBitmap = drawAssociationOverlay(originalBitmap, associations)

        // Save overlay image
        val overlayImagePath = withContext(Dispatchers.IO) {
            ImageUtils.saveBitmapToCache(this@PhotoCaptureActivity, overlayBitmap, "association_overlay_")
        }

        // Save associations to database
        val db = AppDatabase.getInstance(this)
        val dao = db.barcodeDao()

        withContext(Dispatchers.IO) {
            associations.forEachIndexed { groupIndex, association ->
                val allBarcodes = association.getAllBarcodes()
                val groupId = "$sessionId-$groupIndex"

                allBarcodes.forEach { barcode ->
                    val value = barcode.rawValue ?: return@forEach

                    try {
                        // Save individual barcode with group information
                        val croppedBitmap = barcode.boundingBox?.let { box ->
                            if (isValidBoundingBox(box, originalBitmap)) {
                                Bitmap.createBitmap(originalBitmap, box.left, box.top, box.width(), box.height())
                            } else null
                        }

                        val imagePath = croppedBitmap?.let {
                            ImageUtils.saveBitmapToCache(this@PhotoCaptureActivity, it)
                        }

                        // Use existing BarcodeEntity fields - don't add new fields that don't exist
                        val barcodeEntity = BarcodeEntity(
                            value = value,
                            format = barcode.format,
                            timestamp = System.currentTimeMillis(),
                            sessionId = sessionId,
                            imagePath = imagePath
                        )

                        dao.insertBarcode(barcodeEntity)
                        Log.d(TAG, "Saved barcode: $value (Group: $groupId)")

                    } catch (e: Exception) {
                        Log.e(TAG, "Error saving barcode: $value", e)
                    }
                }
            }
        }

        runOnUiThread {
            capturedImageView.setImageBitmap(overlayBitmap)

            val totalTime = detectionTime
            val completeAssociations = associations.count { it.isComplete }
            val totalBarcodes = associations.sumOf { it.getAllBarcodes().size }

           /* Toast.makeText(
                this,
                "⚡ Found $totalBarcodes barcodes in ${totalTime}ms!\n🔗 Created $completeAssociations OLPN+TOTE pairs",
                Toast.LENGTH_LONG
            ).show()*/

            // Navigate to results
            capturedImageView.postDelayed({
                val intent = Intent(this, ResultsActivity::class.java).apply {
                    putExtra("session_id", sessionId)
                    putExtra("from_photo_capture", true)
                    putExtra("boxed_image_path", overlayImagePath)
                }
                startActivity(intent)
                finish()
            }, 1500)
        }
    }

    // 🔗 Draw overlay showing OLPN/TOTE associations
    private fun drawAssociationOverlay(originalBitmap: Bitmap, associations: List<BarcodeAssociation>): Bitmap {
        val overlayBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(overlayBitmap)

        associations.forEachIndexed { index, association ->
            var color = when {
                association.isComplete -> Color.GREEN
                association.olpn != null -> Color.rgb(255, 165, 0) // Orange
                association.toteId != null -> Color.BLUE
                else -> Color.GRAY
            }

            val paint = Paint().apply {
                this.color = color
                style = Paint.Style.STROKE
                strokeWidth = 6f
                isAntiAlias = true
            }

            val textPaint = Paint().apply {
                this.color = Color.WHITE
                textSize = 40f
                isAntiAlias = true
                typeface = Typeface.DEFAULT_BOLD
            }

            val backgroundPaint = Paint().apply {
                this.color = Color.BLACK
                alpha = 180
            }

            // Draw all barcodes in this association
            association.getAllBarcodes().forEach { barcode ->
                barcode.boundingBox?.let { box ->
                    // Draw bounding box
                    val rect = RectF(box)
                    canvas.drawRect(rect, paint)

                    // Draw association label
                    val label = association.getDisplayText()
                    val textBounds = Rect()
                    textPaint.getTextBounds(label, 0, label.length, textBounds)

                    val textX = box.left.toFloat()
                    val textY = box.top - 15f

                    // Draw background for text
                    canvas.drawRect(
                        textX - 10,
                        textY - textBounds.height() - 10,
                        textX + textBounds.width() + 20,
                        textY + 10,
                        backgroundPaint
                    )

                    // Draw text
                    canvas.drawText(label, textX, textY, textPaint)
                }
            }

            // Draw connection line for complete pairs
            if (association.isComplete && association.olpn != null && association.toteId != null) {
                val olpnBox = association.olpn.boundingBox
                val toteBox = association.toteId.boundingBox

                if (olpnBox != null && toteBox != null) {
                    val connectionPaint = Paint().apply {
                        color = Color.GREEN
                        strokeWidth = 4f
                        isAntiAlias = true
                    }

                    val olpnCenterX = olpnBox.left + olpnBox.width() / 2f
                    val olpnCenterY = olpnBox.top + olpnBox.height() / 2f
                    val toteCenterX = toteBox.left + toteBox.width() / 2f
                    val toteCenterY = toteBox.top + toteBox.height() / 2f

                    canvas.drawLine(
                        olpnCenterX,
                        olpnCenterY,
                        toteCenterX,
                        toteCenterY,
                        connectionPaint
                    )
                }
            }
        }

        return overlayBitmap
    }

    private fun isValidBoundingBox(box: Rect, bitmap: Bitmap): Boolean {
        return box.left >= 0 && box.top >= 0 &&
                box.right <= bitmap.width && box.bottom <= bitmap.height &&
                box.width() > 0 && box.height() > 0
    }

    private fun showNoBarcodesDetectedDialog() {
        readBarcodesButton.isEnabled = true
        readBarcodesButton.text = "Try Again"

        AlertDialog.Builder(this)
            .setTitle("No Barcodes Detected")
            .setMessage("No barcodes were detected. Try adjusting the zoom or focus and capture again.")
            .setPositiveButton("Retry") { _, _ ->
                capturedImageView.visibility = View.GONE
                previewView.visibility = View.VISIBLE
                captureButton.visibility = View.VISIBLE
                readBarcodesButton.visibility = View.GONE
                showZoomControls()
                capturedOptimizedBitmap = null
            }
            .setNegativeButton("Back") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    // Helper extension function for Task.await()
    private suspend fun <T> com.google.android.gms.tasks.Task<T>.await(): T {
        return suspendCancellableCoroutine { cont ->
            addOnSuccessListener { cont.resume(it) }
            addOnFailureListener { cont.resumeWithException(it) }
        }
    }

    // Utility methods
    private fun getImageSizeKB(bitmap: Bitmap): Int {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        return stream.size() / 1024
    }

    private fun correctImageOrientation(bitmap: Bitmap, rotationDegrees: Int): Bitmap {
        return if (rotationDegrees == 0) {
            bitmap
        } else {
            val matrix = Matrix()
            matrix.postRotate(rotationDegrees.toFloat())
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
    }

    private fun setupZoomInfo() {
        cameraInstance?.let { camera ->
            val zoomState = camera.cameraInfo.zoomState
            zoomState.observe(this) { state ->
                minZoomRatio = state.minZoomRatio
                maxZoomRatio = state.maxZoomRatio
                currentZoomRatio = state.zoomRatio
                updateZoomUI()
            }
        }
    }

    private fun updateZoomUI() {
        runOnUiThread {
            zoomLevelText.text = String.format("%.1fx", currentZoomRatio)
            zoomInButton.isEnabled = currentZoomRatio < maxZoomRatio
            zoomOutButton.isEnabled = currentZoomRatio > minZoomRatio
            zoomResetButton.isEnabled = currentZoomRatio != 1.0f
        }
    }

    private fun zoomIn() {
        cameraInstance?.let { camera ->
            val newZoomRatio = min(currentZoomRatio + zoomStep, maxZoomRatio)
            camera.cameraControl.setZoomRatio(newZoomRatio)
        }
    }

    private fun zoomOut() {
        cameraInstance?.let { camera ->
            val newZoomRatio = max(currentZoomRatio - zoomStep, minZoomRatio)
            camera.cameraControl.setZoomRatio(newZoomRatio)
        }
    }

    private fun resetZoom() {
        cameraInstance?.let { camera ->
            camera.cameraControl.setZoomRatio(1.0f)
        }
    }

    private fun hideZoomControls() {
        zoomInButton.visibility = View.GONE
        zoomOutButton.visibility = View.GONE
        zoomResetButton.visibility = View.GONE
        zoomLevelText.visibility = View.GONE
        focusButton.visibility = View.GONE
    }

    private fun showZoomControls() {
        zoomInButton.visibility = View.VISIBLE
        zoomOutButton.visibility = View.VISIBLE
        zoomResetButton.visibility = View.VISIBLE
        zoomLevelText.visibility = View.VISIBLE
        focusButton.visibility = View.VISIBLE
    }

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
                Toast.makeText(this, "Camera permissions required", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        if (capturedImageView.visibility == View.VISIBLE) {
            capturedImageView.visibility = View.GONE
            previewView.visibility = View.VISIBLE
            captureButton.visibility = View.VISIBLE
            readBarcodesButton.visibility = View.GONE
            showZoomControls()
            capturedOptimizedBitmap = null
        } else {
            super.onBackPressed()
        }
    }
}