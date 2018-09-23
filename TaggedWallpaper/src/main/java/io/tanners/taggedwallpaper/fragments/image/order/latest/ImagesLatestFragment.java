package io.tanners.taggedwallpaper.fragments.image.order.latest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.dev.tanners.wallpaperresources.config.ConfigPhotosAll;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.dev.tanners.wallpaperresources.network.ImageLoader;
import io.tanners.taggedwallpaper.fragments.image.order.ImagesOrderFragment;

public class ImagesLatestFragment extends ImagesOrderFragment {
    // fragment title
    public static final String LATEST = "Latest";
    // creates new instance
    public static ImagesLatestFragment newInstance() {
        return new ImagesLatestFragment();
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
        // load image lib to load latest images
        mRequester.getPhotos("1", "20", ConfigPhotosAll.Order.LATEST, new ImageLoader.ImageRequest<Photo>() {
            @Override
            public void onResultsPost(Photo mData) {

            }
        });
        // return view
        return view;
    }
}
