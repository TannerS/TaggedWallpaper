package io.tanners.taggedwallpaper.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.ApiBuilder;
import io.tanners.taggedwallpaper.interfaces.IGetTag;
import io.tanners.taggedwallpaper.network.images.ImageRequester;
import io.tanners.taggedwallpaper.network.images.Request;

public class NewestImagesFragment extends ImageFragment {

    public static final String NEWEST = "Newest";
//    private final String mUrl = "https://api.unsplash.com/photos?per_page=" + PERPAGE + "&page=" + PAGE + "&order_by=newest";

    public static NewestImagesFragment newInstance() {
        return new NewestImagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_images, container, false);

        loadResources(view);

        loadRequest(new ApiBuilder(this.tag, 50, 1, ApiBuilder.OrderBy.NEWEST));

        return view;
    }
}
