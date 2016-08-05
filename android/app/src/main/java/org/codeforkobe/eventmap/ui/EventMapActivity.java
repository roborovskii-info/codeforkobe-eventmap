package org.codeforkobe.eventmap.ui;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.codeforkobe.eventmap.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class EventMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String LOG_TAG = "EventMapActivity";

    private static final LatLng DEFAULT_GEO_POINT = new LatLng(34.889556, 135.225389);

    private static final int DEFAULT_ZOOM_LEVEL = 14;

    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_map);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setIndoorEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.setOnMarkerClickListener(mOnMarkerClickListener);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_GEO_POINT, DEFAULT_ZOOM_LEVEL));
    }

    private GoogleMap.OnMarkerClickListener mOnMarkerClickListener = new GoogleMap.OnMarkerClickListener() {

        @Override
        public boolean onMarkerClick(Marker marker) {
            Log.d(LOG_TAG, "+ onMarkerClick(Marker)");
            return false;
        }
    };

}

