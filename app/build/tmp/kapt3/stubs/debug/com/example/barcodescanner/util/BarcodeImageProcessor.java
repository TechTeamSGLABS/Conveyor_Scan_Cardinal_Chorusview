package com.example.barcodescanner.util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002 !B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\tJ \u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\tH\u0002J\u0018\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\tH\u0002J\u0018\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\tH\u0002J\u0018\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u0010\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u0010\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u0010\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u0010\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u0019\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cJ\u001f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00060\u001e2\u0006\u0010\u0007\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cJ\u001f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060\u001e2\u0006\u0010\u0007\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\""}, d2 = {"Lcom/example/barcodescanner/util/BarcodeImageProcessor;", "", "()V", "TAG", "", "applyAdaptiveThresholding", "Landroid/graphics/Bitmap;", "bitmap", "windowSize", "", "applyConvolutionFilter", "kernel", "", "kernelSize", "applyDilation", "radius", "applyErosion", "applyGammaCorrection", "gamma", "", "applyMorphologicalCleaning", "applySharpeningFilter", "convertToOptimalGrayscale", "enhanceContrast", "enhanceEdges", "enhanceForDistanceDetection", "Lcom/example/barcodescanner/util/BarcodeImageProcessor$ProcessingResult;", "originalBitmap", "(Landroid/graphics/Bitmap;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateMultiScaleVersions", "", "generateRotatedVersions", "ProcessingConfig", "ProcessingResult", "app_debug"})
public final class BarcodeImageProcessor {
    @org.jetbrains.annotations.NotNull
    public static final com.example.barcodescanner.util.BarcodeImageProcessor INSTANCE = null;
    private static final java.lang.String TAG = "BarcodeImageProcessor";
    
    private BarcodeImageProcessor() {
        super();
    }
    
    /**
     * Apply comprehensive preprocessing pipeline optimized for 1D barcodes at distance
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object enhanceForDistanceDetection(@org.jetbrains.annotations.NotNull
    android.graphics.Bitmap originalBitmap, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.barcodescanner.util.BarcodeImageProcessor.ProcessingResult> continuation) {
        return null;
    }
    
    /**
     * Convert to grayscale using weights optimized for barcode contrast
     */
    private final android.graphics.Bitmap convertToOptimalGrayscale(android.graphics.Bitmap bitmap) {
        return null;
    }
    
    /**
     * Apply adaptive histogram equalization for better contrast
     */
    private final android.graphics.Bitmap enhanceContrast(android.graphics.Bitmap bitmap) {
        return null;
    }
    
    /**
     * Apply sharpening filter optimized for 1D barcodes
     */
    private final android.graphics.Bitmap applySharpeningFilter(android.graphics.Bitmap bitmap) {
        return null;
    }
    
    /**
     * Enhance edges specifically for barcode detection
     */
    private final android.graphics.Bitmap enhanceEdges(android.graphics.Bitmap bitmap) {
        return null;
    }
    
    /**
     * Apply morphological operations to clean up noise
     */
    private final android.graphics.Bitmap applyMorphologicalCleaning(android.graphics.Bitmap bitmap) {
        return null;
    }
    
    private final android.graphics.Bitmap applyErosion(android.graphics.Bitmap bitmap, int radius) {
        return null;
    }
    
    private final android.graphics.Bitmap applyDilation(android.graphics.Bitmap bitmap, int radius) {
        return null;
    }
    
    /**
     * Apply gamma correction for optimal contrast
     */
    private final android.graphics.Bitmap applyGammaCorrection(android.graphics.Bitmap bitmap, float gamma) {
        return null;
    }
    
    /**
     * Apply convolution filter with given kernel
     */
    private final android.graphics.Bitmap applyConvolutionFilter(android.graphics.Bitmap bitmap, float[] kernel, int kernelSize) {
        return null;
    }
    
