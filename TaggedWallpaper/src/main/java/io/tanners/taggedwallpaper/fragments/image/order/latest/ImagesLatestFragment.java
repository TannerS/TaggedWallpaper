package io.tanners.taggedwallpaper.fragments.image.order.latest;

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
import io.tanners.taggedwallpaper.viewmodels.order.latest.LatestOrderImageViewModel;

public class ImagesLatestFragment extends ImagesOrderFragment {
    // fragment title
    public static final String LATEST = "Latest";

    // creates new instance
    public static ImagesLatestFragment newInstance() {
        return new ImagesLatestFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set view model to update adapter on data changes
        // runnable to https://stackoverflow.com/questions/39445330/cannot-call-notifyiteminserted-from-recyclerview-onscrolllistener
        loadViewModelListener(photos -> {
            mRecyclerView.post(() -> mAdapter.updateAdapter(photos));
        });
    }

    @Override
    protected void loadViewModelListener(Observer<ArrayList<Photo>> mObserver) {
        getViewModel().getmPhotos().observe(this, mObserver);
    }

    // TODO maybe put this method in base class, and call in data?
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
