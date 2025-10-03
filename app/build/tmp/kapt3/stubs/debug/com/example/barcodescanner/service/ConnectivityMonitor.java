package com.example.barcodescanner.service;

import java.lang.System;

/**
 * Monitor network connectivity and bandwidth
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\u0006J\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\tR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/example/barcodescanner/service/ConnectivityMonitor;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "TAG", "", "getConnectionType", "hasGoodBandwidth", "", "isConnected", "app_debug"})
public final class ConnectivityMonitor {
    private final android.content.Context context = null;
    private final java.lang.String TAG = "ConnectivityMonitor";
    
    public ConnectivityMonitor(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    public final boolean isConnected() {
        return false;
    }
    
    public final boolean hasGoodBandwidth() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getConnectionType() {
        return null;
    }
}