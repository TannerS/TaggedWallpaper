package io.dev.tanners.wallpaperresources.loader.rest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import io.dev.tanners.wallpaperresources.callbacks.post.download.OnPostDownload;
import io.dev.tanners.wallpaperresources.loader.rest.download.RestDownloadLoader;
import io.dev.tanners.wallpaperresources.models.photos.download.Download;
import io.dev.tanners.wallpaperresources.models.photos.search.PhotoSearch;

import static io.dev.tanners.wallpaperresources.loader.rest.download.RestDownloadLoader.DOWNLOAD_ALBUM_KEY;

public class ImageLoaderDownloadHotLink extends ImageLoader<String> {
    public ImageLoaderDownloadHotLink(Context mContext) {
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
            super.loadLoader(mUrl, getLoaderId(), new LoaderManager.LoaderCallbacks<String>() {
                @NonNull
                @Override
                public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
//                    return new RestDownloadLoader(mContext, args);
                    return new RestLoader(mContext, args);
                }

                @Override
                public void onLoadFinished(@NonNull Loader<String> loader, String results) {
                    ObjectMapper objectMapper = new ObjectMapper();

                    Download mDownload = null;

                    if(results == null || results.length() == 0)
                        return;

                    try {
                        mDownload = objectMapper.readValue(results, Download.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(mDownload == null)
                        return;

                    /*
                        The api has an endpoint that marks the download for analytics
                        then returns the true api endpoint for the actual download
                     */
                    ImageLoaderDownload mImageLoader = new ImageLoaderDownload(mContext);
                    mImageLoader.loadLoader(mDownload.getUrl(), mAlbumName, OnPost);
                }

                @Override
                public void onLoaderReset(@NonNull Loader<String> loader) { }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
