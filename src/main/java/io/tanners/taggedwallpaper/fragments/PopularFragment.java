package io.tanners.taggedwallpaper;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import io.tanners.taggedwallpaper.adapters.GridImagesAdapter;
import io.tanners.taggedwallpaper.network.images.ImageRequester;
import io.tanners.taggedwallpaper.network.images.ImageRequest;
import io.tanners.taggedwallpaper.network.images.Request;


public class PopularFragment extends Fragment {
    private View view;
    public static final String POPULAR = "POPULAR Images";
//    private final String mUrl = "https://api.unsplash.com/photos?per_page=50&page=1&order_by=popular";
//    private
//    private ImageRequest mImageRequest;
//    private GridView mPopularGridview;
//    private GridImagesAdapter mAdapter;


    public static PopularFragment newInstance() {
        return new PopularFragment();
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
        new ImageRequester(getContext(), mAdapter, mPopularGridview, Request.Requested.POPULAR, R.layout.grid_item, R.id.grid_image_background).execute(mUrl);

        return view;
    }

    private void loadResources(View view)
    {
        mPopularGridview = (GridView) view.findViewById(R.id.universal_grideview);
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
