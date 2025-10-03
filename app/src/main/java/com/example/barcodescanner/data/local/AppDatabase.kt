package com.example.barcodescanner.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [BarcodeEntity::class, AssociationEntity::class],
    version = 6, // Increment version for new AssociationEntity
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun barcodeDao(): BarcodeDao
    abstract fun associationDao(): AssociationDao // 🚀 NEW: Add this line

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "barcode_scanner_db"
                )
                    .fallbackToDestructiveMigration() // This will recreate DB if schema changes
                    .enableMultiInstanceInvalidation()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Optional: Clear database instance (for testing)
        fun clearInstance() {
            INSTANCE?.close()
            INSTANCE = null
        }
    }
}