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
import android.telecom.ConnectionRequest;

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
import static io.dev.tanners.wallpaperresources.network.ImageLoader.RestLoader.REST_URL;

public abstract class ImageLoader<T> {
    protected Context mContext;

    public ImageLoader(Context mContext) {
        this.mContext = mContext;
    }

    protected void loadLoader(String mUrl, final ImageRequest mImageCallback, int id)
    {
        // bundle for loader, but not needed for this but can't be null
        Bundle mBundle = new Bundle();

        mBundle.putString(REST_URL, mUrl);

        LoaderManager mLoaderManager = ((AppCompatActivity) mContext).getSupportLoaderManager();

        Loader<List<T>> mImageLoader = mLoaderManager.getLoader(id);

        LoaderManager.LoaderCallbacks<T> mCallback = new LoaderManager.LoaderCallbacks<T>() {
            @NonNull
            @Override
            public Loader<T> onCreateLoader(int id, @Nullable Bundle args) {
                return new RestLoader<T>(mContext, args);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<T> loader, T results) {
                mImageCallback.onResultsPost(results);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<T> loader) {
                // not needed
            }
        };

        if(mImageLoader != null) {
            mLoaderManager.initLoader(id, mBundle, mCallback).forceLoad();
        } else {
            mLoaderManager.restartLoader(id, mBundle, mCallback).forceLoad();
        }
    }

    public static class RestLoader<T> extends AsyncTaskLoader<T> {
        private String mUrl = null;
        public final static String REST_URL = "REST_URL_TO_GET_IMAGE_OR_MORE";

        public RestLoader(@NonNull Context context, Bundle mBundle) {
            super(context);
            if (mBundle == null)
                return;
            mUrl = mBundle.getString(REST_URL);
        }

        @Nullable
        @Override
        public T loadInBackground() {
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
                        .setReadTimeOut(15000)
                        .build();

                if(mRequest.connect() == HttpURLConnection.HTTP_OK) {
                    if(mRequest.isConnectionOk()) {
                        T response = getObjectFromJsonType(
                                IOUtils.toString(
                                        mRequest.getStream(),
                                        mEncoding
                                )
                        );

                        mRequest.closeConnection();

                        return response;
                    } else {
                        throw new Exception("Connection failure");
                    }
                }
                // TODO error handling here
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                mRequest.closeConnection();
            }

            return null;
        }

        /**
         * Credit: https://stackoverflow.com/a/18397672
         *
         * @return
         */
        public T getObjectFromJsonType(String mJson) {
            GsonBuilder gson = new GsonBuilder();
            Type mType = new TypeToken<T>(){}.getType();

            return gson.create().fromJson(mJson, mType);
        }
    }

    public interface ImageRequest<T> {
        public void onResultsPost(T mData);
    }
}
