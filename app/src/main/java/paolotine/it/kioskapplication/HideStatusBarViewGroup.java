package paolotine.it.kioskapplication;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by oloap on 04/03/2018.
 */

public class HideStatusBarViewGroup extends ViewGroup {

    public HideStatusBarViewGroup(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Intercepted touch!
        return true;
    }
}
