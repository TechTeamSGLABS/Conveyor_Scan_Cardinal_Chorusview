package com.example.barcodescanner.ui;

import java.lang.System;

/**
 * Barcode analyzer for CameraX integration
 * Uses ML Kit for barcode detection
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u001f\u0012\u0018\u0010\u0002\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\u0002\u0010\u0007J\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0017R \u0010\u0002\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u00020\u00060\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0012"}, d2 = {"Lcom/example/barcodescanner/ui/BarcodeAnalyzer;", "Landroidx/camera/core/ImageAnalysis$Analyzer;", "onBarcodeDetected", "Lkotlin/Function1;", "", "Lcom/google/mlkit/vision/barcode/common/Barcode;", "", "(Lkotlin/jvm/functions/Function1;)V", "scanner", "Lcom/google/mlkit/vision/barcode/BarcodeScanner;", "getScanner", "()Lcom/google/mlkit/vision/barcode/BarcodeScanner;", "scanner$delegate", "Lkotlin/Lazy;", "analyze", "imageProxy", "Landroidx/camera/core/ImageProxy;", "Companion", "app_debug"})
public final class BarcodeAnalyzer implements androidx.camera.core.ImageAnalysis.Analyzer {
    private final kotlin.jvm.functions.Function1<java.util.List<? extends com.google.mlkit.vision.barcode.common.Barcode>, kotlin.Unit> onBarcodeDetected = null;
    @org.jetbrains.annotations.NotNull
    public static final com.example.barcodescanner.ui.BarcodeAnalyzer.Companion Companion = null;
    private static final java.lang.String TAG = "BarcodeAnalyzer";
    private final kotlin.Lazy scanner$delegate = null;
    
    public BarcodeAnalyzer(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.util.List<? extends com.google.mlkit.vision.barcode.common.Barcode>, kotlin.Unit> onBarcodeDetected) {
        super();
    }
    
    private final com.google.mlkit.vision.barcode.BarcodeScanner getScanner() {
        return null;
    }
    
    @androidx.camera.core.ExperimentalGetImage
    @java.lang.Override
    public void analyze(@org.jetbrains.annotations.NotNull
    androidx.camera.core.ImageProxy imageProxy) {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/barcodescanner/ui/BarcodeAnalyzer$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}