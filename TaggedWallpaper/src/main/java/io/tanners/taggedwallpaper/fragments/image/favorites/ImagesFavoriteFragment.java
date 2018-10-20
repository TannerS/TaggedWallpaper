package io.tanners.taggedwallpaper.fragments.image.favorites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import java.util.ArrayList;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.adapters.favorite.ImageFavoriteAdapter;
import io.tanners.taggedwallpaper.fragments.image.ImagesFragment;
import io.tanners.taggedwallpaper.viewmodels.favorite.FavoriteImageViewModel;

public class ImagesFavoriteFragment extends ImagesFragment {
    // fragment title
    public static final String FAVORITE = "Favorites";

    // creates new instance
    public static ImagesFavoriteFragment newInstance() {
        return new ImagesFavoriteFragment();
    }

    @Override
    protected void loadViewModelListener(Observer<ArrayList<Photo>> mObserver) {
        getViewModel().getmFavorites().observe(this, mObserver);
    }

    @Override
    protected void loadAdapter() {
        mAdapter = new ImageFavoriteAdapter(mContext);
    }

    @Override
    protected FavoriteImageViewModel getViewModel() {
        return ViewModelProviders.of(this).get(FavoriteImageViewModel.class);
    }
}
