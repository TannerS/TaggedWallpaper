package io.tanners.taggedwallpaper.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.ApiBuilder;

public class LatestImagesFragment extends ImageFragment {

    public static final String NEWEST = "Newest";
//    private final String mUrl = "https://api.unsplash.com/photos?per_page=" + PERPAGE + "&page=" + PAGE + "&order_by=newest";

    public static LatestImagesFragment newInstance() {
        return new LatestImagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_images, container, false);

        loadResources(view);

        loadRequest(new ApiBuilder(this.tag, 250, 1, ApiBuilder.OrderBy.LATEST));

        return view;
    }
}
