package org.codeforkobe.eventmap.database;

import org.codeforkobe.eventmap.entity.Event;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ISHIMARU Sohei on 2016/08/05.
 */
public class EventDao extends AbstractDataProvider<Event> implements IEventSchema, Dao<Event> {

    private static final String LOG_TAG = "EventDao";

    public EventDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected Event cursorToEntity(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        Event event = new Event();
        event.setEventId(cursor.getLong(cursor.getColumnIndex(COLUMN_EVENT_ID)));
        event.setCalendarId(cursor.getLong(cursor.getColumnIndex(COLUMN_CALENDAR_ID)));
        event.setUid(cursor.getString(cursor.getColumnIndex(COLUMN_UID)));
        event.setDateTimeStamp(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME_STAMP)));
        event.setSummary(cursor.getString(cursor.getColumnIndex(COLUMN_SUMMARY)));
        event.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
        event.setDateTimeStart(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME_START)));
        event.setDateTimeEnd(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME_END)));
        event.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
        event.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)));
        event.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
        event.setContact(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT)));
        event.setTransparent(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSPARENT)));
        return event;
    }

    @Override
    protected ContentValues entityToContentValues(Event event) {
        if (event == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        if (0 < event.getEventId()) {
            values.put(COLUMN_EVENT_ID, event.getEventId());
        }
        values.put(COLUMN_CALENDAR_ID, event.getCalendarId());
        values.put(COLUMN_UID, event.getUid());
        values.put(COLUMN_DATE_TIME_STAMP, event.getDateTimeStamp());
        values.put(COLUMN_SUMMARY, event.getSummary());
        values.put(COLUMN_DESCRIPTION, event.getDescription());
        values.put(COLUMN_DATE_TIME_START, event.getDateTimeStart());
        values.put(COLUMN_DATE_TIME_END, event.getDateTimeEnd());
        values.put(COLUMN_LOCATION, event.getLocation());
        values.put(COLUMN_LATITUDE, event.getLatitude());
        values.put(COLUMN_LONGITUDE, event.getLongitude());
        values.put(COLUMN_CONTACT, event.getContact());
        values.put(COLUMN_TRANSPARENT, event.getTransparent());
        return values;
    }

    @Override
    public Event fetchById(long id) {
        Event event = null;
        String where = COLUMN_EVENT_ID + " = ?";
        String[] whereArgs = {Long.toString(id)};
        Cursor cursor = null;
        try {
            cursor = super.query(TABLE_NAME, COLUMNS, where, whereArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                event = cursorToEntity(cursor);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return event;
    }

    @Override
    public List<Event> fetchAll() {
        List<Event> events = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = super.query(TABLE_NAME, COLUMNS, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Event calendar = cursorToEntity(cursor);
                    events.add(calendar);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return events;
    }

    public List<Event> fetchByCalendarId(long calendarId) {
        String where = COLUMN_CALENDAR_ID + " = ?";
        String[] whereArgs = {Long.toString(calendarId)};
        List<Event> events = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = super.query(TABLE_NAME, COLUMNS, where, whereArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Event calendar = cursorToEntity(cursor);
                    events.add(calendar);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return events;
    }

    /**
     * 指定された範囲に含まれるイベントのデータを返す。
     *
     * @param west  西側の経度
     * @param north 北側の緯度
     * @param east  東側の経度
     * @param south 南側の緯度
     */

    public List<Event> fetchByBounds(double west, double north, double east, double south) {
        double lat1 = Math.min(north, south);
        double lat2 = Math.max(north, south);
        double lng1 = Math.min(west, east);
        double lng2 = Math.max(west, east);

        String where = COLUMN_LATITUDE + " BETWEEN ? AND ?"
                + " AND " + COLUMN_LONGITUDE + " BETWEEN ? AND ?"
                + " AND " + COLUMN_LATITUDE + " != 0"
                + " AND " + COLUMN_LONGITUDE + " != 0";
        String[] whereArgs = {
                Double.toString(lat1),
                Double.toString(lat2),
                Double.toString(lng1),
                Double.toString(lng2),
        };

        List<Event> events = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = super.query(TABLE_NAME, COLUMNS, where, whereArgs, null);
//            DatabaseUtils.dumpCursor(cursor);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Event calendar = cursorToEntity(cursor);
                    events.add(calendar);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return events;
    }

    @Override
    public long add(Event event) {
        if (event == null) {
            return 0;
        }
        return super.insert(TABLE_NAME, entityToContentValues(event));
    }

    @Override
    public int update(Event event) {
        if (event == null) {
            return 0;
        }
        long id = event.getEventId();
        String where = COLUMN_EVENT_ID + " = ?";
        String[] whereArgs = {Long.toString(id)};

        return super.update(TABLE_NAME, entityToContentValues(event), where, whereArgs);
    }

    @Override
    public boolean deleteById(long id) {
        Log.d(LOG_TAG, "+ deleteById(long) : ID = " + id);
        String where = COLUMN_EVENT_ID + " = ?";
        String[] whereArgs = {Long.toString(id)};
        int count = super.delete(TABLE_NAME, where, whereArgs);
        Log.d(LOG_TAG, "  delete " + count + " items.");
        return 0 < count;
    }

    public boolean deleteByCalendarId(long id) {
        Log.d(LOG_TAG, "+ deleteByCalendarId(long) : Calendar ID = " + id);
        String where = COLUMN_CALENDAR_ID + " = ?";
        String[] whereArgs = {Long.toString(id)};
        int count = super.delete(TABLE_NAME, where, whereArgs);
        Log.d(LOG_TAG, "  delete " + count + " items.");
        return 0 < count;
    }

    @Override
    public boolean deleteAll() {
        return 0 < super.delete(TABLE_NAME, null, null);
    }
}
