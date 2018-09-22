package io.tanners.taggedwallpaper.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;

public class ImagesLatestFragment extends ImagesBaseFragment implements ErrorCallBack {
    public static final String LATEST = "Latest";
    // creates new instance
    public static ImagesLatestFragment newInstance() {
        return new ImagesLatestFragment();
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
