package io.dev.tanners.wallpaperresources.network;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import io.dev.tanners.wallpaperresources.callbacks.post.order.OnPostAll;
import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;

public class ImageLoaderAll extends ImageLoader<Photos> {
    protected final int IMAGE_ALL_LOADER = 46646;

    public ImageLoaderAll(Context mContext) {
        super(mContext);
    }

    public void loadLoader(String mUrl, final OnPostAll OnPost)
    {
        super.loadLoader(mUrl, IMAGE_ALL_LOADER, new LoaderManager.LoaderCallbacks<Photos>() {
            @NonNull
            @Override
            public Loader<Photos> onCreateLoader(int id, @Nullable Bundle args) {
                return new RestLoader<Photos>(mContext, args);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Photos> loader, Photos results) {
                OnPost.onPostCall(results);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Photos> loader) {
                // not needed
            }
        });
    }
}
