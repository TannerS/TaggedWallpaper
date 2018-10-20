package io.tanners.taggedwallpaper.fragments.image.order;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.dev.tanners.wallpaperresources.ImageRequester;
import io.dev.tanners.wallpaperresources.config.ConfigPhotosAll;
//import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.adapters.image.order.ImageOrderAdapter;
import io.tanners.taggedwallpaper.fragments.image.ImagesFragment;
import io.tanners.taggedwallpaper.fragments.image.ImagesHelperFragment;
import io.tanners.taggedwallpaper.support.network.NetworkUtil;
import io.tanners.taggedwallpaper.viewmodels.ImageViewModel;
import io.tanners.taggedwallpaper.viewmodels.order.OrderImageViewModel;

public abstract class ImagesOrderFragment extends ImagesHelperFragment
{
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set view model to update adapter on data changes
        // runnable to https://stackoverflow.com/questions/39445330/cannot-call-notifyiteminserted-from-recyclerview-onscrolllistener
        loadViewModelListener(photos -> {
            mRecyclerView.post(() -> mAdapter.updateAdapter(photos));
        });
        // call proper requested functionality
        loadEntryPointData();
    }

    protected abstract void loadEntryPointData();

    protected void loadImageDataByType(ConfigPhotosAll.Order mOrder) {
        loading = true;
        mRequester.getPhotos(String.valueOf(((OrderImageViewModel)getViewModel()).getAllImagePageCount()), "5", mOrder, mData -> {
            // check for response data
            if(mData == null)
                return;
            // get view model
            OrderImageViewModel mViewModel = (OrderImageViewModel) getViewModel();
            mViewModel.addData(mData);
            // increment for next call
            mViewModel.incrementImagePage();
            // since the data is in a background thread, you need to restore the state in that thread
            // this was mentioned in here: https://stackoverflow.com/questions/27816217/how-to-save-recyclerviews-scroll-position-using-recyclerview-state
//            mMovieRecyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerviewLayoutSavedState);
            mProgressBar.setVisibility(View.GONE);

            loading = false;
        });
    }

    protected void loadAdapter() {
        mAdapter = new ImageOrderAdapter(mContext);
    }
}