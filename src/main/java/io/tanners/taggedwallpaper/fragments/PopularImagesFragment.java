package io.tanners.taggedwallpaper.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.ApiBuilder;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;
import io.tanners.taggedwallpaper.interfaces.IGetTag;
import io.tanners.taggedwallpaper.network.images.ImageRequest;
import io.tanners.taggedwallpaper.network.images.ImageRequester;
import io.tanners.taggedwallpaper.network.images.Request;

public class PopularImagesFragment extends ImageFragment implements ErrorCallBack  {
    public static final String POPULAR = "Popular";

    public static PopularImagesFragment newInstance() {
        return new PopularImagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_images, container, false);

        loadResources(view);

        loadRequest(this, new ApiBuilder(this.tag, 100, 1, ApiBuilder.OrderBy.POPULAR));

        return view;
    }

    @Override
    public void displayError() {
        final Snackbar mErrorSnackBar = Snackbar.make(view.findViewById(R.id.fragment_images_container_id), "Error loading images", Snackbar.LENGTH_INDEFINITE);

        mErrorSnackBar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mErrorSnackBar.dismiss();
            }
        });

        mErrorSnackBar.show();
    }

}
