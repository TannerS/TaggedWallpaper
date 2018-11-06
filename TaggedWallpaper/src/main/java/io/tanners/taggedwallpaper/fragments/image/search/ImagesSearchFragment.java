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
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.adapters.image.order.ImageOrderAdapter;
import io.tanners.taggedwallpaper.fragments.image.ImagesHelperFragment;
import io.tanners.taggedwallpaper.viewmodels.search.SearchImageViewModel;

public class ImagesSearchFragment extends ImagesHelperFragment {
    public static final String FRAGMENT_TAG = "IMAGE_SEARCH_FRAGMENT";
    public final static String TAG = "SEARCH_QUERY";
    protected String mQuery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // https://stackoverflow.com/a/32888963
        setRetainInstance(true);
        // check for arguments
        if (getArguments() != null) {
            mQuery = getArguments().getString(TAG);
        } else {
            // no query, so end it
            getActivity().finish();
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
        // load data
        loadDataBasedOnPreviousState();
        // restore state (if needed)
        restoreListState();
    }

    @Override
    protected void onScroll() {
        loadData();
    }

    protected void loadData() {
        loading = true;

        mRequester.getSearchPhoto(mQuery, String.valueOf(getViewModel().getSearchImagePageCount()), "5", mData -> {
            // check for response data
            if(mData == null)
                return;
            // get view model
            SearchImageViewModel mViewModel = getViewModel();
            // set data into view model
            mViewModel.addData(mData.getResults());
            // increase for next page
            getViewModel().incrementImageSearchPage();
            // no longer loading new data
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

    @Override
    protected void loadDataBasedOnPreviousState() {
        // get data from view model
        List<Photo> mPhotos = getViewModel().getmItems().getValue();
        // if viewmdoel has data
        if(mPhotos != null && mPhotos.size() > 0) {
            // load it into adapter
            mAdapter.updateAdapter(new ArrayList<Photo>(mPhotos));
        } else {
            // load init data
            loadData();
        }
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadViewModelListener(Observer<List<Photo>> mObserver) {
        getViewModel().getmItems().observe(this, mObserver);
    }

    protected void loadAdapter() {
        // this will work fine for searching
        mAdapter = new ImageOrderAdapter(mContext);
    }
}
