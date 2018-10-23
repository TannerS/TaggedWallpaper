package io.dev.tanners.wallpaperresources.network;

import android.content.Context;

public class ImageLoaderAllLatest extends ImageLoaderAll {
    public ImageLoaderAllLatest(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getLoaderId() {
        return 46646;
    }
}