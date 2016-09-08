package org.codeforkobe.eventmap.ui;

import org.codeforkobe.eventmap.R;
import org.codeforkobe.eventmap.database.Database;
import org.codeforkobe.eventmap.entity.Calendar;
import org.codeforkobe.eventmap.entity.Event;
import org.codeforkobe.eventmap.ui.adapter.EventListAdapter;
import org.codeforkobe.eventmap.ui.adapter.OnItemClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventListActivity extends AppCompatActivity {

    private static final String LOG_TAG = "EventListActivity";

    @BindView(R.id.list_event)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private EventListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "# onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new EventListAdapter(this, mOnItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        LocalEventLoader task = new LocalEventLoader(this);
        task.setListener(mEventLoadListener);
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_event_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_map:
                displayMap();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayMap() {
        Intent intent = new Intent(this, EventMapActivity.class);
        startActivity(intent);
    }

    private LocalEventLoader.Listener mEventLoadListener = new LocalEventLoader.Listener() {
        @Override
        public void onLoad(Calendar calendar) {
            Database.calendars.deleteAll();
            Database.events.deleteAll();

            long calendarId = Database.calendars.add(calendar);
            for (Event event : calendar.getEventList()) {
                Log.d(LOG_TAG, event.toString());
                event.setCalendarId(calendarId);
                Database.events.add(event);
            }

            List<Event> events = Database.events.fetchByCalendarId(calendarId);
            for (Event event : events) {
                mAdapter.addEvent(event);
            }
        }
    };

    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClicked(View v, int position) {
            Event event = mAdapter.getEvent(position);
            Intent intent = new Intent(EventListActivity.this, EventDetailActivity.class);
            intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, event.getEventId());
            startActivity(intent);
        }
    };
}
