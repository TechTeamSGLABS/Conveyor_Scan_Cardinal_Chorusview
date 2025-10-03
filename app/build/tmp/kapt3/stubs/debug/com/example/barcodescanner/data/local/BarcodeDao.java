package com.example.barcodescanner.data.local;

import java.lang.System;

/**
 * Data Access Object for Barcode operations
 */
@androidx.room.Dao
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0011\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010\u0005\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0019\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0019\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u0011H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00110\u0013H\'J\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u0011H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u0015\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0016\u001a\u00020\u0017H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J#\u0010\u0019\u001a\u0004\u0018\u00010\b2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cJ\u001f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\b0\u00112\u0006\u0010\u001e\u001a\u00020\u0017H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\'\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\b0\u00112\u0006\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 J\u001f\u0010!\u001a\b\u0012\u0004\u0012\u00020\b0\u00112\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00110\u00132\u0006\u0010\u000f\u001a\u00020\fH\'J\u001f\u0010#\u001a\b\u0012\u0004\u0012\u00020\b0\u00112\u0006\u0010\u001a\u001a\u00020\u001bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010$J-\u0010%\u001a\b\u0012\u0004\u0012\u00020\b0\u00112\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00112\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\'J\u0011\u0010(\u001a\u00020\u0017H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J-\u0010)\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00112\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00112\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\'J\u0017\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u0011H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J!\u0010,\u001a\b\u0012\u0004\u0012\u00020\b0\u00112\b\b\u0002\u0010-\u001a\u00020\u0017H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010.\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u001b\u0010/\u001a\u0004\u0018\u0001002\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0011\u00101\u001a\u00020\u0017H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0019\u00102\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u001f\u00103\u001a\u00020\u00032\f\u00104\u001a\b\u0012\u0004\u0012\u00020\b0\u0011H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00105J\u0019\u00106\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u001f\u00107\u001a\u00020\u00032\f\u00104\u001a\b\u0012\u0004\u0012\u00020\b0\u0011H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00105J\u001f\u00108\u001a\b\u0012\u0004\u0012\u00020\b0\u00112\u0006\u00109\u001a\u00020\u001bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010$J\u0019\u0010:\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ!\u0010;\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010<\u001a\u00020=H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010>\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006?"}, d2 = {"Lcom/example/barcodescanner/data/local/BarcodeDao;", "", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAllBarcodes", "deleteBarcode", "barcode", "Lcom/example/barcodescanner/data/local/BarcodeEntity;", "(Lcom/example/barcodescanner/data/local/BarcodeEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOldBarcodes", "cutoffTime", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSessionBarcodes", "sessionId", "getAllBarcodes", "", "getAllBarcodesFlow", "Lkotlinx/coroutines/flow/Flow;", "getAllSessionIds", "getBarcodeById", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBarcodeByValueAndSession", "value", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBarcodesByFormat", "format", "getBarcodesByFormatAndSession", "(IJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBarcodesBySession", "getBarcodesBySessionFlow", "getBarcodesByValue", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBarcodesByValuesAndSession", "values", "(Ljava/util/List;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCount", "getExistingBarcodeValues", "getFormatDistribution", "Lcom/example/barcodescanner/data/local/FormatStatistics;", "getRecentBarcodes", "limit", "getSessionBarcodeCount", "getSessionBarcodeStatistics", "Lcom/example/barcodescanner/data/local/SessionBarcodeStatistics;", "getTotalBarcodeCount", "getUniqueFormatCount", "insertAll", "barcodes", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertBarcode", "insertBarcodes", "searchBarcodes", "searchTerm", "updateBarcode", "updateSyncStatus", "synced", "", "(IZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface BarcodeDao {
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Insert(onConflict = 5)
    public abstract java.lang.Object insertBarcode(@org.jetbrains.annotations.NotNull
    com.example.barcodescanner.data.local.BarcodeEntity barcode, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Insert(onConflict = 5)
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.barcodescanner.data.local.BarcodeEntity> barcodes, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Insert(onConflict = 5)
    public abstract java.lang.Object insertBarcodes(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.barcodescanner.data.local.BarcodeEntity> barcodes, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Update
    public abstract java.lang.Object updateBarcode(@org.jetbrains.annotations.NotNull
    com.example.barcodescanner.data.local.BarcodeEntity barcode, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Delete
    public abstract java.lang.Object deleteBarcode(@org.jetbrains.annotations.NotNull
    com.example.barcodescanner.data.local.BarcodeEntity barcode, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM barcodes ORDER BY timestamp DESC")
    public abstract java.lang.Object getAllBarcodes(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.BarcodeEntity>> continuation);
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.Query(value = "SELECT * FROM barcodes ORDER BY timestamp DESC")
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.barcodescanner.data.local.BarcodeEntity>> getAllBarcodesFlow();
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM barcodes WHERE session_id = :sessionId ORDER BY timestamp DESC")
    public abstract java.lang.Object getBarcodesBySession(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.BarcodeEntity>> continuation);
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.Query(value = "SELECT * FROM barcodes WHERE session_id = :sessionId ORDER BY timestamp DESC")
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.barcodescanner.data.local.BarcodeEntity>> getBarcodesBySessionFlow(long sessionId);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM barcodes WHERE value = :value AND session_id = :sessionId LIMIT 1")
    public abstract java.lang.Object getBarcodeByValueAndSession(@org.jetbrains.annotations.NotNull
    java.lang.String value, long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.barcodescanner.data.local.BarcodeEntity> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM barcodes WHERE value = :value ORDER BY timestamp DESC")
    public abstract java.lang.Object getBarcodesByValue(@org.jetbrains.annotations.NotNull
    java.lang.String value, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.BarcodeEntity>> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM barcodes WHERE format = :format ORDER BY timestamp DESC")
    public abstract java.lang.Object getBarcodesByFormat(int format, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.BarcodeEntity>> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM barcodes WHERE format = :format AND session_id = :sessionId ORDER BY timestamp DESC")
    public abstract java.lang.Object getBarcodesByFormatAndSession(int format, long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.BarcodeEntity>> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM barcodes WHERE id = :id")
    public abstract java.lang.Object getBarcodeById(int id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.barcodescanner.data.local.BarcodeEntity> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM barcodes WHERE value IN (:values) AND session_id = :sessionId")
    public abstract java.lang.Object getBarcodesByValuesAndSession(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> values, long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.BarcodeEntity>> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT value FROM barcodes WHERE value IN (:values) AND session_id = :sessionId")
    public abstract java.lang.Object getExistingBarcodeValues(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> values, long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT COUNT(*) FROM barcodes")
    public abstract java.lang.Object getCount(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT COUNT(*) FROM barcodes WHERE session_id = :sessionId")
    public abstract java.lang.Object getSessionBarcodeCount(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT COUNT(DISTINCT format) FROM barcodes WHERE session_id = :sessionId")
    public abstract java.lang.Object getUniqueFormatCount(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT COUNT(*) FROM barcodes")
    public abstract java.lang.Object getTotalBarcodeCount(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "UPDATE barcodes SET synced = :synced WHERE id = :id")
    public abstract java.lang.Object updateSyncStatus(int id, boolean synced, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "DELETE FROM barcodes")
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "DELETE FROM barcodes")
    public abstract java.lang.Object deleteAllBarcodes(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "DELETE FROM barcodes WHERE session_id = :sessionId")
    public abstract java.lang.Object deleteSessionBarcodes(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "DELETE FROM barcodes WHERE timestamp < :cutoffTime")
    public abstract java.lang.Object deleteOldBarcodes(long cutoffTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM barcodes ORDER BY timestamp DESC LIMIT :limit")
    public abstract java.lang.Object getRecentBarcodes(int limit, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.BarcodeEntity>> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT DISTINCT session_id FROM barcodes ORDER BY session_id DESC")
    public abstract java.lang.Object getAllSessionIds(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM barcodes WHERE value LIKE \'%\' || :searchTerm || \'%\' ORDER BY timestamp DESC")
    public abstract java.lang.Object searchBarcodes(@org.jetbrains.annotations.NotNull
    java.lang.String searchTerm, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.BarcodeEntity>> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "\n        SELECT session_id, \n               COUNT(*) as total_count,\n               COUNT(DISTINCT format) as format_count,\n               MIN(timestamp) as first_scan,\n               MAX(timestamp) as last_scan,\n               AVG(timestamp) as avg_timestamp\n        FROM barcodes \n        WHERE session_id = :sessionId\n        GROUP BY session_id\n    ")
    public abstract java.lang.Object getSessionBarcodeStatistics(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.barcodescanner.data.local.SessionBarcodeStatistics> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "\n        SELECT format, \n               COUNT(*) as count,\n               COUNT(DISTINCT session_id) as session_count\n        FROM barcodes \n        GROUP BY format\n        ORDER BY count DESC\n    ")
    public abstract java.lang.Object getFormatDistribution(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.FormatStatistics>> continuation);
    
    /**
     * Data Access Object for Barcode operations
     */
    @kotlin.Metadata(mv = {1, 8, 0}, k = 3)
    public final class DefaultImpls {
    }
}