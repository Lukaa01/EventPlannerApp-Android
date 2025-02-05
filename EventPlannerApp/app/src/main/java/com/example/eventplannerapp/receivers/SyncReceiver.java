package com.example.eventplannerapp.receivers;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.activities.ReportsActivity;
import com.example.eventplannerapp.services.SyncService;

/*
 * BroadcastReceiver je komonenta koja moze da reaguje na poruke drugih delova
 * samog sistema kao i korisnicki definisanih. Cesto se koristi u sprezi sa
 * servisima i asinhronim zadacima.
 *
 * Pored toga on moze da reaguje i na neke sistemske dogadjaje prispece sms poruke
 * paljenje uredjaja, novi poziv isl.
 */
public class SyncReceiver extends BroadcastReceiver {
    private final static String REPORT_DECLINED = "REPORT_DECLINED";
    private final static String USER_BLOCKED = "USER_BLOCKED";
    private final static String NEW_REPORT = "NEW_REPORT";
    private final static String RESERVATION_CANCELED = "RESERVATION_CANCELED";
    private final static String RESERVATION_CANCELED_OWNER = "RESERVATION_CANCELED_OWNER";

    private final static String TERM_STARTING = "TERM_STARTING";
    private static int NOTIFICATION_ID = 1;
    private static String SYNC_CHANNEL_ID = "Sync channel";
    public boolean isPermissions = false;
    private String[] permissions = {Manifest.permission.POST_NOTIFICATIONS};

    /*
     * Intent je bitan parametar za BroadcastReceiver. Kada posaljemo neku poruku,
     * ovaj Intent cuva akciju i podatke koje smo mu poslali.
     * */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("REZ", "onReceive");
        // STARIJE VERZIJE ANDROIDA
//        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, SYNC_CHANNEL_ID);

        /*
         * Posto nas BroadcastReceiver reaguje samo na jednu akciju koju smo definisali
         * dobro je da proverimo da li som dobili bas tu akciju. Ako jesmo onda mozemo
         * preuzeti i sadrzaj ako ga ima.
         *
         * Voditi racuna o tome da se naziv akcije kada korisnik salje Intent mora poklapati sa
         * nazivom akcije kada akciju proveravamo unutar BroadcastReceiver-a. Isto vazi i za podatke.
         * Dobra praksa je da se ovi nazivi izdvoje unutar neke staticke promenljive.
         * */
        if (intent.getAction().equals(ReportsActivity.SYNC_DATA)) {
            String resultCode = intent.getExtras().getString(SyncService.RESULT_CODE);
            String description = intent.getExtras().getString(SyncService.DESCRIPTION);
            Bitmap bm;

            if (resultCode.equals(REPORT_DECLINED)) {
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_info);
                mBuilder.setSmallIcon(R.drawable.ic_info);
                mBuilder.setContentTitle(context.getString(R.string.declineReportNotificationTitle));
                mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            } else if (resultCode.equals(USER_BLOCKED)) {
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_info);
                mBuilder.setSmallIcon(R.drawable.ic_info);
                mBuilder.setContentTitle(context.getString(R.string.acceptReportNotificationTitle));
                mBuilder.setContentText(description);
                mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            } else if (resultCode.equals(NEW_REPORT)) {
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_info);
                mBuilder.setSmallIcon(R.drawable.ic_info);
                mBuilder.setContentTitle(context.getString(R.string.newReportNotificationTitle));
                mBuilder.setContentText(context.getString(R.string.newReportNotificationText));
                mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            } else if (resultCode.equals("RATING_REPORT_SENT")) { // done
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_info);
                mBuilder.setSmallIcon(R.drawable.ic_info);
                mBuilder.setContentTitle("You have new rating reports!");
                mBuilder.setContentText("Check all rating reports tab to see new reports.");
                mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            } else if (resultCode.equals("RATING_REPORT_ACCEPTED")) { // done
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_info);
                mBuilder.setSmallIcon(R.drawable.ic_info);
                mBuilder.setContentTitle("Admin accepted your rating report!");
                mBuilder.setContentText("Check all ratings and reported rating won't be there.");
                mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            } else if (resultCode.equals("RATING_REPORT_REJECTED")) { // done
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_info);
                mBuilder.setSmallIcon(R.drawable.ic_info);
                mBuilder.setContentTitle("Admin rejected your rating report!");
                mBuilder.setContentText("You can't report every bad comment you get lol.");
                mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            } else if (resultCode.equals("RATING_SENT")) { // done
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_info);
                mBuilder.setSmallIcon(R.drawable.ic_info);
                mBuilder.setContentTitle("Someone rated your company!");
                mBuilder.setContentText("Check all ratings and see what they said.");
                mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            } else if (resultCode.equals(RESERVATION_CANCELED)) {
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_info);
                mBuilder.setSmallIcon(R.drawable.ic_info);
                mBuilder.setContentTitle("Some reservations has been canceled!");
                mBuilder.setContentText("User that booked your products has been banned.");
                mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            } else if (resultCode.equals(RESERVATION_CANCELED_OWNER)) {
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_info);
                mBuilder.setSmallIcon(R.drawable.ic_info);
                mBuilder.setContentTitle("Some reservations has been canceled!");
                mBuilder.setContentText("Company owner that had your reservations is banned, so your reservations there have been canceled.");
                mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            } else if (resultCode.equals("TERM_STARTING")) {
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_info);
                mBuilder.setSmallIcon(R.drawable.ic_info);
                mBuilder.setContentTitle("Your term reservation is about to start!");
                mBuilder.setContentText("Check your reservations.");
                mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            } else if(resultCode.equals("NEW_EVENT")){
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_info);
                mBuilder.setSmallIcon(R.drawable.ic_info);
                mBuilder.setContentTitle("You have new event created");
                mBuilder.setContentText("Check your events");
                mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            } else {
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
                mBuilder.setSmallIcon(R.drawable.ic_info);
                mBuilder.setContentTitle(context.getString(R.string.sysInfo));
                mBuilder.setContentText(context.getString(R.string.everythingOk));
            }
            mBuilder.setLargeIcon(bm);
        } else {
            Log.e("ERROR", "Greska u sync receiveru");
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, permissions, 101);
        } else {
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 101) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("ShopApp", "permission " + permissions[i] + " " + grantResults[i]);
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    isPermissions = false;
                }
            }
        }

        if (!isPermissions) {
            Log.e("ShopApp", "Error: no permission");
        }

    }
}
