package lebelleami.com.pillbox.alarm;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import lebelleami.com.pillbox.R;
import lebelleami.com.pillbox.controller.AddMedicationActivity;
import lebelleami.com.pillbox.controller.MainActivity;

public class AlarmNotificationService extends IntentService {

    //Notification ID for Alarm
    public static final int NOTIFICATION_ID = 1;
    public static final String NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";


    public AlarmNotificationService() {
        super("AlarmNotificationService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        //Send notification
        sendNotification("Please take your " + AddMedicationActivity.drugName + " medication now!");
    }

    //handle notification
    private void sendNotification(String msg) {
        NotificationManager alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, this.getString(R.string.main_notification_channel_name), NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setShowBadge(true);
            notificationChannel.setDescription("Notifies you to take your medication");
            if (alarmNotificationManager != null) {
                alarmNotificationManager.createNotificationChannel(notificationChannel);
            }
        }

        //get pending intent
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        //Create notification
        NotificationCompat.Builder alarmNotificationBuilder = new NotificationCompat.Builder(
                this, NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setContentTitle("Medication Reminder")
                .setSmallIcon(R.mipmap.ic_launcher_pillbox)
                .setLargeIcon(largeIcon(this))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg)
                .setContentIntent(contentIntent)
                .setAutoCancel(true);


        //notify notification manager about new notification
        assert alarmNotificationManager != null;
        alarmNotificationManager.notify(NOTIFICATION_ID, alarmNotificationBuilder.build());
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, R.drawable.drugs);
    }
}
