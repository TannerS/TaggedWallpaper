package io.dev.tanners.backgroundsetter;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import static io.dev.tanners.backgroundsetter.BackgroundSetter.LOCK_SCREEN;
import static io.dev.tanners.backgroundsetter.BackgroundSetter.TYPE;
import static io.dev.tanners.backgroundsetter.BackgroundSetter.URL;
import static io.dev.tanners.backgroundsetter.BackgroundSetter.WALLPAPER;

public class BackgroundLoader extends AsyncTaskLoader<Boolean> {
    private int mType;
    private String mUrl;
    private Context mContext;

    public BackgroundLoader(@NonNull Context context, Bundle mBundle) {
        super(context);

        mContext = context;

        if (mBundle == null)
            return;
        this.mType = mBundle.getInt(TYPE);
        this.mUrl = mBundle.getString(URL);
    }

    @Override
        protected void onStartLoading() {
            super.onStartLoading();
            // choose which wallpaper to set
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                switch (mType) {
                    case LOCK_SCREEN:
                        mType = WallpaperManager.FLAG_LOCK;
                        break;
                    case WALLPAPER:
                        mType = WallpaperManager.FLAG_SYSTEM;
                        break;
                }
            } else {
                mType = WallpaperManager.FLAG_SYSTEM;
            }
        }

    @Nullable
    @Override
    public Boolean loadInBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                // set wallpaper based on image stream
                WallpaperManager.getInstance(mContext).setStream(
                        getNetworkConnection(mUrl),
                        null,
                        true,
                        mType
                );
            } catch (IOException e) {
                Log.i("IMAGE_SET_LOAD_BK", "Error with stream");
                return false;
            }
        } else {
            try {
                // get bitmap from stream
                Bitmap bitmap = BitmapFactory.decodeStream(getNetworkConnection(mUrl));
                // set wallpaper
                WallpaperManager.getInstance(mContext).setBitmap(bitmap);
            } catch (IOException e) {
                Log.i("IMAGE_SET_LOAD_BK", "Error with stream");
                return false;
            }
        }

        return true;
    }

    /**
     * Connect to image to download
     *
     * TODO use connection request
     * TODO make exception pass back to activity
     * @param strUrl
     * @return
     */
    private InputStream getNetworkConnection(String strUrl) throws IOException {
        java.net.URL url = null;

//        try {
            url = new  java.net.URL(strUrl);
            // connect to image url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // set to get input
            connection.setDoInput(true);
            connection.connect();
            // return stream
            return connection.getInputStream();
            // TODO throw back exception to caller
//        } catch (MalformedURLException e) {
//            return null;
//        } catch (IOException e) {
//            return null;
//        }
    }
}
