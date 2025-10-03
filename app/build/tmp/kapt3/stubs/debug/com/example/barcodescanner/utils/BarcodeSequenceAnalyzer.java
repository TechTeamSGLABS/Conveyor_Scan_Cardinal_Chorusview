package com.example.barcodescanner.utils;

import java.lang.System;

/**
 * Analyzes barcode sequences and patterns for association
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u001cB\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u0005H\u0002J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u0005H\u0002J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u0005H\u0002J\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u0005H\u0002J\u0006\u0010\u001b\u001a\u00020\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/example/barcodescanner/utils/BarcodeSequenceAnalyzer;", "", "()V", "detectionSequence", "", "Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$BarcodeDetection;", "addDetection", "", "detection", "cleanup", "currentTime", "", "detectAlternatingPattern", "", "Lcom/example/barcodescanner/utils/BarcodeSequenceAnalyzer$SequencePattern;", "newDetection", "detectPairedPattern", "detectPatterns", "detectTimingPattern", "getSequenceConfidence", "", "toteId", "olpn", "isComplementarySequence", "", "detection1", "detection2", "reset", "SequencePattern", "app_debug"})
public final class BarcodeSequenceAnalyzer {
    private final java.util.List<com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection> detectionSequence = null;
    
    public BarcodeSequenceAnalyzer() {
        super();
    }
    
    public final void addDetection(@org.jetbrains.annotations.NotNull
    com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.barcodescanner.utils.BarcodeSequenceAnalyzer.SequencePattern> detectPatterns(@org.jetbrains.annotations.NotNull
    com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection newDetection, long currentTime) {
        return null;
    }
    
    private final java.util.List<com.example.barcodescanner.utils.BarcodeSequenceAnalyzer.SequencePattern> detectAlternatingPattern(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection newDetection) {
        return null;
    }
    
    private final java.util.List<com.example.barcodescanner.utils.BarcodeSequenceAnalyzer.SequencePattern> detectPairedPattern(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection newDetection) {
        return null;
    }
    
    private final java.util.List<com.example.barcodescanner.utils.BarcodeSequenceAnalyzer.SequencePattern> detectTimingPattern(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection newDetection) {
        return null;
    }
    
    private final boolean isComplementarySequence(com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection1, com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection detection2) {
        return false;
    }
    
    public final float getSequenceConfidence(@org.jetbrains.annotations.NotNull
    com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection toteId, @org.jetbrains.annotations.NotNull
    com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection olpn) {
        return 0.0F;
    }
    
    public final void cleanup(long currentTime) {
    }
    
    public final void reset() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\bH\u00c6\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001J\t\u0010\u001b\u001a\u00020\bH\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f\u00a8\u0006\u001c"}, d2 = {"Lcom/example/barcodescanner/utils/BarcodeSequenceAnalyzer$SequencePattern;", "", "toteId", "Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$BarcodeDetection;", "olpn", "confidence", "", "description", "", "(Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$BarcodeDetection;Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$BarcodeDetection;FLjava/lang/String;)V", "getConfidence", "()F", "getDescription", "()Ljava/lang/String;", "getOlpn", "()Lcom/example/barcodescanner/utils/MovingBarcodeAssociationManager$BarcodeDetection;", "getToteId", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class SequencePattern {
        @org.jetbrains.annotations.NotNull
        private final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection toteId = null;
        @org.jetbrains.annotations.NotNull
        private final com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection olpn = null;
        private final float confidence = 0.0F;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String description = null;
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.utils.BarcodeSequenceAnalyzer.SequencePattern copy(@org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection toteId, @org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection olpn, float confidence, @org.jetbrains.annotations.NotNull
        java.lang.String description) {
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
        
        public SequencePattern(@org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection toteId, @org.jetbrains.annotations.NotNull
        com.example.barcodescanner.utils.MovingBarcodeAssociationManager.BarcodeDetection olpn, float confidence, @org.jetbrains.annotations.NotNull
        java.lang.String description) {
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
        
        public final float component3() {
            return 0.0F;
        }
        
        public final float getConfidence() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDescription() {
            return null;
        }
    }
}