package io.tanners.taggedwallpaper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import io.tanners.taggedwallpaper.mappings.results.photo.Photo;
import io.tanners.taggedwallpaper.network.images.TagImageRequest;


public class PopularFragment extends Fragment {
    private View view;
    public static final String POPULAR = "Popular Images";
    private final String mUrl = "https://api.unsplash.com/photos?per_page=1&page=1&order_by=popular";
    private TagImageRequest mImageRequest;

    public static PopularFragment newInstance() {
        return new PopularFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mImageRequest = new TagImageRequest(mUrl, null);
//
//        ArrayList<Photo> photos = mImageRequest.getPhotos();
//
//        Log.i("REQUEST", "SIZE: " + photos.size());
//        Log.i("REQUEST", "DEBUG: " + photos.get(0).getUrls().getSmall());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_popular, container, false);

        new ImageRequester().execute();

        Log.i("REQUEST", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");



        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class ImageRequester extends AsyncTask<String, String, String> {

        private String resp = "";
        private ProgressBar mProgressBar = null;

        @Override
        protected String doInBackground(String... params) {


            mImageRequest = new TagImageRequest(mUrl, null);

            ArrayList<Photo> photos = mImageRequest.getPhotos();

            Log.i("REQUEST", "SIZE: " + photos.size());
//            Log.i("REQUEST", "DEBUG: " + photos.get(0).getUrls().getSmall());

            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            mProgressBar.setVisibility(View.GONE);
//            finalResult.setText(result);
        }


        @Override
        protected void onPreExecute() {
            mProgressBar = (ProgressBar) view.findViewById(R.id.popular_progress_bar);
            mProgressBar.setVisibility(View.VISIBLE);
        }

//        @Override
//        protected void onProgressUpdate(String... text) {
//            finalResult.setText(text[0]);
//
//        }
    }
}
