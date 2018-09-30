package io.tanners.taggedwallpaper.fragments.image.order.popular;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.dev.tanners.wallpaperresources.config.ConfigPhotosAll;
import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;
import io.tanners.taggedwallpaper.fragments.image.order.ImagesOrderFragment;
import io.tanners.taggedwallpaper.support.network.NetworkUtil;
import io.tanners.taggedwallpaper.viewmodels.order.OrderViewModel;

public class ImagesPopularFragment extends ImagesOrderFragment {
    // fragment title
    public static final String POPULAR = "Popular";
    // creates new instance
    public static ImagesPopularFragment newInstance() {
        return new ImagesPopularFragment();
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
        // set view model to update adapter on data changes
        loadViewModelListener(photos -> mAdapter.updateAdapter(photos));
        // set listener for list
        // TODO turn into callback for for base class later to not recreate
        loadRecyclerView(view, new RecyclerView.OnScrollListener() {
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
                            // call api for images
                            loadImageDataBYType(ConfigPhotosAll.Order.POPULAR);
                        }
                    }
                }
            });
        // load init data
        loadImageDataBYType(ConfigPhotosAll.Order.POPULAR);
        // return view
        return view;
    }
}
