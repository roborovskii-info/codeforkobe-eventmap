package org.codeforkobe.eventmap.database;

import org.codeforkobe.eventmap.model.Event;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * @author ISHIMARU Sohei on 2016/08/05.
 */
public class EventDao extends AbstractDataProvider<Event> implements IEventSchema, Dao<Event> {

    public EventDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected Event cursorToEntity(Cursor cursor) {
        return null;
    }

    @Override
    protected ContentValues entityToContentValues(Event event) {
        return null;
    }

    @Override
    public Event fetchById(long id) {
        return null;
    }

    @Override
    public List<Event> fetchAll() {
        return null;
    }

    @Override
    public long add(Event event) {
        return 0;
    }

    @Override
    public int update(Event event) {
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
