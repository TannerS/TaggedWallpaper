package io.dev.tanners.wallpaperresources.network;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import io.dev.tanners.wallpaperresources.callbacks.post.download.OnPostDownload;
import io.dev.tanners.wallpaperresources.models.photos.download.Download;

public class ImageLoaderDownload extends ImageLoader<Download> {
    protected final int IMAGE_DOWNLOAD_LOADER = 46190;

    public ImageLoaderDownload(Context mContext) {
        super(mContext);
    }

    public void loadLoader(String mUrl, final OnPostDownload OnPost)
    {
        super.loadLoader(mUrl, IMAGE_DOWNLOAD_LOADER, new LoaderManager.LoaderCallbacks<Download>() {
            @NonNull
            @Override
            public Loader<Download> onCreateLoader(int id, @Nullable Bundle args) {
                return new RestLoader<Download>(mContext, args);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Download> loader, Download results) {
                OnPost.onPostCall(results);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Download> loader) {
                // not needed
            }
        });
    }
}
