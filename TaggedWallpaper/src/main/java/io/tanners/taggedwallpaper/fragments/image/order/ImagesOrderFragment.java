package io.tanners.taggedwallpaper.fragments.image.order;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.dev.tanners.wallpaperresources.ImageRequester;
import io.dev.tanners.wallpaperresources.config.ConfigPhotosAll;
import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;
import io.tanners.taggedwallpaper.fragments.image.ImagesFragment;
import io.tanners.taggedwallpaper.viewmodels.order.OrderViewModel;

public class ImagesOrderFragment extends ImagesFragment
{
    protected ImageRequester mRequester;
    protected OrderViewModel mViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        loadImageRequester();
        return view;
    }

    protected void loadImageRequester() {
        mRequester = new ImageRequester(getContext());
    }

    protected void loadViewModelListener(Observer<Photos> mObserver)
    {
        // load view model
        mViewModel = loadViewModel();
        // set observer
        mViewModel.getmPhotos().observe(this, mObserver);
    }

    protected OrderViewModel loadViewModel() {
        return ViewModelProviders.of(this).get(OrderViewModel.class);
    }

    /**
     * @param view
     */
    protected void loadRecyclerView(View view, RecyclerView.OnScrollListener mListener)
    {
        super.loadRecyclerView(view);
//         depending on the version of the OS, add listener to the recycler view
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            mRecyclerView.addOnScrollListener(mListener);
        }
        else
        {
            mRecyclerView.setOnScrollListener(mListener);
        }
    }

    protected void loadImageDataBYType(ConfigPhotosAll.Order mOrder) {
        mRequester.getPhotos(String.valueOf(loadViewModel().getCurrentRestCallPage()), "20", mOrder, mData -> {
            // get view model
            OrderViewModel mViewModel = loadViewModel();
            // get photos from view model
            Photos mCurrentPhotos = mViewModel.getmPhotosData();
            // update it with new images
            mCurrentPhotos.addAll(mData);
            // set the new data back into the view model
            // TODO since this is updated and reference, we may not need to re set it
            mViewModel.setmPhotosData(mCurrentPhotos);
            // increment for next call
            mViewModel.incrementPage();
        });
    }
}