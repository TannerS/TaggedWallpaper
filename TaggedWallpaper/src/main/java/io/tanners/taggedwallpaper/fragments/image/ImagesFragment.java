package io.tanners.taggedwallpaper.fragments.image;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
//import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;
import java.util.ArrayList;

import io.dev.tanners.wallpaperresources.ImageRequester;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.adapters.image.order.ImageOrderAdapter;
import io.tanners.taggedwallpaper.support.builder.snackbar.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.adapters.image.ImageAdapter;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;
import io.tanners.taggedwallpaper.support.network.NetworkUtil;
import io.tanners.taggedwallpaper.viewmodels.ImageViewModel;
import io.tanners.taggedwallpaper.viewmodels.order.OrderImageViewModel;

public abstract class ImagesFragment extends Fragment implements ErrorCallBack
{
    protected View view;
    protected RecyclerView mRecyclerView;
    protected ProgressBar mProgressBar;
//    protected ImageAdapter mAdapter;
    protected GridLayoutManager mRecyclerViewLayoutManager;
    // TODO fix this somehow
    // kinda of a hack but we need this in multiple areas for endless scrolling
    // this should be improved but for now..
//    protected static boolean loading;
    protected boolean loading;
    protected Context mContext;
    protected ImageRequester mRequester;
    protected ImageOrderAdapter mAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set view model to update adapter on data changes
        // runnable to https://stackoverflow.com/questions/39445330/cannot-call-notifyiteminserted-from-recyclerview-onscrolllistener
        loadViewModelListener(photos -> {
            mRecyclerView.post(() -> mAdapter.updateAdapter(photos));
        });
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadImageRequester();
    }

    protected abstract void onScroll();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_images, container, false);
        // return view
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    protected abstract void loadViewModelListener(Observer<ArrayList<Photo>> mObserver);

    protected void loadImageRequester() {
        mRequester = new ImageRequester(getContext());
    }

    protected void loadAdapter() {
        mAdapter = new ImageOrderAdapter(mContext);
    }

    protected abstract ImageViewModel getViewModel();

    /**
     *
     */
    protected void loadRecyclerView(RecyclerView.OnScrollListener mListener)
    {
        loadRecyclerView();
        // load adapter
        loadAdapter();
        // attach adapter to lsit
        mRecyclerView.setAdapter(mAdapter);
        // depending on the version of the OS, add listener to the recycler view
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            mRecyclerView.addOnScrollListener(mListener);
        } else {
            mRecyclerView.setOnScrollListener(mListener);
        }
    }

    /**
     *
     */
    protected void loadRecyclerView()
    {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.universal_grideview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.universal_progressbar);
        mRecyclerViewLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerViewLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerViewLayoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);
    }

    @Override
    public void displayError(String mMessage) {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(
                view.findViewById(R.id.fragment_images_container),
                "Error: " + mMessage,
                Snackbar.LENGTH_INDEFINITE,
                "Close");
    }

    @Override
    public void displayNoError(String mMessage) {
        // no need for implementation
    }
}
