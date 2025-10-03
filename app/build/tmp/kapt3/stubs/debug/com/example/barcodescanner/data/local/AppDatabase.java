package com.example.barcodescanner.data.local;

import java.lang.System;

@androidx.room.Database(entities = {com.example.barcodescanner.data.local.BarcodeEntity.class, com.example.barcodescanner.data.local.AssociationEntity.class}, version = 6, exportSchema = false)
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\b"}, d2 = {"Lcom/example/barcodescanner/data/local/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "associationDao", "Lcom/example/barcodescanner/data/local/AssociationDao;", "barcodeDao", "Lcom/example/barcodescanner/data/local/BarcodeDao;", "Companion", "app_debug"})
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull
    public static final com.example.barcodescanner.data.local.AppDatabase.Companion Companion = null;
    @kotlin.jvm.Volatile
    private static volatile com.example.barcodescanner.data.local.AppDatabase INSTANCE;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract com.example.barcodescanner.data.local.BarcodeDao barcodeDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.example.barcodescanner.data.local.AssociationDao associationDao();
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/example/barcodescanner/data/local/AppDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/example/barcodescanner/data/local/AppDatabase;", "clearInstance", "", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.barcodescanner.data.local.AppDatabase getInstance(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return null;
        }
        
        public final void clearInstance() {
        }
    }
}