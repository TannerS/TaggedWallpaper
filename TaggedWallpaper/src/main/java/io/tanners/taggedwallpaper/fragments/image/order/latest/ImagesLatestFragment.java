package io.tanners.taggedwallpaper.fragments.image.order.latest;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import java.util.List;

import io.dev.tanners.wallpaperresources.config.ConfigPhotosAll;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.fragments.image.order.ImagesOrderFragment;
import io.tanners.taggedwallpaper.viewmodels.order.latest.LatestOrderImageViewModel;

public class ImagesLatestFragment extends ImagesOrderFragment {
    // fragment title
    public static final String LATEST = "Latest";

    // creates new instance
    public static ImagesLatestFragment newInstance() {
        return new ImagesLatestFragment();
    }

    @Override
    protected void loadViewModelListener(Observer<List<Photo>> mObserver) {
        getViewModel().getmItems().observe(this, mObserver);
    }

    @Override
    protected void loadEntryPointData() {
        // load init data
        loadImageDataByType(ConfigPhotosAll.Order.LATEST);
    }

    @Override
    protected void onScroll() {
        // load init data
        loadImageDataByType(ConfigPhotosAll.Order.LATEST);
    }

    @Override
    protected LatestOrderImageViewModel getViewModel() {
        return ViewModelProviders.of(this).get(LatestOrderImageViewModel.class);
    }
}
