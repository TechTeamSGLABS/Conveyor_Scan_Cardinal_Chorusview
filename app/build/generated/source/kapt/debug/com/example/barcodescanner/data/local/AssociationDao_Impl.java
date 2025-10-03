package com.example.barcodescanner.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AssociationDao_Impl implements AssociationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AssociationEntity> __insertionAdapterOfAssociationEntity;

  private final EntityDeletionOrUpdateAdapter<AssociationEntity> __updateAdapterOfAssociationEntity;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsSubmitted;

  private final SharedSQLiteStatement __preparedStmtOfIncrementRetryCount;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSessionAssociations;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldAssociations;

  private final SharedSQLiteStatement __preparedStmtOfResetAssociationForRetry;

  public AssociationDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAssociationEntity = new EntityInsertionAdapter<AssociationEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `associations` (`id`,`tote_id`,`olpn`,`timestamp`,`session_id`,`submitted`,`submit_timestamp`,`response_data`,`retry_count`,`error_message`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AssociationEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getToteId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getToteId());
        }
        if (value.getOlpn() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getOlpn());
        }
        stmt.bindLong(4, value.getTimestamp());
        stmt.bindLong(5, value.getSessionId());
        final int _tmp = value.getSubmitted() ? 1 : 0;
        stmt.bindLong(6, _tmp);
        if (value.getSubmitTimestamp() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.getSubmitTimestamp());
        }
        if (value.getResponseData() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getResponseData());
        }
        stmt.bindLong(9, value.getRetryCount());
        if (value.getErrorMessage() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getErrorMessage());
        }
      }
    };
    this.__updateAdapterOfAssociationEntity = new EntityDeletionOrUpdateAdapter<AssociationEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `associations` SET `id` = ?,`tote_id` = ?,`olpn` = ?,`timestamp` = ?,`session_id` = ?,`submitted` = ?,`submit_timestamp` = ?,`response_data` = ?,`retry_count` = ?,`error_message` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AssociationEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getToteId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getToteId());
        }
        if (value.getOlpn() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getOlpn());
        }
        stmt.bindLong(4, value.getTimestamp());
        stmt.bindLong(5, value.getSessionId());
        final int _tmp = value.getSubmitted() ? 1 : 0;
        stmt.bindLong(6, _tmp);
        if (value.getSubmitTimestamp() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.getSubmitTimestamp());
        }
        if (value.getResponseData() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getResponseData());
        }
        stmt.bindLong(9, value.getRetryCount());
        if (value.getErrorMessage() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getErrorMessage());
        }
        stmt.bindLong(11, value.getId());
      }
    };
    this.__preparedStmtOfMarkAsSubmitted = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE associations SET submitted = 1, submit_timestamp = ?, response_data = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfIncrementRetryCount = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE associations SET retry_count = retry_count + 1, error_message = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteSessionAssociations = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM associations WHERE session_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM associations";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldAssociations = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM associations WHERE timestamp < ?";
        return _query;
      }
    };
    this.__preparedStmtOfResetAssociationForRetry = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE associations SET submitted = 0, error_message = NULL, retry_count = 0 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAssociation(final AssociationEntity association,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfAssociationEntity.insertAndReturnId(association);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertAll(final List<AssociationEntity> associations,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAssociationEntity.insert(associations);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertAssociations(final List<AssociationEntity> associations,
      final Continuation<? super List<Long>> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<List<Long>>() {
      @Override
      public List<Long> call() throws Exception {
        __db.beginTransaction();
        try {
          List<Long> _result = __insertionAdapterOfAssociationEntity.insertAndReturnIdsList(associations);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateAssociation(final AssociationEntity association,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAssociationEntity.handle(association);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object markAsSubmitted(final int id, final long timestamp, final String responseData,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsSubmitted.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        if (responseData == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, responseData);
        }
        _argIndex = 3;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfMarkAsSubmitted.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object incrementRetryCount(final int id, final String errorMessage,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementRetryCount.acquire();
        int _argIndex = 1;
        if (errorMessage == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, errorMessage);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfIncrementRetryCount.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteSessionAssociations(final long sessionId,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSessionAssociations.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteSessionAssociations.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteOldAssociations(final long cutoffTime,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldAssociations.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cutoffTime);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteOldAssociations.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object resetAssociationForRetry(final int id,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfResetAssociationForRetry.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfResetAssociationForRetry.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object getAllAssociations(
      final Continuation<? super List<AssociationEntity>> continuation) {
    final String _sql = "SELECT * FROM associations ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AssociationEntity>>() {
      @Override
      public List<AssociationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfToteId = CursorUtil.getColumnIndexOrThrow(_cursor, "tote_id");
          final int _cursorIndexOfOlpn = CursorUtil.getColumnIndexOrThrow(_cursor, "olpn");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "submitted");
          final int _cursorIndexOfSubmitTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "submit_timestamp");
          final int _cursorIndexOfResponseData = CursorUtil.getColumnIndexOrThrow(_cursor, "response_data");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retry_count");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "error_message");
          final List<AssociationEntity> _result = new ArrayList<AssociationEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AssociationEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpToteId;
            if (_cursor.isNull(_cursorIndexOfToteId)) {
              _tmpToteId = null;
            } else {
              _tmpToteId = _cursor.getString(_cursorIndexOfToteId);
            }
            final String _tmpOlpn;
            if (_cursor.isNull(_cursorIndexOfOlpn)) {
              _tmpOlpn = null;
            } else {
              _tmpOlpn = _cursor.getString(_cursorIndexOfOlpn);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final boolean _tmpSubmitted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSubmitted);
            _tmpSubmitted = _tmp != 0;
            final Long _tmpSubmitTimestamp;
            if (_cursor.isNull(_cursorIndexOfSubmitTimestamp)) {
              _tmpSubmitTimestamp = null;
            } else {
              _tmpSubmitTimestamp = _cursor.getLong(_cursorIndexOfSubmitTimestamp);
            }
            final String _tmpResponseData;
            if (_cursor.isNull(_cursorIndexOfResponseData)) {
              _tmpResponseData = null;
            } else {
              _tmpResponseData = _cursor.getString(_cursorIndexOfResponseData);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            _item = new AssociationEntity(_tmpId,_tmpToteId,_tmpOlpn,_tmpTimestamp,_tmpSessionId,_tmpSubmitted,_tmpSubmitTimestamp,_tmpResponseData,_tmpRetryCount,_tmpErrorMessage);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<AssociationEntity>> getAllAssociationsFlow() {
    final String _sql = "SELECT * FROM associations ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"associations"}, new Callable<List<AssociationEntity>>() {
      @Override
      public List<AssociationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfToteId = CursorUtil.getColumnIndexOrThrow(_cursor, "tote_id");
          final int _cursorIndexOfOlpn = CursorUtil.getColumnIndexOrThrow(_cursor, "olpn");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "submitted");
          final int _cursorIndexOfSubmitTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "submit_timestamp");
          final int _cursorIndexOfResponseData = CursorUtil.getColumnIndexOrThrow(_cursor, "response_data");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retry_count");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "error_message");
          final List<AssociationEntity> _result = new ArrayList<AssociationEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AssociationEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpToteId;
            if (_cursor.isNull(_cursorIndexOfToteId)) {
              _tmpToteId = null;
            } else {
              _tmpToteId = _cursor.getString(_cursorIndexOfToteId);
            }
            final String _tmpOlpn;
            if (_cursor.isNull(_cursorIndexOfOlpn)) {
              _tmpOlpn = null;
            } else {
              _tmpOlpn = _cursor.getString(_cursorIndexOfOlpn);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final boolean _tmpSubmitted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSubmitted);
            _tmpSubmitted = _tmp != 0;
            final Long _tmpSubmitTimestamp;
            if (_cursor.isNull(_cursorIndexOfSubmitTimestamp)) {
              _tmpSubmitTimestamp = null;
            } else {
              _tmpSubmitTimestamp = _cursor.getLong(_cursorIndexOfSubmitTimestamp);
            }
            final String _tmpResponseData;
            if (_cursor.isNull(_cursorIndexOfResponseData)) {
              _tmpResponseData = null;
            } else {
              _tmpResponseData = _cursor.getString(_cursorIndexOfResponseData);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            _item = new AssociationEntity(_tmpId,_tmpToteId,_tmpOlpn,_tmpTimestamp,_tmpSessionId,_tmpSubmitted,_tmpSubmitTimestamp,_tmpResponseData,_tmpRetryCount,_tmpErrorMessage);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAssociationsBySession(final long sessionId,
      final Continuation<? super List<AssociationEntity>> continuation) {
    final String _sql = "SELECT * FROM associations WHERE session_id = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AssociationEntity>>() {
      @Override
      public List<AssociationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfToteId = CursorUtil.getColumnIndexOrThrow(_cursor, "tote_id");
          final int _cursorIndexOfOlpn = CursorUtil.getColumnIndexOrThrow(_cursor, "olpn");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "submitted");
          final int _cursorIndexOfSubmitTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "submit_timestamp");
          final int _cursorIndexOfResponseData = CursorUtil.getColumnIndexOrThrow(_cursor, "response_data");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retry_count");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "error_message");
          final List<AssociationEntity> _result = new ArrayList<AssociationEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AssociationEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpToteId;
            if (_cursor.isNull(_cursorIndexOfToteId)) {
              _tmpToteId = null;
            } else {
              _tmpToteId = _cursor.getString(_cursorIndexOfToteId);
            }
            final String _tmpOlpn;
            if (_cursor.isNull(_cursorIndexOfOlpn)) {
              _tmpOlpn = null;
            } else {
              _tmpOlpn = _cursor.getString(_cursorIndexOfOlpn);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final boolean _tmpSubmitted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSubmitted);
            _tmpSubmitted = _tmp != 0;
            final Long _tmpSubmitTimestamp;
            if (_cursor.isNull(_cursorIndexOfSubmitTimestamp)) {
              _tmpSubmitTimestamp = null;
            } else {
              _tmpSubmitTimestamp = _cursor.getLong(_cursorIndexOfSubmitTimestamp);
            }
            final String _tmpResponseData;
            if (_cursor.isNull(_cursorIndexOfResponseData)) {
              _tmpResponseData = null;
            } else {
              _tmpResponseData = _cursor.getString(_cursorIndexOfResponseData);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            _item = new AssociationEntity(_tmpId,_tmpToteId,_tmpOlpn,_tmpTimestamp,_tmpSessionId,_tmpSubmitted,_tmpSubmitTimestamp,_tmpResponseData,_tmpRetryCount,_tmpErrorMessage);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<AssociationEntity>> getAssociationsBySessionFlow(final long sessionId) {
    final String _sql = "SELECT * FROM associations WHERE session_id = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"associations"}, new Callable<List<AssociationEntity>>() {
      @Override
      public List<AssociationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfToteId = CursorUtil.getColumnIndexOrThrow(_cursor, "tote_id");
          final int _cursorIndexOfOlpn = CursorUtil.getColumnIndexOrThrow(_cursor, "olpn");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "submitted");
          final int _cursorIndexOfSubmitTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "submit_timestamp");
          final int _cursorIndexOfResponseData = CursorUtil.getColumnIndexOrThrow(_cursor, "response_data");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retry_count");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "error_message");
          final List<AssociationEntity> _result = new ArrayList<AssociationEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AssociationEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpToteId;
            if (_cursor.isNull(_cursorIndexOfToteId)) {
              _tmpToteId = null;
            } else {
              _tmpToteId = _cursor.getString(_cursorIndexOfToteId);
            }
            final String _tmpOlpn;
            if (_cursor.isNull(_cursorIndexOfOlpn)) {
              _tmpOlpn = null;
            } else {
              _tmpOlpn = _cursor.getString(_cursorIndexOfOlpn);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final boolean _tmpSubmitted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSubmitted);
            _tmpSubmitted = _tmp != 0;
            final Long _tmpSubmitTimestamp;
            if (_cursor.isNull(_cursorIndexOfSubmitTimestamp)) {
              _tmpSubmitTimestamp = null;
            } else {
              _tmpSubmitTimestamp = _cursor.getLong(_cursorIndexOfSubmitTimestamp);
            }
            final String _tmpResponseData;
            if (_cursor.isNull(_cursorIndexOfResponseData)) {
              _tmpResponseData = null;
            } else {
              _tmpResponseData = _cursor.getString(_cursorIndexOfResponseData);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            _item = new AssociationEntity(_tmpId,_tmpToteId,_tmpOlpn,_tmpTimestamp,_tmpSessionId,_tmpSubmitted,_tmpSubmitTimestamp,_tmpResponseData,_tmpRetryCount,_tmpErrorMessage);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getPendingAssociations(
      final Continuation<? super List<AssociationEntity>> continuation) {
    final String _sql = "SELECT * FROM associations WHERE submitted = 0 AND retry_count < 3 ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AssociationEntity>>() {
      @Override
      public List<AssociationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfToteId = CursorUtil.getColumnIndexOrThrow(_cursor, "tote_id");
          final int _cursorIndexOfOlpn = CursorUtil.getColumnIndexOrThrow(_cursor, "olpn");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "submitted");
          final int _cursorIndexOfSubmitTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "submit_timestamp");
          final int _cursorIndexOfResponseData = CursorUtil.getColumnIndexOrThrow(_cursor, "response_data");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retry_count");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "error_message");
          final List<AssociationEntity> _result = new ArrayList<AssociationEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AssociationEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpToteId;
            if (_cursor.isNull(_cursorIndexOfToteId)) {
              _tmpToteId = null;
            } else {
              _tmpToteId = _cursor.getString(_cursorIndexOfToteId);
            }
            final String _tmpOlpn;
            if (_cursor.isNull(_cursorIndexOfOlpn)) {
              _tmpOlpn = null;
            } else {
              _tmpOlpn = _cursor.getString(_cursorIndexOfOlpn);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final boolean _tmpSubmitted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSubmitted);
            _tmpSubmitted = _tmp != 0;
            final Long _tmpSubmitTimestamp;
            if (_cursor.isNull(_cursorIndexOfSubmitTimestamp)) {
              _tmpSubmitTimestamp = null;
            } else {
              _tmpSubmitTimestamp = _cursor.getLong(_cursorIndexOfSubmitTimestamp);
            }
            final String _tmpResponseData;
            if (_cursor.isNull(_cursorIndexOfResponseData)) {
              _tmpResponseData = null;
            } else {
              _tmpResponseData = _cursor.getString(_cursorIndexOfResponseData);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            _item = new AssociationEntity(_tmpId,_tmpToteId,_tmpOlpn,_tmpTimestamp,_tmpSessionId,_tmpSubmitted,_tmpSubmitTimestamp,_tmpResponseData,_tmpRetryCount,_tmpErrorMessage);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getAssociation(final String toteId, final String olpn,
      final Continuation<? super AssociationEntity> continuation) {
    final String _sql = "SELECT * FROM associations WHERE tote_id = ? AND olpn = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (toteId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, toteId);
    }
    _argIndex = 2;
    if (olpn == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, olpn);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AssociationEntity>() {
      @Override
      public AssociationEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfToteId = CursorUtil.getColumnIndexOrThrow(_cursor, "tote_id");
          final int _cursorIndexOfOlpn = CursorUtil.getColumnIndexOrThrow(_cursor, "olpn");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "submitted");
          final int _cursorIndexOfSubmitTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "submit_timestamp");
          final int _cursorIndexOfResponseData = CursorUtil.getColumnIndexOrThrow(_cursor, "response_data");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retry_count");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "error_message");
          final AssociationEntity _result;
          if(_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpToteId;
            if (_cursor.isNull(_cursorIndexOfToteId)) {
              _tmpToteId = null;
            } else {
              _tmpToteId = _cursor.getString(_cursorIndexOfToteId);
            }
            final String _tmpOlpn;
            if (_cursor.isNull(_cursorIndexOfOlpn)) {
              _tmpOlpn = null;
            } else {
              _tmpOlpn = _cursor.getString(_cursorIndexOfOlpn);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final boolean _tmpSubmitted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSubmitted);
            _tmpSubmitted = _tmp != 0;
            final Long _tmpSubmitTimestamp;
            if (_cursor.isNull(_cursorIndexOfSubmitTimestamp)) {
              _tmpSubmitTimestamp = null;
            } else {
              _tmpSubmitTimestamp = _cursor.getLong(_cursorIndexOfSubmitTimestamp);
            }
            final String _tmpResponseData;
            if (_cursor.isNull(_cursorIndexOfResponseData)) {
              _tmpResponseData = null;
            } else {
              _tmpResponseData = _cursor.getString(_cursorIndexOfResponseData);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            _result = new AssociationEntity(_tmpId,_tmpToteId,_tmpOlpn,_tmpTimestamp,_tmpSessionId,_tmpSubmitted,_tmpSubmitTimestamp,_tmpResponseData,_tmpRetryCount,_tmpErrorMessage);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getSessionCount(final long sessionId,
      final Continuation<? super Integer> continuation) {
    final String _sql = "SELECT COUNT(*) FROM associations WHERE session_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getUniqueToteIdCount(final long sessionId,
      final Continuation<? super Integer> continuation) {
    final String _sql = "SELECT COUNT(DISTINCT tote_id) FROM associations WHERE session_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getUniqueOlpnCount(final long sessionId,
      final Continuation<? super Integer> continuation) {
    final String _sql = "SELECT COUNT(DISTINCT olpn) FROM associations WHERE session_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getSubmittedCount(final Continuation<? super Integer> continuation) {
    final String _sql = "SELECT COUNT(*) FROM associations WHERE submitted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getPendingCount(final Continuation<? super Integer> continuation) {
    final String _sql = "SELECT COUNT(*) FROM associations WHERE submitted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getAssociationById(final int id,
      final Continuation<? super AssociationEntity> continuation) {
    final String _sql = "SELECT * FROM associations WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AssociationEntity>() {
      @Override
      public AssociationEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfToteId = CursorUtil.getColumnIndexOrThrow(_cursor, "tote_id");
          final int _cursorIndexOfOlpn = CursorUtil.getColumnIndexOrThrow(_cursor, "olpn");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "submitted");
          final int _cursorIndexOfSubmitTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "submit_timestamp");
          final int _cursorIndexOfResponseData = CursorUtil.getColumnIndexOrThrow(_cursor, "response_data");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retry_count");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "error_message");
          final AssociationEntity _result;
          if(_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpToteId;
            if (_cursor.isNull(_cursorIndexOfToteId)) {
              _tmpToteId = null;
            } else {
              _tmpToteId = _cursor.getString(_cursorIndexOfToteId);
            }
            final String _tmpOlpn;
            if (_cursor.isNull(_cursorIndexOfOlpn)) {
              _tmpOlpn = null;
            } else {
              _tmpOlpn = _cursor.getString(_cursorIndexOfOlpn);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final boolean _tmpSubmitted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSubmitted);
            _tmpSubmitted = _tmp != 0;
            final Long _tmpSubmitTimestamp;
            if (_cursor.isNull(_cursorIndexOfSubmitTimestamp)) {
              _tmpSubmitTimestamp = null;
            } else {
              _tmpSubmitTimestamp = _cursor.getLong(_cursorIndexOfSubmitTimestamp);
            }
            final String _tmpResponseData;
            if (_cursor.isNull(_cursorIndexOfResponseData)) {
              _tmpResponseData = null;
            } else {
              _tmpResponseData = _cursor.getString(_cursorIndexOfResponseData);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            _result = new AssociationEntity(_tmpId,_tmpToteId,_tmpOlpn,_tmpTimestamp,_tmpSessionId,_tmpSubmitted,_tmpSubmitTimestamp,_tmpResponseData,_tmpRetryCount,_tmpErrorMessage);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getSessionStatistics(final long sessionId,
      final Continuation<? super SessionStatistics> continuation) {
    final String _sql = "\n"
            + "        SELECT session_id, \n"
            + "               COUNT(*) as total_count,\n"
            + "               COUNT(DISTINCT tote_id) as unique_tote_count,\n"
            + "               COUNT(DISTINCT olpn) as unique_olpn_count,\n"
            + "               SUM(CASE WHEN submitted = 1 THEN 1 ELSE 0 END) as submitted_count\n"
            + "        FROM associations \n"
            + "        WHERE session_id = ?\n"
            + "        GROUP BY session_id\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SessionStatistics>() {
      @Override
      public SessionStatistics call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = 0;
          final int _cursorIndexOfTotalCount = 1;
          final int _cursorIndexOfUniqueToteCount = 2;
          final int _cursorIndexOfUniqueOlpnCount = 3;
          final int _cursorIndexOfSubmittedCount = 4;
          final SessionStatistics _result;
          if(_cursor.moveToFirst()) {
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final int _tmpTotalCount;
            _tmpTotalCount = _cursor.getInt(_cursorIndexOfTotalCount);
            final int _tmpUniqueToteCount;
            _tmpUniqueToteCount = _cursor.getInt(_cursorIndexOfUniqueToteCount);
            final int _tmpUniqueOlpnCount;
            _tmpUniqueOlpnCount = _cursor.getInt(_cursorIndexOfUniqueOlpnCount);
            final int _tmpSubmittedCount;
            _tmpSubmittedCount = _cursor.getInt(_cursorIndexOfSubmittedCount);
            _result = new SessionStatistics(_tmpSessionId,_tmpTotalCount,_tmpUniqueToteCount,_tmpUniqueOlpnCount,_tmpSubmittedCount);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getRecentAssociations(final int limit,
      final Continuation<? super List<AssociationEntity>> continuation) {
    final String _sql = "SELECT * FROM associations ORDER BY timestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AssociationEntity>>() {
      @Override
      public List<AssociationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfToteId = CursorUtil.getColumnIndexOrThrow(_cursor, "tote_id");
          final int _cursorIndexOfOlpn = CursorUtil.getColumnIndexOrThrow(_cursor, "olpn");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "submitted");
          final int _cursorIndexOfSubmitTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "submit_timestamp");
          final int _cursorIndexOfResponseData = CursorUtil.getColumnIndexOrThrow(_cursor, "response_data");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retry_count");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "error_message");
          final List<AssociationEntity> _result = new ArrayList<AssociationEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AssociationEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpToteId;
            if (_cursor.isNull(_cursorIndexOfToteId)) {
              _tmpToteId = null;
            } else {
              _tmpToteId = _cursor.getString(_cursorIndexOfToteId);
            }
            final String _tmpOlpn;
            if (_cursor.isNull(_cursorIndexOfOlpn)) {
              _tmpOlpn = null;
            } else {
              _tmpOlpn = _cursor.getString(_cursorIndexOfOlpn);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final boolean _tmpSubmitted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSubmitted);
            _tmpSubmitted = _tmp != 0;
            final Long _tmpSubmitTimestamp;
            if (_cursor.isNull(_cursorIndexOfSubmitTimestamp)) {
              _tmpSubmitTimestamp = null;
            } else {
              _tmpSubmitTimestamp = _cursor.getLong(_cursorIndexOfSubmitTimestamp);
            }
            final String _tmpResponseData;
            if (_cursor.isNull(_cursorIndexOfResponseData)) {
              _tmpResponseData = null;
            } else {
              _tmpResponseData = _cursor.getString(_cursorIndexOfResponseData);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            _item = new AssociationEntity(_tmpId,_tmpToteId,_tmpOlpn,_tmpTimestamp,_tmpSessionId,_tmpSubmitted,_tmpSubmitTimestamp,_tmpResponseData,_tmpRetryCount,_tmpErrorMessage);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object searchAssociations(final String searchTerm,
      final Continuation<? super List<AssociationEntity>> continuation) {
    final String _sql = "SELECT * FROM associations WHERE tote_id LIKE '%' || ? || '%' OR olpn LIKE '%' || ? || '%' ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (searchTerm == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, searchTerm);
    }
    _argIndex = 2;
    if (searchTerm == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, searchTerm);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AssociationEntity>>() {
      @Override
      public List<AssociationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfToteId = CursorUtil.getColumnIndexOrThrow(_cursor, "tote_id");
          final int _cursorIndexOfOlpn = CursorUtil.getColumnIndexOrThrow(_cursor, "olpn");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSubmitted = CursorUtil.getColumnIndexOrThrow(_cursor, "submitted");
          final int _cursorIndexOfSubmitTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "submit_timestamp");
          final int _cursorIndexOfResponseData = CursorUtil.getColumnIndexOrThrow(_cursor, "response_data");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retry_count");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "error_message");
          final List<AssociationEntity> _result = new ArrayList<AssociationEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AssociationEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpToteId;
            if (_cursor.isNull(_cursorIndexOfToteId)) {
              _tmpToteId = null;
            } else {
              _tmpToteId = _cursor.getString(_cursorIndexOfToteId);
            }
            final String _tmpOlpn;
            if (_cursor.isNull(_cursorIndexOfOlpn)) {
              _tmpOlpn = null;
            } else {
              _tmpOlpn = _cursor.getString(_cursorIndexOfOlpn);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final boolean _tmpSubmitted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSubmitted);
            _tmpSubmitted = _tmp != 0;
            final Long _tmpSubmitTimestamp;
            if (_cursor.isNull(_cursorIndexOfSubmitTimestamp)) {
              _tmpSubmitTimestamp = null;
            } else {
              _tmpSubmitTimestamp = _cursor.getLong(_cursorIndexOfSubmitTimestamp);
            }
            final String _tmpResponseData;
            if (_cursor.isNull(_cursorIndexOfResponseData)) {
              _tmpResponseData = null;
            } else {
              _tmpResponseData = _cursor.getString(_cursorIndexOfResponseData);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            _item = new AssociationEntity(_tmpId,_tmpToteId,_tmpOlpn,_tmpTimestamp,_tmpSessionId,_tmpSubmitted,_tmpSubmitTimestamp,_tmpResponseData,_tmpRetryCount,_tmpErrorMessage);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getAllSessionIds(final Continuation<? super List<Long>> continuation) {
    final String _sql = "SELECT DISTINCT session_id FROM associations ORDER BY session_id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Long>>() {
      @Override
      public List<Long> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<Long> _result = new ArrayList<Long>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Long _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getLong(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getExistingPairKeys(final List<String> pairKeys,
      final Continuation<? super List<String>> continuation) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT (tote_id || '-' || olpn) as pair_key FROM associations WHERE (tote_id || '-' || olpn) IN (");
    final int _inputSize = pairKeys.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : pairKeys) {
      if (_item == null) {
        _statement.bindNull(_argIndex);
      } else {
        _statement.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final String _item_1;
            final String _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(0);
            }
            _item_1 = _tmp;
            _result.add(_item_1);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
