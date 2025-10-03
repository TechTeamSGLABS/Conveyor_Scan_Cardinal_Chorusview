package com.example.barcodescanner.ui;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u00b2\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 e2\u00020\u0001:\u0001eB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020#H\u0002J\u001c\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%2\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020(0%H\u0002J\u0018\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020(2\u0006\u0010,\u001a\u00020(H\u0002J\u0010\u0010-\u001a\u00020\r2\u0006\u0010.\u001a\u00020\rH\u0002J\u0018\u0010/\u001a\u00020\r2\u0006\u0010.\u001a\u00020\r2\u0006\u00100\u001a\u000201H\u0002J\u001e\u00102\u001a\u00020\r2\u0006\u00103\u001a\u00020\r2\f\u00104\u001a\b\u0012\u0004\u0012\u00020&0%H\u0002J\u0010\u00105\u001a\u00020#2\u0006\u0010.\u001a\u00020\rH\u0002J\b\u00106\u001a\u00020#H\u0002J\u0010\u00107\u001a\u0002012\u0006\u0010.\u001a\u00020\rH\u0002J\b\u00108\u001a\u00020#H\u0002J\u0010\u00109\u001a\u00020\r2\u0006\u0010:\u001a\u00020;H\u0002J\b\u0010<\u001a\u00020#H\u0002J\u0010\u0010=\u001a\u00020!2\u0006\u0010>\u001a\u00020(H\u0002J\u0010\u0010?\u001a\u00020!2\u0006\u0010>\u001a\u00020(H\u0002J\u0018\u0010@\u001a\u00020!2\u0006\u0010A\u001a\u00020B2\u0006\u0010.\u001a\u00020\rH\u0002J\b\u0010C\u001a\u00020#H\u0016J\u0012\u0010D\u001a\u00020#2\b\u0010E\u001a\u0004\u0018\u00010FH\u0014J\b\u0010G\u001a\u00020#H\u0014J+\u0010H\u001a\u00020#2\u0006\u0010I\u001a\u0002012\f\u0010J\u001a\b\u0012\u0004\u0012\u00020L0K2\u0006\u0010M\u001a\u00020NH\u0016\u00a2\u0006\u0002\u0010OJ\b\u0010P\u001a\u00020!H\u0016J\u0010\u0010Q\u001a\u00020\r2\u0006\u0010.\u001a\u00020\rH\u0002J\u001f\u0010R\u001a\b\u0012\u0004\u0012\u00020(0%2\u0006\u0010.\u001a\u00020\rH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010SJ/\u0010T\u001a\u00020#2\u0006\u00103\u001a\u00020\r2\f\u00104\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010U\u001a\u00020\u0019H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010VJ\b\u0010W\u001a\u00020#H\u0002J\b\u0010X\u001a\u00020#H\u0002J\b\u0010Y\u001a\u00020#H\u0002J\b\u0010Z\u001a\u00020#H\u0002J\b\u0010[\u001a\u00020#H\u0002J\b\u0010\\\u001a\u00020#H\u0002J\b\u0010]\u001a\u00020#H\u0002J\b\u0010^\u001a\u00020#H\u0002J\b\u0010_\u001a\u00020#H\u0002J\b\u0010`\u001a\u00020#H\u0002J!\u0010a\u001a\u0002Hb\"\u0004\b\u0000\u0010b*\b\u0012\u0004\u0012\u0002Hb0cH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010dR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u000fX\u0082D\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006f"}, d2 = {"Lcom/example/barcodescanner/ui/PhotoCaptureActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "backButton", "Landroid/widget/Button;", "cameraExecutor", "Ljava/util/concurrent/ExecutorService;", "cameraInstance", "Landroidx/camera/core/Camera;", "captureButton", "capturedImageView", "Landroid/widget/ImageView;", "capturedOptimizedBitmap", "Landroid/graphics/Bitmap;", "currentZoomRatio", "", "focusButton", "imageCapture", "Landroidx/camera/core/ImageCapture;", "maxZoomRatio", "minZoomRatio", "previewView", "Landroidx/camera/view/PreviewView;", "readBarcodesButton", "sessionId", "", "zoomInButton", "zoomLevelText", "Landroid/widget/TextView;", "zoomOutButton", "zoomResetButton", "zoomStep", "allPermissionsGranted", "", "applyInitialZoom", "", "associateOLPNAndTOTE", "", "Lcom/example/barcodescanner/ui/BarcodeAssociation;", "barcodes", "Lcom/google/mlkit/vision/barcode/common/Barcode;", "calculateDistance", "", "barcode1", "barcode2", "compressToTargetSize", "bitmap", "correctImageOrientation", "rotationDegrees", "", "drawAssociationOverlay", "originalBitmap", "associations", "fastBarcodeDetection", "fastPhotoCapture", "getImageSizeKB", "hideZoomControls", "imageProxyToOptimizedBitmap", "image", "Landroidx/camera/core/ImageProxy;", "initializeViews", "isOLPN", "barcode", "isTOTE", "isValidBoundingBox", "box", "Landroid/graphics/Rect;", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onRequestPermissionsResult", "requestCode", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onSupportNavigateUp", "optimizeBitmap", "performFastDetection", "(Landroid/graphics/Bitmap;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processBarcodeAssociations", "detectionTime", "(Landroid/graphics/Bitmap;Ljava/util/List;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "quickFocus", "resetZoom", "setupClickListeners", "setupZoomInfo", "showNoBarcodesDetectedDialog", "showZoomControls", "startCamera", "updateZoomUI", "zoomIn", "zoomOut", "await", "T", "Lcom/google/android/gms/tasks/Task;", "(Lcom/google/android/gms/tasks/Task;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class PhotoCaptureActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.camera.view.PreviewView previewView;
    private android.widget.Button captureButton;
    private android.widget.Button readBarcodesButton;
    private android.widget.ImageView capturedImageView;
    private android.widget.Button backButton;
    private android.widget.Button zoomInButton;
    private android.widget.Button zoomOutButton;
    private android.widget.Button zoomResetButton;
    private android.widget.TextView zoomLevelText;
    private android.widget.Button focusButton;
    private androidx.camera.core.ImageCapture imageCapture;
    private androidx.camera.core.Camera cameraInstance;
    private java.util.concurrent.ExecutorService cameraExecutor;
    private android.graphics.Bitmap capturedOptimizedBitmap;
    private long sessionId = 0L;
    private float currentZoomRatio = 1.5F;
    private float minZoomRatio = 1.0F;
    private float maxZoomRatio = 1.0F;
    private final float zoomStep = 0.3F;
    @org.jetbrains.annotations.NotNull
    public static final com.example.barcodescanner.ui.PhotoCaptureActivity.Companion Companion = null;
    private static final java.lang.String TAG = "FastPhotoCapture";
    private static final int REQUEST_CODE_PERMISSIONS = 1001;
    private static final java.lang.String[] REQUIRED_PERMISSIONS = {"android.permission.CAMERA"};
    private static final int MAX_IMAGE_SIZE_KB = 512;
    private static final int OPTIMAL_WIDTH = 2048;
    private static final int OPTIMAL_HEIGHT = 1536;
    
    public PhotoCaptureActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeViews() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void startCamera() {
    }
    
    private final void applyInitialZoom() {
    }
    
    private final void quickFocus() {
    }
    
    private final void fastPhotoCapture() {
    }
    
    private final android.graphics.Bitmap imageProxyToOptimizedBitmap(androidx.camera.core.ImageProxy image) {
        return null;
    }
    
    private final android.graphics.Bitmap optimizeBitmap(android.graphics.Bitmap bitmap) {
        return null;
    }
    
    private final android.graphics.Bitmap compressToTargetSize(android.graphics.Bitmap bitmap) {
        return null;
    }
    
    private final void fastBarcodeDetection(android.graphics.Bitmap bitmap) {
    }
    
    private final java.lang.Object performFastDetection(android.graphics.Bitmap bitmap, kotlin.coroutines.Continuation<? super java.util.List<? extends com.google.mlkit.vision.barcode.common.Barcode>> continuation) {
        return null;
    }
    
    private final java.util.List<com.example.barcodescanner.ui.BarcodeAssociation> associateOLPNAndTOTE(java.util.List<? extends com.google.mlkit.vision.barcode.common.Barcode> barcodes) {
        return null;
    }
    
    private final boolean isOLPN(com.google.mlkit.vision.barcode.common.Barcode barcode) {
        return false;
    }
    
    private final boolean isTOTE(com.google.mlkit.vision.barcode.common.Barcode barcode) {
        return false;
    }
    
    private final double calculateDistance(com.google.mlkit.vision.barcode.common.Barcode barcode1, com.google.mlkit.vision.barcode.common.Barcode barcode2) {
        return 0.0;
    }
    
    private final java.lang.Object processBarcodeAssociations(android.graphics.Bitmap originalBitmap, java.util.List<com.example.barcodescanner.ui.BarcodeAssociation> associations, long detectionTime, kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    private final android.graphics.Bitmap drawAssociationOverlay(android.graphics.Bitmap originalBitmap, java.util.List<com.example.barcodescanner.ui.BarcodeAssociation> associations) {
        return null;
    }
    
    private final boolean isValidBoundingBox(android.graphics.Rect box, android.graphics.Bitmap bitmap) {
        return false;
    }
    
    private final void showNoBarcodesDetectedDialog() {
    }
    
    private final <T extends java.lang.Object>java.lang.Object await(com.google.android.gms.tasks.Task<T> $this$await, kotlin.coroutines.Continuation<? super T> p1) {
        return null;
    }
    
    private final int getImageSizeKB(android.graphics.Bitmap bitmap) {
        return 0;
    }
    
    private final android.graphics.Bitmap correctImageOrientation(android.graphics.Bitmap bitmap, int rotationDegrees) {
        return null;
    }
    
    private final void setupZoomInfo() {
    }
    
    private final void updateZoomUI() {
    }
    
    private final void zoomIn() {
    }
    
    private final void zoomOut() {
    }
    
    private final void resetZoom() {
    }
    
    private final void hideZoomControls() {
    }
    
    private final void showZoomControls() {
    }
    
    private final boolean allPermissionsGranted() {
        return false;
    }
    
    @java.lang.Override
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull
    int[] grantResults) {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    @java.lang.Override
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    @java.lang.Override
    public void onBackPressed() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\nX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/example/barcodescanner/ui/PhotoCaptureActivity$Companion;", "", "()V", "MAX_IMAGE_SIZE_KB", "", "OPTIMAL_HEIGHT", "OPTIMAL_WIDTH", "REQUEST_CODE_PERMISSIONS", "REQUIRED_PERMISSIONS", "", "", "[Ljava/lang/String;", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}