package io.tanners.taggedwallpaper.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.ApiBuilder;
import io.tanners.taggedwallpaper.Util.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;

public class LatestImagesFragment extends ImageFragment implements ErrorCallBack{
    public static final String LATEST = "Latest";
    // creates new instance
    public static LatestImagesFragment newInstance() {
        return new LatestImagesFragment();
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mBuilder = new ApiBuilder(this.tag, mPerPage, mPage, ApiBuilder.OrderBy.LATEST);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_images, container, false);
        loadRecyclerView(view);
        loadImageData();
        return view;
    }

    /**
     * callback for imagerequester
     */
    @Override
    public void displayError() {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(
                view.findViewById(R.id.fragment_images_container_id),
                "Error loading images",
                Snackbar.LENGTH_INDEFINITE,
                "Close");
    }
}
