package com.example.barcodescanner.util

import android.graphics.*
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.*

object BarcodeImageProcessor {

    private const val TAG = "BarcodeImageProcessor"

    /**
     * Apply comprehensive preprocessing pipeline optimized for 1D barcodes at distance
     */
    suspend fun enhanceForDistanceDetection(originalBitmap: Bitmap): ProcessingResult = withContext(Dispatchers.Default) {
        val startTime = System.currentTimeMillis()
        val enhancedBitmaps = mutableListOf<Bitmap>()

        try {
            // 1. Convert to optimal grayscale for barcode detection
            val grayscaleBitmap = convertToOptimalGrayscale(originalBitmap)
            enhancedBitmaps.add(grayscaleBitmap)

            // 2. Apply contrast enhancement using adaptive histogram equalization
            val contrastEnhanced = enhanceContrast(grayscaleBitmap)
            enhancedBitmaps.add(contrastEnhanced)

            // 3. Apply sharpening specifically tuned for 1D barcodes
            val sharpenedBitmap = applySharpeningFilter(contrastEnhanced)
            enhancedBitmaps.add(sharpenedBitmap)

            // 4. Apply edge enhancement for better bar detection
            val edgeEnhanced = enhanceEdges(sharpenedBitmap)
            enhancedBitmaps.add(edgeEnhanced)

            // 5. Apply morphological operations to clean up noise
            val cleanedBitmap = applyMorphologicalCleaning(edgeEnhanced)
            enhancedBitmaps.add(cleanedBitmap)

            // 6. Apply gamma correction for optimal bar-to-background contrast
            val gammaCorreted = applyGammaCorrection(cleanedBitmap, 1.2f)
            enhancedBitmaps.add(gammaCorreted)

            val processingTime = System.currentTimeMillis() - startTime
            Log.d(TAG, "Image preprocessing completed in ${processingTime}ms")

            ProcessingResult(
                originalBitmap = originalBitmap,
                enhancedBitmaps = enhancedBitmaps,
                processingTimeMs = processingTime,
                success = true
            )

        } catch (e: Exception) {
            Log.e(TAG, "Image preprocessing failed", e)
            ProcessingResult(
                originalBitmap = originalBitmap,
                enhancedBitmaps = listOf(originalBitmap),
                processingTimeMs = System.currentTimeMillis() - startTime,
                success = false,
                error = e.message
            )
        }
    }

    /**
     * Convert to grayscale using weights optimized for barcode contrast
     */
    private fun convertToOptimalGrayscale(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val grayscaleBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        // Use optimized weights for barcode detection (emphasize contrast)
        for (i in pixels.indices) {
            val pixel = pixels[i]
            val r = Color.red(pixel)
            val g = Color.green(pixel)
            val b = Color.blue(pixel)

            // Weighted grayscale optimized for barcode detection
            val gray = (0.299 * r + 0.587 * g + 0.114 * b).toInt()
            pixels[i] = Color.argb(255, gray, gray, gray)
        }

        grayscaleBitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return grayscaleBitmap
    }

    /**
     * Apply adaptive histogram equalization for better contrast
     */
    private fun enhanceContrast(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        // Calculate histogram
        val histogram = IntArray(256)
        for (pixel in pixels) {
            val gray = Color.red(pixel) // Assuming grayscale
            histogram[gray]++
        }

        // Calculate cumulative distribution function
        val cdf = IntArray(256)
        cdf[0] = histogram[0]
        for (i in 1 until 256) {
            cdf[i] = cdf[i - 1] + histogram[i]
        }

        // Normalize CDF
        val totalPixels = width * height
        for (i in cdf.indices) {
            cdf[i] = (cdf[i] * 255.0 / totalPixels).toInt()
        }

        // Apply histogram equalization
        for (i in pixels.indices) {
            val gray = Color.red(pixels[i])
            val equalizedGray = cdf[gray]
            pixels[i] = Color.argb(255, equalizedGray, equalizedGray, equalizedGray)
        }

        result.setPixels(pixels, 0, width, 0, 0, width, height)
        return result
    }

    /**
     * Apply sharpening filter optimized for 1D barcodes
     */
    private fun applySharpeningFilter(bitmap: Bitmap): Bitmap {
        // Enhanced sharpening kernel for 1D barcodes (emphasizes vertical edges)
        val kernel = floatArrayOf(
            -0.5f, -1f, -0.5f,
            -1f, 7f, -1f,
            -0.5f, -1f, -0.5f
        )

        return applyConvolutionFilter(bitmap, kernel, 3)
    }

