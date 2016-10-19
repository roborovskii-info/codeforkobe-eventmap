package org.codeforkobe.eventmap.database;

import org.codeforkobe.eventmap.entity.Event;
import org.codeforkobe.eventmap.entity.ICalendar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ISHIMARU Sohei on 2016/08/05.
 */
public class CalendarDao extends AbstractDataProvider<ICalendar> implements ICalendarSchema, Dao<ICalendar> {

    private static final String LOG_TAG = "CalendarDao";

    public CalendarDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected ICalendar cursorToEntity(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        ICalendar calendar = new ICalendar();
        calendar.setCalendarId(cursor.getLong(cursor.getColumnIndex(COLUMN_CALENDAR_ID)));
        calendar.setMethod(cursor.getString(cursor.getColumnIndex(COLUMN_METHOD)));
        calendar.setVersion(cursor.getString(cursor.getColumnIndex(COLUMN_VERSION)));
        calendar.setProductIdentifier(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_IDENTIFIER)));
        calendar.setCalendarScale(cursor.getString(cursor.getColumnIndex(COLUMN_CALENDAR_SCALE)));
        calendar.setTimeZoneIdentifier(cursor.getString(cursor.getColumnIndex(COLUMN_TIME_ZONE_IDENTIFIER)));
        calendar.setTimeZoneOffsetFrom(cursor.getString(cursor.getColumnIndex(COLUMN_TIME_ZONE_OFFSET_FROM)));
        calendar.setTimeZoneOffsetTo(cursor.getString(cursor.getColumnIndex(COLUMN_TIME_ZONE_OFFSET_TO)));
        calendar.setTimeZoneName(cursor.getString(cursor.getColumnIndex(COLUMN_TIME_ZONE_NAME)));

        List<Event> eventList = Database.events.fetchByCalendarId(calendar.getCalendarId());
        calendar.setEventList(eventList);
        return calendar;
    }

    @Override
    protected ContentValues entityToContentValues(ICalendar calendar) {
        if (calendar == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_CALENDAR_ID, calendar.getCalendarId());
        values.put(COLUMN_METHOD, calendar.getMethod());
        if (calendar.getVersion() == null) {
            values.put(COLUMN_VERSION, "0.0");
        } else {
            values.put(COLUMN_VERSION, calendar.getVersion());
        }
        if (calendar.getProductIdentifier() == null) {
            values.put(COLUMN_PRODUCT_IDENTIFIER, "unknown");
        } else {
            values.put(COLUMN_PRODUCT_IDENTIFIER, calendar.getProductIdentifier());
        }
        values.put(COLUMN_CALENDAR_SCALE, calendar.getCalendarScale());
        values.put(COLUMN_TIME_ZONE_IDENTIFIER, calendar.getTimeZoneIdentifier());
        values.put(COLUMN_TIME_ZONE_OFFSET_FROM, calendar.getTimeZoneOffsetFrom());
        values.put(COLUMN_TIME_ZONE_OFFSET_TO, calendar.getTimeZoneOffsetTo());
        values.put(COLUMN_TIME_ZONE_NAME, calendar.getTimeZoneName());
        return values;
    }

    @Override
    public ICalendar fetchById(long id) {
        ICalendar calendar = null;
        String where = COLUMN_CALENDAR_ID + " = ?";
        String[] whereArgs = {Long.toString(id)};
        Cursor cursor = null;
        try {
            cursor = super.query(TABLE_NAME, COLUMNS, where, whereArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                calendar = cursorToEntity(cursor);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return calendar;
    }

    @Override
    public List<ICalendar> fetchAll() {
        List<ICalendar> calendars = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = super.query(TABLE_NAME, COLUMNS, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ICalendar calendar = cursorToEntity(cursor);
                    calendars.add(calendar);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return calendars;
    }

    @Override
    public long add(ICalendar calendar) {
        if (calendar == null) {
            return 0;
        }
        Log.d(LOG_TAG, "+ add(ICalendar) : Calendar ID = " + calendar.getCalendarId());
        return super.insert(TABLE_NAME, entityToContentValues(calendar));
    }

    @Override
    public int update(ICalendar calendar) {
        if (calendar == null) {
            return 0;
        }
        long id = calendar.getCalendarId();
        String where = COLUMN_CALENDAR_ID + " = ?";
        String[] whereArgs = {Long.toString(id)};
        Log.d(LOG_TAG, "+ update(ICalendar) : Calendar ID = " + id);
        return super.update(TABLE_NAME, entityToContentValues(calendar), where, whereArgs);
    }

    @Override
    public boolean deleteById(long id) {
        Log.d(LOG_TAG, "+ deleteById(long) : ID = " + id);
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
