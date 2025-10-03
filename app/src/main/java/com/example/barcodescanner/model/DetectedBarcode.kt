package com.example.barcodescanner.model

import android.graphics.RectF
import java.util.*

/**
 * Enhanced DetectedBarcode data class for Live View and Photo Capture modes
 * Supports persistent tick marks and improved visual feedback
 */
data class DetectedBarcode(
    val boundingBox: RectF,
    val value: String,
    val format: Int,
    val alreadyScanned: Boolean = false,
    val distanceEstimate: String = "Unknown",
    val timeDetected: Long = System.currentTimeMillis(),
    val id: String = UUID.randomUUID().toString()
) {
    /**
     * Check if this barcode was detected recently (within the last 3 seconds)
     */
    fun isRecentlyDetected(): Boolean {
        return (System.currentTimeMillis() - timeDetected) < 3000L
    }

    /**
     * Get a formatted display string for logging
     */
    fun getDisplayInfo(): String {
        return "DetectedBarcode(value=$value, format=$format, scanned=$alreadyScanned, distance=$distanceEstimate)"
    }

    /**
     * Check if this barcode is the same as another (by value)
     */
    fun isSameBarcode(other: DetectedBarcode): Boolean {
        return this.value == other.value
    }

    companion object {
        /**
         * Create a DetectedBarcode for persistent display when the actual barcode
         * is no longer visible but should still show a tick mark
         */
        fun createPersistentTickMark(
            value: String,
            format: Int,
            lastKnownPosition: RectF,
            originalDetectionTime: Long
        ): DetectedBarcode {
            return DetectedBarcode(
                boundingBox = lastKnownPosition,
                value = value,
                format = format,
                alreadyScanned = true, // Always show as scanned for persistent marks
                distanceEstimate = "Recent",
                timeDetected = originalDetectionTime
            )
        }
    }
}