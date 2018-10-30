package io.tanners.taggedwallpaper.viewmodels.favorite;

import android.app.Application;

import android.support.annotation.NonNull;
import io.tanners.taggedwallpaper.db.ImageDatabase;
import io.tanners.taggedwallpaper.db.ImageEntry;
import io.tanners.taggedwallpaper.viewmodels.ImageViewModel;

public class FavoriteImageViewModel extends ImageViewModel<ImageEntry> {
    public FavoriteImageViewModel(@NonNull Application application) {
        super(application);

        ImageDatabase mImageDatabase = ImageDatabase.getInstance(this.getApplication());
        // load data from db
        mItems = mImageDatabase.getImageDao().loadAllPhotoEntries();
    }
}