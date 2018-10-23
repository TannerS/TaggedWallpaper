package io.dev.tanners.wallpaperresources.network;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import java.util.List;
import static io.dev.tanners.wallpaperresources.network.RestLoader.REST_URL;

public abstract class ImageLoader {
    protected Context mContext;

    public ImageLoader(Context mContext) {
        this.mContext = mContext;
    }

    protected void loadLoader(String mUrl, int id, LoaderManager.LoaderCallbacks<String> mCallback) {
        // bundle for loader, but not needed for this but can't be null
        Bundle mBundle = new Bundle();

        mBundle.putString(REST_URL, mUrl);

        LoaderManager mLoaderManager = ((AppCompatActivity) mContext).getSupportLoaderManager();



        Loader<List<String>> mImageLoader = mLoaderManager.getLoader(id);

        if (mImageLoader == null) {
            mLoaderManager.initLoader(id, mBundle, mCallback).forceLoad();
        } else {
            mLoaderManager.restartLoader(id, mBundle, mCallback).forceLoad();
        }
    }

    // for each loader must have it's own unique id, this method is over loaded with the child class having the id for its own loader
    protected abstract int getLoaderId();
}
