package io.dev.tanners.wallpaperresources.network;

import android.content.Context;

public class ImageLoaderAllPopular extends ImageLoaderAll {
    public ImageLoaderAllPopular(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getLoaderId() {
        return 11111;
    }
}
