package io.dev.tanners.wallpaperresources.network;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import io.dev.tanners.wallpaperresources.callbacks.post.OnPost;
import io.dev.tanners.wallpaperresources.callbacks.post.search.OnPostSearch;
import io.dev.tanners.wallpaperresources.models.photos.download.Download;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.dev.tanners.wallpaperresources.models.photos.search.PhotoSearch;

public class ImageLoaderSearch extends ImageLoader {
    protected final int IMAGE_SEARCH_LOADER = 65287;

    public ImageLoaderSearch(Context mContext) {
        super(mContext);
    }

    public void loadLoader(String mUrl, final OnPostSearch OnPost)
    {
        super.loadLoader(mUrl, IMAGE_SEARCH_LOADER, new LoaderManager.LoaderCallbacks<String>() {
            @NonNull
            @Override
            public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
                return new RestLoader(mContext, args);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<String> loader, String results) {
                ObjectMapper objectMapper = new ObjectMapper();

                PhotoSearch photoSearch = null;

                try {
                    photoSearch = objectMapper.readValue(results, PhotoSearch.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                OnPost.onPostCall(photoSearch);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<String> loader) {
                // not needed
            }
        });
    }
}