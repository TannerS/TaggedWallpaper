package io.tanners.taggedwallpaper.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;

public class ImagesPopularFragment extends ImagesBaseFragment implements ErrorCallBack  {
    public static final String POPULAR = "Popular";
    // creates new instance
    public static ImagesPopularFragment newInstance() {
        return new ImagesPopularFragment();
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
