package com.example.barcodescanner.service;

import java.lang.System;

/**
 * Background service for syncing associations to cloud API
 * Runs continuously and handles offline/online scenarios
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\r\u0018\u0000 )2\u00020\u0001:\u0004&\'()B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\'\u0010\u000f\u001a\u00020\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u000eH\u0016J\b\u0010\u001b\u001a\u00020\u000eH\u0016J\"\u0010\u001c\u001a\u00020\u001d2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u001dH\u0016J\u0011\u0010 \u001a\u00020\u000eH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J\b\u0010\"\u001a\u00020\u000eH\u0002J\b\u0010#\u001a\u00020\u000eH\u0002J\u001f\u0010$\u001a\u00020\n2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010%R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006*"}, d2 = {"Lcom/example/barcodescanner/service/CloudSyncService;", "Landroid/app/Service;", "()V", "apiService", "Lcom/example/barcodescanner/service/CloudSyncService$AssociationApiService;", "connectivityMonitor", "Lcom/example/barcodescanner/service/ConnectivityMonitor;", "database", "Lcom/example/barcodescanner/data/local/AppDatabase;", "isRunning", "", "syncJob", "Lkotlinx/coroutines/Job;", "forceSyncNow", "", "markBatchForRetry", "associations", "", "Lcom/example/barcodescanner/data/local/AssociationEntity;", "errorMessage", "", "(Ljava/util/List;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "", "flags", "startId", "performSync", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startSync", "stopSync", "submitBatch", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "AssociationApiService", "AssociationRequest", "AssociationResponse", "Companion", "app_debug"})
public final class CloudSyncService extends android.app.Service {
    @org.jetbrains.annotations.NotNull
    public static final com.example.barcodescanner.service.CloudSyncService.Companion Companion = null;
    private static final java.lang.String TAG = "CloudSyncService";
    private static final java.lang.String API_BASE_URL = "http://3.91.8.133:3000/";
    private static final long SYNC_INTERVAL_MS = 10000L;
    private static final long RETRY_DELAY_MS = 30000L;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_START_SYNC = "START_SYNC";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_STOP_SYNC = "STOP_SYNC";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_FORCE_SYNC = "FORCE_SYNC";
    private kotlinx.coroutines.Job syncJob;
    private boolean isRunning = false;
    private com.example.barcodescanner.data.local.AppDatabase database;
    private com.example.barcodescanner.service.CloudSyncService.AssociationApiService apiService;
    private com.example.barcodescanner.service.ConnectivityMonitor connectivityMonitor;
    
    public CloudSyncService() {
        super();
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    @java.lang.Override
    public int onStartCommand(@org.jetbrains.annotations.Nullable
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    @java.lang.Override
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
        return null;
    }
    
    private final void startSync() {
    }
    
    private final void stopSync() {
    }
    
    private final void forceSyncNow() {
    }
    
    private final java.lang.Object performSync(kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    private final java.lang.Object submitBatch(java.util.List<com.example.barcodescanner.data.local.AssociationEntity> associations, kotlin.coroutines.Continuation<? super java.lang.Boolean> continuation) {
        return null;
    }
    
    private final java.lang.Object markBatchForRetry(java.util.List<com.example.barcodescanner.data.local.AssociationEntity> associations, java.lang.String errorMessage, kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\'\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0001\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\n\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2 = {"Lcom/example/barcodescanner/service/CloudSyncService$AssociationApiService;", "", "submitAssociations", "Lretrofit2/Response;", "Lcom/example/barcodescanner/service/CloudSyncService$AssociationResponse;", "associations", "", "Lcom/example/barcodescanner/service/CloudSyncService$AssociationRequest;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitSingleAssociation", "association", "(Lcom/example/barcodescanner/service/CloudSyncService$AssociationRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
    public static abstract interface AssociationApiService {
        
        @org.jetbrains.annotations.Nullable
        @retrofit2.http.Headers(value = {"Content-Type: application/json", "User-Agent: Chorus-API-Tester/1.0"})
        @retrofit2.http.POST(value = "api/associations")
        public abstract java.lang.Object submitAssociations(@org.jetbrains.annotations.NotNull
        @retrofit2.http.Body
        java.util.List<com.example.barcodescanner.service.CloudSyncService.AssociationRequest> associations, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.barcodescanner.service.CloudSyncService.AssociationResponse>> continuation);
        
        @org.jetbrains.annotations.Nullable
        @retrofit2.http.Headers(value = {"Content-Type: application/json", "User-Agent: Chorus-API-Tester/1.0"})
        @retrofit2.http.POST(value = "api/associations")
        public abstract java.lang.Object submitSingleAssociation(@org.jetbrains.annotations.NotNull
        @retrofit2.http.Body
        com.example.barcodescanner.service.CloudSyncService.AssociationRequest association, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.barcodescanner.service.CloudSyncService.AssociationResponse>> continuation);
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/example/barcodescanner/service/CloudSyncService$AssociationRequest;", "", "toteId", "", "olpn", "(Ljava/lang/String;Ljava/lang/String;)V", "getOlpn", "()Ljava/lang/String;", "getToteId", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class AssociationRequest {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String toteId = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String olpn = null;
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.service.CloudSyncService.AssociationRequest copy(@org.jetbrains.annotations.NotNull
        java.lang.String toteId, @org.jetbrains.annotations.NotNull
        java.lang.String olpn) {
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
        
        public AssociationRequest(@org.jetbrains.annotations.NotNull
        java.lang.String toteId, @org.jetbrains.annotations.NotNull
        java.lang.String olpn) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getToteId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getOlpn() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0019\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000fJ\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003JJ\u0010\u001a\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010\u001bJ\u0013\u0010\u001c\u001a\u00020\u00032\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u0007H\u00d6\u0001J\t\u0010\u001f\u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006 "}, d2 = {"Lcom/example/barcodescanner/service/CloudSyncService$AssociationResponse;", "", "success", "", "message", "", "processed", "", "error", "status", "(Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;)V", "getError", "()Ljava/lang/String;", "getMessage", "getProcessed", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getStatus", "getSuccess", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;)Lcom/example/barcodescanner/service/CloudSyncService$AssociationResponse;", "equals", "other", "hashCode", "toString", "app_debug"})
    public static final class AssociationResponse {
        @org.jetbrains.annotations.Nullable
        private final java.lang.Boolean success = null;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String message = null;
        @org.jetbrains.annotations.Nullable
        private final java.lang.Integer processed = null;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String error = null;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String status = null;
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.service.CloudSyncService.AssociationResponse copy(@org.jetbrains.annotations.Nullable
        java.lang.Boolean success, @org.jetbrains.annotations.Nullable
        java.lang.String message, @org.jetbrains.annotations.Nullable
        java.lang.Integer processed, @org.jetbrains.annotations.Nullable
        java.lang.String error, @org.jetbrains.annotations.Nullable
        java.lang.String status) {
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
        
        public AssociationResponse() {
            super();
        }
        
        public AssociationResponse(@org.jetbrains.annotations.Nullable
        java.lang.Boolean success, @org.jetbrains.annotations.Nullable
        java.lang.String message, @org.jetbrains.annotations.Nullable
        java.lang.Integer processed, @org.jetbrains.annotations.Nullable
        java.lang.String error, @org.jetbrains.annotations.Nullable
        java.lang.String status) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.Boolean component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.Boolean getSuccess() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.Integer component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.Integer getProcessed() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getError() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getStatus() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/example/barcodescanner/service/CloudSyncService$Companion;", "", "()V", "ACTION_FORCE_SYNC", "", "ACTION_START_SYNC", "ACTION_STOP_SYNC", "API_BASE_URL", "RETRY_DELAY_MS", "", "SYNC_INTERVAL_MS", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}