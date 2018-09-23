package io.tanners.taggedwallpaper.fragments.image.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.dev.tanners.wallpaperresources.ImageRequester;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.fragments.image.ImagesFragment;

public class ImagesOrderFragment extends ImagesFragment
{
    protected ImageRequester mRequester;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        loadImageRequester();
        return view;
    }

    protected void loadImageRequester() {
        mRequester = new ImageRequester(getContext());
    }
}