package com.example.barcodescanner.view;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u001c\u001a\u00020\u001dJ\u0014\u0010\u001e\u001a\u00020\u001d2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00130 J\u0010\u0010!\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020#H\u0002J \u0010$\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020#2\u0006\u0010%\u001a\u00020\u00132\u0006\u0010&\u001a\u00020\rH\u0002J\b\u0010\'\u001a\u00020\u001dH\u0014J\u0010\u0010(\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020#H\u0014J\b\u0010)\u001a\u00020\u001dH\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00130\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u001b0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/example/barcodescanner/view/OverlayView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "animationRunnable", "Ljava/lang/Runnable;", "barcodeLastPosition", "", "", "Landroid/graphics/RectF;", "crosshairPaint", "Landroid/graphics/Paint;", "currentBarcodes", "", "Lcom/example/barcodescanner/model/DetectedBarcode;", "duplicateTickPaint", "handler", "Landroid/os/Handler;", "persistentTickMarks", "scannedTickPaint", "tickBackgroundPaint", "tickMarkAnimations", "", "clear", "", "drawBarcodes", "detectedBarcodes", "", "drawScanningInterface", "canvas", "Landroid/graphics/Canvas;", "drawTickMark", "barcode", "value", "onDetachedFromWindow", "onDraw", "updateAnimations", "app_debug"})
public final class OverlayView extends android.view.View {
    private final java.util.List<com.example.barcodescanner.model.DetectedBarcode> currentBarcodes = null;
    private final java.util.Map<java.lang.String, java.lang.Float> tickMarkAnimations = null;
    private final java.util.Map<java.lang.String, com.example.barcodescanner.model.DetectedBarcode> persistentTickMarks = null;
    private final java.util.Map<java.lang.String, android.graphics.RectF> barcodeLastPosition = null;
    private final android.graphics.Paint scannedTickPaint = null;
    private final android.graphics.Paint duplicateTickPaint = null;
    private final android.graphics.Paint tickBackgroundPaint = null;
    private final android.graphics.Paint crosshairPaint = null;
    private final android.os.Handler handler = null;
    private final java.lang.Runnable animationRunnable = null;
    
    @kotlin.jvm.JvmOverloads
    public OverlayView(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads
    public OverlayView(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads
    public OverlayView(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    private final void updateAnimations() {
    }
    
    public final void drawBarcodes(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.barcodescanner.model.DetectedBarcode> detectedBarcodes) {
    }
    
    public final void clear() {
    }
    
    @java.lang.Override
    protected void onDraw(@org.jetbrains.annotations.NotNull
    android.graphics.Canvas canvas) {
    }
    
    private final void drawScanningInterface(android.graphics.Canvas canvas) {
    }
    
    private final void drawTickMark(android.graphics.Canvas canvas, com.example.barcodescanner.model.DetectedBarcode barcode, java.lang.String value) {
    }
    
    @java.lang.Override
    protected void onDetachedFromWindow() {
    }
}