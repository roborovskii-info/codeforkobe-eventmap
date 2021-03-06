package org.codeforkobe.eventmap.ui.fragment;

import org.codeforkobe.eventmap.R;
import org.codeforkobe.eventmap.database.Database;
import org.codeforkobe.eventmap.entity.Event;
import org.codeforkobe.eventmap.entity.ICalendar;
import org.codeforkobe.eventmap.ics.EventParseTask;
import org.codeforkobe.eventmap.ics.RemoteEventLoader;
import org.codeforkobe.eventmap.ui.EventDetailActivity;
import org.codeforkobe.eventmap.ui.adapter.EventListAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ISHIMARU Sohei on 2016/10/18.
 */

public class EventListFragment extends Fragment {

    private static final String LOG_TAG = "EventListFragment";

    private static final String ARGS_YEAR = "year";

    private static final String ARGS_MONTH = "month";

    @BindView(R.id.card_empty)
    CardView mEmptyCard;

    @BindView(R.id.list_event)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private EventListAdapter mAdapter;

    private int mYear;

    private int mMonth;

    public static EventListFragment newInstance() {
        Calendar calendar = Calendar.getInstance();
        return newInstance(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
    }

    public static EventListFragment newInstance(int year, int month) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle(2);
        args.putInt(ARGS_YEAR, year);
        args.putInt(ARGS_MONTH, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_event_list, container, false);
        ButterKnife.bind(this, parent);
        return parent;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new EventListAdapter(getActivity(), mOnItemClickListener);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(mRefreshListener);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.accent);

        Bundle args = getArguments();
        Calendar calendar = Calendar.getInstance();
        mYear = args.getInt(ARGS_YEAR, calendar.get(Calendar.YEAR));
        mMonth = args.getInt(ARGS_MONTH, calendar.get(Calendar.MONTH) + 1);

        requestEvent();
    }

    private void requestEvent() {
        mSwipeRefreshLayout.setRefreshing(true);
        RemoteEventLoader loader = new RemoteEventLoader(getActivity());
        loader.setYear(mYear);
        loader.setMonth(mMonth);

        loader.setOnFileSavedListener(mFileSavedListener);
        loader.execute();
    }

    private RemoteEventLoader.Listener mFileSavedListener = new RemoteEventLoader.Listener() {
        @Override
        public void onFileSaved(String path) {
            Log.d(LOG_TAG, "Path : " + path);
            if (path == null) {
                showEmptyCard(true);
                mSwipeRefreshLayout.setRefreshing(false);
                return;
            }
            EventParseTask task = new EventParseTask(getActivity());
            task.setCalendarId(mYear * 100 + mMonth);
            task.setListener(mParseListener);
            File file = new File(getActivity().getFilesDir(), path);
            task.execute(file.toString());
        }
    };

    private EventParseTask.Listener mParseListener = new EventParseTask.Listener() {
        @Override
        public void onCalendarLoaded(ICalendar calendar) {
            Log.d(LOG_TAG, "+ onCalendarLoaded(ICalendar)");

            long calendarId = calendar.getCalendarId();

            Database.events.deleteByCalendarId(calendarId);

            Database.calendars.add(calendar);
            for (Event event : calendar.getEventList()) {
                Log.d(LOG_TAG, event.toString());
                event.setCalendarId(calendarId);
                Database.events.add(event);
            }

            List<Event> events = Database.events.fetchByCalendarId(calendarId);
            for (Event event : events) {
                mAdapter.addEvent(event);
            }

            showEmptyCard(mAdapter.getItemCount() == 0);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    private void showEmptyCard(boolean isEmpty) {
        if (isEmpty) {
            mEmptyCard.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mEmptyCard.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            requestEvent();
        }
    };

    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Event event = mAdapter.getEvent(position);
            Intent intent = new Intent(getActivity(), EventDetailActivity.class);
            intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, event.getEventId());
            startActivity(intent);
        }
    };

}
