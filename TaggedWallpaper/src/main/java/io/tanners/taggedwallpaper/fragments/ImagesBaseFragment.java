package io.tanners.taggedwallpaper.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;

public class ImagesBaseFragment extends Fragment implements ErrorCallBack
{
//public class ImagesBaseFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<PhotoResult>> {
    protected View view;
    protected RecyclerView mRecyclerView;
    protected ProgressBar mProgressBar;
//    protected ResultImageAdapter mAdapter;
//    protected String tag;
//    protected boolean loading;
    protected LinearLayoutManager mRecyclerViewLayoutManager;
//    protected ApiBuilder mBuilder;
//    protected int mPerPage;
//    protected int mPage;
//    protected final int IMAGE_SEARCH_LOADER = 4;

    /**
     * we shall get the tag passed into the activity here
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // set page info
//        mPerPage = 20;
//        mPage = 1;





//        // check if current activity loading has the igettag interface implemented
//        // this is due in part that main activity can load the popular and latest fragments
//        // but do not need tags so no need to implement it, but imageactivity needs it since
//        // it does take in tags so it does implement it
//        if (getActivity() instanceof IGetTag) {
//            // get tag from activity
//            this.tag = ((IGetTag)getActivity()).getTag();
//        } else {
//           throw new RuntimeException(getActivity().toString() + " must implement IGetTag");
//            // nothing to do
//        }

    }

//    private RecyclerView.OnScrollListener getListener() {
//        return new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (loading) {
//                    return;
//                }
//                int mVisibleCount = mRecyclerViewLayoutManager.getChildCount();
//                int mTotalCount = mRecyclerViewLayoutManager.getItemCount();
//                int mPastCount = mRecyclerViewLayoutManager.findFirstVisibleItemPosition();
//                // if at bottom of list, and there is not an already network call updating the adatper,
//                // and all those results are updated, update the list with next set of results
//                if ((mPastCount + mVisibleCount >= mTotalCount) && !loading) {
//                    mBuilder.increasePage();
//                    mProgressBar.setVisibility(View.VISIBLE);
//                    loadLoader();
//                }
//            }
//        };
//    }

    /**
     * @param view
     */
    protected void loadRecyclerView(View view)
    {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.universal_grideview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.universal_progressbar);
         mRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerViewLayoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);
        // depending on the version of the OS, add listener to the recycler view
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
//            mRecyclerView.addOnScrollListener(getListener());
//        }
//        else
//        {
//            mRecyclerView.setOnScrollListener(getListener());
//        }
    }

//    protected void loadLoader()
//    {
//        // bundle for loader, but not needed for this but can't be null
//        Bundle mBundle = new Bundle();
//
//        LoaderManager mLoaderManager = getLoaderManager();
//        Loader<List<PhotoResult>> mImageLoader = mLoaderManager.getLoader(IMAGE_SEARCH_LOADER);
//
//        loading = true;
//
//        if(mImageLoader != null) {
//            mLoaderManager.initLoader(IMAGE_SEARCH_LOADER, mBundle, this).forceLoad();
//        }
//        else
//        {
//            mLoaderManager.restartLoader(IMAGE_SEARCH_LOADER, mBundle, this).forceLoad();
//        }
//    }

//    @NonNull
//    @Override
//    public Loader<List<PhotoResult>> onCreateLoader(int id, @Nullable Bundle args) {
//        return new ImageRequester(getContext(), mBuilder);
//    }

//    @Override
//    public void onLoadFinished(@NonNull Loader<List<PhotoResult>> loader, List<PhotoResult> data) {
//        if(data != null)
//        {
//            // if adapter has no images
//            if(mAdapter == null || mRecyclerView.getAdapter() == null)
//            {
//                // create new object with photo data
//                mAdapter = new ResultImageAdapter(getContext(), new ArrayList<PhotoResult>(data),  R.layout.image_list_item, R.id.image_background);
//                mRecyclerView.setAdapter(mAdapter);
//            }
//            else
//            {
//                // update adapter
//                mAdapter.updateAdapter((ArrayList<PhotoResult>) data);
//            }
//        }
//        else
//        {
//            // display error snackbar
//            SimpleSnackBarBuilder.createAndDisplaySnackBar(view.findViewById(R.id.fragment_images_container),
//                    "Error loading images",
//                    Snackbar.LENGTH_INDEFINITE,
//                    "Close");
//        }
//        // let system know images are no longer being loaded
//        loading = false;
//        // hide progressbar
//        mProgressBar.setVisibility(View.GONE);
//    }

//    @Override
//    public void onLoaderReset(@NonNull Loader<List<PhotoResult>> loader) {
//        // not needed
//    }

//    private static class ImageRequester extends AsyncTaskLoader<List<PhotoResult> > {
//        private ImageRequest mRequest;
//        private ApiBuilder mBuilder;
//
//        public ImageRequester(Context mContext, ApiBuilder mBuilder)
//        {
//            super(mContext);
//            this.mBuilder = mBuilder;
//            this.mRequest = new ImageRequest();
//        }
//
//        @Nullable
//        @Override
//        public List<PhotoResult> loadInBackground() {
//            List<PhotoResult> photos = null;
//
//            try {
//                // builder api url and request images
//                photos = mRequest.getResult(mBuilder.getHeaders(), mBuilder.buildHighResImageUrl(), null);
//
//                Log.i("REST", mBuilder.buildHighResImageUrl());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return photos;
//        }
//
//        @Override
//        protected void onStartLoading() {
//            super.onStartLoading();
//        }
//    }

    @Override
    public void displayError() {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(
                view.findViewById(R.id.fragment_images_container),
                "Error loading images",
                Snackbar.LENGTH_INDEFINITE,
                "Close");
    }


    @Override
    public void displayNoError() {
        // no need for implementation
    }
}
