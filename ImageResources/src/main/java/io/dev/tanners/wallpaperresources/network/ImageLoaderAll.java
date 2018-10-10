package io.dev.tanners.wallpaperresources.network;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

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
    public ImageLoaderAll(Context mContext) {
        super(mContext);
    }

    public void loadLoader(final String mUrl, final OnPostAll OnPost)
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
                    if(results == null || results.length() == 0)
                        return;
                    photos = objectMapper.readValue(results, Photo[].class);
                } catch (IOException e) {
                    e.printStackTrace();
                }




                OnPost.onPostCall(new ArrayList<Photo>(Arrays.asList(photos)));


                ((Activity)mContext).getLoaderManager().destroyLoader(getLoaderId());



            }

            @Override
            public void onLoaderReset(@NonNull Loader<String> loader) {
                Log.i("LOADER", "RESTARTED");
            }

        });
    }
}
