package com.example.barcodescanner.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barcodes")
data class BarcodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "value")
    val value: String,

    @ColumnInfo(name = "format")
    val format: Int,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "session_id")
    val sessionId: Long,

    @ColumnInfo(name = "image_path")
    val imagePath: String? = null,

    @ColumnInfo(name = "synced", defaultValue = "0")
    val synced: Boolean = false
)