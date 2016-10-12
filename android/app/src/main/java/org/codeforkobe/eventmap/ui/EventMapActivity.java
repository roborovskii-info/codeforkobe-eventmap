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

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * イベントの情報を地図で表示する画面
 *
 * @author ISHIMARU Sohei
 */
public class EventMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String LOG_TAG = "EventMapActivity";

    public static final String EXTRA_EVENT_ID = "event_id";

    /** 近所にあるPOIを検出する範囲 */
    private static final double TOUCH_AREA = 0.0001;

    /** デフォルトの緯度経度(三田市) */
    private static final LatLng DEFAULT_GEO_POINT = new LatLng(34.889556, 135.225389);

    private static final int DEFAULT_ZOOM_LEVEL = 14;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private GoogleMap mGoogleMap;

    private Map<String, Event> mMappingMarkers;

    private boolean mMarkerClick = false;

    private boolean mIsPermanentMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_map);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

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
     * マーカーをクリックしたときのコールバック。
     * マーカーを地図の中心へ移動させる。
     * タッチしたマーカー付近に他のイベント情報がある場合は、ダイアログで複数候補を表示する。
     */
    private GoogleMap.OnMarkerClickListener mOnMarkerClickListener = new GoogleMap.OnMarkerClickListener() {

        @Override
        public boolean onMarkerClick(Marker marker) {
            Log.d(LOG_TAG, "+ onMarkerClick(Marker)");
            List<Event> events = new ArrayList<>();

            Event clickedEvent = mMappingMarkers.get(marker.getId());
            if (clickedEvent != null) {
                for (Map.Entry<String, Event> entry : mMappingMarkers.entrySet()) {
                    Event event = entry.getValue();
                    double dx = clickedEvent.getLongitude() - event.getLongitude();
                    double dy = clickedEvent.getLatitude() - event.getLatitude();
                    if (Math.abs(dx) < TOUCH_AREA && Math.abs(dy) < TOUCH_AREA) {
                        if (!events.contains(event)) {
                            events.add(event);
                        }
                    }
                }
            }

            if (1 < events.size()) {
                showEventChooseDialog(events);

            }
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
            showEventDetail(event.getEventId());
        }
    };

    private void showEventDetail(long eventId) {
        Intent intent = new Intent(EventMapActivity.this, EventDetailActivity.class);
        intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, eventId);
        startActivity(intent);
    }

    private void showEventChooseDialog(final List<Event> events) {
        if (events == null) {
            return;
        }
        String[] summaries = new String[events.size()];
        for (int i = 0; i < events.size(); i++) {
            summaries[i] = events.get(i).getSummary() + " : " + events.get(i).getUid();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(summaries, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Event event = events.get(which);
                showEventDetail(event.getEventId());
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}