    /**
     * Generate multiple scaled versions for multi-scale detection
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object generateMultiScaleVersions(@org.jetbrains.annotations.NotNull
    android.graphics.Bitmap bitmap, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<android.graphics.Bitmap>> continuation) {
        return null;
    }
    
    /**
     * Generate rotated versions for rotation-tolerant detection
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object generateRotatedVersions(@org.jetbrains.annotations.NotNull
    android.graphics.Bitmap bitmap, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<android.graphics.Bitmap>> continuation) {
        return null;
    }
    
    /**
     * Apply adaptive thresholding for better barcode segmentation
     */
    @org.jetbrains.annotations.NotNull
    public final android.graphics.Bitmap applyAdaptiveThresholding(@org.jetbrains.annotations.NotNull
    android.graphics.Bitmap bitmap, int windowSize) {
        return null;
    }
    
    /**
     * Result class for image processing operations
     */
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0016\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\tH\u00c6\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003JC\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020\t2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\"H\u00d6\u0001J\t\u0010#\u001a\u00020\u000bH\u00d6\u0001R\u0011\u0010\r\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006$"}, d2 = {"Lcom/example/barcodescanner/util/BarcodeImageProcessor$ProcessingResult;", "", "originalBitmap", "Landroid/graphics/Bitmap;", "enhancedBitmaps", "", "processingTimeMs", "", "success", "", "error", "", "(Landroid/graphics/Bitmap;Ljava/util/List;JZLjava/lang/String;)V", "bestEnhancedBitmap", "getBestEnhancedBitmap", "()Landroid/graphics/Bitmap;", "getEnhancedBitmaps", "()Ljava/util/List;", "getError", "()Ljava/lang/String;", "getOriginalBitmap", "getProcessingTimeMs", "()J", "getSuccess", "()Z", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class ProcessingResult {
        @org.jetbrains.annotations.NotNull
        private final android.graphics.Bitmap originalBitmap = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<android.graphics.Bitmap> enhancedBitmaps = null;
        private final long processingTimeMs = 0L;
        private final boolean success = false;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String error = null;
        
        /**
         * Result class for image processing operations
         */
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.util.BarcodeImageProcessor.ProcessingResult copy(@org.jetbrains.annotations.NotNull
        android.graphics.Bitmap originalBitmap, @org.jetbrains.annotations.NotNull
        java.util.List<android.graphics.Bitmap> enhancedBitmaps, long processingTimeMs, boolean success, @org.jetbrains.annotations.Nullable
        java.lang.String error) {
            return null;
        }
        
        /**
         * Result class for image processing operations
         */
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        /**
         * Result class for image processing operations
         */
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        /**
         * Result class for image processing operations
         */
        @org.jetbrains.annotations.NotNull
        @java.lang.Override
        public java.lang.String toString() {
            return null;
        }
        
        public ProcessingResult(@org.jetbrains.annotations.NotNull
        android.graphics.Bitmap originalBitmap, @org.jetbrains.annotations.NotNull
        java.util.List<android.graphics.Bitmap> enhancedBitmaps, long processingTimeMs, boolean success, @org.jetbrains.annotations.Nullable
        java.lang.String error) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.graphics.Bitmap component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.graphics.Bitmap getOriginalBitmap() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<android.graphics.Bitmap> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<android.graphics.Bitmap> getEnhancedBitmaps() {
            return null;
        }
        
        public final long component3() {
            return 0L;
        }
        
        public final long getProcessingTimeMs() {
            return 0L;
        }
        
        public final boolean component4() {
            return false;
        }
        
        public final boolean getSuccess() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getError() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.graphics.Bitmap getBestEnhancedBitmap() {
            return null;
        }
    }
    
    /**
     * Configuration for different processing strategies
     */
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0019\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 &2\u00020\u0001:\u0001&BU\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\tH\u00c6\u0003JY\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\tH\u00c6\u0001J\u0013\u0010 \u001a\u00020\u00032\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020#H\u00d6\u0001J\t\u0010$\u001a\u00020%H\u00d6\u0001R\u0011\u0010\u000b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000eR\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000e\u00a8\u0006\'"}, d2 = {"Lcom/example/barcodescanner/util/BarcodeImageProcessor$ProcessingConfig;", "", "enableContrast", "", "enableSharpening", "enableEdgeEnhancement", "enableMorphological", "enableGammaCorrection", "gammaValue", "", "sharpeningStrength", "contrastBoost", "(ZZZZZFFF)V", "getContrastBoost", "()F", "getEnableContrast", "()Z", "getEnableEdgeEnhancement", "getEnableGammaCorrection", "getEnableMorphological", "getEnableSharpening", "getGammaValue", "getSharpeningStrength", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "", "toString", "", "Companion", "app_debug"})
    public static final class ProcessingConfig {
        private final boolean enableContrast = false;
        private final boolean enableSharpening = false;
        private final boolean enableEdgeEnhancement = false;
        private final boolean enableMorphological = false;
        private final boolean enableGammaCorrection = false;
        private final float gammaValue = 0.0F;
        private final float sharpeningStrength = 0.0F;
        private final float contrastBoost = 0.0F;
        @org.jetbrains.annotations.NotNull
        public static final com.example.barcodescanner.util.BarcodeImageProcessor.ProcessingConfig.Companion Companion = null;
        @org.jetbrains.annotations.NotNull
        private static final com.example.barcodescanner.util.BarcodeImageProcessor.ProcessingConfig DEFAULT = null;
        @org.jetbrains.annotations.NotNull
        private static final com.example.barcodescanner.util.BarcodeImageProcessor.ProcessingConfig AGGRESSIVE = null;
        @org.jetbrains.annotations.NotNull
        private static final com.example.barcodescanner.util.BarcodeImageProcessor.ProcessingConfig CONSERVATIVE = null;
        
        /**
         * Configuration for different processing strategies
         */
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.util.BarcodeImageProcessor.ProcessingConfig copy(boolean enableContrast, boolean enableSharpening, boolean enableEdgeEnhancement, boolean enableMorphological, boolean enableGammaCorrection, float gammaValue, float sharpeningStrength, float contrastBoost) {
            return null;
        }
        
        /**
         * Configuration for different processing strategies
         */
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        /**
         * Configuration for different processing strategies
         */
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        /**
         * Configuration for different processing strategies
         */
        @org.jetbrains.annotations.NotNull
        @java.lang.Override
        public java.lang.String toString() {
            return null;
        }
        
        public ProcessingConfig() {
            super();
        }
        
        public ProcessingConfig(boolean enableContrast, boolean enableSharpening, boolean enableEdgeEnhancement, boolean enableMorphological, boolean enableGammaCorrection, float gammaValue, float sharpeningStrength, float contrastBoost) {
            super();
        }
        
        public final boolean component1() {
            return false;
        }
        
        public final boolean getEnableContrast() {
            return false;
        }
        
        public final boolean component2() {
            return false;
        }
        
        public final boolean getEnableSharpening() {
            return false;
        }
        
        public final boolean component3() {
            return false;
        }
        
        public final boolean getEnableEdgeEnhancement() {
            return false;
        }
        
        public final boolean component4() {
            return false;
        }
        
        public final boolean getEnableMorphological() {
            return false;
        }
        
        public final boolean component5() {
            return false;
        }
        
        public final boolean getEnableGammaCorrection() {
            return false;
        }
        
        public final float component6() {
            return 0.0F;
        }
        
        public final float getGammaValue() {
            return 0.0F;
        }
        
        public final float component7() {
            return 0.0F;
        }
        
        public final float getSharpeningStrength() {
            return 0.0F;
        }
        
        public final float component8() {
            return 0.0F;
        }
        
        public final float getContrastBoost() {
            return 0.0F;
        }
        
        @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006\u00a8\u0006\u000b"}, d2 = {"Lcom/example/barcodescanner/util/BarcodeImageProcessor$ProcessingConfig$Companion;", "", "()V", "AGGRESSIVE", "Lcom/example/barcodescanner/util/BarcodeImageProcessor$ProcessingConfig;", "getAGGRESSIVE", "()Lcom/example/barcodescanner/util/BarcodeImageProcessor$ProcessingConfig;", "CONSERVATIVE", "getCONSERVATIVE", "DEFAULT", "getDEFAULT", "app_debug"})
        public static final class Companion {
            
            private Companion() {
                super();
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.example.barcodescanner.util.BarcodeImageProcessor.ProcessingConfig getDEFAULT() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.example.barcodescanner.util.BarcodeImageProcessor.ProcessingConfig getAGGRESSIVE() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.example.barcodescanner.util.BarcodeImageProcessor.ProcessingConfig getCONSERVATIVE() {
                return null;
            }
        }
    }
}