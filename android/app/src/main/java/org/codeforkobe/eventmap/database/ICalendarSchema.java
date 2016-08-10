package org.codeforkobe.eventmap.database;

/**
 * @author ISHIMARU Sohei on 2016/08/05.
 */
public interface ICalendarSchema {

    String TABLE_NAME = "events";

    /**
     * プライマリキー。
     */
    String COLUMN_CALENDAR_ID = "calendar_id";

    String COLUMN_METHOD = "method";
    String COLUMN_PRODUCT_IDENTIFIER = "product_identifier";
    String COLUMN_CALENDAR_SCALE = "calendar_scale";
    String COLUMN_TIME_ZONE_IDENTIFIER = "time_zone_identifier";
    String COLUMN_TIME_ZONE_OFFSET_FROM = "time_zone_from";
    String COLUMN_TIME_ZONE_OFFSET_TO = "time_zone_to";
    String COLUMN_TIME_ZONE_NAME = "time_zone_name";

    String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + COLUMN_CALENDAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_METHOD + " TEXT NOT NULL, "
            + COLUMN_PRODUCT_IDENTIFIER + " TEXT NOT NULL, "
            + COLUMN_CALENDAR_SCALE + " TEXT NOT NULL, "
            + COLUMN_TIME_ZONE_IDENTIFIER + " TEXT NOT NULL, "
            + COLUMN_TIME_ZONE_OFFSET_FROM + " TEXT NOT NULL, "
            + COLUMN_TIME_ZONE_OFFSET_TO + " TEXT NOT NULL, "
            + COLUMN_TIME_ZONE_NAME + " TEXT NOT NULL, "
            + "UNIQUE (" + COLUMN_PRODUCT_IDENTIFIER + ") ON CONFLICT REPLACE"
            + ");";

    String[] CREATE_INDEX = {
            /* NOP */
    };

    String[] COLUMNS = {
            COLUMN_CALENDAR_ID,
            COLUMN_METHOD,
            COLUMN_PRODUCT_IDENTIFIER,
            COLUMN_CALENDAR_SCALE,
            COLUMN_TIME_ZONE_IDENTIFIER,
            COLUMN_TIME_ZONE_OFFSET_FROM,
            COLUMN_TIME_ZONE_OFFSET_TO,
            COLUMN_TIME_ZONE_NAME,
    };
}
