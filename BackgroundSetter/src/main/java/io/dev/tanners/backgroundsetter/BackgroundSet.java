package io.dev.tanners.backgroundsetter;

import android.content.Context;

public class BackgroundSet {
    public static void setBackground(Context mContext, String mUrl, BackgroundSetter.BackgroundCallback mCallback) {
        Background mBackgroundLoader = new Background(mContext);
        try {
            mBackgroundLoader.loadLoader(mUrl, mCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setLockScreen(Context mContext, String mUrl, BackgroundSetter.BackgroundCallback mCallback) {
        LockScreen mLockLoader = new LockScreen(mContext);
        try {
            mLockLoader.loadLoader(mUrl, mCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
