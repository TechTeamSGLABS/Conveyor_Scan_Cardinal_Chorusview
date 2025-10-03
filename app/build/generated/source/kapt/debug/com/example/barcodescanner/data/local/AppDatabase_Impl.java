package com.example.barcodescanner.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile BarcodeDao _barcodeDao;

  private volatile AssociationDao _associationDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(6) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `barcodes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `value` TEXT NOT NULL, `format` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `session_id` INTEGER NOT NULL, `image_path` TEXT, `synced` INTEGER NOT NULL DEFAULT 0)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `associations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tote_id` TEXT NOT NULL, `olpn` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `session_id` INTEGER NOT NULL, `submitted` INTEGER NOT NULL, `submit_timestamp` INTEGER, `response_data` TEXT, `retry_count` INTEGER NOT NULL, `error_message` TEXT)");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_associations_tote_id_olpn` ON `associations` (`tote_id`, `olpn`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5a247a4a8ec592b96d16353a61ce5376')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `barcodes`");
        _db.execSQL("DROP TABLE IF EXISTS `associations`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsBarcodes = new HashMap<String, TableInfo.Column>(7);
        _columnsBarcodes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarcodes.put("value", new TableInfo.Column("value", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarcodes.put("format", new TableInfo.Column("format", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarcodes.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarcodes.put("session_id", new TableInfo.Column("session_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarcodes.put("image_path", new TableInfo.Column("image_path", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarcodes.put("synced", new TableInfo.Column("synced", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBarcodes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBarcodes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBarcodes = new TableInfo("barcodes", _columnsBarcodes, _foreignKeysBarcodes, _indicesBarcodes);
        final TableInfo _existingBarcodes = TableInfo.read(_db, "barcodes");
        if (! _infoBarcodes.equals(_existingBarcodes)) {
          return new RoomOpenHelper.ValidationResult(false, "barcodes(com.example.barcodescanner.data.local.BarcodeEntity).\n"
                  + " Expected:\n" + _infoBarcodes + "\n"
                  + " Found:\n" + _existingBarcodes);
        }
        final HashMap<String, TableInfo.Column> _columnsAssociations = new HashMap<String, TableInfo.Column>(10);
        _columnsAssociations.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssociations.put("tote_id", new TableInfo.Column("tote_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssociations.put("olpn", new TableInfo.Column("olpn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssociations.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssociations.put("session_id", new TableInfo.Column("session_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssociations.put("submitted", new TableInfo.Column("submitted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssociations.put("submit_timestamp", new TableInfo.Column("submit_timestamp", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssociations.put("response_data", new TableInfo.Column("response_data", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssociations.put("retry_count", new TableInfo.Column("retry_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssociations.put("error_message", new TableInfo.Column("error_message", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAssociations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAssociations = new HashSet<TableInfo.Index>(1);
        _indicesAssociations.add(new TableInfo.Index("index_associations_tote_id_olpn", true, Arrays.asList("tote_id","olpn"), Arrays.asList("ASC","ASC")));
        final TableInfo _infoAssociations = new TableInfo("associations", _columnsAssociations, _foreignKeysAssociations, _indicesAssociations);
        final TableInfo _existingAssociations = TableInfo.read(_db, "associations");
        if (! _infoAssociations.equals(_existingAssociations)) {
          return new RoomOpenHelper.ValidationResult(false, "associations(com.example.barcodescanner.data.local.AssociationEntity).\n"
                  + " Expected:\n" + _infoAssociations + "\n"
                  + " Found:\n" + _existingAssociations);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "5a247a4a8ec592b96d16353a61ce5376", "240eefeb2b8e86e728f2bc5a7a8cc29e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "barcodes","associations");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `barcodes`");
      _db.execSQL("DELETE FROM `associations`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(BarcodeDao.class, BarcodeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AssociationDao.class, AssociationDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public BarcodeDao barcodeDao() {
    if (_barcodeDao != null) {
      return _barcodeDao;
    } else {
      synchronized(this) {
        if(_barcodeDao == null) {
          _barcodeDao = new BarcodeDao_Impl(this);
        }
        return _barcodeDao;
      }
    }
  }

  @Override
  public AssociationDao associationDao() {
    if (_associationDao != null) {
      return _associationDao;
    } else {
      synchronized(this) {
        if(_associationDao == null) {
          _associationDao = new AssociationDao_Impl(this);
        }
        return _associationDao;
      }
    }
  }
}
