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

import io.dev.tanners.wallpaperresources.callbacks.post.download.OnPostDownload;
import io.dev.tanners.wallpaperresources.models.photos.download.Download;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;

public class ImageLoaderDownload extends ImageLoader {
    protected final int IMAGE_DOWNLOAD_LOADER = 46190;

    public ImageLoaderDownload(Context mContext) {
        super(mContext);
    }

    public void loadLoader(String mUrl, final OnPostDownload OnPost)
    {
        super.loadLoader(mUrl, IMAGE_DOWNLOAD_LOADER, new LoaderManager.LoaderCallbacks<String>() {
            @NonNull
            @Override
            public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
                return new RestLoader(mContext, args);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<String> loader, String results) {
                ObjectMapper objectMapper = new ObjectMapper();

                Download download = null;

                try {
                    download = objectMapper.readValue(results, Download.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                OnPost.onPostCall(download);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<String> loader) {
                // not needed
            }
        });
    }
}
