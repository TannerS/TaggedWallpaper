package io.tanners.taggedwallpaper.fragments.image.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.dev.tanners.wallpaperresources.callbacks.post.search.OnPostSearch;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.dev.tanners.wallpaperresources.models.photos.search.PhotoSearch;
import io.tanners.taggedwallpaper.adapters.image.order.ImageOrderAdapter;
import io.tanners.taggedwallpaper.fragments.image.ImagesHelperFragment;
import io.tanners.taggedwallpaper.viewmodels.search.SearchImageViewModel;

public class ImagesSearchFragment extends ImagesHelperFragment {
    public final static String TAG = "SEARCH_QUERY";
    protected String mQuery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuery = getArguments().getString(TAG);
        }
        // set view model to update adapter on data changes
        // runnable to https://stackoverflow.com/questions/39445330/cannot-call-notifyiteminserted-from-recyclerview-onscrolllistener
        loadViewModelListener(photos -> {
            mRecyclerView.post(() -> mAdapter.updateAdapter(new ArrayList<Photo>(photos)));
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // init load
        loadData();
    }

    @Override
    protected void onScroll() {
        loadData();
    }

    protected void loadData() {
        loading = true;

        mRequester.getSearchPhoto(mQuery, String.valueOf(getViewModel().getSearchImagePageCount()), "5", mData -> {
            // get view model
            SearchImageViewModel mViewModel = getViewModel();
            // set data into view model
            mViewModel.addData(mData.getResults());

            Log.i("TANNER_SEARCH", String.valueOf(getViewModel().getSearchImagePageCount()));
            Log.i("TANNER_SEARCH", mData.getResults().get(0).getId());

            getViewModel().incrementImageSearchPage();

            loading = false;
        });
    }

    // creates new instance
    public static ImagesSearchFragment newInstance(String mQuery) {
        ImagesSearchFragment mFragment = new ImagesSearchFragment();
        Bundle args = new Bundle();
        args.putString(TAG, mQuery);
        mFragment.setArguments(args);

        return mFragment;
    }

    @Override
    protected SearchImageViewModel getViewModel() {
        return ViewModelProviders.of(this).get(SearchImageViewModel.class);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadViewModelListener(Observer<List<Photo>> mObserver) {
        getViewModel().getmItems().observe(this, mObserver);
    }

    protected void loadAdapter() {
        mAdapter = new ImageOrderAdapter(mContext);
    }
}
