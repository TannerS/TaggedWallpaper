package io.tanners.taggedwallpaper.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.ApiBuilder;
import io.tanners.taggedwallpaper.adapters.ImagesAdapter;
import io.tanners.taggedwallpaper.interfaces.IGetTag;
import io.tanners.taggedwallpaper.network.images.ImageRequest;
import io.tanners.taggedwallpaper.network.images.ImageRequester;

public class ImageFragment extends Fragment {
    protected View view;
    protected GridView mPopularGridview;
    protected ImagesAdapter mAdapter;
    protected String tag;
    protected ProgressBar mProgressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.tag = ((IGetTag)getActivity()).getTag();
    }

    protected void loadResources(View view)
    {
        mPopularGridview = (GridView) view.findViewById(R.id.universal_grideview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.image_progressbar);
    }

    protected void loadRequest(ApiBuilder builder)
    {
        new ImageRequester(getContext())
            .setAdapter(mAdapter)
            .setGridLayoutId(R.layout.grid_item)
            .setImageViewId(R.id.grid_image_background)
            .setRequest(new ImageRequest(builder.getHeaders(), builder.buildRestfulUrl(), null))
    //                .setRequestType(Request.Requested.SEARCH)
            .setView(mPopularGridview)
            .setProgressBar(mProgressBar).execute();
    }


}
