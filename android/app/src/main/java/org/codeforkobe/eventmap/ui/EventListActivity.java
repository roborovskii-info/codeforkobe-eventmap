package org.codeforkobe.eventmap.ui;

import org.codefork.eventmap.model.Calendar;
import org.codefork.eventmap.model.Event;
import org.codeforkobe.eventmap.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class EventListActivity extends AppCompatActivity {

    private static final String LOG_TAG = "EventListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "# onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        EventLoadTask task = new EventLoadTask(this);
        task.setListener(new EventLoadTask.Listener() {
            @Override
            public void onLoad(Calendar calendar) {
                for (Event event : calendar.getEventList()) {
                    Log.d(LOG_TAG, event.toString());
                }
            }
        });
        task.execute();
    }
}
