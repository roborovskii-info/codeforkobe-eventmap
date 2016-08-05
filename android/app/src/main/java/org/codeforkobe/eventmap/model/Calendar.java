package org.codeforkobe.eventmap.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ISHIMARU Sohei on 2016/08/05.
 */
public class Calendar {

    private String method;

    private String version;

    private String productIdentifier;

    private String calendarScale;

    private String timeZoneIdentifier;

    private String timeZoneOffsetFrom;

    private String timeZoneOffsetTo;

    private String timeZoneName;

    private List<Event> eventList;

    public Calendar() {
        eventList = new ArrayList<>();
    }

    public void addEvent(Event event) {
        eventList.add(event);
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProductIdentifier() {
        return productIdentifier;
    }

    public void setProductIdentifier(String productIdentifier) {
        this.productIdentifier = productIdentifier;
    }

    public String getCalendarScale() {
        return calendarScale;
    }

    public void setCalendarScale(String calendarScale) {
        this.calendarScale = calendarScale;
    }

    public String getTimeZoneIdentifier() {
        return timeZoneIdentifier;
    }

    public void setTimeZoneIdentifier(String timeZoneIdentifier) {
        this.timeZoneIdentifier = timeZoneIdentifier;
    }

    public String getTimeZoneOffsetFrom() {
        return timeZoneOffsetFrom;
    }

    public void setTimeZoneOffsetFrom(String timeZoneOffsetFrom) {
        this.timeZoneOffsetFrom = timeZoneOffsetFrom;
    }

    public String getTimeZoneOffsetTo() {
        return timeZoneOffsetTo;
    }

    public void setTimeZoneOffsetTo(String timeZoneOffsetTo) {
        this.timeZoneOffsetTo = timeZoneOffsetTo;
    }

    public String getTimeZoneName() {
        return timeZoneName;
    }

    public void setTimeZoneName(String timeZoneName) {
        this.timeZoneName = timeZoneName;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
}
