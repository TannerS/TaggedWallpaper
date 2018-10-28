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

public class ImageLoaderDownload extends ImageLoader<RestDownloadLoader.RestDownloadLoaderReturn> {
    public ImageLoaderDownload(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getLoaderId() {
        return -2;
    }

    public void loadLoader(String mUrl, final OnPostDownload OnPost)
    {
        try {
            super.loadLoader(mUrl, getLoaderId(), new LoaderManager.LoaderCallbacks<RestDownloadLoader.RestDownloadLoaderReturn>() {
                @NonNull
                @Override
                public Loader<RestDownloadLoader.RestDownloadLoaderReturn> onCreateLoader(int id, @Nullable Bundle args) {
                    return new RestDownloadLoader(mContext, args);
                }

                @Override
                public void onLoadFinished(@NonNull Loader<RestDownloadLoader.RestDownloadLoaderReturn> loader, RestDownloadLoader.RestDownloadLoaderReturn results) {
//                    if (results.isGood && results.errorMessage == null && results.mFile != null) {
                        // call media scanner
                    OnPost.onPostCall(results);
//                    mErrorCallBack.displayNoError();
//                    } else if (results.errorMessage != null) {
//                        throw new Exception(results.errorMessage);
//                    } else {
//                    mErrorCallBack.displayError(errorMessage);
//                    }
                }

                @Override
                public void onLoaderReset(@NonNull Loader<RestDownloadLoader.RestDownloadLoaderReturn> loader) { }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Callback for error messages when fragments attempt to populate images
     * This used in the LatestImage and PopulateImages fragments
     */
//    public interface ErrorCallBack
//    {
//        public void displayError(String message);
//        public void displayNoError(String message);
//        public void displayError();
//        public void displayNoError();
//    }
}
