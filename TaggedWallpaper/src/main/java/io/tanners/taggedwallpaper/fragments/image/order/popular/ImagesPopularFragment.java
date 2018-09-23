package io.tanners.taggedwallpaper.fragments.image.order.popular;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.dev.tanners.wallpaperresources.config.ConfigPhotosAll;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.dev.tanners.wallpaperresources.models.photos.photos.Photos;
import io.dev.tanners.wallpaperresources.network.ImageLoader;
import io.tanners.taggedwallpaper.fragments.image.order.ImagesOrderFragment;

public class ImagesPopularFragment extends ImagesOrderFragment {
    // fragment title
    public static final String POPULAR = "Popular";
    // creates new instance
    public static ImagesPopularFragment newInstance() {
        return new ImagesPopularFragment();
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        // load image lib to load popular images
        mRequester.getPhotos("1", "20", ConfigPhotosAll.Order.POPULAR, new ImageLoader.ImageRequest<Photos>() {
            @Override
            public void onResultsPost(ArrayList<Photos> mData) {

            }
        });




        // return view
        return view;
    }
}
