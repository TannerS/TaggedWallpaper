package io.dev.tanners.wallpaperresources.network;

import android.content.Context;
import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;

public class ImageLoaderAll extends ImageLoader<Photos> {
    protected final int IMAGE_ALL_LOADER = 46646;

    public ImageLoaderAll(Context mContext) {
        super(mContext);
    }

    public void loadLoader(String mUrl, final ImageRequest mImageCallback)
    {
        super.loadLoader(mUrl, mImageCallback, IMAGE_ALL_LOADER);
    }
}
