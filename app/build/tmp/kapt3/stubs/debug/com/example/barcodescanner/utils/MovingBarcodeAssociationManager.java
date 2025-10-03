package com.example.barcodescanner.utils;

import java.lang.System;

/**
 * 🚀 ADVANCED Moving Barcode Association Manager
 *
 * Handles association of barcodes on moving conveyors with multiple strategies:
 * ✅ Extended time windows with smart buffering
 * ✅ Positional/spatial association tracking
 * ✅ Sequence-based pattern detection
 * ✅ Movement prediction and tracking
 * ✅ Multi-pass association with confidence scoring
 * ✅ Conveyor speed adaptation
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 M2\u00020\u0001:\u0005IJKLMB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\rH\u0002J \u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\tH\u0002J\u0018\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\rH\u0002J \u0010\u001e\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0010\u0010!\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 H\u0002J(\u0010\"\u001a\u00020\r2\u0006\u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020 H\u0002J\u0010\u0010)\u001a\u00020\n2\u0006\u0010*\u001a\u00020\u0010H\u0002J\u0018\u0010+\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0018\u0010,\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0018\u0010-\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0018\u0010.\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0018\u0010/\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0006\u00100\u001a\u000201J\u0010\u00102\u001a\u00020\u00072\u0006\u0010$\u001a\u00020%H\u0002J\u0010\u00103\u001a\u00020\t2\u0006\u00104\u001a\u00020\bH\u0002J\u001e\u00105\u001a\b\u0012\u0004\u0012\u00020\r062\u0006\u00107\u001a\u00020 2\u0006\u0010\u001f\u001a\u00020 H\u0002J&\u00108\u001a\b\u0012\u0004\u0012\u00020\r062\u0006\u00109\u001a\u00020:2\u0006\u00107\u001a\u00020 2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0010\u0010;\u001a\u00020\t2\u0006\u00104\u001a\u00020\bH\u0002J\u0018\u0010<\u001a\u00020:2\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\rH\u0002J3\u0010=\u001a\u00020\n2\u0006\u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\'2\b\b\u0002\u0010\u001f\u001a\u00020 H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010>J\u0019\u0010?\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010@J\u0006\u0010A\u001a\u00020\nJ\u00b5\u0001\u0010B\u001a\u00020\n2u\u0010C\u001aq\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\bD\u0012\b\bE\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\bD\u0012\b\bE\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\bD\u0012\b\bE\u0012\u0004\b\b(4\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bD\u0012\b\bE\u0012\u0004\b\b(F\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\bD\u0012\b\bE\u0012\u0004\b\b(G\u0012\u0004\u0012\u00020\n0\u000626\u0010H\u001a2\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\bD\u0012\b\bE\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\bD\u0012\b\bE\u0012\u0004\b\b(G\u0012\u0004\u0012\u00020\n0\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R4\u0010\u0005\u001a(\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006N"}, d2 = {"Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager;", "", "()V", "movementTracker", "Lcom/example/barcodescanner/utils/ConveyorMovementTracker;", "onAssociationFound", "Lkotlin/Function5;", "", "Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$AssociationType;", "", "", "onMissedAssociation", "Lkotlin/Function2;", "Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$BarcodeDetection;", "pendingAssociations", "", "Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$AssociationCandidate;", "recentDetections", "sequenceAnalyzer", "Lcom/example/barcodescanner/utils/BarcodeSequenceAnalyzer;", "calculateImmediateConfidence", "toteId", "olpn", "calculateMovementPredictionConfidence", "detection", "predicted", "distance", "calculateSpatialConfidence", "detection1", "detection2", "calculateTemporalConfidence", "currentTime", "", "cleanupOldDetections", "createBarcodeDetection", "value", "format", "", "boundingBox", "Landroid/graphics/RectF;", "timestamp", "executeAssociation", "candidate", "findImmediateAssociations", "findMovementPredictionAssociations", "findPatternBasedAssociations", "findSpatialAssociations", "findTemporalAssociations", "getAssociationStats", "Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$AssociationStats;", "getBarcodeTypeName", "getMinimumConfidenceThreshold", "type", "getRecentDetections", "", "windowMs", "getRecentDetectionsByType", "isOlpn", "", "getTypeWeight", "isComplementaryType", "processDetection", "(Ljava/lang/String;ILandroid/graphics/RectF;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processPendingAssociations", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reset", "setCallbacks", "onAssociation", "Lkotlin/ParameterName;", "name", "confidence", "reason", "onMissed", "AssociationCandidate", "AssociationStats", "AssociationType", "BarcodeDetection", "Companion", "app_debug"})
public final class MovingBarcodeAssociationManager {
    @org.jetbrains.annotations.NotNull
    public static final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.Companion Companion = null;
    private static final java.lang.String TAG = "MovingBarcodeAssociation";
    private static final long IMMEDIATE_ASSOCIATION_WINDOW_MS = 2000L;
    private static final long EXTENDED_ASSOCIATION_WINDOW_MS = 8000L;
    private static final long SEQUENCE_ASSOCIATION_WINDOW_MS = 15000L;
    private static final float POSITION_TOLERANCE_PIXELS = 200.0F;
    private static final float MIN_SPEED_THRESHOLD = 50.0F;
    private static final float MAX_SPEED_THRESHOLD = 500.0F;
    private static final long PREDICTION_TIME_MS = 3000L;
    private final java.util.List<com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection> recentDetections = null;
    private final java.util.List<com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationCandidate> pendingAssociations = null;
    private final com.example.barcodescanner.utils.ConveyorMovementTracker movementTracker = null;
    private final com.example.barcodescanner.utils.BarcodeSequenceAnalyzer sequenceAnalyzer = null;
    private kotlin.jvm.functions.Function5<? super java.lang.String, ? super java.lang.String, ? super com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationType, ? super java.lang.Float, ? super java.lang.String, kotlin.Unit> onAssociationFound;
    private kotlin.jvm.functions.Function2<? super com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection, ? super java.lang.String, kotlin.Unit> onMissedAssociation;
    
    public MovingBarcodeAssociationManager() {
        super();
    }
    
    public final void setCallbacks(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function5<? super java.lang.String, ? super java.lang.String, ? super com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationType, ? super java.lang.Float, ? super java.lang.String, kotlin.Unit> onAssociation, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection, ? super java.lang.String, kotlin.Unit> onMissed) {
    }
    
    /**
     * 🚀 MAIN: Process new barcode detection with all association strategies
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object processDetection(@org.jetbrains.annotations.NotNull
    java.lang.String value, int format, @org.jetbrains.annotations.NotNull
    android.graphics.RectF boundingBox, long currentTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    /**
     * 🚀 STRATEGY 1: Immediate same-frame associations (traditional approach)
     */
    private final void findImmediateAssociations(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection, long currentTime) {
    }
    
    /**
     * 🚀 STRATEGY 2: Temporal sequence association (extended time windows)
     */
    private final void findTemporalAssociations(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection, long currentTime) {
    }
    
    /**
     * 🚀 STRATEGY 3: Spatial proximity association (position-based)
     */
    private final void findSpatialAssociations(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection, long currentTime) {
    }
    
    /**
     * 🚀 STRATEGY 4: Movement prediction association
     */
    private final void findMovementPredictionAssociations(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection, long currentTime) {
    }
    
    /**
     * 🚀 STRATEGY 5: Pattern-based association
     */
    private final void findPatternBasedAssociations(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection, long currentTime) {
    }
    
    /**
     * 🚀 PROCESS: Evaluate and execute pending associations
     */
    private final java.lang.Object processPendingAssociations(long currentTime, kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    /**
     * 🚀 CONFIDENCE: Calculate confidence scores for different association types
     */
    private final float calculateImmediateConfidence(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection toteId, com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection olpn) {
        return 0.0F;
    }
    
    private final float calculateTemporalConfidence(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection toteId, com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection olpn, long currentTime) {
        return 0.0F;
    }
    
    private final float calculateSpatialConfidence(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection1, com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection2) {
        return 0.0F;
    }
    
    private final float calculateMovementPredictionConfidence(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection, com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection predicted, float distance) {
        return 0.0F;
    }
    
    /**
     * 🚀 UTILITIES: Helper functions
     */
    private final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection createBarcodeDetection(java.lang.String value, int format, android.graphics.RectF boundingBox, long timestamp) {
        return null;
    }
    
    private final java.util.List<com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection> getRecentDetectionsByType(boolean isOlpn, long windowMs, long currentTime) {
        return null;
    }
    
    private final java.util.List<com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection> getRecentDetections(long windowMs, long currentTime) {
        return null;
    }
    
    private final boolean isComplementaryType(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection1, com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection2) {
        return false;
    }
    
    private final float getTypeWeight(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationType type) {
        return 0.0F;
    }
    
    private final float getMinimumConfidenceThreshold(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationType type) {
        return 0.0F;
    }
    
    private final void executeAssociation(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationCandidate candidate) {
    }
    
    private final void cleanupOldDetections(long currentTime) {
    }
    
    private final java.lang.String getBarcodeTypeName(int format) {
        return null;
    }
    
    /**
     * 🚀 STATS: Get association statistics
     */
    @org.jetbrains.annotations.NotNull
    public final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationStats getAssociationStats() {
        return null;
    }
    
    /**
     * 🚀 CLEANUP: Clear all state
     */
    public final void reset() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b(\b\u0086\b\u0018\u00002\u00020\u0001BW\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\r\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\u0012J\u000e\u0010&\u001a\u00020\u00072\u0006\u0010\'\u001a\u00020\u0007J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0005H\u00c6\u0003J\t\u0010*\u001a\u00020\u0007H\u00c6\u0003J\t\u0010+\u001a\u00020\tH\u00c6\u0003J\t\u0010,\u001a\u00020\u000bH\u00c6\u0003J\t\u0010-\u001a\u00020\rH\u00c6\u0003J\t\u0010.\u001a\u00020\u000fH\u00c6\u0003J\t\u0010/\u001a\u00020\rH\u00c6\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003Je\u00101\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\r2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001J\u000e\u00102\u001a\u00020\r2\u0006\u00103\u001a\u00020\u0000J\u0013\u00104\u001a\u00020\u000f2\b\u00103\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00105\u001a\u00020\u0005H\u00d6\u0001J\t\u00106\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u0010\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001eR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%\u00a8\u00067"}, d2 = {"Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$BarcodeDetection;", "", "value", "", "format", "", "timestamp", "", "position", "Landroid/graphics/RectF;", "screenCenter", "Lcom/example/barcodescanner/utils/PointF;", "confidence", "", "isAssociated", "", "associationConfidence", "movementVector", "(Ljava/lang/String;IJLandroid/graphics/RectF;Lcom/example/barcodescanner/utils/PointF;FZFLcom/example/barcodescanner/utils/PointF;)V", "getAssociationConfidence", "()F", "setAssociationConfidence", "(F)V", "getConfidence", "getFormat", "()I", "()Z", "setAssociated", "(Z)V", "getMovementVector", "()Lcom/example/barcodescanner/utils/PointF;", "getPosition", "()Landroid/graphics/RectF;", "getScreenCenter", "getTimestamp", "()J", "getValue", "()Ljava/lang/String;", "age", "currentTime", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "distanceTo", "other", "equals", "hashCode", "toString", "app_debug"})
    public static final class BarcodeDetection {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String value = null;
        private final int format = 0;
        private final long timestamp = 0L;
        @org.jetbrains.annotations.NotNull
        private final android.graphics.RectF position = null;
        @org.jetbrains.annotations.NotNull
        private final com.example.barcodescanner.utils.PointF screenCenter = null;
        private final float confidence = 0.0F;
        private boolean isAssociated;
        private float associationConfidence;
        @org.jetbrains.annotations.Nullable
        private final com.example.barcodescanner.utils.PointF movementVector = null;
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection copy(@org.jetbrains.annotations.NotNull
        java.lang.String value, int format, long timestamp, @org.jetbrains.annotations.NotNull
        android.graphics.RectF position, @org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.PointF screenCenter, float confidence, boolean isAssociated, float associationConfidence, @org.jetbrains.annotations.Nullable
        com.example.barcodescanner.utils.PointF movementVector) {
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
        
        public BarcodeDetection(@org.jetbrains.annotations.NotNull
        java.lang.String value, int format, long timestamp, @org.jetbrains.annotations.NotNull
        android.graphics.RectF position, @org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.PointF screenCenter, float confidence, boolean isAssociated, float associationConfidence, @org.jetbrains.annotations.Nullable
        com.example.barcodescanner.utils.PointF movementVector) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getValue() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        public final int getFormat() {
            return 0;
        }
        
        public final long component3() {
            return 0L;
        }
        
        public final long getTimestamp() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.graphics.RectF component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.graphics.RectF getPosition() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.PointF component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.PointF getScreenCenter() {
            return null;
        }
        
        public final float component6() {
            return 0.0F;
        }
        
        public final float getConfidence() {
            return 0.0F;
        }
        
        public final boolean component7() {
            return false;
        }
        
        public final boolean isAssociated() {
            return false;
        }
        
        public final void setAssociated(boolean p0) {
        }
        
        public final float component8() {
            return 0.0F;
        }
        
        public final float getAssociationConfidence() {
            return 0.0F;
        }
        
        public final void setAssociationConfidence(float p0) {
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.example.barcodescanner.utils.PointF component9() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.example.barcodescanner.utils.PointF getMovementVector() {
            return null;
        }
        
        public final long age(long currentTime) {
            return 0L;
        }
        
        public final float distanceTo(@org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection other) {
            return 0.0F;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\nH\u00c6\u0003J;\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001J\t\u0010 \u001a\u00020\nH\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011\u00a8\u0006!"}, d2 = {"Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$AssociationCandidate;", "", "toteId", "Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$BarcodeDetection;", "olpn", "associationType", "Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$AssociationType;", "confidence", "", "reason", "", "(Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$BarcodeDetection;Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$BarcodeDetection;Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$AssociationType;FLjava/lang/String;)V", "getAssociationType", "()Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$AssociationType;", "getConfidence", "()F", "getOlpn", "()Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$BarcodeDetection;", "getReason", "()Ljava/lang/String;", "getToteId", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class AssociationCandidate {
        @org.jetbrains.annotations.NotNull
        private final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection toteId = null;
        @org.jetbrains.annotations.NotNull
        private final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection olpn = null;
        @org.jetbrains.annotations.NotNull
        private final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationType associationType = null;
        private final float confidence = 0.0F;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String reason = null;
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationCandidate copy(@org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection toteId, @org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection olpn, @org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationType associationType, float confidence, @org.jetbrains.annotations.NotNull
        java.lang.String reason) {
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
        
        public AssociationCandidate(@org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection toteId, @org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection olpn, @org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationType associationType, float confidence, @org.jetbrains.annotations.NotNull
        java.lang.String reason) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection getToteId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection getOlpn() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationType component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationType getAssociationType() {
            return null;
        }
        
        public final float component4() {
            return 0.0F;
        }
        
        public final float getConfidence() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getReason() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$AssociationType;", "", "(Ljava/lang/String;I)V", "IMMEDIATE_SAME_FRAME", "TEMPORAL_SEQUENCE", "SPATIAL_PROXIMITY", "MOVEMENT_PREDICTION", "PATTERN_BASED", "app_debug"})
    public static enum AssociationType {
        /*public static final*/ IMMEDIATE_SAME_FRAME /* = new IMMEDIATE_SAME_FRAME() */,
        /*public static final*/ TEMPORAL_SEQUENCE /* = new TEMPORAL_SEQUENCE() */,
        /*public static final*/ SPATIAL_PROXIMITY /* = new SPATIAL_PROXIMITY() */,
        /*public static final*/ MOVEMENT_PREDICTION /* = new MOVEMENT_PREDICTION() */,
        /*public static final*/ PATTERN_BASED /* = new PATTERN_BASED() */;
        
        AssociationType() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0007H\u00c6\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n\u00a8\u0006\u001a"}, d2 = {"Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$AssociationStats;", "", "totalDetections", "", "associatedCount", "pendingCount", "associationRate", "", "(IIIF)V", "getAssociatedCount", "()I", "getAssociationRate", "()F", "getPendingCount", "getTotalDetections", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class AssociationStats {
        private final int totalDetections = 0;
        private final int associatedCount = 0;
        private final int pendingCount = 0;
        private final float associationRate = 0.0F;
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.AssociationStats copy(int totalDetections, int associatedCount, int pendingCount, float associationRate) {
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
        
        public AssociationStats(int totalDetections, int associatedCount, int pendingCount, float associationRate) {
            super();
        }
        
        public final int component1() {
            return 0;
        }
        
        public final int getTotalDetections() {
            return 0;
        }
        
        public final int component2() {
            return 0;
        }
        
        public final int getAssociatedCount() {
            return 0;
        }
        
        public final int component3() {
            return 0;
        }
        
        public final int getPendingCount() {
            return 0;
        }
        
        public final float component4() {
            return 0.0F;
        }
        
        public final float getAssociationRate() {
            return 0.0F;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$Companion;", "", "()V", "EXTENDED_ASSOCIATION_WINDOW_MS", "", "IMMEDIATE_ASSOCIATION_WINDOW_MS", "MAX_SPEED_THRESHOLD", "", "MIN_SPEED_THRESHOLD", "POSITION_TOLERANCE_PIXELS", "PREDICTION_TIME_MS", "SEQUENCE_ASSOCIATION_WINDOW_MS", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}