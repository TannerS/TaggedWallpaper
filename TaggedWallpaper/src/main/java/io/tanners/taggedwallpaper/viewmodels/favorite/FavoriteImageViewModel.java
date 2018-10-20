package io.tanners.taggedwallpaper.viewmodels.favorite;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.model.categories.CategoryItem;
import io.tanners.taggedwallpaper.viewmodels.ImageViewModel;

public class FavoriteImageViewModel extends ImageViewModel {
    private LiveData<ArrayList<Photo>> mFavorites;

    public FavoriteImageViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<Photo>> getmFavorites() {
        return mFavorites;
    }
}
