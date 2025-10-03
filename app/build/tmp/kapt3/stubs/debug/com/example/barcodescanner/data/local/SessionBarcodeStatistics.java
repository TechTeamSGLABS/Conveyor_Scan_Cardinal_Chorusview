package com.example.barcodescanner.data.local;

import java.lang.System;

/**
 * 🔧 FIXED: Moved to separate file to avoid Room annotation processing issues
 * ✅ Session statistics data class for performance monitoring
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003JE\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020\u0005H\u00d6\u0001J\t\u0010$\u001a\u00020%H\u00d6\u0001R\u0011\u0010\u000b\u001a\u00020\f8F\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0015\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0010R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013\u00a8\u0006&"}, d2 = {"Lcom/example/barcodescanner/data/local/SessionBarcodeStatistics;", "", "sessionId", "", "totalCount", "", "formatCount", "firstScan", "lastScan", "avgTimestamp", "(JIIJJJ)V", "avgScansPerSecond", "", "getAvgScansPerSecond", "()F", "getAvgTimestamp", "()J", "getFirstScan", "getFormatCount", "()I", "getLastScan", "sessionDuration", "getSessionDuration", "getSessionId", "getTotalCount", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class SessionBarcodeStatistics {
    @androidx.room.ColumnInfo(name = "session_id")
    private final long sessionId = 0L;
    @androidx.room.ColumnInfo(name = "total_count")
    private final int totalCount = 0;
    @androidx.room.ColumnInfo(name = "format_count")
    private final int formatCount = 0;
    @androidx.room.ColumnInfo(name = "first_scan")
    private final long firstScan = 0L;
    @androidx.room.ColumnInfo(name = "last_scan")
    private final long lastScan = 0L;
    @androidx.room.ColumnInfo(name = "avg_timestamp")
    private final long avgTimestamp = 0L;
    
    /**
     * 🔧 FIXED: Moved to separate file to avoid Room annotation processing issues
     * ✅ Session statistics data class for performance monitoring
     */
    @org.jetbrains.annotations.NotNull
    public final com.example.barcodescanner.data.local.SessionBarcodeStatistics copy(long sessionId, int totalCount, int formatCount, long firstScan, long lastScan, long avgTimestamp) {
        return null;
    }
    
    /**
     * 🔧 FIXED: Moved to separate file to avoid Room annotation processing issues
     * ✅ Session statistics data class for performance monitoring
     */
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 🔧 FIXED: Moved to separate file to avoid Room annotation processing issues
     * ✅ Session statistics data class for performance monitoring
     */
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    /**
     * 🔧 FIXED: Moved to separate file to avoid Room annotation processing issues
     * ✅ Session statistics data class for performance monitoring
     */
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public java.lang.String toString() {
        return null;
    }
    
    public SessionBarcodeStatistics(long sessionId, int totalCount, int formatCount, long firstScan, long lastScan, long avgTimestamp) {
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
    
    public final int getFormatCount() {
        return 0;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final long getFirstScan() {
        return 0L;
    }
    
    public final long component5() {
        return 0L;
    }
    
    public final long getLastScan() {
        return 0L;
    }
    
    public final long component6() {
        return 0L;
    }
    
    public final long getAvgTimestamp() {
        return 0L;
    }
    
    public final long getSessionDuration() {
        return 0L;
    }
    
    public final float getAvgScansPerSecond() {
        return 0.0F;
    }
}