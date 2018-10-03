package io.dev.tanners.wallpaperresources.network;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import io.dev.tanners.connectionrequester.ConnectionRequester;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_AUTH_KEY;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_AUTH_VALUE;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_VERSION_KEY;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_VERSION_VALUE;
import static io.dev.tanners.wallpaperresources.network.RestLoader.REST_URL;

public abstract class ImageLoader {
    protected Context mContext;

    public ImageLoader(Context mContext) {
        this.mContext = mContext;
    }

    protected void loadLoader(String mUrl, int id, LoaderManager.LoaderCallbacks<String> mCallback) {
        // bundle for loader, but not needed for this but can't be null
        Bundle mBundle = new Bundle();

        mBundle.putString(REST_URL, mUrl);

        LoaderManager mLoaderManager = ((AppCompatActivity) mContext).getSupportLoaderManager();



        Loader<List<String>> mImageLoader = mLoaderManager.getLoader(id);

        if (mImageLoader == null) {
            mLoaderManager.initLoader(id, mBundle, mCallback).forceLoad();
        } else {
            mLoaderManager.restartLoader(id, mBundle, mCallback).forceLoad();
        }
    }
}
