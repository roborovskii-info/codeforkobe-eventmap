package org.codeforkobe.eventmap.entity;

/**
 * イベントの情報を表すエンティティ
 *
 * @author ISHIMARU Sohei(2016/06/30)
 */
public class Event implements Cloneable {

    private long mEventId;

    private long mCalendarId;

    /**
     * UID : ユニークID。
     */
    private String mUid;

    /**
     * DTSTAMP : イベントの作成日時。
     */
    private String mDateTimeStamp;

    /**
     * SUMMARY : イベントの見出し。
     */
    private String mSummary;

    /**
     * DESCRIPTION : イベントの本文。
     */
    private String mDescription;

    /**
     * DTSTART : イベントの開始日時。
     * ローカルのタイムゾーンで、`yyyyMMdd"T"HHmmss`書式
     */
    private String mDateTimeStart;

    /**
     * DTEND : イベントの終了日時。
     * ローカルのタイムゾーンで、`yyyyMMdd"T"HHmmss`書式
     */
    private String mDateTimeEnd;

    /**
     * LOCATION : 場所の名称。
     */
    private String mLocation;

    /**
     * GEO : 場所の緯度
     *
     * `GEO:34.94686,135.1694`
     */
    private double mLatitude;

    private double mLongitude;

    /**
     * CONTACT : イベントの問い合わせ先
     */
    private String mContact;

    /**
     * TRANSP : 予定の公開方法。予定有り(OPAQUE)または予定なし(TRANSPARENT)を指定
     */
    private String mTransparent;

    public Event() {
        /* NOP */
    }

    public long getEventId() {
        return mEventId;
    }

    public void setEventId(long eventId) {
        this.mEventId = eventId;
    }

    public long getCalendarId() {
        return mCalendarId;
    }

    public void setCalendarId(long calendarId) {
        mCalendarId = calendarId;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        this.mUid = uid;
    }

    public String getDateTimeStamp() {
        return mDateTimeStamp;
    }

    public void setDateTimeStamp(String dateTimeStamp) {
        this.mDateTimeStamp = dateTimeStamp;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        this.mSummary = summary;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getDateTimeStart() {
        return mDateTimeStart;
    }

    public void setDateTimeStart(String dateTimeStart) {
        this.mDateTimeStart = dateTimeStart;
    }

    public String getDateTimeEnd() {
        return mDateTimeEnd;
    }

    public void setDateTimeEnd(String dateTimeEnd) {
        this.mDateTimeEnd = dateTimeEnd;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        this.mContact = contact;
    }

    public String getTransparent() {
        return mTransparent;
    }

    public void setTransparent(String transparent) {
        this.mTransparent = transparent;
    }

    @Override
    public String toString() {
        return mSummary;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Event)){
            return false;
        }
        Event event = (Event) obj;
        return event.getUid().equals(mUid);
    }

    @Override
    public Event clone() throws CloneNotSupportedException {
        return (Event) super.clone();
    }
}
