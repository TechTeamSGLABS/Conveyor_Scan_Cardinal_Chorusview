package com.example.barcodescanner.data.local;

import java.lang.System;

/**
 * Database entity for storing OLPN-ToteID associations from Conveyer Scan
 */
@androidx.room.Entity(tableName = "associations", indices = {@androidx.room.Index(unique = true, value = {"tote_id", "olpn"})})
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b)\b\u0087\b\u0018\u00002\u00020\u0001Bi\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0010J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\t\u0010$\u001a\u00020\u0005H\u00c6\u0003J\t\u0010%\u001a\u00020\bH\u00c6\u0003J\t\u0010&\u001a\u00020\bH\u00c6\u0003J\t\u0010\'\u001a\u00020\u000bH\u00c6\u0003J\u0010\u0010(\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u000b\u0010)\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003Jx\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010,J\u0013\u0010-\u001a\u00020\u000b2\b\u0010.\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010/\u001a\u00020\u0005J\u0006\u00100\u001a\u00020\u0005J\t\u00101\u001a\u00020\u0003H\u00d6\u0001J\u0006\u00102\u001a\u00020\u000bJ\t\u00103\u001a\u00020\u0005H\u00d6\u0001R\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0018\u0010\r\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0016\u0010\u000e\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0016\u0010\t\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\f\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\n\u001a\u00020\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0012\u00a8\u00064"}, d2 = {"Lcom/example/barcodescanner/data/local/AssociationEntity;", "", "id", "", "toteId", "", "olpn", "timestamp", "", "sessionId", "submitted", "", "submitTimestamp", "responseData", "retryCount", "errorMessage", "(ILjava/lang/String;Ljava/lang/String;JJZLjava/lang/Long;Ljava/lang/String;ILjava/lang/String;)V", "getErrorMessage", "()Ljava/lang/String;", "getId", "()I", "getOlpn", "getResponseData", "getRetryCount", "getSessionId", "()J", "getSubmitTimestamp", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getSubmitted", "()Z", "getTimestamp", "getToteId", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(ILjava/lang/String;Ljava/lang/String;JJZLjava/lang/Long;Ljava/lang/String;ILjava/lang/String;)Lcom/example/barcodescanner/data/local/AssociationEntity;", "equals", "other", "getDisplayStatus", "getFormattedTimestamp", "hashCode", "isReadyForSubmission", "toString", "app_debug"})
public final class AssociationEntity {
    @androidx.room.ColumnInfo(name = "id")
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final int id = 0;
    @org.jetbrains.annotations.NotNull
    @androidx.room.ColumnInfo(name = "tote_id")
    private final java.lang.String toteId = null;
    @org.jetbrains.annotations.NotNull
    @androidx.room.ColumnInfo(name = "olpn")
    private final java.lang.String olpn = null;
    @androidx.room.ColumnInfo(name = "timestamp")
    private final long timestamp = 0L;
    @androidx.room.ColumnInfo(name = "session_id")
    private final long sessionId = 0L;
    @androidx.room.ColumnInfo(name = "submitted")
    private final boolean submitted = false;
    @org.jetbrains.annotations.Nullable
    @androidx.room.ColumnInfo(name = "submit_timestamp")
    private final java.lang.Long submitTimestamp = null;
    @org.jetbrains.annotations.Nullable
    @androidx.room.ColumnInfo(name = "response_data")
    private final java.lang.String responseData = null;
    @androidx.room.ColumnInfo(name = "retry_count")
    private final int retryCount = 0;
    @org.jetbrains.annotations.Nullable
    @androidx.room.ColumnInfo(name = "error_message")
    private final java.lang.String errorMessage = null;
    
    /**
     * Database entity for storing OLPN-ToteID associations from Conveyer Scan
     */
    @org.jetbrains.annotations.NotNull
    public final com.example.barcodescanner.data.local.AssociationEntity copy(int id, @org.jetbrains.annotations.NotNull
    java.lang.String toteId, @org.jetbrains.annotations.NotNull
    java.lang.String olpn, long timestamp, long sessionId, boolean submitted, @org.jetbrains.annotations.Nullable
    java.lang.Long submitTimestamp, @org.jetbrains.annotations.Nullable
    java.lang.String responseData, int retryCount, @org.jetbrains.annotations.Nullable
    java.lang.String errorMessage) {
        return null;
    }
    
    /**
     * Database entity for storing OLPN-ToteID associations from Conveyer Scan
     */
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    /**
     * Database entity for storing OLPN-ToteID associations from Conveyer Scan
     */
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    /**
     * Database entity for storing OLPN-ToteID associations from Conveyer Scan
     */
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public java.lang.String toString() {
        return null;
    }
    
    public AssociationEntity(int id, @org.jetbrains.annotations.NotNull
    java.lang.String toteId, @org.jetbrains.annotations.NotNull
    java.lang.String olpn, long timestamp, long sessionId, boolean submitted, @org.jetbrains.annotations.Nullable
    java.lang.Long submitTimestamp, @org.jetbrains.annotations.Nullable
    java.lang.String responseData, int retryCount, @org.jetbrains.annotations.Nullable
    java.lang.String errorMessage) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getToteId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getOlpn() {
        return null;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final long getTimestamp() {
        return 0L;
    }
    
    public final long component5() {
        return 0L;
    }
    
    public final long getSessionId() {
        return 0L;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean getSubmitted() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getSubmitTimestamp() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getResponseData() {
        return null;
    }
    
    public final int component9() {
        return 0;
    }
    
    public final int getRetryCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    /**
     * Get display status for UI
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDisplayStatus() {
        return null;
    }
    
    /**
     * Check if this association is ready for submission
     */
    public final boolean isReadyForSubmission() {
        return false;
    }
    
    /**
     * Get formatted timestamp for display
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFormattedTimestamp() {
        return null;
    }
}