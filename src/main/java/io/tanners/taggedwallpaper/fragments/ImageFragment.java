package io.tanners.taggedwallpaper.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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
    protected ImagesAdapter mAdapter;
    protected String tag;
    private boolean loading;
    private GridLayoutManager mRecyclerViewLayoutManager;
    protected ApiBuilder mBuilder;

    /**
     * we shall get the tag passed into the activity here
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    Log.i("LOADING", "LOADING");
                    return;
                }

                Log.i("LOADING", "NOT LOADING");

                int mVisibleCount = mRecyclerViewLayoutManager.getChildCount();
                int mTotalCount = mRecyclerViewLayoutManager.getItemCount();
                int mPastCount = mRecyclerViewLayoutManager.findFirstVisibleItemPosition();

                if ((mPastCount + mVisibleCount >= mTotalCount) && !loading) {
                    mBuilder.increasePage();
                    new ImageRequester().execute();
//                    ((ImagesAdapter) mRecyclerView.getAdapter()).updateAdapter();
                }
            }
        };
    }

//    https://gist.github.com/yqritc/ccca77dc42f2364777e1
//    private class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
//
//        private int mItemOffset;
//
//        public ItemOffsetDecoration(int itemOffset) {
//            mItemOffset = itemOffset;
//        }
//
//        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
//            this(context.getResources().getDimensionPixelSize(itemOffsetId));
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
//                                   RecyclerView.State state) {
//            super.getItemOffsets(outRect, view, parent, state);
//            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
//        }
//    }

    /**
     * @param view
     */
//    protected void loadRecyclerView(View view)
    protected void loadRecyclerView(View view)
    {
        Log.i("UPDATE2", "DEBUG 3");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.universal_grideview);
        int cols = 2;
        mRecyclerViewLayoutManager = new GridLayoutManager(getContext(), cols);

        // https://stackoverflow.com/questions/42183858/android-recyclerview-large-gap-space-after-some-rows-of-items-on-tablet-landscap
        mRecyclerViewLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerViewLayoutManager.setAutoMeasureEnabled(false);
        mRecyclerViewLayoutManager.setSpanCount(cols);
//        https://stackoverflow.com/questions/35817610/large-gap-forms-between-recyclerview-items-when-scrolling-down

        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);

//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.list_padding);
//        mRecyclerView.addItemDecoration(itemDecoration);



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            mRecyclerView.addOnScrollListener(getListener());
            Log.i("UPDATE2", "DEBUG 4");
        }
        else
        {
            mRecyclerView.setOnScrollListener(getListener());
        }

        new ImageRequester().execute();

    }

    private class ImageRequester extends AsyncTask<Void, Void, List<PhotoResult>> {
        private Request<PhotoResult > mRequest;

        public ImageRequester()
//        public ImageRequester(Request<PhotoResult> mRequest)
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
//                if(mAdapter == null)
                if(mAdapter == null || mRecyclerView.getAdapter() == null)
                {
//                    mAdapter = new ImagesAdapter(getContext(), new ArrayList<PhotoResult>(photos),  mGridRowLayoutId, mGridImageViewId, mProgressBarId);
                    mAdapter = new ImagesAdapter(getContext(), new ArrayList<PhotoResult>(photos),  R.layout.grid_item, R.id.grid_image_background, R.id.grid_progressbar);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                {
                    ((ImagesAdapter) mRecyclerView.getAdapter()).updateAdapter((ArrayList<PhotoResult>) photos);
                }
            }
            else
            {
                SimpleSnackBarBuilder.createAndDisplaySnackBar(view.findViewById(R.id.fragment_images_container_id),
                        "Error loading images",
                        Snackbar.LENGTH_INDEFINITE,
                        "Close");
            }
            
            loading = false;
        }
    }
}
