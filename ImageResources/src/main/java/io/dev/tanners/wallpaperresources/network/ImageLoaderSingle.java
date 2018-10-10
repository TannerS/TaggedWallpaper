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

import io.dev.tanners.wallpaperresources.callbacks.post.single.OnPostSingle;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.dev.tanners.wallpaperresources.models.photos.search.PhotoSearch;

public class ImageLoaderSingle extends ImageLoader {
    public ImageLoaderSingle(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getLoaderId() {
        return 457853;
    }

    public void loadLoader(String mUrl, final OnPostSingle OnPost)
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

                Photo photo = null;

                try {
                    photo = objectMapper.readValue(results, Photo.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                OnPost.onPostCall(photo);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<String> loader) {
                // not needed
            }
        });
    }
}
