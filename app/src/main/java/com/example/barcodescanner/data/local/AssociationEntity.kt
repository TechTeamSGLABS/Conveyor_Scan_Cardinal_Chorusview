package com.example.barcodescanner.data.local
import com.example.barcodescanner.data.local.AssociationEntity
import com.example.barcodescanner.data.local.AssociationDao
import com.example.barcodescanner.data.local.SessionStatistics

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index

/**
 * Database entity for storing OLPN-ToteID associations from Conveyer Scan
 */
@Entity(
    tableName = "associations",
    indices = [Index(value = ["tote_id", "olpn"], unique = true)]
)
data class AssociationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "tote_id")
    val toteId: String,

    @ColumnInfo(name = "olpn")
    val olpn: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "session_id")
    val sessionId: Long,

    @ColumnInfo(name = "submitted")
    val submitted: Boolean = false,

    @ColumnInfo(name = "submit_timestamp")
    val submitTimestamp: Long? = null,

    @ColumnInfo(name = "response_data")
    val responseData: String? = null,

    @ColumnInfo(name = "retry_count")
    val retryCount: Int = 0,

    @ColumnInfo(name = "error_message")
    val errorMessage: String? = null
) {
    /**
     * Get display status for UI
     */
    fun getDisplayStatus(): String {
        return when {
            submitted -> "✅ Submitted"
            retryCount > 0 -> "🔄 Retrying ($retryCount)"
            else -> "⏳ Pending"
        }
    }

    /**
     * Check if this association is ready for submission
     */
    fun isReadyForSubmission(): Boolean {
        return !submitted && retryCount < 3
    }

    /**
     * Get formatted timestamp for display
     */
    fun getFormattedTimestamp(): String {
        return java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
            .format(java.util.Date(timestamp))
    }
}