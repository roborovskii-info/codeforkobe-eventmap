package org.codeforkobe.eventmap.ics;

import org.codeforkobe.eventmap.entity.Event;
import org.codeforkobe.eventmap.entity.ICalendar;
import org.codeforkobe.eventmap.entity.Property;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * doInBackground時にファイルパスを与えるとicsファイルをパースする。
 *
 * @author ISHIMARU Sohei on 2016/07/01.
 */
public class EventParseTask extends AsyncTask<String, Void, ICalendar> {

    private static final String LOG_TAG = "EventLoadTask";

    private Listener mListener;

    private Context mContext;

    private int mCalendarId;

    public EventParseTask(Context context) {
        mContext = context;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void setCalendarId(int calendarId) {
        this.mCalendarId = calendarId;
    }

    @Override
    protected ICalendar doInBackground(String... params) {
        Log.d(LOG_TAG, "# doInBackground(Void...)");
        if (params == null || params.length == 0) {
            return null;
        }

        ICalendar cal = new ICalendar();
        File file = new File(params[0]);

        /* ical4j大きすぎる...汚いけど自前で... */

        /* icsファイルのパース処理 */
        try {
            Scanner scanner = new Scanner(new FileInputStream(file));
            Event event = null;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null && 0 < line.length() && line.contains(":")) {
                    String[] values = line.split(":");

                    switch (values[0]) {
                        case Property.BEGIN:
                            /* BEGIN:VEVENT */
                            if (Property.VEVENT.equals(values[1])) {
                                event = new Event();
                            }
                            break;

                        case Property.END:
                            /* END:VEVENT */
                            if (Property.VEVENT.equals(values[1])) {
                                if (event != null) {
                                    try {
                                        cal.addEvent(event.clone());
                                    } catch (CloneNotSupportedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                event = null;
                            }
                            break;
                        default:
                            if (event == null) {
                                if (1 < values.length) {
                                    int startIndex = line.indexOf(":") + 1;
                                    handleCalendarNode(values[0], line.substring(startIndex, line.length()), cal);
                                }
                            } else {
                                if (1 < values.length) {
                                    int startIndex = line.indexOf(":") + 1;
                                    handleEventNode(values[0], line.substring(startIndex, line.length()), event);
                                }
                            }
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cal;
    }

    private void handleCalendarNode(String key, String value, ICalendar calendar) {
        if (key == null) {
            return;
        }
        switch (key) {
            case Property.METHOD:
                calendar.setMethod(value);
                break;
            case Property.VERSION:
                calendar.setVersion(value);
                break;
            case Property.PRODID:
                calendar.setProductIdentifier(value);
                break;
            case Property.CALSCALE:
                calendar.setCalendarScale(value);
                break;
            case Property.TZID:
                calendar.setTimeZoneIdentifier(value);
                break;
            case Property.TZOFFSETFROM:
                calendar.setTimeZoneOffsetFrom(value);
                break;
            case Property.TZOFFSETTO:
                calendar.setTimeZoneOffsetTo(value);
                break;
            case Property.TZNAME:
                calendar.setTimeZoneName(value);
                break;
        }
    }

    private void handleEventNode(String key, String value, Event event) {
        if (key == null || event == null) {
            return;
        }
        switch (key) {
            case Property.UID:
                event.setUid(value);
                break;
            case Property.DTSTAMP:
                event.setDateTimeStamp(value);
                break;
            case Property.SUMMARY:
                event.setSummary(value);
                break;
            case Property.DESCRIPTION:
                event.setDescription(value);
                break;
            case Property.DTSTART:
                event.setDateTimeStart(value);
                break;
            case Property.DTEND:
                event.setDateTimeEnd(value);
                break;
            case Property.LOCATION:
                event.setLocation(value);
                break;
            case Property.GEO:
                String[] latLng = value.split(";");
                if (1 < latLng.length) {
                    event.setLatitude(Double.parseDouble(latLng[0]));
                    event.setLongitude(Double.parseDouble(latLng[1]));
                }
                break;
            case Property.CONTACT:
                event.setContact(value);
                break;
            case Property.TRANSP:
                event.setTransparent(value);
                break;
        }
    }

    @Override
    protected void onPostExecute(ICalendar calendar) {
        super.onPostExecute(calendar);
        calendar.setCalendarId(mCalendarId);
        if (mListener != null) {
            mListener.onCalendarLoaded(calendar);
        }
    }

    public interface Listener {
        void onCalendarLoaded(ICalendar calendar);
    }
}
