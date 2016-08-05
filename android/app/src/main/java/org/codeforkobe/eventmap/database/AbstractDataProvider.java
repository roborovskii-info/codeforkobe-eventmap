package org.codeforkobe.eventmap.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author ISHIMARU Sohei on 2016/08/05.
 */
public abstract class AbstractDataProvider<T> {

    private static final String LOG_TAG = "AbstractDataProvider";

    public SQLiteDatabase mDatabase;

    public AbstractDataProvider(SQLiteDatabase db) {
        this.mDatabase = db;
    }

    public long insert(String tableName, ContentValues values) {
        Log.d(LOG_TAG, "+ insert : " + tableName);
        return mDatabase.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public int bulkInsert(String tableName, ContentValues[] valuesArray) {
        Log.d(LOG_TAG, "+ bulkInsert : " + tableName);
        mDatabase.beginTransaction();
        int count = 0;
        try {
            for (ContentValues values : valuesArray) {
                long id = mDatabase.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (0 < id) {
                    count++;
                }
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return count;
    }

    public int delete(String tableName, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, "+ delete : " + tableName);
        return mDatabase.delete(tableName, selection, selectionArgs);
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(LOG_TAG, "+ query : " + tableName);
        return mDatabase.query(tableName, columns, selection, selectionArgs, null, null, sortOrder);
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String sortOrder, String limit) {
        Log.d(LOG_TAG, "+ query : " + tableName);
        return mDatabase.query(tableName, columns, selection, selectionArgs, null, null, sortOrder, limit);
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        Log.d(LOG_TAG, "+ query : " + tableName);
        return mDatabase.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public int update(String tableName, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, "+ update : " + tableName);
        return mDatabase.update(tableName, values, selection, selectionArgs);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        Log.d(LOG_TAG, "+ rawQuery : " + sql);
        return mDatabase.rawQuery(sql, selectionArgs);
    }

    protected abstract T cursorToEntity(Cursor cursor);

    protected abstract ContentValues entityToContentValues(T t);
}
