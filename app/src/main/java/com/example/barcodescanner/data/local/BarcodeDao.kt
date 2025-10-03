package com.example.barcodescanner.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Barcode operations
 */
@Dao
interface BarcodeDao {

    // ===========================
    // CORE CRUD OPERATIONS
    // ===========================

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBarcode(barcode: BarcodeEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(barcodes: List<BarcodeEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBarcodes(barcodes: List<BarcodeEntity>)

    @Update
    suspend fun updateBarcode(barcode: BarcodeEntity)

    @Delete
    suspend fun deleteBarcode(barcode: BarcodeEntity)

    // ===========================
    // QUERY OPERATIONS
    // ===========================

    @Query("SELECT * FROM barcodes ORDER BY timestamp DESC")
    suspend fun getAllBarcodes(): List<BarcodeEntity>

    @Query("SELECT * FROM barcodes ORDER BY timestamp DESC")
    fun getAllBarcodesFlow(): Flow<List<BarcodeEntity>>

    @Query("SELECT * FROM barcodes WHERE session_id = :sessionId ORDER BY timestamp DESC")
    suspend fun getBarcodesBySession(sessionId: Long): List<BarcodeEntity>

    @Query("SELECT * FROM barcodes WHERE session_id = :sessionId ORDER BY timestamp DESC")
    fun getBarcodesBySessionFlow(sessionId: Long): Flow<List<BarcodeEntity>>

    @Query("SELECT * FROM barcodes WHERE value = :value AND session_id = :sessionId LIMIT 1")
    suspend fun getBarcodeByValueAndSession(value: String, sessionId: Long): BarcodeEntity?

    @Query("SELECT * FROM barcodes WHERE value = :value ORDER BY timestamp DESC")
    suspend fun getBarcodesByValue(value: String): List<BarcodeEntity>

    @Query("SELECT * FROM barcodes WHERE format = :format ORDER BY timestamp DESC")
    suspend fun getBarcodesByFormat(format: Int): List<BarcodeEntity>

    @Query("SELECT * FROM barcodes WHERE format = :format AND session_id = :sessionId ORDER BY timestamp DESC")
    suspend fun getBarcodesByFormatAndSession(format: Int, sessionId: Long): List<BarcodeEntity>

    @Query("SELECT * FROM barcodes WHERE id = :id")
    suspend fun getBarcodeById(id: Int): BarcodeEntity?

    @Query("SELECT * FROM barcodes WHERE value IN (:values) AND session_id = :sessionId")
    suspend fun getBarcodesByValuesAndSession(values: List<String>, sessionId: Long): List<BarcodeEntity>

    @Query("SELECT value FROM barcodes WHERE value IN (:values) AND session_id = :sessionId")
    suspend fun getExistingBarcodeValues(values: List<String>, sessionId: Long): List<String>


    // ===========================
    // COUNT OPERATIONS
    // ===========================

    @Query("SELECT COUNT(*) FROM barcodes")
    suspend fun getCount(): Int

    @Query("SELECT COUNT(*) FROM barcodes WHERE session_id = :sessionId")
    suspend fun getSessionBarcodeCount(sessionId: Long): Int

    @Query("SELECT COUNT(DISTINCT format) FROM barcodes WHERE session_id = :sessionId")
    suspend fun getUniqueFormatCount(sessionId: Long): Int

    @Query("SELECT COUNT(*) FROM barcodes")
    suspend fun getTotalBarcodeCount(): Int

    // ===========================
    // UPDATE OPERATIONS
    // ===========================

    @Query("UPDATE barcodes SET synced = :synced WHERE id = :id")
    suspend fun updateSyncStatus(id: Int, synced: Boolean)


    // ===========================
    // DELETE OPERATIONS
    // ===========================

    @Query("DELETE FROM barcodes")
    suspend fun deleteAll()

    @Query("DELETE FROM barcodes")
    suspend fun deleteAllBarcodes()

    @Query("DELETE FROM barcodes WHERE session_id = :sessionId")
    suspend fun deleteSessionBarcodes(sessionId: Long)

    @Query("DELETE FROM barcodes WHERE timestamp < :cutoffTime")
    suspend fun deleteOldBarcodes(cutoffTime: Long)

    // ===========================
    // SEARCH AND UTILITY
    // ===========================

    @Query("SELECT * FROM barcodes ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentBarcodes(limit: Int = 50): List<BarcodeEntity>

    @Query("SELECT DISTINCT session_id FROM barcodes ORDER BY session_id DESC")
    suspend fun getAllSessionIds(): List<Long>

    @Query("SELECT * FROM barcodes WHERE value LIKE '%' || :searchTerm || '%' ORDER BY timestamp DESC")
    suspend fun searchBarcodes(searchTerm: String): List<BarcodeEntity>



    // ===========================
    // STATISTICS OPERATIONS
    // ===========================

    @Query("""
        SELECT session_id, 
               COUNT(*) as total_count,
               COUNT(DISTINCT format) as format_count,
               MIN(timestamp) as first_scan,
               MAX(timestamp) as last_scan,
               AVG(timestamp) as avg_timestamp
        FROM barcodes 
        WHERE session_id = :sessionId
        GROUP BY session_id
    """)
    suspend fun getSessionBarcodeStatistics(sessionId: Long): SessionBarcodeStatistics?

    @Query("""
        SELECT format, 
               COUNT(*) as count,
               COUNT(DISTINCT session_id) as session_count
        FROM barcodes 
        GROUP BY format
        ORDER BY count DESC
    """)
    suspend fun getFormatDistribution(): List<FormatStatistics>
}