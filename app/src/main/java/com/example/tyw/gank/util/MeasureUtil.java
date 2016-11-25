package com.example.tyw.gank.util;

import android.content.Context;

/**
 * Created by tangyiwu on 2016/11/25.
 */

public class MeasureUtil {
    public static int dp2px(Context context, int dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static final int px2dp(Context context, int pxValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }
}
