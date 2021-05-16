package hk.edu.ouhk.weatherapplication.ServiceHandler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import hk.edu.ouhk.weatherapplication.APIHandler.WarningInfoAPIHandler.WarningInfo;
import hk.edu.ouhk.weatherapplication.APIHandler.WarningInfoAPIHandler.WarningInfoAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.WarnsumAPIHandler.Warnsum;
import hk.edu.ouhk.weatherapplication.MainActivity;
import hk.edu.ouhk.weatherapplication.R;

public class WeatherNoticeService extends Service {
    private static final String TAG = "WeatherNoticeService";
    Timer timer;
    WarningInfoAPIHandler_Service warningInfoAPIHandler_Service;
    PendingIntent resultPendingIntent;
    NotificationManagerCompat notificationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: Service Created");
         warningInfoAPIHandler_Service = new WarningInfoAPIHandler_Service();
        timer = new Timer();

        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, MainActivity.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationManager = NotificationManagerCompat.from(this);

        /*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());*/
    }

    /*@RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground()
    {
        String NOTIFICATION_CHANNEL_ID = "WeatherNoticeService";
        String channelName = "WarningInfo";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName,
                NotificationManager.IMPORTANCE_HIGH);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle("Background!") // title for notification
                .setContentText("Background ContentText Here")// message for notification
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    /*@Override
    public void onStart(Intent intent, int startid) {
        Log.d(TAG, "onStart: Service Started");

        timer.schedule( new TimerTask() {
            public void run() {
                warningInfoAPIHandler_Service.sendURL();
                if(!WarningInfo_Service.warningInfoList_Service.equals(WarningInfo.warningInfoList)){
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                                "YOUR_CHANNEL_NAME",
                                NotificationManager.IMPORTANCE_DEFAULT);
                        channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
                        mNotificationManager.createNotificationChannel(channel);
                    }
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
                            .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                            .setContentTitle("WaringInfo Update News!") // title for notification
                            .setContentText("WaringInfo Update ContentText Here")// message for notification
                            .setAutoCancel(true); // clear notification after click
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    //mBuilder.setContentIntent(pi);
                    mNotificationManager.notify(0, mBuilder.build());
                }
            }
        }, 0, 5000);

        //60*1000*5
    }*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Service Stopped");
        stoptimertask();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
    }

    TimerTask timerTask;
    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {

                //warningInfoAPIHandler_Service.sendURL();

                warningInfoAPIHandler_Service.loadJSONFromAsset(MainActivity.getContext());
                warningInfoAPIHandler_Service.getJsonData();


                Log.d(TAG, "warningInfoList_Service: "+WarningInfo_Service.warningInfoList_Service);
                Log.d(TAG, "warningInfoList: "+WarningInfo.warningInfoList);

                if(!WarningInfo_Service.warningInfoList_Service.equals(WarningInfo.warningInfoList)) {

                    WarningInfo_Service.warningInfoList_Service.removeAll(WarningInfo.warningInfoList);
                    for (int i =0; i < WarningInfo_Service.warningInfoList_Service.size();i++){
                        HashMap<String, Object> warningInfoList_Service =
                                WarningInfo_Service.warningInfoList_Service.get(i);
                        String NOTIFICATION_CHANNEL_ID = "WeatherNoticeService";
                        String channelName = "WarningInfo";
                        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName,
                                NotificationManager.IMPORTANCE_HIGH);
                        chan.setLightColor(Color.BLUE);
                        chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        assert manager != null;
                        manager.createNotificationChannel(chan);

                        Warnsum.checkIconMap();
                        String code = String.valueOf(warningInfoList_Service.get("warningStatementCode"));
                        if(code.equals("WFIRE") || code.equals("WRAIN") ||code.equals("WTCSGNL")){
                            code = String.valueOf(warningInfoList_Service.get("subtype"));
                        }
                        String iconid = Warnsum.iconMap.get(code);
                        ArrayList<String> arrayList = (ArrayList<String>) warningInfoList_Service.get("contents");
                        String title = "";
                        String content = "";

                        for(int j =0; j< arrayList.size() ;j++){
                            if (j==0){
                                title = arrayList.get(j);

                            }else {
                                content += arrayList.get(j);
                            }
                        }
                        Log.d(TAG,
                                "iconid: "+String.valueOf(warningInfoList_Service.get("warningStatementCode"))+", " +
                                                iconid);
                        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), Integer.parseInt(iconid));


                        NotificationCompat.Builder notificationBuilder =
                                new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID);

                        Notification notification = notificationBuilder.setOngoing(true)
                                .setSmallIcon(Integer.parseInt(iconid)) // notification icon
                                .setContentTitle(title) // title for notification
                                //.setContentText(content)// message for notification
                                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                                .setCategory(Notification.CATEGORY_SERVICE)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(content))
                                .setLargeIcon(largeIcon)
                                .setContentIntent(resultPendingIntent)
                                .setOngoing(false)
                                .setAutoCancel(true)
                                .build();

                        //startForeground(2, notification);
                        notificationManager.notify(i, notification);
                    }


                    WarningInfo_Service.warningInfoList_Service = WarningInfo.warningInfoList;

                }
            }
        };
        timer.schedule(timerTask, 1000 * 30, 1000 * 30); //
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
