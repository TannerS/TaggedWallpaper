package io.tanners.taggedwallpaper.fragments;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.ApiBuilder;
import io.tanners.taggedwallpaper.Util.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.adapters.ImagesAdapter;
import io.tanners.taggedwallpaper.data.results.photo.PhotoResult;
import io.tanners.taggedwallpaper.interfaces.IGetTag;
import io.tanners.taggedwallpaper.network.images.ImageRequest;
import io.tanners.taggedwallpaper.network.images.Request;

public class ImageFragment extends Fragment {
    protected View view;
    protected RecyclerView mRecyclerView;
    protected ProgressBar mProgressBar;
    protected ImagesAdapter mAdapter;
    protected String tag;
    private boolean loading;
    private LinearLayoutManager mRecyclerViewLayoutManager;
//    private GridLayoutManager mRecyclerViewLayoutManager;
    protected ApiBuilder mBuilder;
    protected int mPerPage;
    protected int mPage;

    /**
     * we shall get the tag passed into the activity here
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set page info
        mPerPage = 20;
        mPage = 1;
        // check if current activity loading has the igettag interface implemented
        // this is due in part that main activity can load the popular and latest fragments
        // but do not need tags so no need ot implment it, but imageactivity needs it since
        // it does take in tags so it does implement it
        if (getActivity() instanceof IGetTag) {
            // get tag from activity
            this.tag = ((IGetTag)getActivity()).getTag();
        } else {
           // throw new RuntimeException(getActivity().toString() + " must implement IGetTag");
            // nothing to do
        }

        // used to not load more of the images until last request is done
        loading = false;

    }

    private RecyclerView.OnScrollListener getListener() {
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
                    mBuilder.increasePage();
                    new ImageRequester().execute();
                    Log.i("REQUEST", "DEBUG 1");
                    mProgressBar.setVisibility(View.VISIBLE);
//                    ((ImagesAdapter) mRecyclerView.getAdapter()).updateAdapter();
                }
            }
        };
    }

    /**
     * @param view
     */
//    protected void loadRecyclerView(View view)
    protected void loadRecyclerView(View view)
    {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.universal_grideview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.universal_progressbar);
         mRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        // https://stackoverflow.com/questions/42183858/android-recyclerview-large-gap-space-after-some-rows-of-items-on-tablet-landscap
        mRecyclerViewLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerViewLayoutManager.setAutoMeasureEnabled(false);
//        https://stackoverflow.com/questions/35817610/large-gap-forms-between-recyclerview-items-when-scrolling-down
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);
        // depending on the version of the OS, add listener to the recycler view
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            mRecyclerView.addOnScrollListener(getListener());
        }
        else
        {
            mRecyclerView.setOnScrollListener(getListener());
        }
        // request image
        new ImageRequester().execute();
    }

    private class ImageRequester extends AsyncTask<Void, Void, List<PhotoResult>> {
        private Request<PhotoResult > mRequest;

        public ImageRequester()
        {
            this.mRequest = new ImageRequest();
        }

        @Override
        protected void onPreExecute() {
            loading = true;
        }

        /**
         * Parameter at index 0 is restful api url
         * @param params
         * @return
         */
        @Override
        protected List<PhotoResult> doInBackground(Void... params)
        {
            List<PhotoResult> photos = null;

            try {
                // builder api url and request images
                photos = mRequest.getResult(mBuilder.getHeaders(), mBuilder.buildUrl(), null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return photos;
        }

        @Override
        protected void onPostExecute(List<PhotoResult> photos) {
            if(photos != null)
            {
                // if adapter has no images
                if(mAdapter == null || mRecyclerView.getAdapter() == null)
                {
                    // create new object with photo data
                    mAdapter = new ImagesAdapter(getContext(), new ArrayList<PhotoResult>(photos),  R.layout.grid_item, R.id.image_background, R.id.image_text);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                {
                    // update adapter
                    mAdapter.updateAdapter((ArrayList<PhotoResult>) photos);
                }
            }
            else
            {
                // display error snackbar
                SimpleSnackBarBuilder.createAndDisplaySnackBar(view.findViewById(R.id.fragment_images_container_id),
                        "Error loading images",
                        Snackbar.LENGTH_INDEFINITE,
                        "Close");
            }
            // let system know images are no longer being loaded
            loading = false;
            // hide progressbar
            mProgressBar.setVisibility(View.GONE);
        }
    }

    /**
     * all credit too ianhanniballake
     * https://stackoverflow.com/questions/28531996/android-recyclerview-gridlayoutmanager-column-spacing
     */
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }
}
