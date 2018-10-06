package io.tanners.taggedwallpaper.fragments.image;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
//import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.adapters.image.order.ImageOrderAdapter;
import io.tanners.taggedwallpaper.support.builder.snackbar.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.adapters.image.ImageAdapter;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;

public class ImagesFragment extends Fragment implements ErrorCallBack
{
    protected View view;
    protected RecyclerView mRecyclerView;
    protected ProgressBar mProgressBar;
//    protected ImageAdapter mAdapter;
    protected GridLayoutManager mRecyclerViewLayoutManager;
    // TODO fix this somehow
    // kinda of a hack but we need this in multiple areas for endless scrolling
    protected static boolean loading;
    protected Context mContext;

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