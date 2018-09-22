package io.dev.tanners.wallpaperresources.network;

import android.content.Context;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;

public class ImageLoaderSingle extends ImageLoader<Photo> {
    protected final int IMAGE_SINGLE_LOADER = 34532;

    public ImageLoaderSingle(Context mContext) {
        super(mContext);
    }

    public void loadLoader(String mUrl, final ImageRequest mImageCallback)
    {
        super.loadLoader(mUrl, mImageCallback, IMAGE_SINGLE_LOADER);
    }
}
