package io.tanners.taggedwallpaper;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import io.tanners.taggedwallpaper.adapters.ImagesAdapter;
import io.tanners.taggedwallpaper.network.images.ImageRequester;
import io.tanners.taggedwallpaper.network.images.ImageRequest;
import io.tanners.taggedwallpaper.network.images.Request;


public class PopularFragment extends Fragment {
    private View view;
    public static final String POPULAR = "POPULAR Images";
    private final String mUrl = "https://api.unsplash.com/photos?per_page=50&page=1&order_by=popular";
    private ImageRequest mImageRequest;
    private GridView mPopularGridview;
    private ImagesAdapter mAdapter;


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

//    private class ImageRequester extends AsyncTask<Void, Void, List<Photo>> {
////        private ProgressBar mProgressBar = null;
//
//        // TODO do better, maybe pass URL int oclass in here and main in earlier functions?
//        @Override
//        protected List<Photo> doInBackground(Void... params) {
//
//            mImageRequest = new ImageRequest(mUrl, null);
//
//            List<Photo> photos = mImageRequest.getPhotos();
//
//            Log.i("REQUEST", "SIZE: " + photos.size());
////            Log.i("REQUEST", "SIZE: " + photos.get(0).getId());
////            Log.i("REQUEST", "SIZE: " + photos.get(1).getId());
////            Log.i("REQUEST", "SIZE: " + photos.get(2).getId());
//
//            return photos;
//        }
//
//
//        @Override
//        protected void onPostExecute(List<Photo> photos) {
//            // execution of result of Long time consuming operation
////            mProgressBar.setVisibility(View.GONE);
////            finalResult.setText(result);
//
//            mPopularGridview.setAdapter(new ImagesAdapter(getContext(), new ArrayList<Photo>(photos), R.layout.grid_item, R.id.grid_image_background));
//
//
//        }
//
//
//        @Override
//        protected void onPreExecute() {
////            mProgressBar = (ProgressBar) view.findViewById(R.id.popular_progress_bar);
////            mProgressBar.setVisibility(View.VISIBLE);
//        }
//
////        @Override
////        protected void onProgressUpdate(String... text) {
////            finalResult.setText(text[0]);
////
////        }
//    }
}
