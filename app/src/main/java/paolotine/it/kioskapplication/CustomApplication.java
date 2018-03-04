package paolotine.it.kioskapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

/**
 * Created by oloap on 04/03/2018.
 */

public class CustomApplication extends Application {

    private CustomApplication instance;
    private PowerManager.WakeLock wakeLock;
    private ShortPowerButtonReceiver powerButtonReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerKioskModePowerButtonReceiver();
        startActivityMonitorService();
    }

    private void registerKioskModePowerButtonReceiver() {

        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        powerButtonReceiver = new ShortPowerButtonReceiver();
        registerReceiver(powerButtonReceiver, filter);
    }

    public PowerManager.WakeLock getWakeLock() {
        if(wakeLock == null) {

            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "screenon");
        }
        return wakeLock;
    }

    private void startActivityMonitorService() {
        startService(new Intent(this, ActivityMonitor.class));
    }
}
