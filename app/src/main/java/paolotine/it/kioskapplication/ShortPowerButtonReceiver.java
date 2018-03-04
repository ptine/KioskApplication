package paolotine.it.kioskapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

/**
 * Created by oloap on 04/03/2018.
 */

public class ShortPowerButtonReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
            CustomApplication ctx = (CustomApplication) context.getApplicationContext();
            //otteniamo il wake lock e rilasciamo il precedente
            PowerManager.WakeLock wakeLock = ctx.getWakeLock();
            if (wakeLock.isHeld()) {
                wakeLock.release();
            }

            // creiamo un wake lock e lo rilasciamo
            wakeLock.acquire();
            wakeLock.release();
        }
    }
}
