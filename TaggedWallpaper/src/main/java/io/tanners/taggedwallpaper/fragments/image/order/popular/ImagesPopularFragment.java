package io.tanners.taggedwallpaper.fragments.image.order.popular;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.dev.tanners.wallpaperresources.callbacks.post.search.OnPostSearch;
import io.dev.tanners.wallpaperresources.config.ConfigPhotosAll;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.dev.tanners.wallpaperresources.models.photos.search.PhotoSearch;
import io.tanners.taggedwallpaper.fragments.image.order.ImagesOrderFragment;
import io.tanners.taggedwallpaper.viewmodels.order.OrderImageViewModel;
import io.tanners.taggedwallpaper.viewmodels.order.popular.PopularOrderImageViewModel;

public class ImagesPopularFragment extends ImagesOrderFragment {
    // fragment title
    public static final String POPULAR = "Popular";

    @Override
    protected void loadViewModelListener(Observer<ArrayList<Photo>> mObserver) {
        getViewModel().getmPhotos().observe(this, mObserver);
    }

    @Override
    protected void loadEntryPointData() {
        // load init data
        loadImageDataByType(ConfigPhotosAll.Order.POPULAR);
    }

    @Override
    protected void onScroll() {
        // load init data
        loadImageDataByType(ConfigPhotosAll.Order.POPULAR);
    }

    // creates new instance
    public static ImagesPopularFragment newInstance() {
        return new ImagesPopularFragment();
    }

    @Override
    protected PopularOrderImageViewModel getViewModel() {
        return ViewModelProviders.of(this).get(PopularOrderImageViewModel.class);
    }

    /**
     *
     */
    @Override
    public void onResume() {
        super.onResume();
        // reload adapter with view model's cached movie data
//        if(mAdapter != null) {
//            mAdapter.updateAdapter(getViewModel().getmPhotosValue());
//        }
    }
}
