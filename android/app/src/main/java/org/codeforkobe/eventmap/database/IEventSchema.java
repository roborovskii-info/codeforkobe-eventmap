package org.codeforkobe.eventmap.database;

/**
 * eventsテーブルの定義など
 *
 * @author ISHIMARU Sohei on 2016/08/05.
 */
public interface IEventSchema {

    String TABLE_NAME = "events";

    /**
     * プライマリキー。UIDがあるけれど、数値型がいいので別途定義
     */
    String COLUMN_EVENT_ID = "event_id";

    String COLUMN_UID = "uid";

    String COLUMN_DATE_TIME_STAMP = "date_time_stamp";

    String COLUMN_SUMMARY = "summary";

    String COLUMN_DESCRIPTION = "description";

    String COLUMN_DATE_TIME_START = "date_time_start";

    String COLUMN_DATE_TIME_END = "date_time_end";

    String COLUMN_LOCATION = "location";

    String COLUMN_LATITUDE = "latitude";

    String COLUMN_LONGITUDE = "longitude";

    String COLUMN_CONTACT = "contact";

    String COLUMN_TRANSPARENT = "transparent";

    String COLUMN_CALENDAR_ID = "calendar_id";

    String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_UID + " TEXT NOT NULL, "
            + COLUMN_DATE_TIME_STAMP + " TEXT NOT NULL, "
            + COLUMN_SUMMARY + " TEXT NOT NULL, "
            + COLUMN_DESCRIPTION + " TEXT, "
            + COLUMN_DATE_TIME_START + " TEXT NOT NULL, "
            + COLUMN_DATE_TIME_END + " TEXT NOT NULL, "
            + COLUMN_LOCATION + " TEXT, "
            + COLUMN_LATITUDE + " REAL, "
            + COLUMN_LONGITUDE + " REAL, "
            + COLUMN_CONTACT + " TEXT, "
            + COLUMN_TRANSPARENT + " TEXT, "
            + COLUMN_CALENDAR_ID + " INTEGER NOT NULL"
//            + "UNIQUE (" + COLUMN_UID + ") ON CONFLICT REPLACE"
            + ");";

    String[] CREATE_INDEX = {
            /* NOP */
    };

    String[] COLUMNS = {
            COLUMN_EVENT_ID,
            COLUMN_UID,
            COLUMN_DATE_TIME_STAMP,
            COLUMN_SUMMARY,
            COLUMN_DESCRIPTION,
            COLUMN_DATE_TIME_START,
            COLUMN_DATE_TIME_END,
            COLUMN_LOCATION,
            COLUMN_LATITUDE,
            COLUMN_LONGITUDE,
            COLUMN_CONTACT,
            COLUMN_TRANSPARENT,
            COLUMN_CALENDAR_ID,
    };
}
