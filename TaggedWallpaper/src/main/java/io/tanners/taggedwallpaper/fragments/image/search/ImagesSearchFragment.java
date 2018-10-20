package io.tanners.taggedwallpaper.fragments.image.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.dev.tanners.wallpaperresources.callbacks.post.search.OnPostSearch;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.dev.tanners.wallpaperresources.models.photos.search.PhotoSearch;
import io.tanners.taggedwallpaper.adapters.image.order.ImageOrderAdapter;
import io.tanners.taggedwallpaper.fragments.image.ImagesFragment;
import io.tanners.taggedwallpaper.fragments.image.ImagesHelperFragment;
import io.tanners.taggedwallpaper.fragments.image.order.ImagesOrderFragment;
import io.tanners.taggedwallpaper.viewmodels.order.OrderImageViewModel;
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
        mRequester.getSearchPhoto(mQuery, String.valueOf(getViewModel().getSearchImagePageCount()), "5", new OnPostSearch() {
            @Override
            public void onPostCall(PhotoSearch mData) {
                // get view model
                SearchImageViewModel mViewModel = getViewModel();
                // set data into view model
                mViewModel.addData(mData.getResults());

                getViewModel().incrementImageSearchPage();

                loading = false;
            }
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

    // todo use this in base class, it is used in multiple places
    @Override
    protected void loadViewModelListener(Observer<ArrayList<Photo>> mObserver) {
        getViewModel().getmPhotos().observe(this, mObserver);
    }

    protected void loadAdapter() {
        mAdapter = new ImageOrderAdapter(mContext);
    }
}
