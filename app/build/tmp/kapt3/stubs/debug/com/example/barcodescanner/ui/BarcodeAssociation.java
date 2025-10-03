package com.example.barcodescanner.ui;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\bH\u00c6\u0003J;\u0010\u0014\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0015\u001a\u00020\b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006J\u0006\u0010\u0018\u001a\u00020\u0019J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u0019H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\nR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\f\u00a8\u0006\u001d"}, d2 = {"Lcom/example/barcodescanner/ui/BarcodeAssociation;", "", "olpn", "Lcom/google/mlkit/vision/barcode/common/Barcode;", "toteId", "otherBarcodes", "", "isComplete", "", "(Lcom/google/mlkit/vision/barcode/common/Barcode;Lcom/google/mlkit/vision/barcode/common/Barcode;Ljava/util/List;Z)V", "()Z", "getOlpn", "()Lcom/google/mlkit/vision/barcode/common/Barcode;", "getOtherBarcodes", "()Ljava/util/List;", "getToteId", "component1", "component2", "component3", "component4", "copy", "equals", "other", "getAllBarcodes", "getDisplayText", "", "hashCode", "", "toString", "app_debug"})
public final class BarcodeAssociation {
    @org.jetbrains.annotations.Nullable
    private final com.google.mlkit.vision.barcode.common.Barcode olpn = null;
    @org.jetbrains.annotations.Nullable
    private final com.google.mlkit.vision.barcode.common.Barcode toteId = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.google.mlkit.vision.barcode.common.Barcode> otherBarcodes = null;
    private final boolean isComplete = false;
    
    @org.jetbrains.annotations.NotNull
    public final com.example.barcodescanner.ui.BarcodeAssociation copy(@org.jetbrains.annotations.Nullable
    com.google.mlkit.vision.barcode.common.Barcode olpn, @org.jetbrains.annotations.Nullable
    com.google.mlkit.vision.barcode.common.Barcode toteId, @org.jetbrains.annotations.NotNull
    java.util.List<? extends com.google.mlkit.vision.barcode.common.Barcode> otherBarcodes, boolean isComplete) {
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
    
    public BarcodeAssociation() {
        super();
    }
    
    public BarcodeAssociation(@org.jetbrains.annotations.Nullable
    com.google.mlkit.vision.barcode.common.Barcode olpn, @org.jetbrains.annotations.Nullable
    com.google.mlkit.vision.barcode.common.Barcode toteId, @org.jetbrains.annotations.NotNull
    java.util.List<? extends com.google.mlkit.vision.barcode.common.Barcode> otherBarcodes, boolean isComplete) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.mlkit.vision.barcode.common.Barcode component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.mlkit.vision.barcode.common.Barcode getOlpn() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.mlkit.vision.barcode.common.Barcode component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.mlkit.vision.barcode.common.Barcode getToteId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.google.mlkit.vision.barcode.common.Barcode> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.google.mlkit.vision.barcode.common.Barcode> getOtherBarcodes() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean isComplete() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.google.mlkit.vision.barcode.common.Barcode> getAllBarcodes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDisplayText() {
        return null;
    }
}