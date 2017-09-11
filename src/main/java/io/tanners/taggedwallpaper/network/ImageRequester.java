package io.tanners.taggedwallpaper.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import io.tanners.taggedwallpaper.adapters.ImagesAdapter;
import io.tanners.taggedwallpaper.data.results.photo.Photo;
//import io.tanners.taggedwallpaper.interfaces.IImageRequestCallBack;
import io.tanners.taggedwallpaper.network.images.TagImageRequest;

//public class ImageRequester extends AsyncTask<String, Void, List<Photo>> implements IImageRequestCallBack {
public class ImageRequester extends AsyncTask<String, Void, List<Photo>> {
//    private TagImageRequest mImageRequest;
    private GridView mGridView;
//    private IImageRequestCallBack mCallback;
    private Context mContext;
    private int mGridRowLayoutId;
    private int mGridImageViewId;
    private ImagesAdapter mAdapter;
//    private BaseAdapter mAdapter;

//    public ImageRequester(GridView mGridView, IImageRequestCallBack mCallback, int mGridRowLayoutId,int mGridImageViewId)
    public ImageRequester(Context mContext, ImagesAdapter mAdapter, GridView mGridView, int mGridRowLayoutId,int mGridImageViewId)
    {
        this.mContext = mContext;
        this.mGridView = mGridView;
//        this.mCallback = mCallback;
        this.mAdapter = mAdapter;
        this.mGridRowLayoutId = mGridRowLayoutId;
        this.mGridImageViewId = mGridImageViewId;


    }

    // TODO do better, maybe pass URL int oclass in here and main in earlier functions?
    @Override
    protected List<Photo> doInBackground(String... params) {

//        mImageRequest = new TagImageRequest(mUrl, null);
//        mImageRequest = new TagImageRequest(params[0], null);
//        mImageRequest = (new TagImageRequest(params[0], null);

//        List<Photo> photos = mImageRequest.getPhotos();
        List<Photo> photos = (new TagImageRequest(params[0], null)).getPhotos();

//        Log.i("REQUEST", "SIZE: " + photos.size());
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

//        GridView mPopularGridview =

//        mPopularGridview.setAdapter(new ImagesAdapter(getContext(), new ArrayList<Photo>(photos), R.layout.grid_item, R.id.grid_image_background));
//        mGridView.setAdapter(new ImagesAdapter(mContext, new ArrayList<Photo>(photos), R.layout.grid_item, R.id.grid_image_background));

        if(photos != null) {
            mAdapter = new ImagesAdapter(mContext, new ArrayList<Photo>(photos), mGridRowLayoutId, mGridImageViewId);

            mGridView.setAdapter(mAdapter);
//        mGridView.setAdapter(mCallback(photos));
//        mGridView.setAdapter(new ImagesAdapter(mContext, new ArrayList<Photo>(photos), R.layout.grid_item, R.id.grid_image_background));
        }

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
