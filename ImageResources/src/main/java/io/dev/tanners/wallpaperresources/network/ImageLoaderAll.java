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
import java.util.Arrays;

import io.dev.tanners.wallpaperresources.callbacks.post.order.OnPostAll;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
//import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;

//public class ImageLoaderAll extends ImageLoader<Photos> {
public abstract class ImageLoaderAll extends ImageLoader {
//    protected final int IMAGE_ALL_LOADER = 46646;

    public ImageLoaderAll(Context mContext) {
        super(mContext);
    }

    public void loadLoader(String mUrl, final OnPostAll OnPost)
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
                Photo[] photos = null;

                try {
                    photos = objectMapper.readValue(results, Photo[].class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                OnPost.onPostCall(new ArrayList<Photo>(Arrays.asList(photos)));
            }

            @Override
            public void onLoaderReset(@NonNull Loader<String> loader) {
                // not needed
            }
        });
    }

    // for each loader must have it's own unique id, this method is over loaded with the child class having the id for its own loader
    protected abstract int getLoaderId();
}
