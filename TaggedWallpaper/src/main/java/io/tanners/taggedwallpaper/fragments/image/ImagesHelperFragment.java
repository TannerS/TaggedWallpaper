package io.tanners.taggedwallpaper.fragments.image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.support.network.NetworkUtil;

public abstract class ImagesHelperFragment extends ImagesFragment<Photo>
{
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set listener for list
        loadRecyclerView(loadListener());
    }

    protected abstract void onScroll();

    /**
     * We may not need this, so a method the child can override works great
     * @return
     */
    protected RecyclerView.OnScrollListener loadListener()
    {
        return new RecyclerView.OnScrollListener() {
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
                        loading = true;
                        // call proper requested functionality
                        onScroll();
                    }
                }
            }
        };
    }

    /**
     *
     */
    protected void loadRecyclerView(RecyclerView.OnScrollListener mListener)
    {
        super.loadRecycler();
        // attach adapter to list
        mRecyclerView.setAdapter(mAdapter);

        if(mListener != null) {
            // depending on the version of the OS, add listener to the recycler view
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                mRecyclerView.addOnScrollListener(mListener);
            } else {
                mRecyclerView.setOnScrollListener(mListener);
            }
        }
    }
}