package io.tanners.taggedwallpaper.fragments.image;

import android.arch.lifecycle.Observer;
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
import java.util.ArrayList;

import io.dev.tanners.snackbarbuilder.SimpleSnackBarBuilder;
import io.dev.tanners.wallpaperresources.ImageRequester;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.adapters.image.ImageAdapter;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;
import io.tanners.taggedwallpaper.viewmodels.ImageViewModel;

public abstract class ImagesFragment extends Fragment implements ErrorCallBack
{
    protected View view;
    protected RecyclerView mRecyclerView;
    protected ProgressBar mProgressBar;
    protected GridLayoutManager mRecyclerViewLayoutManager;
    protected boolean loading;
    protected Context mContext;
    protected ImageRequester mRequester;
    protected ImageAdapter mAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadRecycler();
        // set view model to update adapter on data changes
        // runnable to https://stackoverflow.com/questions/39445330/cannot-call-notifyiteminserted-from-recyclerview-onscrolllistener
        loadViewModelListener(photos -> {
            mRecyclerView.post(() -> mAdapter.updateAdapter(photos));
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadImageRequester();
    }

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

    protected abstract void loadAdapter();

    protected abstract ImageViewModel getViewModel();

    /**
     *
     */
    protected void loadRecycler()
    {
        loadRecyclerView();
        // load adapter
        loadAdapter();
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
