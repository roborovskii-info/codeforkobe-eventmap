package org.codeforkobe.eventmap.database;

import org.codeforkobe.eventmap.model.Calendar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        return null;
    }

    @Override
    protected ContentValues entityToContentValues(Calendar calendar) {
        return null;
    }

    @Override
    public Calendar fetchById(long id) {
        return null;
    }

    @Override
    public List<Calendar> fetchAll() {
        return null;
    }

    @Override
    public long add(Calendar calendar) {
        return 0;
    }

    @Override
    public int update(Calendar calendar) {
        return 0;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }
}
