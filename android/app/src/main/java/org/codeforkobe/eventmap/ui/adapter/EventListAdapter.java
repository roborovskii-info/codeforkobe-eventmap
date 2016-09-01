package org.codeforkobe.eventmap.ui.adapter;

import org.codeforkobe.eventmap.R;
import org.codeforkobe.eventmap.entity.Event;
import org.codeforkobe.eventmap.util.DateTimeUtils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ISHIMARU Sohei on 2016/08/31.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Event> mDataList;
    private OnItemClickListener mClickListener;

    public EventListAdapter(Context context, OnItemClickListener listener) {
        mDataList = new ArrayList<>();
        mContext = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mClickListener = listener;
    }

    public void addEvent(@NonNull Event event) {
        int position = mDataList.size();
        mDataList.add(event);
        notifyItemInserted(position);
    }

    public Event getEvent(int position) {
        return mDataList.get(position);
    }

    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.row_event, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventListAdapter.ViewHolder holder, final int position) {
        Event event = mDataList.get(position);
        if (event != null) {

            holder.titleView.setText(event.getSummary());
            holder.summaryView.setText(DateTimeUtils.formatDateTime(mContext, event.getDateTimeStart()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mClickListener != null) {
                        mClickListener.onItemClicked(v, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mDataList != null) {
            return mDataList.size();
        } else {
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title)
        TextView titleView;
        @BindView(R.id.text_summary)
        TextView summaryView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}