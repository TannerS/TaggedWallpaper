//package io.tanners.taggedwallpaper.network.images;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.support.design.widget.Snackbar;
//import android.util.Log;
//import android.view.View;
//import android.widget.GridView;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import io.tanners.taggedwallpaper.adapters.ImagesAdapter;
//import io.tanners.taggedwallpaper.data.results.photo.PhotoResult;
//import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;
////import io.tanners.taggedwallpaper.data.results.photo.Photo;
//
//
//public class ImageRequester extends AsyncTask<Void, Void, List<PhotoResult>> {
//    private GridView mGridView;
//    private Context mContext;
//    private int mGridRowLayoutId;
//    private int mGridImageViewId;
//    private int mProgressBarId;
//    private ImagesAdapter mAdapter;
//    private ProgressBar mProgressBar;
//    private ErrorCallBack mCallback;
////    private Request.Requested mRequestType;
////    private ImageRequest(params[0], null)).getResult(mRequestType)
//    private ImageRequest imageRequest;
//
//    public ImageRequester setCallBack(ErrorCallBack mCallback)
//    {
//        this.mCallback = mCallback;
//        return this;
//    }
//
//    public ImageRequester(Context mContext)
//    {
//        this.mContext = mContext;
//    }
//
////    public ImageRequester setRequestType(Request.Requested mRequestType)
////    {
////        this.mRequestType = mRequestType;
////        return this;
////    }
//
//    public ImageRequester setProgressBar(ProgressBar mProgressBar)
//    {
//        this.mProgressBar = mProgressBar;
//        return this;
//    }
//
//    public ImageRequester setRequest(ImageRequest imageRequest)
//    {
//        this.imageRequest = imageRequest;
//        return this;
//    }
//
//    public ImageRequester setAdapter(ImagesAdapter mAdapter)
//    {
//        this.mAdapter = mAdapter;
//        return this;
//    }
//
//
////    public ImageRequester setViewId(int mGridView)
//    public ImageRequester setView(GridView mGridView)
//    {
//        this.mGridView = mGridView;
//        return this;
//    }
//
//
//    public ImageRequester setImageViewId(int mGridImageViewId)
//    {
//        this.mGridImageViewId = mGridImageViewId;
//        return this;
//    }
//
//
//    public ImageRequester setGridLayoutId(int mGridRowLayoutId)
//    {
//        this.mGridRowLayoutId = mGridRowLayoutId;
//        return this;
//    }
//
//
//    public ImageRequester setProgressBarId(int mProgressBarId)
//    {
//        this.mProgressBarId = mProgressBarId;
//        return this;
//    }
//
//
//    /**
//     * Parameter at index 0 is restful api url
//     * @param params
//     * @return
//     */
//    @Override
//    protected List<PhotoResult> doInBackground(Void... params) {
////        List<Photo> photos = (new ImageRequest(params[0], null)).getResult(mRequestType);
////        List<Photo> photos = imageRequest.getResult(mRequestType);
//        List<PhotoResult> photos = null;
//
//        try {
//            photos = imageRequest.getResult();
//            Log.i("REQUEST", "DEBU 3");
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//
//
//            Log.i("REQUEST", "DEBU 4");
//        }
//
//
//        return photos;
//    }
//
//    @Override
//    protected void onPostExecute(List<PhotoResult> photos) {
//        if(photos != null)
//        {
//            Log.i("REQUEST", "DEBU 1");
////            mAdapter.updateAdapter(new ArrayList<Photo>(photos),  mGridRowLayoutId, mGridImageViewId);
//            mAdapter = new ImagesAdapter(mContext, new ArrayList<PhotoResult>(photos),  mGridRowLayoutId, mGridImageViewId, mProgressBarId);
//            mGridView.setAdapter(mAdapter);
////            mProgressBar.setVisibility(View.GONE);
//            mGridView.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            Log.i("REQUEST", "DEBU 2");
//            mCallback.displayError();
////            mProgressBar.setVisibility(View.GONE);
////            mGridView.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    protected void onPreExecute() {
//        mGridView.setVisibility(View.VISIBLE);
////        mGridView.setVisibility(View.GONE);
////        mProgressBar.setVisibility(View.VISIBLE);
//    }
//
//}
