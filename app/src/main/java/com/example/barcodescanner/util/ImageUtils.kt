package com.example.barcodescanner.util

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.graphics.*
import com.google.mlkit.vision.barcode.common.Barcode
import android.util.Log
import java.io.IOException


object ImageUtils {

    private const val TAG = "ImageUtils"

    fun saveBitmapToCache(context: Context, bitmap: Bitmap): String? {
        return try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "barcode_$timeStamp.jpg"
            val file = File(context.cacheDir, fileName)
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun drawBarcodeBoundingBoxes(original: Bitmap, barcodes: List<Barcode>): Bitmap {
        val mutableBitmap = original.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.RED
            strokeWidth = 5f
        }
        for (barcode in barcodes) {
            barcode.boundingBox?.let {
                canvas.drawRect(it, paint)
            }
        }
        return mutableBitmap
    }

    /**
     * Save bitmap with custom prefix for different processing stages
     */
    fun saveBitmapToCache(context: Context, bitmap: Bitmap, prefix: String = "barcode_"): String? {
        return try {
            val filename = "${prefix}${System.currentTimeMillis()}.jpg"
            val file = File(context.cacheDir, filename)

            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
            }

            Log.d(TAG, "Saved bitmap to cache: ${file.absolutePath}")
            file.absolutePath
        } catch (e: IOException) {
            Log.e(TAG, "Failed to save bitmap to cache", e)
            null
        }
    }
    /**
     * Save multiple processed bitmaps for debugging
     */
    fun saveProcessedBitmaps(context: Context, bitmaps: List<Bitmap>, sessionId: Long): List<String> {
        val savedPaths = mutableListOf<String>()

        bitmaps.forEachIndexed { index, bitmap ->
            val path = saveBitmapToCache(context, bitmap, "processed_${sessionId}_step${index}_")
            path?.let { savedPaths.add(it) }
        }

        return savedPaths
    }

    /**
     * Get optimal image size based on device capabilities
     */
    fun getOptimalImageSize(maxWidth: Int = 4000, maxHeight: Int = 3000): Pair<Int, Int> {
        // You can add device-specific logic here
        return Pair(maxWidth, maxHeight)
    }

    /**
     * Check if bitmap is suitable for barcode detection
     */
    fun validateBitmapForDetection(bitmap: Bitmap?): Boolean {
        return bitmap != null &&
                bitmap.width >= 100 &&
                bitmap.height >= 100 &&
                !bitmap.isRecycled
    }

    /**
     * Calculate memory usage of bitmap
     */
    fun getBitmapMemorySize(bitmap: Bitmap): Long {
        return bitmap.allocationByteCount.toLong()
    }

    /**
     * Clean up old cache files to prevent storage bloat
     */
    fun cleanupOldCacheFiles(context: Context, olderThanHours: Int = 24) {
        try {
            val cacheDir = context.cacheDir
            val cutoffTime = System.currentTimeMillis() - (olderThanHours * 60 * 60 * 1000L)

            cacheDir.listFiles()?.forEach { file ->
                if (file.isFile && file.lastModified() < cutoffTime) {
                    if (file.delete()) {
                        Log.d(TAG, "Deleted old cache file: ${file.name}")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to cleanup cache files", e)
        }
    }
}
