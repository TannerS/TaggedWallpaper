package io.tanners.taggedwallpaper.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.GridView;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.adapters.GridImagesAdapter;

public class ImageFragment extends Fragment {
    protected View view;
    protected final int PERPAGE = 50;//https://api.unsplash.com/photos?per_page=50&page=1&order_by=newest";
    protected final int PAGE = 1;//https://api.unsplash.com/photos?per_page=50&page=1&order_by=newest";
    protected GridView mPopularGridview;
    protected GridImagesAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    protected void loadResources(View view)
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
