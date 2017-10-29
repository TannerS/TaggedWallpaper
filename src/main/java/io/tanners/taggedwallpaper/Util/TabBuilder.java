package io.tanners.taggedwallpaper.Util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.customtabs.CustomTabsIntent;
import android.util.Log;

import io.tanners.taggedwallpaper.R;

public class TabBuilder {

    public static void buildAndLaunchCustomTab(final Context mContext, final String url)
    {
        Log.i("TABS", "DEBUG 2");
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.setToolbarColor(mContext.getResources().getColor(R.color.black, mContext.getTheme()));
        }
        else
        {
            // one of these should work
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                builder.setToolbarColor(mContext.getColor(R.color.black));
            }
            else
            {
                builder.setToolbarColor(mContext.getResources().getColor(R.color.black));
            }
        }
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(mContext, Uri.parse(url));

        Log.i("TABS", "DEBUG 3");
    }

}
