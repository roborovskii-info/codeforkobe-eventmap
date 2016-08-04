package org.codeforkobe.eventmap.ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.codeforkobe.eventmap.R;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ISHIMARU Sohei on 2016/07/01.
 */
public class EventLoadTask extends AsyncTask<Void, Void, List<EventData>> {

    private Listener mListener;

    private Context mContext;

    public EventLoadTask(Context context) {
        mContext = context;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    @Override
    protected List<EventData> doInBackground(Void... params) {
        List<EventData> list = null;
        InputStream is = mContext.getResources().openRawResource(R.raw.sanda);
        Reader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            /* JSONのパース処理 */
            Gson gson = new Gson();
            list = gson.fromJson(reader, new TypeToken<List<EventData>>() {
                /* NOP */
            }.getType());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<EventData> list) {
        super.onPostExecute(list);
        if (mListener != null) {
            mListener.onLoad(list);
        }
    }

    public interface Listener {

        void onLoad(List<EventData> list);
    }
}
