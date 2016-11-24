package com.livin.vedapadakam;

import android.content.res.Resources;

/**
 * Created by i302913 on 5/7/16.
 */
public class Utilitiy {

    static float dptopx(int dp)
    {
        return (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
