package io.dev.tanners.wallpaperresources.loader.rest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import io.dev.tanners.wallpaperresources.callbacks.post.search.OnPostSearch;
import io.dev.tanners.wallpaperresources.models.photos.search.PhotoSearch;

public class ImageLoaderSearch extends ImageLoader<String> {
    public ImageLoaderSearch(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getLoaderId() {
        return 653456;
    }

    public void loadLoader(String mUrl, final OnPostSearch OnPost)
    {
        super.loadLoader(mUrl, getLoaderId(), new LoaderManager.LoaderCallbacks<String>() {
            @NonNull
            @Override
            public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
                return new RestLoader(mContext, args);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<String> loader, String results) {
                ObjectMapper objectMapper = new ObjectMapper();

                PhotoSearch photoSearch = null;

                if(results == null || results.length() == 0)
                    return;

                try {
                    photoSearch = objectMapper.readValue(results, PhotoSearch.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                OnPost.onPostCall(photoSearch);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<String> loader) { }
        });
    }
}