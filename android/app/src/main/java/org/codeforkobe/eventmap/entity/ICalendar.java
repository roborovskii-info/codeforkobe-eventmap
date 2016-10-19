package org.codeforkobe.eventmap.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ISHIMARU Sohei on 2016/08/05.
 */
public class ICalendar {

    /** カレンダーIDは、西暦+月を使用する yyyymm */
    private long mCalendarId;

    private String mMethod;

    private String mVersion;

    private String mProductIdentifier;

    private String mCalendarScale;

    private String mTimeZoneIdentifier;

    private String mTimeZoneOffsetFrom;

    private String mTimeZoneOffsetTo;

    private String mTimeZoneName;

    private List<Event> mEventList;

    public ICalendar() {
        mEventList = new ArrayList<>();
    }

    public void addEvent(Event event) {
        mEventList.add(event);
    }

    public List<Event> getEventList() {
        return mEventList;
    }

    public void setEventList(List<Event> eventList) {
        this.mEventList = eventList;
    }

    public long getCalendarId() {
        return mCalendarId;
    }

    public void setCalendarId(long calendarId) {
        this.mCalendarId = calendarId;
    }

    public String getMethod() {
        return mMethod;
    }

    public void setMethod(String method) {
        this.mMethod = method;
    }

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        this.mVersion = version;
    }

    public String getProductIdentifier() {
        return mProductIdentifier;
    }

    public void setProductIdentifier(String productIdentifier) {
        this.mProductIdentifier = productIdentifier;
    }

    public String getCalendarScale() {
        return mCalendarScale;
    }

    public void setCalendarScale(String calendarScale) {
        this.mCalendarScale = calendarScale;
    }

    public String getTimeZoneIdentifier() {
        return mTimeZoneIdentifier;
    }

    public void setTimeZoneIdentifier(String timeZoneIdentifier) {
        this.mTimeZoneIdentifier = timeZoneIdentifier;
    }

    public String getTimeZoneOffsetFrom() {
        return mTimeZoneOffsetFrom;
    }

    public void setTimeZoneOffsetFrom(String timeZoneOffsetFrom) {
        this.mTimeZoneOffsetFrom = timeZoneOffsetFrom;
    }

    public String getTimeZoneOffsetTo() {
        return mTimeZoneOffsetTo;
    }

    public void setTimeZoneOffsetTo(String timeZoneOffsetTo) {
        this.mTimeZoneOffsetTo = timeZoneOffsetTo;
    }

    public String getTimeZoneName() {
        return mTimeZoneName;
    }

    public void setTimeZoneName(String timeZoneName) {
        this.mTimeZoneName = timeZoneName;
    }

}
