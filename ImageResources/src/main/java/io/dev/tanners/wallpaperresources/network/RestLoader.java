package io.dev.tanners.wallpaperresources.network;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import io.dev.tanners.connectionrequester.ConnectionRequester;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_AUTH_KEY;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_AUTH_VALUE;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_VERSION_KEY;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_VERSION_VALUE;

public class RestLoader extends AsyncTaskLoader<String> {
    private String mUrl = null;
    public final static String REST_URL = "REST_URL_TO_GET_IMAGE_OR_MORE";
    private Context mContext;

    public RestLoader(@NonNull Context context, Bundle mBundle) {
        super(context);

        if (mBundle == null)
            return;

        mUrl = mBundle.getString(REST_URL);
        this.mContext = context;;
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
                    .setConnectionTimeOut(5000)
                    .setReadTimeOut(15000);


            Log.d("POPULAR", "BREAK 1: ");


            if(mRequest.connect() == HttpURLConnection.HTTP_OK) {
                if(mRequest.isConnectionOk()) {
                    String response = IOUtils.toString(
                            mRequest.getStream(),
                            mEncoding
                    );

                    Log.d("POPULAR", "BREAK 2: "+ response);


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
}