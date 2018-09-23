package io.tanners.taggedwallpaper.support.builder.tabs;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

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
