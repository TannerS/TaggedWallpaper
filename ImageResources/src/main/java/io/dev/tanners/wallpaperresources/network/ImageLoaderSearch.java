package io.dev.tanners.wallpaperresources.network;

import android.content.Context;
import io.dev.tanners.wallpaperresources.models.photos.search.PhotoSearch;

public class ImageLoaderSearch extends ImageLoader<PhotoSearch> {
    protected final int IMAGE_SEARCH_LOADER = 65287;

    public ImageLoaderSearch(Context mContext) {
        super(mContext);
    }

    public void loadLoader(String mUrl, final ImageRequest mImageCallback)
    {
        super.loadLoader(mUrl, mImageCallback, IMAGE_SEARCH_LOADER);
    }

}