    /**
     * Enhance edges specifically for barcode detection
     */
    private fun enhanceEdges(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        val newPixels = IntArray(width * height)

        // Sobel edge detection with emphasis on vertical edges (important for 1D barcodes)
        val sobelX = arrayOf(
            intArrayOf(-1, 0, 1),
            intArrayOf(-2, 0, 2),
            intArrayOf(-1, 0, 1)
        )

        val sobelY = arrayOf(
            intArrayOf(-1, -2, -1),
            intArrayOf(0, 0, 0),
            intArrayOf(1, 2, 1)
        )

        for (y in 1 until height - 1) {
            for (x in 1 until width - 1) {
                var gx = 0f
                var gy = 0f

                for (ky in -1..1) {
                    for (kx in -1..1) {
                        val pixel = pixels[(y + ky) * width + (x + kx)]
                        val gray = Color.red(pixel)

                        gx += gray * sobelX[ky + 1][kx + 1]
                        gy += gray * sobelY[ky + 1][kx + 1]
                    }
                }

                // Emphasize vertical edges (important for 1D barcodes)
                val magnitude = sqrt(gx * gx + gy * gy * 0.3f).toInt()
                val enhancedValue = min(255, max(0, magnitude))

                newPixels[y * width + x] = Color.argb(255, enhancedValue, enhancedValue, enhancedValue)
            }
        }

        result.setPixels(newPixels, 0, width, 0, 0, width, height)
        return result
    }

    /**
     * Apply morphological operations to clean up noise
     */
    private fun applyMorphologicalCleaning(bitmap: Bitmap): Bitmap {
        // Apply erosion followed by dilation (opening operation)
        val eroded = applyErosion(bitmap, 1)
        val cleaned = applyDilation(eroded, 1)
        return cleaned
    }

    private fun applyErosion(bitmap: Bitmap, radius: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        val newPixels = IntArray(width * height)

        for (y in radius until height - radius) {
            for (x in radius until width - radius) {
                var minValue = 255

                for (ky in -radius..radius) {
                    for (kx in -radius..radius) {
                        val pixel = pixels[(y + ky) * width + (x + kx)]
                        val gray = Color.red(pixel)
                        minValue = min(minValue, gray)
                    }
                }

                newPixels[y * width + x] = Color.argb(255, minValue, minValue, minValue)
            }
        }

        result.setPixels(newPixels, 0, width, 0, 0, width, height)
        return result
    }

    private fun applyDilation(bitmap: Bitmap, radius: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        val newPixels = IntArray(width * height)

        for (y in radius until height - radius) {
            for (x in radius until width - radius) {
                var maxValue = 0

                for (ky in -radius..radius) {
                    for (kx in -radius..radius) {
                        val pixel = pixels[(y + ky) * width + (x + kx)]
                        val gray = Color.red(pixel)
                        maxValue = max(maxValue, gray)
                    }
                }

                newPixels[y * width + x] = Color.argb(255, maxValue, maxValue, maxValue)
            }
        }

        result.setPixels(newPixels, 0, width, 0, 0, width, height)
        return result
    }

    /**
     * Apply gamma correction for optimal contrast
     */
    private fun applyGammaCorrection(bitmap: Bitmap, gamma: Float): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        // Pre-calculate gamma correction lookup table
        val gammaTable = IntArray(256)
        for (i in 0..255) {
            gammaTable[i] = (255.0 * (i / 255.0).pow(1.0 / gamma)).toInt().coerceIn(0, 255)
        }

        for (i in pixels.indices) {
            val gray = Color.red(pixels[i])
            val correctedGray = gammaTable[gray]
            pixels[i] = Color.argb(255, correctedGray, correctedGray, correctedGray)
        }

