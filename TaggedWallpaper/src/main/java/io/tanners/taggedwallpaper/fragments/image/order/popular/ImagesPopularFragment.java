package io.tanners.taggedwallpaper.fragments.image.order.popular;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.dev.tanners.wallpaperresources.config.ConfigPhotosAll;
import io.tanners.taggedwallpaper.fragments.image.order.ImagesOrderFragment;
import io.tanners.taggedwallpaper.support.network.NetworkUtil;
import io.tanners.taggedwallpaper.viewmodels.order.OrderViewModel;
import io.tanners.taggedwallpaper.viewmodels.order.popular.PopularOrderViewModel;

public class ImagesPopularFragment extends ImagesOrderFragment {
    // fragment title
    public static final String POPULAR = "Popular";
    // creates new instance
    public static ImagesPopularFragment newInstance() {
        return new ImagesPopularFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        view = super.onCreateView(inflater, container, savedInstanceState);
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

                        // call api for images
                        loadImageDataByType(ConfigPhotosAll.Order.POPULAR);
                    }
                }
            }
        });
        // set view model to update adapter on data changes
        // runnable to https://stackoverflow.com/questions/39445330/cannot-call-notifyiteminserted-from-recyclerview-onscrolllistener
        loadViewModelListener(photos -> {
            mRecyclerView.post(() -> mAdapter.updateAdapter(photos));
//            mAdapter.updateAdapter(photos);
        });
        loading = true;
        // load init data
        loadImageDataByType(ConfigPhotosAll.Order.POPULAR);
        // return view
        return view;
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
