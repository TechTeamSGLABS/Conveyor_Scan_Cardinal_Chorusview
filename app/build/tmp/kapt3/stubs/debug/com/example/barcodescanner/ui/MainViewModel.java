package com.example.barcodescanner.ui;

import java.lang.System;

/**
 * ViewModel for MainActivity
 * Manages barcode scanning state and database operations
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \'2\u00020\u0001:\u0001\'B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u0007J\u0006\u0010\u001e\u001a\u00020\u001bJ\u0006\u0010\u001f\u001a\u00020\u001bJ\u0006\u0010 \u001a\u00020\u0012J\u001a\u0010!\u001a\u00020\u001b2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u001b0#J\u0006\u0010$\u001a\u00020\u001bJ\u0006\u0010%\u001a\u00020\u001bJ\b\u0010&\u001a\u00020\u001bH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0010\u00a8\u0006("}, d2 = {"Lcom/example/barcodescanner/ui/MainViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_barcodeCount", "Landroidx/lifecycle/MutableLiveData;", "", "_errorMessage", "", "_isScanning", "", "_lastScannedBarcode", "barcodeCount", "Landroidx/lifecycle/LiveData;", "getBarcodeCount", "()Landroidx/lifecycle/LiveData;", "currentSessionId", "", "database", "Lcom/example/barcodescanner/data/local/AppDatabase;", "errorMessage", "getErrorMessage", "isScanning", "lastScannedBarcode", "getLastScannedBarcode", "addBarcode", "", "value", "format", "clearCurrentSession", "clearError", "getCurrentSessionId", "getTotalBarcodeCount", "callback", "Lkotlin/Function1;", "startScanning", "stopScanning", "updateBarcodeCount", "Companion", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull
    public static final com.example.barcodescanner.ui.MainViewModel.Companion Companion = null;
    private static final java.lang.String TAG = "MainViewModel";
    private final com.example.barcodescanner.data.local.AppDatabase database = null;
    private long currentSessionId;
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> _barcodeCount = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Integer> barcodeCount = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isScanning = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isScanning = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _errorMessage = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.String> errorMessage = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _lastScannedBarcode = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.String> lastScannedBarcode = null;
    
    public MainViewModel(@org.jetbrains.annotations.NotNull
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Integer> getBarcodeCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isScanning() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.String> getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.String> getLastScannedBarcode() {
        return null;
    }
    
    /**
     * Add a new barcode to the database
     */
    public final void addBarcode(@org.jetbrains.annotations.NotNull
    java.lang.String value, int format) {
    }
    
    /**
     * Update barcode count from database
     */
    private final void updateBarcodeCount() {
    }
    
    /**
     * Start scanning session
     */
    public final void startScanning() {
    }
    
    /**
     * Stop scanning session
     */
    public final void stopScanning() {
    }
    
    /**
     * Get current session ID
     */
    public final long getCurrentSessionId() {
        return 0L;
    }
    
    /**
     * Clear error message
     */
    public final void clearError() {
    }
    
    /**
     * Get total barcode count across all sessions
     */
    public final void getTotalBarcodeCount(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> callback) {
    }
    
    /**
     * Delete all barcodes for current session
     */
    public final void clearCurrentSession() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/barcodescanner/ui/MainViewModel$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}