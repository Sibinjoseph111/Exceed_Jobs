package com.app.exceedjobs.utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.app.exceedjobs.R;
import com.app.exceedjobs.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class FcmMessageService extends FirebaseMessagingService {

    //const
    private static final String TAG = "FCM";
    private static final String NOTIFICATION_CHANNEL_ID = "com.app.exceedjobs";

    //vars
    Bitmap bitmap;
    String imageUri = "";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG,s);

        FirebaseMessaging.getInstance().subscribeToTopic("global");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        imageUri = remoteMessage.getData().get("image");
        if (imageUri != null && !imageUri.equals("")) {
            bitmap = getBitmapfromUrl(imageUri);
        }
        showNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"), bitmap);
    }

    private void showNotification(String title, String body, Bitmap image) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Notification",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("ExceedJob");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("ExceedJob")
                .setContentIntent(pendingIntent);

        if (image!=null){
            notificationBuilder
                    .setLargeIcon(image)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image));
        }

        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());
    }

    private Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}