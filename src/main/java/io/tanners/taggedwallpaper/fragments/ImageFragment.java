package io.tanners.taggedwallpaper.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.ApiBuilder;
import io.tanners.taggedwallpaper.adapters.ImagesAdapter;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;
import io.tanners.taggedwallpaper.interfaces.IGetTag;
import io.tanners.taggedwallpaper.network.images.ImageRequest;
import io.tanners.taggedwallpaper.network.images.ImageRequester;

public class ImageFragment extends Fragment { //implements ErrorCallBack {
    protected View view;
    protected GridView mPopularGridview;
    protected ImagesAdapter mAdapter;
    protected String tag;
    protected ProgressBar mProgressBar;


    /**
     * we shall get the tag passed into the activity here
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // check if current activity loading has the igettag interface implemented
        // this is due in part that main activity can load the popular and latest fragments
        // but do not need tags so no need ot implment it, but imageactivity needs it since
        // it does take in tags so it does implement it
        if (getActivity() instanceof IGetTag) {
            // get tag from activity
            this.tag = ((IGetTag)getActivity()).getTag();
        } else {
           // throw new RuntimeException(getActivity().toString() + " must implement OnFragmentInteractionListener");
            // nothing to do
        }


//        this.tag = ((IGetTag)getActivity()).getTag();
    }

    /**
     * @param view
     */
    protected void loadResources(View view)
    {
        mPopularGridview = (GridView) view.findViewById(R.id.universal_grideview);
//        mProgressBar = (ProgressBar) view.findViewById(R.id.image_progressbar);
    }

    /**
     * request images that will be loaded into fragment
     * @param mCallback
     * @param builder
     */
    protected void loadRequest(ErrorCallBack mCallback, ApiBuilder builder)
    {
        new ImageRequester(getContext())
                .setAdapter(mAdapter)
                .setGridLayoutId(R.layout.grid_item)
                .setImageViewId(R.id.grid_image_background)
//            .setRequest(new ImageRequest(builder.getHeaders(), builder.buildRestfulUrl(), null))
                .setRequest(new ImageRequest(builder.getHeaders(), builder.buildRestfulUrl(), null))
    //                .setRequestType(Request.Requested.SEARCH)
                .setView(mPopularGridview)
//                .setProgressBar(mProgressBar)
                .setProgressBarId(R.id.grid_progressbar)
                .setCallBack(mCallback).execute();
    }
}
