package io.tanners.taggedwallpaper.fragments.image.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import io.dev.tanners.wallpaperresources.config.ConfigPhotosAll;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.adapters.image.order.ImageOrderAdapter;
import io.tanners.taggedwallpaper.fragments.image.ImagesHelperFragment;
import io.tanners.taggedwallpaper.viewmodels.order.OrderImageViewModel;

public abstract class ImagesOrderFragment extends ImagesHelperFragment
{
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set view model to update adapter on data changes
        // runnable to https://stackoverflow.com/questions/39445330/cannot-call-notifyiteminserted-from-recyclerview-onscrolllistener
        loadViewModelListener(photos -> {
            mRecyclerView.post(() -> mAdapter.updateAdapter(new ArrayList<Photo>(photos)));
        });
        // call proper requested functionality
        loadEntryPointData();
        // restore cycle state (if needed)
        restoreListState();
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

            loading = false;
        });
    }

    protected void loadAdapter() {
        mAdapter = new ImageOrderAdapter(mContext);
    }
}