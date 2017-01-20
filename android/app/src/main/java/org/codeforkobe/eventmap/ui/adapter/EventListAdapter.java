package org.codeforkobe.eventmap.ui.adapter;

import org.codeforkobe.eventmap.R;
import org.codeforkobe.eventmap.entity.Event;
import org.codeforkobe.eventmap.util.DateTimeUtils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * イベントをリストに表示するためのアダプタ
 *
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
    public void onBindViewHolder(final EventListAdapter.ViewHolder holder, int position) {
        Event event = mDataList.get(position);
        if (event != null) {

            String title = event.getSummary();
            holder.titleView.setText(title);
            holder.summaryView.setText(DateTimeUtils.formatDateTime(mContext, event.getDateTimeStart()));
            holder.iconView.setText(event.getSummary().substring(0, 1));
            /* 先頭の文字から●の背景色を決定 */
            int color = holder.getColor(mContext, title);
            holder.iconView.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

            holder.itemView.setOnClickListener(holder);

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

    class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        @BindView(R.id.text_title)
        TextView titleView;

        @BindView(R.id.text_summary)
        TextView summaryView;

        @BindView(R.id.text_event_icon)
        TextView iconView;

        int[] colors = {
                R.color.red_500,
                R.color.pink_500,
                R.color.purple_500,
                R.color.deep_purple_500,
                R.color.indigo_500,
                R.color.blue_500,
                R.color.light_blue_500,
                R.color.cyan_500,
                R.color.teal_500,
                R.color.green_500,
                R.color.light_green_500,
                R.color.lime_500,
                R.color.amber_500,
                R.color.orange_500,
                R.color.deep_orange_500,
                R.color.brown_500,
        };

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        int getColor(Context context, String title) {
            if (TextUtils.isEmpty(title)) {
                return context.getResources().getColor(colors[0]);
            }
            int codeAt = title.codePointAt(0);
            int index =  codeAt % colors.length;

            return context.getResources().getColor(colors[index]);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClick(null, v, getAdapterPosition(), getItemId());
            }
        }
    }
}
