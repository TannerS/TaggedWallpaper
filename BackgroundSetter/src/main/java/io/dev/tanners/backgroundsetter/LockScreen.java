package io.dev.tanners.backgroundsetter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

public class LockScreen extends BackgroundSetter {


    public LockScreen(Context mContext) {
        super(mContext);
    }

    public void loadLoader(final String mUrl, final BackgroundCallback onCompleted) throws Exception {
        super.loadLoader(mUrl, getLoaderId(), LOCK_SCREEN, new LoaderManager.LoaderCallbacks<Boolean>() {
            @NonNull
            @Override
            public Loader<Boolean> onCreateLoader(int id, @Nullable Bundle args) {
                return new BackgroundLoader(mContext, args);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Boolean> loader, Boolean results) {
                onCompleted.OnCompletedCallback(results);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Boolean> loader) { }
        });
    }

    @Override
    protected int getLoaderId() {
        return 12345678;
    }
}
