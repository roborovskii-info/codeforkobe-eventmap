package org.codeforkobe.eventmap.ui;

import com.google.gson.annotations.SerializedName;

/**
 * イベントの情報を表すエンティティ
 *
 *
 * @author ISHIMARU Sohei(2016/06/30)
 */
public class EventData {

    /**
     * イベントのID 
     */
    @SerializedName("event_id")
    private String mEventId;

    /** 
     * イベントの見出し 
     */
    @SerializedName("title")
    private String mTitle;

    /** 
     * イベントの詳細な内容 
     */
    @SerializedName("content")
    private String mContent;

    /** 
     * 開催場所の住所 
     */
    @SerializedName("address")
    private String mAddress;

    /** 
     * 会場名 
     */
    @SerializedName("place")
    private String mPlace;

    /** 
     * イベント開催場所の全国地方公共団体コード 
     */
    @SerializedName("area_code")
    private String mAreaCode;

    /** 
     * 開始日時 
     */
    @SerializedName("start_date")
    private String mStartDate;

    /** 
     * 終了日時 
     */
    @SerializedName("end_date")
    private String mEndDate;

    /** 
     * 画像URL (任意) 
     */
    @SerializedName("image_url")
    private String mImageUrl;

    /** 
     * 緯度 
     */
    @SerializedName("latitude")
    private double mLatitude;

    /** 
     * 経度 
     */
    @SerializedName("longitude")
    private double mLongitude;

    /** 
     * イベントのWebページURL 
     */
    @SerializedName("page_url")
    private String mPageUrl;

    public EventData() {
        mEventId = "";
        mTitle = "";
        mContent = "";
        mAddress = "";
        mPlace = "";
        mAreaCode = "";
        mStartDate  ="";
        mEndDate = "";
        mImageUrl = "";
        mLatitude = 0.0;
        mLongitude = 0.0;
        mPageUrl = "";
    }

    public String getEventId() {
        return mEventId;
    }

    public void setEventId(String eventId) {
        mEventId = eventId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public String getAreaCode() {
        return mAreaCode;
    }

    public void setAreaCode(String areaCode) {
        mAreaCode = areaCode;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
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

    public String getPageUrl() {
        return mPageUrl;
    }

    public void setPageUrl(String pageUrl) {
        mPageUrl = pageUrl;
    }
}
