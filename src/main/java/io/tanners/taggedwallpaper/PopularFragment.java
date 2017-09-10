package io.tanners.taggedwallpaper;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import io.tanners.taggedwallpaper.adapters.ImagesAdapter;
import io.tanners.taggedwallpaper.data.results.photo.Photo;
import io.tanners.taggedwallpaper.network.images.TagImageRequest;


public class PopularFragment extends Fragment {
    private View view;
    public static final String POPULAR = "Popular Images";
    private final String mUrl = "https://api.unsplash.com/photos?per_page=50&page=1&order_by=popular";
    private TagImageRequest mImageRequest;
    private GridView mPopularGridview;


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

        new ImageRequester().execute();

        return view;
    }

    private void loadResources(View view)
    {
        mPopularGridview = (GridView) view.findViewById(R.id.popular_grideview);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class ImageRequester extends AsyncTask<Void, Void, List<Photo>> {
//        private ProgressBar mProgressBar = null;

        // TODO do better, maybe pass URL int oclass in here and main in earlier functions?
        @Override
        protected List<Photo> doInBackground(Void... params) {

            mImageRequest = new TagImageRequest(mUrl, null);

            List<Photo> photos = mImageRequest.getPhotos();

            Log.i("REQUEST", "SIZE: " + photos.size());
//            Log.i("REQUEST", "SIZE: " + photos.get(0).getId());
//            Log.i("REQUEST", "SIZE: " + photos.get(1).getId());
//            Log.i("REQUEST", "SIZE: " + photos.get(2).getId());

            return photos;
        }


        @Override
        protected void onPostExecute(List<Photo> photos) {
            // execution of result of Long time consuming operation
//            mProgressBar.setVisibility(View.GONE);
//            finalResult.setText(result);

            mPopularGridview.setAdapter(new ImagesAdapter(getContext(), new ArrayList<Photo>(photos), R.layout.grid_item, R.id.grid_image_background));


        }


        @Override
        protected void onPreExecute() {
//            mProgressBar = (ProgressBar) view.findViewById(R.id.popular_progress_bar);
//            mProgressBar.setVisibility(View.VISIBLE);
        }

//        @Override
//        protected void onProgressUpdate(String... text) {
//            finalResult.setText(text[0]);
//
//        }
    }
}
