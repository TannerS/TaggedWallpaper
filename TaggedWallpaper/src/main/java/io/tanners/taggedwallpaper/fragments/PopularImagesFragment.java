package io.tanners.taggedwallpaper.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.ApiBuilder;
import io.tanners.taggedwallpaper.Util.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;

public class PopularImagesFragment extends ImageFragment implements ErrorCallBack  {
    public static final String POPULAR = "Popular";
    // creates new instance
    public static PopularImagesFragment newInstance() {
        return new PopularImagesFragment();
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.mBuilder = new ApiBuilder(this.tag, mPerPage, mPage, ApiBuilder.OrderBy.POPULAR);
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
        return view;
    }
}
