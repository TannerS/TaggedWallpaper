package io.tanners.taggedwallpaper.Util;

import android.content.Context;
import android.content.Intent;

import io.tanners.taggedwallpaper.ImageActivity;

public class IntentBuilder {

    public static void displayPhotoByTag(Context context, String query)
    {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(ImageActivity.TAG, query);
        context.startActivity(intent);
    }
}