        result.setPixels(pixels, 0, width, 0, 0, width, height)
        return result
    }

    /**
     * Apply convolution filter with given kernel
     */
    private fun applyConvolutionFilter(bitmap: Bitmap, kernel: FloatArray, kernelSize: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        val newPixels = IntArray(width * height)
        val offset = kernelSize / 2

        for (y in offset until height - offset) {
            for (x in offset until width - offset) {
                var sum = 0f

                for (ky in 0 until kernelSize) {
                    for (kx in 0 until kernelSize) {
                        val pixel = pixels[(y + ky - offset) * width + (x + kx - offset)]
                        val gray = Color.red(pixel)
                        sum += gray * kernel[ky * kernelSize + kx]
                    }
                }

                val newValue = sum.toInt().coerceIn(0, 255)
                newPixels[y * width + x] = Color.argb(255, newValue, newValue, newValue)
            }
        }

        result.setPixels(newPixels, 0, width, 0, 0, width, height)
        return result
    }

    /**
     * Generate multiple scaled versions for multi-scale detection
     */
    suspend fun generateMultiScaleVersions(bitmap: Bitmap): List<Bitmap> = withContext(Dispatchers.Default) {
        val scales = listOf(0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 2.0f)
        val scaledVersions = mutableListOf<Bitmap>()

        scales.forEach { scale ->
            try {
                val newWidth = (bitmap.width * scale).toInt()
                val newHeight = (bitmap.height * scale).toInt()

                if (newWidth > 100 && newHeight > 100) { // Minimum size check
                    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
                    scaledVersions.add(scaledBitmap)
                }
            } catch (e: Exception) {
                Log.w(TAG, "Failed to create scaled version at $scale", e)
            }
        }

        Log.d(TAG, "Generated ${scaledVersions.size} scaled versions")
        scaledVersions
    }

    /**
     * Generate rotated versions for rotation-tolerant detection
     */
    suspend fun generateRotatedVersions(bitmap: Bitmap): List<Bitmap> = withContext(Dispatchers.Default) {
        val rotations = listOf(0f, 90f, 180f, 270f, 45f, -45f, 135f, -135f)
        val rotatedVersions = mutableListOf<Bitmap>()

        rotations.forEach { rotation ->
            try {
                val matrix = Matrix()
                matrix.postRotate(rotation)

                val rotatedBitmap = Bitmap.createBitmap(
                    bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
                )
                rotatedVersions.add(rotatedBitmap)
            } catch (e: Exception) {
                Log.w(TAG, "Failed to create rotated version at $rotation degrees", e)
            }
        }

        Log.d(TAG, "Generated ${rotatedVersions.size} rotated versions")
        rotatedVersions
    }

    /**
     * Apply adaptive thresholding for better barcode segmentation
     */
    fun applyAdaptiveThresholding(bitmap: Bitmap, windowSize: Int = 15): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        val newPixels = IntArray(width * height)
        val halfWindow = windowSize / 2

        for (y in 0 until height) {
            for (x in 0 until width) {
                var sum = 0
                var count = 0

                // Calculate local mean
                for (wy in max(0, y - halfWindow)..min(height - 1, y + halfWindow)) {
                    for (wx in max(0, x - halfWindow)..min(width - 1, x + halfWindow)) {
                        sum += Color.red(pixels[wy * width + wx])
                        count++
                    }
                }

                val localMean = sum / count
                val currentPixel = Color.red(pixels[y * width + x])

                // Apply threshold with bias for barcode detection
                val threshold = localMean - 10 // Bias towards detecting dark bars
                val newValue = if (currentPixel > threshold) 255 else 0

                newPixels[y * width + x] = Color.argb(255, newValue, newValue, newValue)
            }
        }

        result.setPixels(newPixels, 0, width, 0, 0, width, height)
        return result
    }

    /**
     * Result class for image processing operations
     */
    data class ProcessingResult(
        val originalBitmap: Bitmap,
        val enhancedBitmaps: List<Bitmap>,
        val processingTimeMs: Long,
        val success: Boolean,
        val error: String? = null
    ) {
        val bestEnhancedBitmap: Bitmap
            get() = enhancedBitmaps.lastOrNull() ?: originalBitmap
    }

    /**
     * Configuration for different processing strategies
     */
    data class ProcessingConfig(
        val enableContrast: Boolean = true,
        val enableSharpening: Boolean = true,
        val enableEdgeEnhancement: Boolean = true,
        val enableMorphological: Boolean = true,
        val enableGammaCorrection: Boolean = true,
        val gammaValue: Float = 1.2f,
        val sharpeningStrength: Float = 1.0f,
        val contrastBoost: Float = 1.0f
    ) {
        companion object {
            val DEFAULT = ProcessingConfig()
            val AGGRESSIVE = ProcessingConfig(
                gammaValue = 1.5f,
                sharpeningStrength = 1.5f,
                contrastBoost = 1.3f
            )
            val CONSERVATIVE = ProcessingConfig(
                gammaValue = 1.1f,
                sharpeningStrength = 0.8f,
                contrastBoost = 1.1f
            )
        }
    }
}