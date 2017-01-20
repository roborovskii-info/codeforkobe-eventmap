package org.codeforkobe.eventmap.ui;

import org.codeforkobe.eventmap.R;
import org.codeforkobe.eventmap.ui.fragment.EventListFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * イベントのリストをViewPagerで表示するためのActivity。
 *
 * @author ISHIMARU Sohei
 */
public class EventPagerActivity extends AppCompatActivity {

    private static final String LOG_TAG = "EventListActivity";
    /* 当月 + 前後1年分 */
    private static final int CALENDAR_RANGE = 12 * 2 + 1;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_strip)
    PagerTabStrip mPagerTabStrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "# onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pager);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mViewPager.setAdapter(new FragmentTabsAdapter(getSupportFragmentManager(), this));
        mViewPager.setCurrentItem(12);
        mPagerTabStrip.setDrawFullUnderline(false);
        mPagerTabStrip.setTabIndicatorColorResource(R.color.primary);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_event_pager, menu);
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

    class FragmentTabsAdapter extends FragmentPagerAdapter {

        private Context context;

        public FragmentTabsAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            Calendar calendar = getCalendar(position);
            return EventListFragment.newInstance(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        }

        @Override
        public int getCount() {
            return CALENDAR_RANGE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Calendar calendar = getCalendar(position);
            return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        }

        private Calendar getCalendar(int position) {
            Calendar calendar = Calendar.getInstance();
            int offset = 12 - position;
            calendar.add(Calendar.MONTH, -offset);
            return calendar;
        }
    }
}
