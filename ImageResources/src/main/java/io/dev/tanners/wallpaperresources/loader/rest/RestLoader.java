package io.dev.tanners.wallpaperresources.loader.rest;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import io.dev.tanners.connectionrequester.ConnectionRequester;
import io.dev.tanners.wallpaperresources.loader.BaseRestLoader;

import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_AUTH_KEY;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_AUTH_VALUE;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_VERSION_KEY;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_VERSION_VALUE;

public class RestLoader extends BaseRestLoader<String> {
    public RestLoader(@NonNull Context context, Bundle mBundle) {
        super(context, mBundle);

        if (mBundle == null)
            return;
        mUrl = mBundle.getString(URL_KEY);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        ConnectionRequester mRequest = null;
        String mEncoding = "utf-8";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mEncoding = StandardCharsets.UTF_8.name().toLowerCase(Locale.getDefault());
        }

        mRequest = new ConnectionRequester(mUrl, mEncoding);

        try {
            mRequest = mRequest.addBasicBody(null)
                    .addRequestHeader(HEADER_VERSION_KEY, HEADER_VERSION_VALUE)
                    .addRequestHeader(HEADER_AUTH_KEY, HEADER_AUTH_VALUE)
                    .setRequestType(ConnectionRequester.RequestType.GET)
                    .setConnectionTimeOut(3000)
                    .setReadTimeOut(5000);

            int statusCode = mRequest.connect();

            if(statusCode == HttpURLConnection.HTTP_OK) {
                if(mRequest.isConnectionOk()) {
                    String response = IOUtils.toString(
                            mRequest.getStream(),
                            mEncoding
                    );

                    mRequest.closeConnection();

                    return response;
                } else {
                    throw new Exception("Connection failure");
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            mRequest.closeConnection();
        }

        return null;
    }

    // used to update bundle since loader will use existing instance with old data
    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}