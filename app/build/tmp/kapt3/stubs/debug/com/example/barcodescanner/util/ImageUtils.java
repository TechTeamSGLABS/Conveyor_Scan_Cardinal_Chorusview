package com.example.barcodescanner.util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nJ\u001c\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fJ\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\fJ&\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u00152\b\b\u0002\u0010\u0016\u001a\u00020\n2\b\b\u0002\u0010\u0017\u001a\u00020\nJ\u0018\u0010\u0018\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\fJ\"\u0010\u0018\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\f2\b\b\u0002\u0010\u0019\u001a\u00020\u0004J*\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u000f2\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\f0\u000f2\u0006\u0010\u001c\u001a\u00020\u0012J\u0010\u0010\u001d\u001a\u00020\u001e2\b\u0010\u0013\u001a\u0004\u0018\u00010\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/example/barcodescanner/util/ImageUtils;", "", "()V", "TAG", "", "cleanupOldCacheFiles", "", "context", "Landroid/content/Context;", "olderThanHours", "", "drawBarcodeBoundingBoxes", "Landroid/graphics/Bitmap;", "original", "barcodes", "", "Lcom/google/mlkit/vision/barcode/common/Barcode;", "getBitmapMemorySize", "", "bitmap", "getOptimalImageSize", "Lkotlin/Pair;", "maxWidth", "maxHeight", "saveBitmapToCache", "prefix", "saveProcessedBitmaps", "bitmaps", "sessionId", "validateBitmapForDetection", "", "app_debug"})
public final class ImageUtils {
    @org.jetbrains.annotations.NotNull
    public static final com.example.barcodescanner.util.ImageUtils INSTANCE = null;
    private static final java.lang.String TAG = "ImageUtils";
    
    private ImageUtils() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String saveBitmapToCache(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.graphics.Bitmap bitmap) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.graphics.Bitmap drawBarcodeBoundingBoxes(@org.jetbrains.annotations.NotNull
    android.graphics.Bitmap original, @org.jetbrains.annotations.NotNull
    java.util.List<? extends com.google.mlkit.vision.barcode.common.Barcode> barcodes) {
        return null;
    }
    
    /**
     * Save bitmap with custom prefix for different processing stages
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.String saveBitmapToCache(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.graphics.Bitmap bitmap, @org.jetbrains.annotations.NotNull
    java.lang.String prefix) {
        return null;
    }
    
    /**
     * Save multiple processed bitmaps for debugging
     */
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> saveProcessedBitmaps(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.util.List<android.graphics.Bitmap> bitmaps, long sessionId) {
        return null;
    }
    
    /**
     * Get optimal image size based on device capabilities
     */
    @org.jetbrains.annotations.NotNull
    public final kotlin.Pair<java.lang.Integer, java.lang.Integer> getOptimalImageSize(int maxWidth, int maxHeight) {
        return null;
    }
    
    /**
     * Check if bitmap is suitable for barcode detection
     */
    public final boolean validateBitmapForDetection(@org.jetbrains.annotations.Nullable
    android.graphics.Bitmap bitmap) {
        return false;
    }
    
    /**
     * Calculate memory usage of bitmap
     */
    public final long getBitmapMemorySize(@org.jetbrains.annotations.NotNull
    android.graphics.Bitmap bitmap) {
        return 0L;
    }
    
    /**
     * Clean up old cache files to prevent storage bloat
     */
    public final void cleanupOldCacheFiles(@org.jetbrains.annotations.NotNull
    android.content.Context context, int olderThanHours) {
    }
}