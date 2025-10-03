package com.example.barcodescanner.utils;

import java.lang.System;

/**
 * Tracks conveyor movement patterns and predicts barcode positions
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0001\u001dB\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000bJ\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u000eJ\u0016\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000bJ \u0010\u0018\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u000eJ\u0006\u0010\u001c\u001a\u00020\tR \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/example/barcodescanner/utils/ConveyorMovementTracker;", "", "()V", "positionHistory", "", "", "", "Lcom/example/barcodescanner/utils/ConveyorMovementTracker$PositionRecord;", "addDetection", "", "detection", "Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$BarcodeDetection;", "cleanup", "currentTime", "", "getMovementConsistencyScore", "", "detection1", "detection2", "getPredictedPositions", "", "Lcom/example/barcodescanner/utils/PointF;", "timeAheadMs", "getSpeedConsistencyScore", "predictMovementVector", "value", "currentPos", "timestamp", "reset", "PositionRecord", "app_debug"})
public final class ConveyorMovementTracker {
    private final java.util.Map<java.lang.String, java.util.List<com.example.barcodescanner.utils.ConveyorMovementTracker.PositionRecord>> positionHistory = null;
    
    public ConveyorMovementTracker() {
        super();
    }
    
    public final void addDetection(@org.jetbrains.annotations.NotNull
    com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.example.barcodescanner.utils.PointF predictMovementVector(@org.jetbrains.annotations.NotNull
    java.lang.String value, @org.jetbrains.annotations.NotNull
    com.example.barcodescanner.utils.PointF currentPos, long timestamp) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, com.example.barcodescanner.utils.PointF> getPredictedPositions(long timeAheadMs) {
        return null;
    }
    
    public final float getMovementConsistencyScore(@org.jetbrains.annotations.NotNull
    com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection1, @org.jetbrains.annotations.NotNull
    com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection2) {
        return 0.0F;
    }
    
    public final float getSpeedConsistencyScore(@org.jetbrains.annotations.NotNull
    com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection1, @org.jetbrains.annotations.NotNull
    com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection2) {
        return 0.0F;
    }
    
    public final void cleanup(long currentTime) {
    }
    
    public final void reset() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0015"}, d2 = {"Lcom/example/barcodescanner/utils/ConveyorMovementTracker$PositionRecord;", "", "position", "Lcom/example/barcodescanner/utils/PointF;", "timestamp", "", "(Lcom/example/barcodescanner/utils/PointF;J)V", "getPosition", "()Lcom/example/barcodescanner/utils/PointF;", "getTimestamp", "()J", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class PositionRecord {
        @org.jetbrains.annotations.NotNull
        private final com.example.barcodescanner.utils.PointF position = null;
        private final long timestamp = 0L;
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.ConveyorMovementTracker.PositionRecord copy(@org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.PointF position, long timestamp) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        @java.lang.Override
        public java.lang.String toString() {
            return null;
        }
        
        public PositionRecord(@org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.PointF position, long timestamp) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.PointF component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.PointF getPosition() {
            return null;
        }
        
        public final long component2() {
            return 0L;
        }
        
        public final long getTimestamp() {
            return 0L;
        }
    }
}