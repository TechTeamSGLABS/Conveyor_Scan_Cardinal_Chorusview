package com.example.barcodescanner.view

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.barcodescanner.R
import com.google.mlkit.vision.barcode.common.Barcode
import java.util.*
import com.example.barcodescanner.model.DetectedBarcode
/*

// Enhanced DetectedBarcode data class
data class DetectedBarcode(
    val boundingBox: RectF,
    val value: String,
    val format: Int,
    val alreadyScanned: Boolean = false,
    val distanceEstimate: String = "Unknown",
    val timeDetected: Long = System.currentTimeMillis(),
    val id: String = UUID.randomUUID().toString()
)
*/

class OverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val currentBarcodes = mutableListOf<DetectedBarcode>()
    private val tickMarkAnimations = mutableMapOf<String, Float>()
    private val persistentTickMarks = mutableMapOf<String, DetectedBarcode>() // ✅ NEW: Store persistent tick marks
    private val barcodeLastPosition = mutableMapOf<String, RectF>() // ✅ NEW: Remember last positions

    // Paint for scanned tick marks (green)
    private val scannedTickPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
        color = Color.parseColor("#4CAF50") // Green
    }

    // Paint for duplicate tick marks (orange)
    private val duplicateTickPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
        color = Color.parseColor("#FF9800") // Orange
    }

    // Background for tick marks
    private val tickBackgroundPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    // Crosshair paint
    private val crosshairPaint = Paint().apply {
        color = Color.parseColor("#AAFFFFFF")
        style = Paint.Style.STROKE
        strokeWidth = 2f
        isAntiAlias = true
    }

    private val handler = Handler(Looper.getMainLooper())

    private val animationRunnable = object : Runnable {
        override fun run() {
            updateAnimations()
            handler.postDelayed(this, 16) // 60 FPS
        }
    }

    init {
        handler.post(animationRunnable)
    }

    private fun updateAnimations() {
        var needsInvalidate = false

        // ✅ ENHANCED: Update tick mark animations - keep them at 1.0f when complete
        val tickIterator = tickMarkAnimations.iterator()
        while (tickIterator.hasNext()) {
            val entry = tickIterator.next()
            var progress = entry.value
            progress += 0.08f // Animation speed

            // ✅ FIXED: Keep animation at 1.0f instead of removing
            if (progress >= 1.0f) {
                tickMarkAnimations[entry.key] = 1.0f // Keep at completed state
            } else {
                tickMarkAnimations[entry.key] = progress
            }
            needsInvalidate = true
        }

        if (needsInvalidate) {
            invalidate()
        }
    }

    fun drawBarcodes(detectedBarcodes: List<DetectedBarcode>) {
        // Update current barcodes
        currentBarcodes.clear()
        currentBarcodes.addAll(detectedBarcodes)

        // ✅ ENHANCED: Update persistent tick marks for currently detected barcodes
        val currentBarcodeValues = detectedBarcodes.map { it.value }.toSet()

        // Add newly scanned barcodes to persistent storage and start animation
        for (barcode in detectedBarcodes) {
            if (barcode.alreadyScanned) {
                persistentTickMarks[barcode.value] = barcode
                barcodeLastPosition[barcode.value] = barcode.boundingBox // Remember position

                // Start animation if not already animating
                if (!tickMarkAnimations.containsKey(barcode.value)) {
                    tickMarkAnimations[barcode.value] = 0f
                }
            }
        }

        // ✅ ENHANCED: Remove persistent tick marks only after extended timeout
        val currentTime = System.currentTimeMillis()
        val persistentIterator = persistentTickMarks.iterator()
        while (persistentIterator.hasNext()) {
            val entry = persistentIterator.next()
            val barcode = entry.value

            // Remove tick mark if barcode hasn't been seen for 5+ seconds
            val timeSinceDetection = currentTime - barcode.timeDetected
            if (!currentBarcodeValues.contains(entry.key) && timeSinceDetection > 5000) {
                persistentIterator.remove()
                tickMarkAnimations.remove(entry.key)
                barcodeLastPosition.remove(entry.key)
            }
        }

        invalidate()
    }

    fun clear() {
        currentBarcodes.clear()
        tickMarkAnimations.clear()
        persistentTickMarks.clear() // ✅ Clear persistent tick marks
        barcodeLastPosition.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw scanning interface
        drawScanningInterface(canvas)

        // ✅ ENHANCED: Draw persistent tick marks for all scanned barcodes
        for ((value, barcode) in persistentTickMarks) {
            drawTickMark(canvas, barcode, value)
        }
    }

    private fun drawScanningInterface(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f

        // Draw crosshair
        val crosshairSize = 80f
        canvas.drawLine(centerX - crosshairSize, centerY, centerX + crosshairSize, centerY, crosshairPaint)
        canvas.drawLine(centerX, centerY - crosshairSize, centerX, centerY + crosshairSize, crosshairPaint)

        // Draw center circle
        canvas.drawCircle(centerX, centerY, 40f, crosshairPaint)
    }

    private fun drawTickMark(canvas: Canvas, barcode: DetectedBarcode, value: String) {
        val animationProgress = tickMarkAnimations[value] ?: 1f
        val isAnimating = animationProgress < 1f

        // Calculate tick mark position (center of barcode)
        val centerX = barcode.boundingBox.centerX()
        val centerY = barcode.boundingBox.centerY()
        val tickSize = 50f

        // Determine if this is a duplicate (exists in DB but from different session)
        val isDuplicate = barcode.alreadyScanned

        // Choose paint and background color
        val tickPaint = if (isDuplicate) duplicateTickPaint else scannedTickPaint
        val backgroundColor = if (isDuplicate) {
            Color.parseColor("#FFF3E0") // Light orange for duplicates
        } else {
            Color.parseColor("#E8F5E8") // Light green for new scans
        }

        // ✅ ENHANCED: Full opacity for completed animations, animated opacity for ongoing animations
        val alpha = if (isAnimating) (animationProgress * 255).toInt() else 255
        tickPaint.alpha = alpha

        // Draw background circle
        tickBackgroundPaint.color = backgroundColor
        tickBackgroundPaint.alpha = (alpha * 0.8f).toInt()
        canvas.drawCircle(centerX, centerY, tickSize * 0.8f, tickBackgroundPaint)

        // Calculate animation progress for drawing
        val drawProgress = if (isAnimating) {
            // Smooth easing animation
            val t = animationProgress
            t * t * (3.0f - 2.0f * t) // Smoothstep function
        } else {
            1f
        }

        // Draw tick mark
        val path = Path()

        // First stroke of tick (shorter one)
        val firstStrokeProgress = Math.min(drawProgress * 2f, 1f)
        val firstStartX = centerX - tickSize * 0.3f
        val firstStartY = centerY
        val firstEndX = centerX - tickSize * 0.1f
        val firstEndY = centerY + tickSize * 0.2f

        if (firstStrokeProgress > 0f) {
            path.moveTo(firstStartX, firstStartY)
            path.lineTo(
                firstStartX + (firstEndX - firstStartX) * firstStrokeProgress,
                firstStartY + (firstEndY - firstStartY) * firstStrokeProgress
            )
        }

        // Second stroke of tick (longer one)
        if (drawProgress > 0.5f) {
            val secondStrokeProgress = Math.min((drawProgress - 0.5f) * 2f, 1f)
            val secondStartX = firstEndX
            val secondStartY = firstEndY
            val secondEndX = centerX + tickSize * 0.4f
            val secondEndY = centerY - tickSize * 0.3f

            if (secondStrokeProgress > 0f) {
                path.moveTo(secondStartX, secondStartY)
                path.lineTo(
                    secondStartX + (secondEndX - secondStartX) * secondStrokeProgress,
                    secondStartY + (secondEndY - secondStartY) * secondStrokeProgress
                )
            }
        }

        canvas.drawPath(path, tickPaint)

        // ✅ ENHANCED: Add subtle glow effect for all visible ticks
        if (alpha > 100) { // Show glow when tick is mostly visible
            val glowPaint = Paint(tickPaint)
            glowPaint.strokeWidth = 12f
            glowPaint.alpha = 30
            canvas.drawPath(path, glowPaint)
        }

        // ✅ NEW: Add pulsing effect for very recent detections
        if ((System.currentTimeMillis() - barcode.timeDetected) < 2000) {
            val pulseAlpha = (Math.sin((System.currentTimeMillis() - barcode.timeDetected) / 200.0) * 50 + 50).toInt()
            val pulsePaint = Paint(tickPaint)
            pulsePaint.strokeWidth = 15f
            pulsePaint.alpha = pulseAlpha
            canvas.drawPath(path, pulsePaint)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handler.removeCallbacks(animationRunnable)
    }
}