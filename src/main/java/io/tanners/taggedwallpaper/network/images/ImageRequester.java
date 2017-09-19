package io.tanners.taggedwallpaper.network.images;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import io.tanners.taggedwallpaper.adapters.ImagesAdapter;
import io.tanners.taggedwallpaper.data.results.photo.Photo;


public class ImageRequester extends AsyncTask<Void, Void, List<Photo>> {
    private GridView mGridView;
    private Context mContext;
    private int mGridRowLayoutId;
    private int mGridImageViewId;
    private ImagesAdapter mAdapter;
//    private Request.Requested mRequestType;
//    private ImageRequest(params[0], null)).getPhotos(mRequestType)
    private ImageRequest imageRequest;

    public ImageRequester(Context mContext)
    {
        this.mContext = mContext;
    }

//    public ImageRequester setRequestType(Request.Requested mRequestType)
//    {
//        this.mRequestType = mRequestType;
//        return this;
//    }

    public ImageRequester setRequest(ImageRequest imageRequest)
    {
        this.imageRequest = imageRequest;
        return this;
    }

//    public ImageRequester setContext(Context mContext)
//    {
//        this.mContext = mContext;
//        return this;
//    }

    public ImageRequester setAdapter(ImagesAdapter mAdapter)
    {
        this.mAdapter = mAdapter;
        return this;
    }


//    public ImageRequester setViewId(int mGridView)
    public ImageRequester setView(GridView mGridView)
    {
        this.mGridView = mGridView;
        return this;
    }


    public ImageRequester setImageViewId(int mGridImageViewId)
    {
        this.mGridImageViewId = mGridImageViewId;
        return this;
    }


    public ImageRequester setGridLayoutId(int mGridRowLayoutId)
    {
        this.mGridRowLayoutId = mGridRowLayoutId;
        return this;
    }


    /**
     * Parameter at index 0 is restful api url
     * @param params
     * @return
     */
    @Override
    protected List<Photo> doInBackground(Void... params) {
//        List<Photo> photos = (new ImageRequest(params[0], null)).getPhotos(mRequestType);
//        List<Photo> photos = imageRequest.getPhotos(mRequestType);
        List<Photo> photos = imageRequest.getPhotos();
        return photos;
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        if(photos != null) {
            mAdapter.updateAdapter(new ArrayList<Photo>(photos),  mGridRowLayoutId, mGridImageViewId);
            mGridView.setAdapter(mAdapter);
            mGridView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPreExecute() {
    }
}
