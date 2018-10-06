package io.tanners.taggedwallpaper.fragments.image.order.popular;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.dev.tanners.wallpaperresources.callbacks.post.search.OnPostSearch;
import io.dev.tanners.wallpaperresources.config.ConfigPhotosAll;
import io.dev.tanners.wallpaperresources.models.photos.search.PhotoSearch;
import io.tanners.taggedwallpaper.fragments.image.order.ImagesOrderFragment;
import io.tanners.taggedwallpaper.support.network.NetworkUtil;
import io.tanners.taggedwallpaper.viewmodels.order.OrderViewModel;
import io.tanners.taggedwallpaper.viewmodels.order.popular.PopularOrderViewModel;

public class ImagesPopularFragment extends ImagesOrderFragment {
    // fragment title
    public static final String POPULAR = "Popular";
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
    protected void loadEntryPointData() {
        if(mQuery != null && mQuery.length() > 0) {
            mRequester.getSearchPhoto(mQuery, String.valueOf(getViewModel().getSearchImagePageCount()), "5", new OnPostSearch() {
                @Override
                public void onPostCall(PhotoSearch mData) {
                    // get view model
                    OrderViewModel mViewModel = getViewModel();
                    // set data into view model
                    mViewModel.addData(mData.getResults());
                }
            });
        } else {
            // load init data
            loadImageDataByType(ConfigPhotosAll.Order.POPULAR);
        }
    }

    // creates new instance
    public static ImagesPopularFragment newInstance(String mQuery) {
        ImagesPopularFragment mFragment = new ImagesPopularFragment();
        Bundle args = new Bundle();
        args.putString(TAG, mQuery);
        return mFragment;
    }

    public static ImagesPopularFragment newInstance() {
        return new ImagesPopularFragment();
    }

    @Override
    protected PopularOrderViewModel getViewModel() {
        return ViewModelProviders.of(this).get(PopularOrderViewModel.class);
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