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
import io.tanners.taggedwallpaper.support.network.NetworkUtil;
import io.tanners.taggedwallpaper.viewmodels.order.OrderViewModel;

public abstract class ImagesOrderFragment extends ImagesFragment
{
    protected ImageRequester mRequester;
    protected ImageOrderAdapter mAdapter;
    private static final String NEW_INSTANCE_ARG_SINGLE = "NEW_INSTANCE_ARG_SINGLE";
    private String mtag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadImageRequester();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set listener for list
        // TODO turn into callback for for base class later to not recreate
        loadRecyclerView(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (loading) {
                    return;
                }
                int mVisibleCount = mRecyclerViewLayoutManager.getChildCount();
                int mTotalCount = mRecyclerViewLayoutManager.getItemCount();
                int mPastCount = mRecyclerViewLayoutManager.findFirstVisibleItemPosition();
                // if at bottom of list, and there is not an already network call updating the adatper,
                // and all those results are updated, update the list with next set of results
                if ((mPastCount + mVisibleCount >= mTotalCount) && !loading) {
                    if(NetworkUtil.isNetworkAvailable(mContext)) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        loading = true;
                        // call proper requested functionality
                        loadEntryPointData();
                    }
                }
            }
        });
        // set view model to update adapter on data changes
        // runnable to https://stackoverflow.com/questions/39445330/cannot-call-notifyiteminserted-from-recyclerview-onscrolllistener
        loadViewModelListener(photos -> {
            mRecyclerView.post(() -> mAdapter.updateAdapter(photos));
        });
        loading = true;
        // call proper requested functionality
        loadEntryPointData();
    }

    protected abstract void loadEntryPointData();

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
        mRequester.getPhotos(String.valueOf(getViewModel().getAllImagePageCount()), "5", mOrder, mData -> {
            // check for response data
            if(mData == null)
                return;
            // get view model
            OrderViewModel mViewModel = getViewModel();

            mViewModel.addData(mData);

            // get photos from view model
//            ArrayList<Photo> mCurrentPhotos = mViewModel.getmPhotosValue();
            // check for start when app opens
//            if(mCurrentPhotos == null) {
//                mCurrentPhotos = new ArrayList<Photo>();
//                // update it with new images
//                mCurrentPhotos.addAll(mData);
//                // set the new data back into the view model
//                // TODO since this is updated and reference, we may not need to re set it
//                mViewModel.setmPhotosValue(mCurrentPhotos);
//
//            } else {
//                mViewModel.getmPhotos().setValue(mViewModel.getmPhotosValue().addAll(mData));
//            }

//            for(Photo photo : mData)
//                Log.i("DATA", photo.getId());





//            mViewModel.getmPhotosValue().clear();



            // increment for next call
            mViewModel.incrementImagePage();
            // since the data is in a background thread, you need to restore the state in that thread
            // this was mentioned in here: https://stackoverflow.com/questions/27816217/how-to-save-recyclerviews-scroll-position-using-recyclerview-state
//            mMovieRecyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerviewLayoutSavedState);
            mProgressBar.setVisibility(View.GONE);


            loading = false;

        });
    }
}