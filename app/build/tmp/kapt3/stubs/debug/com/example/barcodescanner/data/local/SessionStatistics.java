package com.example.barcodescanner.data.local;

import java.lang.System;

/**
 * YOUR EXISTING SessionStatistics (UNCHANGED)
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J;\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020\u0005H\u00d6\u0001J\t\u0010!\u001a\u00020\"H\u00d6\u0001R\u0011\u0010\n\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\b\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\u0010\u001a\u00020\u00118F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\fR\u0016\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\fR\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\f\u00a8\u0006#"}, d2 = {"Lcom/example/barcodescanner/data/local/SessionStatistics;", "", "sessionId", "", "totalCount", "", "uniqueToteCount", "uniqueOlpnCount", "submittedCount", "(JIIII)V", "pendingCount", "getPendingCount", "()I", "getSessionId", "()J", "getSubmittedCount", "successRate", "", "getSuccessRate", "()F", "getTotalCount", "getUniqueOlpnCount", "getUniqueToteCount", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class SessionStatistics {
    @androidx.room.ColumnInfo(name = "session_id")
    private final long sessionId = 0L;
    @androidx.room.ColumnInfo(name = "total_count")
    private final int totalCount = 0;
    @androidx.room.ColumnInfo(name = "unique_tote_count")
    private final int uniqueToteCount = 0;
    @androidx.room.ColumnInfo(name = "unique_olpn_count")
    private final int uniqueOlpnCount = 0;
    @androidx.room.ColumnInfo(name = "submitted_count")
    private final int submittedCount = 0;
    
    /**
     * YOUR EXISTING SessionStatistics (UNCHANGED)
     */
    @org.jetbrains.annotations.NotNull
    public final com.example.barcodescanner.data.local.SessionStatistics copy(long sessionId, int totalCount, int uniqueToteCount, int uniqueOlpnCount, int submittedCount) {
        return null;
    }
    
    /**
     * YOUR EXISTING SessionStatistics (UNCHANGED)
     */
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    /**
     * YOUR EXISTING SessionStatistics (UNCHANGED)
     */
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    /**
     * YOUR EXISTING SessionStatistics (UNCHANGED)
     */
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public java.lang.String toString() {
        return null;
    }
    
    public SessionStatistics(long sessionId, int totalCount, int uniqueToteCount, int uniqueOlpnCount, int submittedCount) {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long getSessionId() {
        return 0L;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int getTotalCount() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int getUniqueToteCount() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int getUniqueOlpnCount() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int getSubmittedCount() {
        return 0;
    }
    
    public final int getPendingCount() {
        return 0;
    }
    
    public final float getSuccessRate() {
        return 0.0F;
    }
}