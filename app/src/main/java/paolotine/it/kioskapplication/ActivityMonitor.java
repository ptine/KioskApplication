package paolotine.it.kioskapplication;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by oloap on 04/03/2018.
 */

public class ActivityMonitor extends Service {

    //intervallo di due secondi per verificare lo stato della main activity (foreground o background?)
    private static final long INTERVAL = TimeUnit.SECONDS.toMillis(1);
    private static final String TAG = ActivityMonitor.class.getSimpleName();

    private Thread t = null;
    private Context ctx = null;
    private boolean running = false;

    @Override
    public void onDestroy() {
        Log.i(TAG, "Stopping service 'ActivityMonitor'");
        running =false;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Starting service 'ActivityMonitor'");
        running = true;
        ctx = this;

        // verifichiamo a intervalli regolari che l'app non sia andata in background
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    handleKioskMode();
                    try {
                        Thread.sleep(INTERVAL);
                    } catch (InterruptedException e) {
                        Log.i(TAG, "Thread interrupted: 'ActivityMonitor'");
                    }
                }while(running);
                stopSelf();
            }
        });

        t.start();
        return Service.START_NOT_STICKY;
    }

    private void handleKioskMode() {

        Context ctx = getApplicationContext();

        // verifico se la main activity è in foreground o meno
        if(isBackgroundRunning(ctx)) {
            //se è in background la riavvio
            restoreApp();
        }

    }

    private boolean isBackgroundRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {

            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {

                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {

                        return false;
                    }
                }
            }
        }


        return true;
    }



    //riavviamo l'activity
    private void restoreApp() {

        Intent i = new Intent(ctx, FullscreenActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(i);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
