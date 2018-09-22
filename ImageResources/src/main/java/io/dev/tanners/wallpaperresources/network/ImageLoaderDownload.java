package io.dev.tanners.wallpaperresources.network;

import android.content.Context;
import io.dev.tanners.wallpaperresources.models.photos.download.Download;

public class ImageLoaderDownload extends ImageLoader<Download> {
    protected final int IMAGE_DOWNLOAD_LOADER = 46190;

    public ImageLoaderDownload(Context mContext) {
        super(mContext);
    }

    public void loadLoader(String mUrl, final ImageRequest mImageCallback)
    {
        super.loadLoader(mUrl, mImageCallback, IMAGE_DOWNLOAD_LOADER);
    }
}
