package io.tanners.taggedwallpaper.fragments.image.order;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import io.tanners.taggedwallpaper.viewmodels.order.OrderViewModel;

public class ImagesOrderFragment extends ImagesFragment
{
    protected ImageRequester mRequester;
    protected ImageOrderAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadImageRequester();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    protected void loadAdapter() {
        mAdapter = new ImageOrderAdapter(mContext);
    }

    protected void loadImageRequester() {
        mRequester = new ImageRequester(getContext());
    }

//    protected void loadViewModelListener(Observer<Photos> mObserver)
    protected void loadViewModelListener(Observer<ArrayList<Photo>> mObserver)
    {
        // load view model
//        mViewModel = getViewModel();
        // set observer
//        mViewModel.getmPhotos().observe(this, mObserver);
        getViewModel().getmPhotos().observe(this, mObserver);
    }

    protected OrderViewModel getViewModel() {
        return ViewModelProviders.of(this).get(OrderViewModel.class);
    }

    /**
     *
     */
    protected void loadRecyclerView(RecyclerView.OnScrollListener mListener)
    {
        super.loadRecyclerView();
        // load adapter
        loadAdapter();
        // attach adapter to lsit
        mRecyclerView.setAdapter(mAdapter);
        // depending on the version of the OS, add listener to the recycler view
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            mRecyclerView.addOnScrollListener(mListener);
        } else {
            mRecyclerView.setOnScrollListener(mListener);
        }
    }

    protected void loadImageDataByType(ConfigPhotosAll.Order mOrder) {

        mRequester.getPhotos(String.valueOf(getViewModel().getCurrentRestCallPage()), "5", mOrder, mData -> {


            Log.i("DATAINCOMING", "OF TYPE: " + mOrder.order()+ " " + String.valueOf(mData.size()));

            // check for response data
            if(mData == null)
                return;
            // get view model
            OrderViewModel mViewModel = getViewModel();
            // get photos from view model
//            Photos mCurrentPhotos = mViewModel.getmPhotosValue();
            ArrayList<Photo> mCurrentPhotos = mViewModel.getmPhotosValue();




            // check for start when app opens
            if(mCurrentPhotos == null) {
                mCurrentPhotos = new ArrayList<Photo>();
//                mCurrentPhotos = new Photos();
            }


            // update it with new images
            mCurrentPhotos.addAll(mData);
            // set the new data back into the view model
            // TODO since this is updated and reference, we may not need to re set it
            mViewModel.setmPhotosValue(mCurrentPhotos);
            // increment for next call
            mViewModel.incrementPage();
            // since the data is in a background thread, you need to restore the state in that thread
            // this was mentioned in here: https://stackoverflow.com/questions/27816217/how-to-save-recyclerviews-scroll-position-using-recyclerview-state
//            mMovieRecyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerviewLayoutSavedState);
        });
    }
}