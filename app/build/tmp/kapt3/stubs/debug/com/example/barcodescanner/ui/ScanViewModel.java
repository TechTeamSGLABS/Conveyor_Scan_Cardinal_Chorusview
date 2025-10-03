package com.example.barcodescanner.ui;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/example/barcodescanner/ui/ScanViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "chorusApiService", "Lcom/example/barcodescanner/data/remote/ChorusApiService;", "dao", "Lcom/example/barcodescanner/data/local/BarcodeDao;", "db", "Lcom/example/barcodescanner/data/local/AppDatabase;", "onBarcodeScanned", "", "value", "", "format", "", "sessionId", "", "app_debug"})
public final class ScanViewModel extends androidx.lifecycle.AndroidViewModel {
    private final com.example.barcodescanner.data.local.AppDatabase db = null;
    private final com.example.barcodescanner.data.local.BarcodeDao dao = null;
    private final com.example.barcodescanner.data.remote.ChorusApiService chorusApiService = null;
    
    public ScanViewModel(@org.jetbrains.annotations.NotNull
    android.app.Application application) {
        super(null);
    }
    
    public final void onBarcodeScanned(@org.jetbrains.annotations.NotNull
    java.lang.String value, int format, long sessionId) {
    }
}