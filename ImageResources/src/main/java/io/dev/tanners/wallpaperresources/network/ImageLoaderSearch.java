package io.dev.tanners.wallpaperresources.network;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import io.dev.tanners.wallpaperresources.callbacks.post.OnPost;
import io.dev.tanners.wallpaperresources.callbacks.post.search.OnPostSearch;
import io.dev.tanners.wallpaperresources.models.photos.search.PhotoSearch;

public class ImageLoaderSearch extends ImageLoader<PhotoSearch> {
    protected final int IMAGE_SEARCH_LOADER = 65287;

    public ImageLoaderSearch(Context mContext) {
        super(mContext);
    }

    public void loadLoader(String mUrl, final OnPostSearch OnPost)
    {
        super.loadLoader(mUrl, IMAGE_SEARCH_LOADER, new LoaderManager.LoaderCallbacks<PhotoSearch>() {
            @NonNull
            @Override
            public Loader<PhotoSearch> onCreateLoader(int id, @Nullable Bundle args) {
                return new RestLoader<PhotoSearch>(mContext, args);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<PhotoSearch> loader, PhotoSearch results) {
                OnPost.onPostCall(results);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<PhotoSearch> loader) {
                // not needed
            }
        });
    }
}
