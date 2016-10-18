package org.codeforkobe.eventmap.ics;


import android.content.Context;
import android.os.AsyncTask;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author ISHIMARU Sohei on 2016/10/13.
 */

public class RemoteEventLoader extends AsyncTask<Void, Void, String>{

    private static final String BASE_URL = "https://roborovskii-info.github.io/codeforkobe-eventmap/";

    private int mYear;

    private int mMonth;

    private Context mContext;

    private Listener mListener;

    public RemoteEventLoader(Context context) {
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH) + 1;
        mContext = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + createFileName())
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.code() == 200) {
                String fileName = createFileName();
                String body = response.body().string();
                saveFile(fileName, body);
                return fileName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String path) {
        super.onPostExecute(path);
        if (mListener != null) {
            mListener.onFileSaved(path);
        }
    }

    private void saveFile(String file, String body) {
        FileOutputStream fos;
        try {
            fos = mContext.openFileOutput(file, Context.MODE_PRIVATE);
            fos.write(body.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setOnFileSavedListener(Listener listener) {
        mListener = listener;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public String createFileName() {
        return String.format(Locale.getDefault(), "%1$d%2$02d.ics", mYear, mMonth);
    }

    public interface Listener {
        void onFileSaved(String path);
    }
}
