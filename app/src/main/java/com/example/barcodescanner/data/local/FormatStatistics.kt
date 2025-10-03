package com.example.barcodescanner.data.local

import androidx.room.ColumnInfo

/**
 * 🔧 FIXED: Moved to separate file to avoid Room annotation processing issues
 * ✅ Format statistics for debugging and optimization
 */
data class FormatStatistics(
    @ColumnInfo(name = "format")
    val format: Int,

    @ColumnInfo(name = "count")
    val count: Int,

    @ColumnInfo(name = "session_count")
    val sessionCount: Int
)