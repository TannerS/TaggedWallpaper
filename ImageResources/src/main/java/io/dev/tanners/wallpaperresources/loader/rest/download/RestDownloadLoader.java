package io.dev.tanners.wallpaperresources.loader.rest.download;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.UUID;
import io.dev.tanners.connectionrequester.ConnectionRequester;
import io.dev.tanners.wallpaperresources.loader.BaseRestLoader;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_AUTH_KEY;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_AUTH_VALUE;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_VERSION_KEY;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_VERSION_VALUE;

public class RestDownloadLoader extends BaseRestLoader<RestDownloadLoader.RestDownloadLoaderReturn> {
    public static final String DOWNLOAD_ALBUM_KEY = "DOWNLOAD_ALBUM_KEY";
    private String mAlbumName = null;

    public RestDownloadLoader(@NonNull Context context, Bundle mBundle) {
        super(context, mBundle);

        if (mBundle != null) {
            mAlbumName = mBundle.getString(DOWNLOAD_ALBUM_KEY);
        }
    }

    @Nullable
    @Override
    public RestDownloadLoader.RestDownloadLoaderReturn loadInBackground() {
        ConnectionRequester mRequest = null;
        String mEncoding = "utf-8";
        File mFile = null;

        if (!isExternalStorageWritable()) {
            return new RestDownloadLoaderReturn("External storage is not writable", false);
        }

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

            int httpStatus = mRequest.connect();

            if (httpStatus == HttpURLConnection.HTTP_OK) {
                if (mRequest.isConnectionOk()) {
                    try {
                        mFile = getNewFile(this.getUUID());
                        mRequest.fileDownloader(mFile);
                    } catch (FileNotFoundException e) {
                        return new RestDownloadLoaderReturn("Error has occurred: " + e.getLocalizedMessage(), false);
                    }
                }
            } else {
                Log.i("HTTP_CODE", String.valueOf(mUrl));
                Log.i("HTTP_CODE", String.valueOf(httpStatus));
                return new RestDownloadLoaderReturn("Error has occurred -> HTTP: " + httpStatus, false);
            }
        } catch (IOException e) {
            return new RestDownloadLoaderReturn("Error has occurred: " + e.getLocalizedMessage(), false);
        } finally {
            mRequest.closeConnection();
        }

        return new RestDownloadLoaderReturn(null, true, mFile);
    }

    private String getUUID()
    {
        return UUID.randomUUID().toString() + ".jpg";
    }

    private File getNewFile(String mFileName) {
        File mImageFile = null;
        // create album
        File mImageDir = getAlbumStorageDir(mAlbumName == null ? "TaggedWallpaper" : mAlbumName);
        // create file based on name and album
        mImageFile = new File(mImageDir, mFileName);
        // return image file reference
        return mImageFile;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String mAlbumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), mAlbumName);
        if (!file.mkdirs()) {
            Log.e("STORAGE", "Directory not created");
        }
        return file;
    }

    public class RestDownloadLoaderReturn
    {
        public RestDownloadLoaderReturn(String errorMessage, Boolean isGood, File mFile) {
            this.mFile = mFile;
            this.errorMessage = errorMessage;
            this.isGood = isGood;
        }

        public RestDownloadLoaderReturn(String errorMessage, Boolean isGood) {
            this(errorMessage, isGood, null);
        }

        public File mFile = null;
        public String errorMessage = null;
        public Boolean isGood = false;
    }
}