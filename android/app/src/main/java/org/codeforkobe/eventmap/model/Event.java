package org.codeforkobe.eventmap.model;

/**
 * イベントの情報を表すエンティティ
 *
 * @author ISHIMARU Sohei(2016/06/30)
 */
public class Event implements Cloneable {

    /**
     * UID : ユニークID。
     */
    private String uid;

    /**
     * DTSTAMP : イベントの作成日時。
     */
    private String dateTimeStamp;

    /**
     * SUMMARY : イベントの見出し。
     */
    private String summary;

    /**
     * DESCRIPTION : イベントの本文。
     */
    private String description;

    /**
     * DTSTART : イベントの開始日時。
     * ローカルのタイムゾーンで、`yyyyMMdd"T"HHmmss`書式
     */
    private String dateTimeStart;

    /**
     * DTEND : イベントの終了日時。
     * ローカルのタイムゾーンで、`yyyyMMdd"T"HHmmss`書式
     */
    private String dateTimeEnd;

    /**
     * LOCATION : 場所の名称。
     */
    private String location;

    /**
     * GEO : 場所の緯度経度。
     *
     * `GEO:34.94686,135.1694`
     */
    private String geoPoint;

    /**
     * CONTACT : イベントの問い合わせ先
     */
    private String contact;

    /**
     * TRANSP : 予定の公開方法。予定有り(OPAQUE)または予定なし(TRANSPARENT)を指定
     */
    private String transparent;

    public Event() {
        /* NOP */
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(String dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(String dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public String getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(String dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(String geoPoint) {
        this.geoPoint = geoPoint;
    }

    public double getLatitude() {
        if (geoPoint != null) {
            String[] latLng = geoPoint.split(",");
            if (1 < latLng.length) {
                return Double.parseDouble(latLng[0]);
            }
        }
        return 0.0;
    }

    public double getLongitude() {
        if (geoPoint != null) {
            String[] latLng = geoPoint.split(",");
            if (1 < latLng.length) {
                return Double.parseDouble(latLng[1]);
            }
        }
        return 0.0;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTransparent() {
        return transparent;
    }

    public void setTransparent(String transparent) {
        this.transparent = transparent;
    }

    @Override
    public String toString() {
        return summary;
    }

    @Override
    public Event clone() throws CloneNotSupportedException {
        return (Event) super.clone();
    }
}
