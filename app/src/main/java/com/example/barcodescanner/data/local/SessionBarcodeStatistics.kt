package com.example.barcodescanner.data.local

import androidx.room.ColumnInfo

/**
 * 🔧 FIXED: Moved to separate file to avoid Room annotation processing issues
 * ✅ Session statistics data class for performance monitoring
 */
data class SessionBarcodeStatistics(
    @ColumnInfo(name = "session_id")
    val sessionId: Long,

    @ColumnInfo(name = "total_count")
    val totalCount: Int,

    @ColumnInfo(name = "format_count")
    val formatCount: Int,

    @ColumnInfo(name = "first_scan")
    val firstScan: Long,

    @ColumnInfo(name = "last_scan")
    val lastScan: Long,

    @ColumnInfo(name = "avg_timestamp")
    val avgTimestamp: Long
) {
    val sessionDuration: Long get() = lastScan - firstScan
    val avgScansPerSecond: Float get() = if (sessionDuration > 0) totalCount * 1000f / sessionDuration else 0f
}