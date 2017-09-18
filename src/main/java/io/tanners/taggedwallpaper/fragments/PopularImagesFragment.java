package io.tanners.taggedwallpaper.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.network.images.ImageRequester;
import io.tanners.taggedwallpaper.network.images.Request;

public class PopularImagesFragment extends ImageFragment {
    public static final String POPULAR = "Popular";
    private final String mUrl = "https://api.unsplash.com/photos?per_page=" + PERPAGE + "&page=" + PAGE + "&order_by=popular";

    public static PopularImagesFragment newInstance() {
        return new PopularImagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_popular, container, false);

        loadResources(view);

        new ImageRequester(getContext(), mAdapter, mPopularGridview, Request.Requested.SEARCH, R.layout.grid_item, R.id.grid_image_background).execute(mUrl);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
