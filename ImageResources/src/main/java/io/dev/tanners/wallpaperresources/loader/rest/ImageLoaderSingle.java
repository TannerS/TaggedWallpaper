package io.dev.tanners.wallpaperresources.loader.rest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import io.dev.tanners.wallpaperresources.callbacks.post.single.OnPostSingle;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;

public class ImageLoaderSingle extends ImageLoader<String> {
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
                if(results == null || results.length() == 0)
                    return;

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
            public void onLoaderReset(@NonNull Loader<String> loader) { }
        });
    }
}
