package org.codeforkobe.eventmap.ui;

import org.codeforkobe.eventmap.R;
import org.codeforkobe.eventmap.database.Database;
import org.codeforkobe.eventmap.entity.Event;
import org.codeforkobe.eventmap.util.DateTimeUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * イベントの詳細な情報を表示するためのActivity。
 *
 * @author ISHIMARU Sohei
 */
public class EventDetailActivity extends AppCompatActivity {

    public static final String EXTRA_EVENT_ID = "event_id";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.text_summary)
    TextView mSummaryView;

    @BindView(R.id.text_description)
    TextView mDescriptionView;

    @BindView(R.id.text_event_start)
    TextView mEventStartView;

    @BindView(R.id.text_event_end)
    TextView mEventEndView;

    @BindView(R.id.text_location)
    TextView mLocationView;

    @BindView(R.id.text_contact)
    TextView mContactView;

    @BindView(R.id.button_map)
    ImageButton mMapButton;

    private Event mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadEvent();
        bindEvent();
    }

    private void loadEvent() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long eventId = extras.getLong(EXTRA_EVENT_ID);
            mEvent = Database.events.fetchById(eventId);
        }
    }

    private void bindEvent() {
        if (mEvent != null) {
            mSummaryView.setText(mEvent.getSummary());
            mDescriptionView.setText(mEvent.getDescription());

            String dateStart = DateTimeUtils.formatDateTime(this, mEvent.getDateTimeStart());
            mEventStartView.setText(dateStart);
            String dateEnd = DateTimeUtils.formatDateTime(this, mEvent.getDateTimeEnd());
            mEventEndView.setText(dateEnd);

            mLocationView.setText(mEvent.getLocation());
            mContactView.setText(mEvent.getContact());

            setTitle(mEvent.getSummary());

            if (mEvent.getLatitude() != 0.0 && mEvent.getLongitude() != 0.0) {
                mMapButton.setEnabled(true);
            } else {
                mMapButton.setEnabled(false);
            }
        }
    }

    @OnClick(R.id.button_map)
    void onMapButtonClicked() {
        Intent intent = new Intent(this, EventMapActivity.class);
        intent.putExtra(EventMapActivity.EXTRA_EVENT_ID, mEvent.getEventId());
        startActivity(intent);
    }
}
