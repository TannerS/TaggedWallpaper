package io.tanners.taggedwallpaper.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.network.images.ImageRequester;
import io.tanners.taggedwallpaper.network.images.Request;

public class NewestImagesFragment extends ImageFragment {
    public static final String NEWEST = "NEWEST Images";
    private final String mUrl = "https://api.unsplash.com/photos?per_page=" + PERPAGE + "&page=" + PAGE + "&order_by=newest";

    public static NewestImagesFragment newInstance() {
        return new NewestImagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_popular, container, false);

        loadResources(view);

//        public ImageRequester(Context mContext, GridView mGridView, int mGridLayoutId,int mGridImageViewId)
//        new ImageRequester(getContext(), mPopularGridview, R.layout.grid_item, R.id.grid_image_background).execute(mUrl);
        new ImageRequester(getContext(), mAdapter, mPopularGridview, Request.Requested.SEARCH, R.layout.grid_item, R.id.grid_image_background).execute(mUrl);

        return view;
    }

//    private void loadResources(View view)
//    {
//        mPopularGridview = (GridView) view.findViewById(R.id.universal_grideview);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
