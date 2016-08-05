package org.codeforkobe.eventmap.database;

/**
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

    String COLUMN_GEO_POINT = "geo_point";

    String COLUMN_CONTACT = "contact";

    String COLUMN_TRANSPARENT = "transparent";

    String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_UID + " TEXT NOT NULL, "
            + COLUMN_DATE_TIME_STAMP + " TEXT NOT NULL, "
            + COLUMN_SUMMARY + " TEXT NOT NULL, "
            + COLUMN_DESCRIPTION + " TEXT, "
            + COLUMN_DATE_TIME_START + " TEXT NOT NULL, "
            + COLUMN_DATE_TIME_END + " TEXT NOT NULL, "
            + COLUMN_LOCATION + " TEXT, "
            + COLUMN_GEO_POINT + " TEXT, "
            + COLUMN_CONTACT + " TEXT, "
            + COLUMN_TRANSPARENT + " TEXT, "
            + "UNIQUE (" + COLUMN_UID + ") ON CONFLICT REPLACE"
            + ");";


    String [] CREATE_INDEX = {
            /* NOP */
    };

    String[] COLUMNS = {};
}
