package com.example.barcodescanner.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * 🔄 MINIMAL ENHANCEMENT: Just add essential methods to your existing DAO
 * ✅ Preserves everything you have, adds only what's needed
 */
@Dao
interface AssociationDao {



    // ===========================
    // YOUR EXISTING METHODS (UNCHANGED)
    // ===========================

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAssociation(association: AssociationEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(associations: List<AssociationEntity>)

    @Update
    suspend fun updateAssociation(association: AssociationEntity)

    @Query("SELECT * FROM associations ORDER BY timestamp DESC")
    suspend fun getAllAssociations(): List<AssociationEntity>

    @Query("SELECT * FROM associations ORDER BY timestamp DESC")
    fun getAllAssociationsFlow(): Flow<List<AssociationEntity>>

    @Query("SELECT * FROM associations WHERE session_id = :sessionId ORDER BY timestamp DESC")
    suspend fun getAssociationsBySession(sessionId: Long): List<AssociationEntity>

    @Query("SELECT * FROM associations WHERE session_id = :sessionId ORDER BY timestamp DESC")
    fun getAssociationsBySessionFlow(sessionId: Long): Flow<List<AssociationEntity>>

    @Query("SELECT * FROM associations WHERE submitted = 0 AND retry_count < 3 ORDER BY timestamp ASC")
    suspend fun getPendingAssociations(): List<AssociationEntity>

    @Query("SELECT * FROM associations WHERE tote_id = :toteId AND olpn = :olpn LIMIT 1")
    suspend fun getAssociation(toteId: String, olpn: String): AssociationEntity?

    @Query("UPDATE associations SET submitted = 1, submit_timestamp = :timestamp, response_data = :responseData WHERE id = :id")
    suspend fun markAsSubmitted(id: Int, timestamp: Long, responseData: String?)

    @Query("UPDATE associations SET retry_count = retry_count + 1, error_message = :errorMessage WHERE id = :id")
    suspend fun incrementRetryCount(id: Int, errorMessage: String?)

    @Query("SELECT COUNT(*) FROM associations WHERE session_id = :sessionId")
    suspend fun getSessionCount(sessionId: Long): Int

    @Query("SELECT COUNT(DISTINCT tote_id) FROM associations WHERE session_id = :sessionId")
    suspend fun getUniqueToteIdCount(sessionId: Long): Int

    @Query("SELECT COUNT(DISTINCT olpn) FROM associations WHERE session_id = :sessionId")
    suspend fun getUniqueOlpnCount(sessionId: Long): Int

    @Query("SELECT COUNT(*) FROM associations WHERE submitted = 1")
    suspend fun getSubmittedCount(): Int

    @Query("SELECT COUNT(*) FROM associations WHERE submitted = 0")
    suspend fun getPendingCount(): Int

    @Query("DELETE FROM associations WHERE session_id = :sessionId")
    suspend fun deleteSessionAssociations(sessionId: Long)

    @Query("DELETE FROM associations")
    suspend fun deleteAll()

    @Query("SELECT * FROM associations WHERE id = :id")
    suspend fun getAssociationById(id: Int): AssociationEntity?

    @Query("""
        SELECT session_id, 
               COUNT(*) as total_count,
               COUNT(DISTINCT tote_id) as unique_tote_count,
               COUNT(DISTINCT olpn) as unique_olpn_count,
               SUM(CASE WHEN submitted = 1 THEN 1 ELSE 0 END) as submitted_count
        FROM associations 
        WHERE session_id = :sessionId
        GROUP BY session_id
    """)
    suspend fun getSessionStatistics(sessionId: Long): SessionStatistics?

    @Query("SELECT * FROM associations ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentAssociations(limit: Int = 50): List<AssociationEntity>

    @Query("DELETE FROM associations WHERE timestamp < :cutoffTime")
    suspend fun deleteOldAssociations(cutoffTime: Long)

    // ===========================
    // 🔄 MINIMAL NEW ADDITIONS (Only what's essential)
    // ===========================

    // Simple search functionality
    @Query("SELECT * FROM associations WHERE tote_id LIKE '%' || :searchTerm || '%' OR olpn LIKE '%' || :searchTerm || '%' ORDER BY timestamp DESC")
    suspend fun searchAssociations(searchTerm: String): List<AssociationEntity>

    // Session management helper
    @Query("SELECT DISTINCT session_id FROM associations ORDER BY session_id DESC")
    suspend fun getAllSessionIds(): List<Long>

    // Enhanced retry functionality
    @Query("UPDATE associations SET submitted = 0, error_message = NULL, retry_count = 0 WHERE id = :id")
    suspend fun resetAssociationForRetry(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAssociations(associations: List<AssociationEntity>): List<Long>

    @Query("SELECT (tote_id || '-' || olpn) as pair_key FROM associations WHERE (tote_id || '-' || olpn) IN (:pairKeys)")
    suspend fun getExistingPairKeys(pairKeys: List<String>): List<String>

}

/**
 * YOUR EXISTING SessionStatistics (UNCHANGED)
 */
data class SessionStatistics(
    @ColumnInfo(name = "session_id")
    val sessionId: Long,

    @ColumnInfo(name = "total_count")
    val totalCount: Int,

    @ColumnInfo(name = "unique_tote_count")
    val uniqueToteCount: Int,

    @ColumnInfo(name = "unique_olpn_count")
    val uniqueOlpnCount: Int,

    @ColumnInfo(name = "submitted_count")
    val submittedCount: Int
) {
    val pendingCount: Int get() = totalCount - submittedCount
    val successRate: Float get() = if (totalCount > 0) submittedCount.toFloat() / totalCount else 0f
}

/**
 * 🔄 NEW: Extension function for display status
 * ✅ FIXED: Proper extension function for AssociationEntity
 */
fun AssociationEntity.getDisplayStatus(): String {
    return when {
        submitted -> "✅ Submitted"
        retryCount >= 3 -> "❌ Failed ($retryCount retries)"
        retryCount > 0 -> "🔄 Retrying ($retryCount attempts)"
        errorMessage != null -> "⚠️ Error"
        else -> "⏳ Pending"
    }
}