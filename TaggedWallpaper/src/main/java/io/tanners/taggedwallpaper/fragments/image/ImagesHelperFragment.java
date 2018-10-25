package io.tanners.taggedwallpaper.fragments.image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.tanners.taggedwallpaper.support.builder.snackbar.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.support.network.NetworkUtil;

public abstract class ImagesHelperFragment extends ImagesFragment
{
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
                        onScroll();
                    }
                }
            }
        });
    }

    protected abstract void onScroll();

    /**
     *
     */
    protected void loadRecyclerView(RecyclerView.OnScrollListener mListener)
    {
        super.loadRecycler();
        // attach adapter to list
        mRecyclerView.setAdapter(mAdapter);
        // depending on the version of the OS, add listener to the recycler view
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            mRecyclerView.addOnScrollListener(mListener);
        } else {
            mRecyclerView.setOnScrollListener(mListener);
        }
    }
}