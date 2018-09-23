package io.dev.tanners.wallpaperresources.network;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import io.dev.tanners.wallpaperresources.callbacks.post.single.OnPostSingle;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;

public class ImageLoaderSingle extends ImageLoader<Photo> {
    protected final int IMAGE_SINGLE_LOADER = 34532;

    public ImageLoaderSingle(Context mContext) {
        super(mContext);
    }

    public void loadLoader(String mUrl, final OnPostSingle OnPost)
    {
        super.loadLoader(mUrl, IMAGE_SINGLE_LOADER, new LoaderManager.LoaderCallbacks<Photo>() {
            @NonNull
            @Override
            public Loader<Photo> onCreateLoader(int id, @Nullable Bundle args) {
                return new RestLoader<Photo>(mContext, args);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Photo> loader, Photo results) {
                OnPost.onPostCall(results);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Photo> loader) {
                // not needed
            }
        });
    }
}
