package org.codeforkobe.eventmap;

import org.codeforkobe.eventmap.database.Database;

import android.app.Application;

/**
 * @author ISHIMARU Sohei on 2016/08/05.
 */
public class EventMap extends Application {

    private static Database sDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        sDatabase = new Database(this);
        sDatabase.open();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (sDatabase != null) {
            sDatabase.close();
        }
    }
}
