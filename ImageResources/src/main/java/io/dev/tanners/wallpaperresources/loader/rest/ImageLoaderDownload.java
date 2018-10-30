package io.dev.tanners.wallpaperresources.loader.rest;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.io.File;
import java.io.IOException;

import io.dev.tanners.wallpaperresources.callbacks.post.download.OnPostDownload;
import io.dev.tanners.wallpaperresources.loader.rest.download.RestDownloadLoader;

import static io.dev.tanners.wallpaperresources.loader.BaseRestLoader.URL_KEY;
import static io.dev.tanners.wallpaperresources.loader.rest.download.RestDownloadLoader.DOWNLOAD_ALBUM_KEY;

public class ImageLoaderDownload extends ImageLoader<RestDownloadLoader.RestDownloadLoaderReturn> {
    public ImageLoaderDownload(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getLoaderId() {
        return -2;
    }

    public void loadLoader(String mUrl, String mAlbumName, final OnPostDownload OnPost)
    {
        if(mBundle == null)
            return;

        mBundle.putString(DOWNLOAD_ALBUM_KEY, mAlbumName);

        try {
            super.loadLoader(mUrl, getLoaderId(), new LoaderManager.LoaderCallbacks<RestDownloadLoader.RestDownloadLoaderReturn>() {
                @NonNull
                @Override
                public Loader<RestDownloadLoader.RestDownloadLoaderReturn> onCreateLoader(int id, @Nullable Bundle args) {
                    return new RestDownloadLoader(mContext, args);
                }

                @Override
                public void onLoadFinished(@NonNull Loader<RestDownloadLoader.RestDownloadLoaderReturn> loader, RestDownloadLoader.RestDownloadLoaderReturn results) {
                    OnPost.onPostCall(results);
                }

                @Override
                public void onLoaderReset(@NonNull Loader<RestDownloadLoader.RestDownloadLoaderReturn> loader) { }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
