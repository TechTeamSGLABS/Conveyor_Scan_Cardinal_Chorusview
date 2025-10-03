package com.example.barcodescanner.model;

import java.lang.System;

/**
 * Enhanced DetectedBarcode data class for Live View and Photo Capture modes
 * Supports persistent tick marks and improved visual feedback
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u001f\b\u0086\b\u0018\u0000 *2\u00020\u0001:\u0001*BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00c6\u0003J\t\u0010 \u001a\u00020\fH\u00c6\u0003J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003JO\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010#\u001a\u00020\t2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010%\u001a\u00020\u0005J\t\u0010&\u001a\u00020\u0007H\u00d6\u0001J\u0006\u0010\'\u001a\u00020\tJ\u000e\u0010(\u001a\u00020\t2\u0006\u0010$\u001a\u00020\u0000J\t\u0010)\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\n\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\r\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014\u00a8\u0006+"}, d2 = {"Lcom/example/barcodescanner/model/DetectedBarcode;", "", "boundingBox", "Landroid/graphics/RectF;", "value", "", "format", "", "alreadyScanned", "", "distanceEstimate", "timeDetected", "", "id", "(Landroid/graphics/RectF;Ljava/lang/String;IZLjava/lang/String;JLjava/lang/String;)V", "getAlreadyScanned", "()Z", "getBoundingBox", "()Landroid/graphics/RectF;", "getDistanceEstimate", "()Ljava/lang/String;", "getFormat", "()I", "getId", "getTimeDetected", "()J", "getValue", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "getDisplayInfo", "hashCode", "isRecentlyDetected", "isSameBarcode", "toString", "Companion", "app_debug"})
public final class DetectedBarcode {
    @org.jetbrains.annotations.NotNull
    private final android.graphics.RectF boundingBox = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String value = null;
    private final int format = 0;
    private final boolean alreadyScanned = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String distanceEstimate = null;
    private final long timeDetected = 0L;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull
    public static final com.example.barcodescanner.model.DetectedBarcode.Companion Companion = null;
    
    /**
     * Enhanced DetectedBarcode data class for Live View and Photo Capture modes
     * Supports persistent tick marks and improved visual feedback
     */
    @org.jetbrains.annotations.NotNull
    public final com.example.barcodescanner.model.DetectedBarcode copy(@org.jetbrains.annotations.NotNull
    android.graphics.RectF boundingBox, @org.jetbrains.annotations.NotNull
    java.lang.String value, int format, boolean alreadyScanned, @org.jetbrains.annotations.NotNull
    java.lang.String distanceEstimate, long timeDetected, @org.jetbrains.annotations.NotNull
    java.lang.String id) {
        return null;
    }
    
    /**
     * Enhanced DetectedBarcode data class for Live View and Photo Capture modes
     * Supports persistent tick marks and improved visual feedback
     */
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    /**
     * Enhanced DetectedBarcode data class for Live View and Photo Capture modes
     * Supports persistent tick marks and improved visual feedback
     */
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    /**
     * Enhanced DetectedBarcode data class for Live View and Photo Capture modes
     * Supports persistent tick marks and improved visual feedback
     */
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public java.lang.String toString() {
        return null;
    }
    
    public DetectedBarcode(@org.jetbrains.annotations.NotNull
    android.graphics.RectF boundingBox, @org.jetbrains.annotations.NotNull
    java.lang.String value, int format, boolean alreadyScanned, @org.jetbrains.annotations.NotNull
    java.lang.String distanceEstimate, long timeDetected, @org.jetbrains.annotations.NotNull
    java.lang.String id) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.graphics.RectF component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.graphics.RectF getBoundingBox() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getValue() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int getFormat() {
        return 0;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean getAlreadyScanned() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDistanceEstimate() {
        return null;
    }
    
    public final long component6() {
        return 0L;
    }
    
    public final long getTimeDetected() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getId() {
        return null;
    }
    
    /**
     * Check if this barcode was detected recently (within the last 3 seconds)
     */
    public final boolean isRecentlyDetected() {
        return false;
    }
    
    /**
     * Get a formatted display string for logging
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDisplayInfo() {
        return null;
    }
    
    /**
     * Check if this barcode is the same as another (by value)
     */
    public final boolean isSameBarcode(@org.jetbrains.annotations.NotNull
    com.example.barcodescanner.model.DetectedBarcode other) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f\u00a8\u0006\r"}, d2 = {"Lcom/example/barcodescanner/model/DetectedBarcode$Companion;", "", "()V", "createPersistentTickMark", "Lcom/example/barcodescanner/model/DetectedBarcode;", "value", "", "format", "", "lastKnownPosition", "Landroid/graphics/RectF;", "originalDetectionTime", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Create a DetectedBarcode for persistent display when the actual barcode
         * is no longer visible but should still show a tick mark
         */
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.model.DetectedBarcode createPersistentTickMark(@org.jetbrains.annotations.NotNull
        java.lang.String value, int format, @org.jetbrains.annotations.NotNull
        android.graphics.RectF lastKnownPosition, long originalDetectionTime) {
            return null;
        }
    }
}