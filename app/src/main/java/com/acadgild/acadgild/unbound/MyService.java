package com.acadgild.acadgild.unbound;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.List;

/**
 * Created by Jal on 10/12/2017.
 */
public class MyService extends Service {

    String tag="MyService";
    //Object of Media Player
    MediaPlayer mp;

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    //On create method
    @Override
    public void onCreate(){
        super.onCreate();
        mp = MediaPlayer.create(getApplicationContext(), R.raw.hak);
    }

    //On Start Command method
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //Call start method
        mp.start();
        //Set Looping True
        mp.setLooping(true);
        //Bitmap icon image
        Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);

        //Create  NotificationCompat.Builder object
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
               this).setAutoCancel(true)
                .setContentTitle("Big Text Style")
                .setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(icon1)
                .setContentText("Hello World!");

        //Create NotificationCompat.BigTextStyle object
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("HAK");
        bigText.setBigContentTitle("MP3 Player");
        bigText.setSummaryText("By JB");
        mBuilder.setStyle(bigText);


        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = getPreviousIntent();

        // The stack builder object will contain an artificial back stack for
        // the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);


        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setOngoing(true);

        Notification n;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            n = mBuilder.build();
        } else {
            n = mBuilder.getNotification();
        }

        //Set Notification flag FLAG_ONGOING_EVENT to display Notification until song is not stopped or app is not closed
        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        NotificationManager mNotificationManager1 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        startForeground(100,n);
        //mNotificationManager1.(100, n);
        return START_STICKY;
    }

    //On destroy method
    @Override
    public void onDestroy(){
        mp.release();
        super.onDestroy();
    }

    //getPreviousIntent method to get previous intent
    private Intent getPreviousIntent() {
        Intent newIntent = null;
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final List<ActivityManager.AppTask> recentTaskInfos = activityManager.getAppTasks();
            if (!recentTaskInfos.isEmpty()) {
                for (ActivityManager.AppTask appTaskTaskInfo: recentTaskInfos) {
                    if (appTaskTaskInfo.getTaskInfo().baseIntent.getComponent().getPackageName().equals(getApplicationContext().getPackageName())) {
                        newIntent = appTaskTaskInfo.getTaskInfo().baseIntent;
                        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                }
            }
        } else {
            final List<ActivityManager.RecentTaskInfo> recentTaskInfos = activityManager.getRecentTasks(1024, 0);
            if (!recentTaskInfos.isEmpty()) {
                for (ActivityManager.RecentTaskInfo recentTaskInfo: recentTaskInfos) {
                    if (recentTaskInfo.baseIntent.getComponent().getPackageName().equals(getApplicationContext().getPackageName())) {
                        newIntent = recentTaskInfo.baseIntent;
                        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                }
            }
        }
        if (newIntent == null) newIntent = new Intent();
        return newIntent;
    }
}
