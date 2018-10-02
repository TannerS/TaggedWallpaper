package io.dev.tanners.wallpaperresources.network;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import io.dev.tanners.wallpaperresources.callbacks.post.order.OnPostAll;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;

public class ImageLoaderAllPopular extends ImageLoaderAll {
    public ImageLoaderAllPopular(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getLoaderId() {
        return 11111;
    }
}
