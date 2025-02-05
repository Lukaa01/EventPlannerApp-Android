package com.example.eventplannerapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.example.eventplannerapp.activities.ReportsActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncService extends Service {

    public static String RESULT_CODE = "RESULT_CODE";
    public static String DESCRIPTION = "DESCRIPTION";

    ExecutorService executor = Executors.newSingleThreadExecutor(); //kreira samo jedan thread
    Handler handler = new Handler(Looper.getMainLooper()); //handler koji upravlja glavim thread-om od aplikacije (applications main thread)

    /*
     * Metoda koja se poziva prilikom izvrsavanja zadatka servisa
     * Koristeci Intent mozemo prilikom startovanja servisa proslediti
     * odredjene parametre.
     * */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("REZ", "SyncService onStartCommand");
            // Alternativa za SyncTask
            executor.execute(() -> {
                //Background work here
                Log.i("REZ", "Background work here");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(() -> {
                    //UI Thread work here
                    Log.i("REZ", "UI Thread work here");
                    Intent ints = new Intent(ReportsActivity.SYNC_DATA);
                    ints.putExtra(RESULT_CODE, intent.getStringExtra("key"));
                    ints.putExtra(DESCRIPTION, intent.getStringExtra("description"));
                    getApplicationContext().sendBroadcast(ints);
                });
            });
        //}
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
