package io.dev.tanners.backgroundsetter.setter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

public abstract class BackgroundSetter {
    public static final String URL = "BACKGROUND_SET_BUNDLE_KEY";
    public static final String TYPE = "BACKGROUND_TYPE_BUNDLE_KEY";
    public static final int LOCK_SCREEN = 100;
    public static final int WALLPAPER = 200;
    protected Context mContext;

    /**
     * @param mContext
     */
    public BackgroundSetter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Loader to set background or lock
     *
     * @param mUrl
     * @param id
     * @param type
     * @param mCallback
     * @throws Exception
     */
    protected void loadLoader(String mUrl, int id, int type, LoaderManager.LoaderCallbacks<Boolean> mCallback) throws Exception {
        // bundle for loader
        Bundle mBundle = new Bundle();

        mBundle.putString(URL, mUrl);

        switch (type) {
            case LOCK_SCREEN:
                mBundle.putInt(TYPE, type);
                break;
            case WALLPAPER:
                mBundle.putInt(TYPE, type);
                break;
            default:
                throw new Exception("INVALID STATE FOR BACKGROUND LOADER");
        }

        LoaderManager mLoaderManager = ((AppCompatActivity) mContext).getSupportLoaderManager();

        Loader<Boolean> mBackgroundLoader = mLoaderManager.getLoader(id);

        if (mBackgroundLoader == null) {
            mLoaderManager.initLoader(id, mBundle, mCallback).forceLoad();
        } else {
            mLoaderManager.restartLoader(id, mBundle, mCallback).forceLoad();
        }
    }

    // for each loader must have it's own unique id, this method is over loaded with the child class having the id for its own loader
    protected abstract int getLoaderId();

    public interface BackgroundCallback
    {
        public void OnCompletedCallback(Boolean results);
    }
}


