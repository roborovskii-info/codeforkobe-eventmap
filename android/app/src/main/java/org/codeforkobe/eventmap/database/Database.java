package org.codeforkobe.eventmap.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author ISHIMARU Sohei on 2016/08/05.
 */
public class Database {

    private static final String LOG_TAG = "Database";

    private static final String DATABASE_NAME = "event.sqlite";

    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper mOpenHelper;

    private Context mContext;

    public static EventDao events;
    public static CalendarDao calendars;

    public Database(Context context) {
        mContext = context;
    }

    public Database open() throws SQLiteException {
        mOpenHelper = new DatabaseHelper(mContext);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        events = new EventDao(db);
        calendars = new CalendarDao(db);
        return this;
    }

    public void close() {
        if (mOpenHelper != null) {
            mOpenHelper.close();
        }
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "+ onCreate(SQLiteDatabase)");
            /* テーブル生成 */
            db.execSQL(IEventSchema.CREATE_TABLE);
            Log.d(LOG_TAG, "sql : " + IEventSchema.CREATE_TABLE);
            db.execSQL(ICalendarSchema.CREATE_TABLE);
            Log.d(LOG_TAG, "sql : " + ICalendarSchema.CREATE_TABLE);

            /* インデックス生成 */
            for (String index : IEventSchema.CREATE_INDEX) {
                db.execSQL(index);
            }
            for (String index : ICalendarSchema.CREATE_INDEX) {
                db.execSQL(index);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /* NOP */
        }
    }
}
