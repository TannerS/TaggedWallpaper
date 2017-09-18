package io.tanners.taggedwallpaper.network.images;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import io.tanners.taggedwallpaper.adapters.GridImagesAdapter;
import io.tanners.taggedwallpaper.data.results.photo.Photo;


public class ImageRequester extends AsyncTask<String, Void, List<Photo>> {
    private GridView mGridView;
    private Context mContext;
    private int mGridRowLayoutId;
    private int mGridImageViewId;
    private GridImagesAdapter mAdapter;
    private Request.Requested mRequestType;

    public ImageRequester(Context mContext, GridImagesAdapter mAdapter, GridView mGridView, Request.Requested mRequestType, int mGridRowLayoutId, int mGridImageViewId)
    {
        this.mContext = mContext;
        this.mGridView = mGridView;
        this.mAdapter = mAdapter;
        this.mGridRowLayoutId = mGridRowLayoutId;
        this.mGridImageViewId = mGridImageViewId;
        this.mRequestType = mRequestType;
    }

    /**
     * Parameter at index 0 is restful api url
     * @param params
     * @return
     */
    @Override
    protected List<Photo> doInBackground(String... params) {
        List<Photo> photos = (new ImageRequest(params[0], null)).getPhotos(mRequestType);
        return photos;
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        if(photos != null) {
            mAdapter = new GridImagesAdapter(mContext, new ArrayList<Photo>(photos), mGridRowLayoutId, mGridImageViewId);
            mGridView.setAdapter(mAdapter);
            mGridView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPreExecute() {
    }
}
