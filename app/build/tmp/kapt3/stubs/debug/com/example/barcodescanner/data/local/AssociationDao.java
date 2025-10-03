package com.example.barcodescanner.data.local;

import java.lang.System;

/**
 * 🔄 MINIMAL ENHANCEMENT: Just add essential methods to your existing DAO
 * ✅ Preserves everything you have, adds only what's needed
 */
@androidx.room.Dao
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0016\bg\u0018\u00002\u00020\u0001J\u0011\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0019\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000fH\'J\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J#\u0010\u0011\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J\u001b\u0010\u0016\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0017\u001a\u00020\u0018H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J\u001f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\n\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000f2\u0006\u0010\n\u001a\u00020\u0007H\'J%\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00130\f2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00130\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010 \u001a\u00020\u0018H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J!\u0010!\u001a\b\u0012\u0004\u0012\u00020\r0\f2\b\b\u0002\u0010\"\u001a\u00020\u0018H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J\u0019\u0010#\u001a\u00020\u00182\u0006\u0010\n\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u001b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\n\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0011\u0010&\u001a\u00020\u0018H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\'\u001a\u00020\u00182\u0006\u0010\n\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0019\u0010(\u001a\u00020\u00182\u0006\u0010\n\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ#\u0010)\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010*\u001a\u0004\u0018\u00010\u0013H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010+J\u001f\u0010,\u001a\u00020\u00032\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ\u0019\u0010.\u001a\u00020\u00072\u0006\u0010/\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00100J%\u00101\u001a\b\u0012\u0004\u0012\u00020\u00070\f2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ+\u00102\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u00103\u001a\u00020\u00072\b\u00104\u001a\u0004\u0018\u00010\u0013H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00105J\u0019\u00106\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0018H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J\u001f\u00107\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u00108\u001a\u00020\u0013H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00109J\u0019\u0010:\u001a\u00020\u00032\u0006\u0010/\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00100\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006;"}, d2 = {"Lcom/example/barcodescanner/data/local/AssociationDao;", "", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOldAssociations", "cutoffTime", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSessionAssociations", "sessionId", "getAllAssociations", "", "Lcom/example/barcodescanner/data/local/AssociationEntity;", "getAllAssociationsFlow", "Lkotlinx/coroutines/flow/Flow;", "getAllSessionIds", "getAssociation", "toteId", "", "olpn", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAssociationById", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAssociationsBySession", "getAssociationsBySessionFlow", "getExistingPairKeys", "pairKeys", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPendingAssociations", "getPendingCount", "getRecentAssociations", "limit", "getSessionCount", "getSessionStatistics", "Lcom/example/barcodescanner/data/local/SessionStatistics;", "getSubmittedCount", "getUniqueOlpnCount", "getUniqueToteIdCount", "incrementRetryCount", "errorMessage", "(ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertAll", "associations", "insertAssociation", "association", "(Lcom/example/barcodescanner/data/local/AssociationEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertAssociations", "markAsSubmitted", "timestamp", "responseData", "(IJLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetAssociationForRetry", "searchAssociations", "searchTerm", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateAssociation", "app_debug"})
public abstract interface AssociationDao {
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Insert(onConflict = 5)
    public abstract java.lang.Object insertAssociation(@org.jetbrains.annotations.NotNull
    com.example.barcodescanner.data.local.AssociationEntity association, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Insert(onConflict = 5)
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.barcodescanner.data.local.AssociationEntity> associations, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Update
    public abstract java.lang.Object updateAssociation(@org.jetbrains.annotations.NotNull
    com.example.barcodescanner.data.local.AssociationEntity association, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM associations ORDER BY timestamp DESC")
    public abstract java.lang.Object getAllAssociations(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.AssociationEntity>> continuation);
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.Query(value = "SELECT * FROM associations ORDER BY timestamp DESC")
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.barcodescanner.data.local.AssociationEntity>> getAllAssociationsFlow();
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM associations WHERE session_id = :sessionId ORDER BY timestamp DESC")
    public abstract java.lang.Object getAssociationsBySession(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.AssociationEntity>> continuation);
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.Query(value = "SELECT * FROM associations WHERE session_id = :sessionId ORDER BY timestamp DESC")
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.barcodescanner.data.local.AssociationEntity>> getAssociationsBySessionFlow(long sessionId);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM associations WHERE submitted = 0 AND retry_count < 3 ORDER BY timestamp ASC")
    public abstract java.lang.Object getPendingAssociations(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.AssociationEntity>> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM associations WHERE tote_id = :toteId AND olpn = :olpn LIMIT 1")
    public abstract java.lang.Object getAssociation(@org.jetbrains.annotations.NotNull
    java.lang.String toteId, @org.jetbrains.annotations.NotNull
    java.lang.String olpn, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.barcodescanner.data.local.AssociationEntity> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "UPDATE associations SET submitted = 1, submit_timestamp = :timestamp, response_data = :responseData WHERE id = :id")
    public abstract java.lang.Object markAsSubmitted(int id, long timestamp, @org.jetbrains.annotations.Nullable
    java.lang.String responseData, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "UPDATE associations SET retry_count = retry_count + 1, error_message = :errorMessage WHERE id = :id")
    public abstract java.lang.Object incrementRetryCount(int id, @org.jetbrains.annotations.Nullable
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT COUNT(*) FROM associations WHERE session_id = :sessionId")
    public abstract java.lang.Object getSessionCount(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT COUNT(DISTINCT tote_id) FROM associations WHERE session_id = :sessionId")
    public abstract java.lang.Object getUniqueToteIdCount(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT COUNT(DISTINCT olpn) FROM associations WHERE session_id = :sessionId")
    public abstract java.lang.Object getUniqueOlpnCount(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT COUNT(*) FROM associations WHERE submitted = 1")
    public abstract java.lang.Object getSubmittedCount(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT COUNT(*) FROM associations WHERE submitted = 0")
    public abstract java.lang.Object getPendingCount(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "DELETE FROM associations WHERE session_id = :sessionId")
    public abstract java.lang.Object deleteSessionAssociations(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "DELETE FROM associations")
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM associations WHERE id = :id")
    public abstract java.lang.Object getAssociationById(int id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.barcodescanner.data.local.AssociationEntity> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "\n        SELECT session_id, \n               COUNT(*) as total_count,\n               COUNT(DISTINCT tote_id) as unique_tote_count,\n               COUNT(DISTINCT olpn) as unique_olpn_count,\n               SUM(CASE WHEN submitted = 1 THEN 1 ELSE 0 END) as submitted_count\n        FROM associations \n        WHERE session_id = :sessionId\n        GROUP BY session_id\n    ")
    public abstract java.lang.Object getSessionStatistics(long sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.barcodescanner.data.local.SessionStatistics> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM associations ORDER BY timestamp DESC LIMIT :limit")
    public abstract java.lang.Object getRecentAssociations(int limit, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.AssociationEntity>> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "DELETE FROM associations WHERE timestamp < :cutoffTime")
    public abstract java.lang.Object deleteOldAssociations(long cutoffTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT * FROM associations WHERE tote_id LIKE \'%\' || :searchTerm || \'%\' OR olpn LIKE \'%\' || :searchTerm || \'%\' ORDER BY timestamp DESC")
    public abstract java.lang.Object searchAssociations(@org.jetbrains.annotations.NotNull
    java.lang.String searchTerm, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.barcodescanner.data.local.AssociationEntity>> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT DISTINCT session_id FROM associations ORDER BY session_id DESC")
    public abstract java.lang.Object getAllSessionIds(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "UPDATE associations SET submitted = 0, error_message = NULL, retry_count = 0 WHERE id = :id")
    public abstract java.lang.Object resetAssociationForRetry(int id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Insert(onConflict = 5)
    public abstract java.lang.Object insertAssociations(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.barcodescanner.data.local.AssociationEntity> associations, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> continuation);
    
    @org.jetbrains.annotations.Nullable
    @androidx.room.Query(value = "SELECT (tote_id || \'-\' || olpn) as pair_key FROM associations WHERE (tote_id || \'-\' || olpn) IN (:pairKeys)")
    public abstract java.lang.Object getExistingPairKeys(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> pairKeys, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> continuation);
    
    /**
     * 🔄 MINIMAL ENHANCEMENT: Just add essential methods to your existing DAO
     * ✅ Preserves everything you have, adds only what's needed
     */
    @kotlin.Metadata(mv = {1, 8, 0}, k = 3)
    public final class DefaultImpls {
    }
}