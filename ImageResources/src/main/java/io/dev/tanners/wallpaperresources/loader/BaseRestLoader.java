package io.dev.tanners.wallpaperresources.loader;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import io.dev.tanners.connectionrequester.ConnectionRequester;

import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_AUTH_KEY;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_AUTH_VALUE;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_VERSION_KEY;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_VERSION_VALUE;

public class BaseRestLoader<T> extends AsyncTaskLoader<T> {
    public final static String URL_KEY = "BASE_REST_BUNDLE_KEY";
    protected String mUrl = null;
    protected Context mContext;

    public BaseRestLoader(@NonNull Context context, Bundle mBundle) {
        super(context);

        this.mContext = context;

        if (mBundle != null) {
            mUrl = mBundle.getString(URL_KEY);
        }
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    @Nullable
    @Override
    public T loadInBackground() {
        return null;
    }
}