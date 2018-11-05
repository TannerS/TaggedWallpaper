package io.dev.tanners.backgroundsetter;

import android.content.Context;
import io.dev.tanners.backgroundsetter.setter.BackgroundSetter;
import io.dev.tanners.backgroundsetter.setter.home.Background;
import io.dev.tanners.backgroundsetter.setter.lock.LockScreen;

/**
 * Background setter main entry point
 */
public class BackgroundSet {
    /**
     * Set image as background
     *
     * @param mContext
     * @param mUrl
     * @param mCallback
     */
    public static void setBackground(Context mContext, String mUrl, BackgroundSetter.BackgroundCallback mCallback) {
        Background mBackgroundLoader = new Background(mContext);

        try {
            // load loader
            mBackgroundLoader.loadLoader(mUrl, mCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set image as lock screen
     *
     * @param mContext
     * @param mUrl
     * @param mCallback
     */
    public static void setLockScreen(Context mContext, String mUrl, BackgroundSetter.BackgroundCallback mCallback) {
        LockScreen mLockLoader = new LockScreen(mContext);

        try {
            // load loader
            mLockLoader.loadLoader(mUrl, mCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
