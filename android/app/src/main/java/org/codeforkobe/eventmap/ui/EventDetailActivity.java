package org.codeforkobe.eventmap.ui;

import org.codeforkobe.eventmap.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * イベントの詳細な情報を表示するためのActivity。
 *
 * @author ISHIMARU Sohei
 */
public class EventDetailActivity extends AppCompatActivity {

    public static final String EXTRA_EVENT_ID = "event_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
    }
}
