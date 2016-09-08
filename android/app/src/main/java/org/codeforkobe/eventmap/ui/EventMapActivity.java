package org.codeforkobe.eventmap.ui;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.codeforkobe.eventmap.R;
import org.codeforkobe.eventmap.database.Database;
import org.codeforkobe.eventmap.entity.Event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String LOG_TAG = "EventMapActivity";

    public static final String EXTRA_EVENT_ID = "event_id";

    /** デフォルトの緯度経度(三田市) */
    private static final LatLng DEFAULT_GEO_POINT = new LatLng(34.889556, 135.225389);

    private static final int DEFAULT_ZOOM_LEVEL = 14;

    private GoogleMap mGoogleMap;

    private Map<String, Event> mMappingMarkers;

    private boolean mMarkerClick = false;

    private boolean mIsPermanentMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_map);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mMappingMarkers = new HashMap<>();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setIndoorEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.setOnMarkerClickListener(mOnMarkerClickListener);
        mGoogleMap.setOnCameraIdleListener(mCameraIdleListener);
        mGoogleMap.setOnInfoWindowClickListener(mInfoWindowListener);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long eventId = extras.getLong(EXTRA_EVENT_ID);
            Event event = Database.events.fetchById(eventId);
            if (event != null) {
                LatLng latLng = new LatLng(event.getLatitude(), event.getLongitude());
                addMarker(event);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM_LEVEL));
                mIsPermanentMode = true;
            } else {
                mIsPermanentMode = false;
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_GEO_POINT, DEFAULT_ZOOM_LEVEL));
            }
        } else {
            mIsPermanentMode = false;
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_GEO_POINT, DEFAULT_ZOOM_LEVEL));
        }
    }

    /**
     * カメラが静止したときのコールバック。
     */
    private GoogleMap.OnCameraIdleListener mCameraIdleListener = new GoogleMap.OnCameraIdleListener() {

        @Override
        public void onCameraIdle() {
            if (!mIsPermanentMode) {
                loadMarkers();
            }
        }
    };

    /**
     * 現在表示している地図範囲に含まれるマーカーの読み込み。
     * マーカークリックで地図が移動したとき、`clear()`すると吹き出しが消えてしまうのでフラグを立てて回避する。
     */
    private void loadMarkers() {
        LatLngBounds bounds = mGoogleMap.getProjection().getVisibleRegion().latLngBounds;
        List<Event> events = Database.events.fetchByBounds(
                bounds.southwest.longitude,
                bounds.northeast.latitude,
                bounds.northeast.longitude,
                bounds.southwest.latitude);

        if (mMarkerClick) {
            mMarkerClick = false;
        } else {
            mGoogleMap.clear();
            mMappingMarkers.clear();
        }

        for (Event event : events) {
            addMarker(event);
        }
    }

    private void addMarker(Event event) {
        Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(event.getLatitude(), event.getLongitude()))
                .title(event.getSummary()));
        mMappingMarkers.put(marker.getId(), event);
    }

    /**
     * マーカーをクリックしたときのコールバック
     */
    private GoogleMap.OnMarkerClickListener mOnMarkerClickListener = new GoogleMap.OnMarkerClickListener() {

        @Override
        public boolean onMarkerClick(Marker marker) {
            Log.d(LOG_TAG, "+ onMarkerClick(Marker)");
            mMarkerClick = true;
            return false;
        }
    };

    /**
     * 吹き出しをクリックした時のコールバック
     */
    private GoogleMap.OnInfoWindowClickListener mInfoWindowListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Event event = mMappingMarkers.get(marker.getId());
            Intent intent = new Intent(EventMapActivity.this, EventDetailActivity.class);
            intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, event.getEventId());
            startActivity(intent);
        }
    };
}

