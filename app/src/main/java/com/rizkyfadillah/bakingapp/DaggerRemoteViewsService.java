package com.rizkyfadillah.bakingapp;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import dagger.android.DaggerService;

/**
 * @author rizkyfadillah on 9/14/2017.
 */

public abstract class DaggerRemoteViewsService extends DaggerService {

    public DaggerRemoteViewsService() {
//        throw new RuntimeException("Stub!");
    }

    public IBinder onBind(Intent intent) {
        throw new RuntimeException("Stub!");
    }

    public abstract RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent var1);

    public interface RemoteViewsFactory {
        void onCreate();

        void onDataSetChanged();

        void onDestroy();

        int getCount();

        RemoteViews getViewAt(int var1);

        RemoteViews getLoadingView();

        int getViewTypeCount();

        long getItemId(int var1);

        boolean hasStableIds();
    }
}
