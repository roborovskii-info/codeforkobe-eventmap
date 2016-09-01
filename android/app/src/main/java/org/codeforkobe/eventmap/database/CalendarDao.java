package org.codeforkobe.eventmap.database;

import org.codeforkobe.eventmap.entity.Calendar;
import org.codeforkobe.eventmap.entity.Event;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ISHIMARU Sohei on 2016/08/05.
 */
public class CalendarDao extends AbstractDataProvider<Calendar> implements ICalendarSchema, Dao<Calendar> {

    public CalendarDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected Calendar cursorToEntity(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        Calendar calendar = new Calendar();
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
    protected ContentValues entityToContentValues(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        if (0 < calendar.getCalendarId()) {
            values.put(COLUMN_CALENDAR_ID, calendar.getCalendarId());
        }
        values.put(COLUMN_METHOD, calendar.getCalendarId());
        values.put(COLUMN_VERSION, calendar.getVersion());
        values.put(COLUMN_PRODUCT_IDENTIFIER, calendar.getProductIdentifier());
        values.put(COLUMN_CALENDAR_SCALE, calendar.getCalendarScale());
        values.put(COLUMN_TIME_ZONE_IDENTIFIER, calendar.getTimeZoneIdentifier());
        values.put(COLUMN_TIME_ZONE_OFFSET_FROM, calendar.getTimeZoneOffsetFrom());
        values.put(COLUMN_TIME_ZONE_OFFSET_TO, calendar.getTimeZoneOffsetTo());
        values.put(COLUMN_TIME_ZONE_NAME, calendar.getTimeZoneName());
        return values;
    }

    @Override
    public Calendar fetchById(long id) {
        Calendar calendar = null;
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
    public List<Calendar> fetchAll() {
        List<Calendar> calendars = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = super.query(TABLE_NAME, COLUMNS, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Calendar calendar = cursorToEntity(cursor);
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
    public long add(Calendar calendar) {
        if (calendar == null) {
            return 0;
        }
        return super.insert(TABLE_NAME, entityToContentValues(calendar));
    }

    @Override
    public int update(Calendar calendar) {
        if (calendar == null) {
            return 0;
        }
        long id = calendar.getCalendarId();
        String where = COLUMN_CALENDAR_ID + " = ?";
        String[] whereArgs = {Long.toString(id)};

        return super.update(TABLE_NAME, entityToContentValues(calendar), where, whereArgs);
    }

    @Override
    public boolean deleteById(long id) {
        String where = COLUMN_CALENDAR_ID + " = ?";
        String[] whereArgs = {Long.toString(id)};
        return 0 < super.delete(TABLE_NAME, where, whereArgs);
    }

    @Override
    public boolean deleteAll() {
        return 0 < super.delete(TABLE_NAME, null, null);
    }
}
