package com.rhm.cbc.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.rhm.cbc.R;
import com.rhm.cbc.data.CBCDatabase;
import com.rhm.cbc.features.main.MainActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sambo on 11/21/17.
 */

public class AppForegroundService extends Service {

    private static final int NOTIFICATION_CHANNEL = 1;
    private Disposable d;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification n = createNotificationService(getApplicationContext(), 0);

        startForeground(NOTIFICATION_CHANNEL, n);
        this.d = CBCDatabase.getInstance(getApplicationContext()).changeEventDao().getAllEventCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(count -> updateNotification(getApplicationContext(), count)
                );
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (d != null) {
            d.dispose();
        }
    }

    private void updateNotification(Context appContext, int count) {
        NotificationManager mNotificationManager =
                (NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);

        // mNotificationId is a unique integer your app uses to identify the
        // notification. For example, to cancel the notification, you can pass its ID
        // number to NotificationManager.cancel().
        mNotificationManager.notify(NOTIFICATION_CHANNEL, createNotificationService(appContext, count));
    }

    private Notification createNotificationService(Context appContext, int count) {
        // The id of the channel.
        String s = "";
        if (count != 1) s="s";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(appContext)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(getString(R.string.app_name) + " running: "+count+" event"+s+" today")
                        .setChannelId(getString(R.string.app_name))
                        .setAutoCancel(true);
        Intent resultIntent = new Intent(appContext, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(appContext);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        // mNotificationId is a unique integer your app uses to identify the
        // notification. For example, to cancel the notification, you can pass its ID
        // number to NotificationManager.cancel().
        return mBuilder.build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
