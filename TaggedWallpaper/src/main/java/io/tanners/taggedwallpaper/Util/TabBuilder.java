package io.tanners.taggedwallpaper.Util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import io.tanners.taggedwallpaper.R;

public class TabBuilder {

    public static void buildAndLaunchCustomTab(final Context mContext, final String url, int mColor)
    {
         new CustomTabsIntent.Builder()
                 .setToolbarColor(
                         ContextCompat.getColor(
                                 mContext,
                                 mColor)
                 ).build()
                 .launchUrl(
                         mContext,
                         Uri.parse(url)
                 );
    }
}
