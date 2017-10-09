package io.tanners.taggedwallpaper.adapters;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import io.tanners.taggedwallpaper.DisplayActivity;
import io.tanners.taggedwallpaper.ImageActivity;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.data.results.photo.PhotoResult;
//import io.tanners.taggedwallpaper.data.results.photo.Photo;

/**
 * Class is used to hold gride view of images as a result from the image api
 */
public class ImagesAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<PhotoResult> mItems;
    private int mLayoutId;
    private int mRowId;
    private int mProgressBarId;

    public ImagesAdapter(Context mContext, ArrayList<PhotoResult> mItems, int mLayoutId, int mRowId, int mProgressBarId) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mLayoutId = mLayoutId;
        this.mRowId = mRowId;
        this.mProgressBarId = mProgressBarId;
    }

    /**
     * used to update the adapter to a new dataset
     * @param mItems
     * @param mLayoutId
     * @param mRowId
     */
//    public void updateAdapter(ArrayList<PhotoResult> mItems, int mLayoutId, int mRowId)
//    {
//        clearAdapter();
//
//        this.mItems = mItems;
//        this.mLayoutId = mLayoutId;
//        this.mRowId = mRowId;
//
//        notifyDataSetChanged();
//
//    }

    /**
     * clear and erase adapter
     */
    public void clearAdapter()
    {
        mItems.clear();
    }


    /**
     * get count of items in data set
     * @return
     */
    public int getCount() {
        return mItems == null ? 0 : mItems.size();

    }

    /**
     * get current item
     * @param position
     * @return
     */
    public Object getItem(int position) {

        return null;
    }

    /**
     * get current item id
     * @param position
     * @return
     */
    public long getItemId(int position) {
        return 0;
    }

    /**
     * auto fill (not used)
     * @return
     */
    @Override
    public CharSequence[] getAutofillOptions() {

        return new CharSequence[0];
    }

    /**
     *  create a new ImageView for each item referenced by the Adapter
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View mItem = convertView;
        PhotoCategoryContainerView mItemContainerView = null;

        // if item is new
        if (mItem == null) {
            // inflate layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            mItem = inflater.inflate(mLayoutId, parent, false);
            // create object to hold view
            mItemContainerView = new PhotoCategoryContainerView();
            mItemContainerView.image = mItem.findViewById(mRowId);
            mItemContainerView.progress = mItem.findViewById(mProgressBarId);
            // set item that holds view
            mItem.setTag(mItemContainerView);
        }
        // view is being recycled
        else
        {
            // get view hold item
            mItemContainerView = (PhotoCategoryContainerView) mItem.getTag();
        }

        mItem.setOnClickListener(loadOnClickListener(mItems.get(position)));

        // get photo data at location position
        PhotoResult mCurrentItem = (PhotoResult) mItems.get(position);
        // set image to be loaded into current view
//        setUpImage(mCurrentItem.getUrls().getSmall(), mItemContainerView.image);
        setUpImage(mCurrentItem.getWebformatURL(), mItemContainerView.image, mItemContainerView.progress);

        return mItem;
    }

    private View.OnClickListener loadOnClickListener(final PhotoResult mCurrentItem)
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DisplayActivity.class);
                intent.putExtra(DisplayActivity.ARTIST, mCurrentItem.getUser());
                intent.putExtra(DisplayActivity.FULLIMAGE, mCurrentItem.getImageURL());
                intent.putExtra(DisplayActivity.PREVIEW, mCurrentItem.getLargeImageURL());
                mContext.startActivity(intent);
            }
        };
    }

    /**
     * set image to be loaded into current view
     * @param mElement
     * @param view
     */
//    private void setUpImage(int mElement, ImageView view)
//    {
//        // load image view using glide
//        loadImage(Glide.with(mContext)
//                .load(mElement), view);
//    }

    /**
     * set image to be loaded into current view
     * @param mUrl
     * @param view
     */
    private void setUpImage(String mUrl, ImageView view, ProgressBar mProgressBar)
    {
        // load image view using glide

        loadImage(Glide.with(mContext)
                .load(mUrl), view, mProgressBar);
    }

    /**
     * attach features to image view using glide
     * @param mRequest
     * @param view
     */
    private void loadImage(RequestBuilder<Drawable> mRequest, ImageView view, final ProgressBar mProgressBar)
    {
        mProgressBar.setVisibility(View.VISIBLE);
        // set up transition
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
        // set up request options
        RequestOptions cropOptions = new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.ic_photo_camera_black_48dp)
                .error(R.drawable.ic_error_black_48dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        // apply features
        mRequest.listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(cropOptions)
                .transition(transitionOptions)
                .into(view);
    }

    /**
     * contains views that will be recycled
     */
    static class PhotoCategoryContainerView
    {
        public ImageView image;
        public ProgressBar progress;
    }

}