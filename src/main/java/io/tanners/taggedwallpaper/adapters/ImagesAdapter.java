package io.tanners.taggedwallpaper.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import io.tanners.taggedwallpaper.DisplayActivity;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.data.results.photo.PhotoResult;

/**
 * Class to handle a single array recyclerlist such that we will use this for the image categories
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {
    private Context mContext;
    private ArrayList<PhotoResult> mItems;
    private int mLayoutId;
    private int mRowId;
    private int mProgressBarId;
//    private int mAllLoadedCount;

    public ImagesAdapter(Context mContext, ArrayList<PhotoResult> mItems, int mLayoutId, int mRowId, int mProgressBarId) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mLayoutId = mLayoutId;
        this.mRowId = mRowId;
        this.mProgressBarId = mProgressBarId;
//        mAllLoadedCount = 0;
    }

    public void updateAdapter(ArrayList<PhotoResult> mItems) {
        int startPos = this.mItems.size() + 1;
        this.mItems.addAll(mItems);
        notifyItemRangeInserted(startPos, mItems.size());
    }



//    public boolean isAllLoaded()
//    {
////        return mItems == null ? false : mItems.size();
//        return mItems != null && (mItems.size() == mAllLoadedCount);
//        return mItems != null && (mItems.size() == mAllLoadedCount);
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

    private void loadImage(RequestBuilder<Drawable> mRequest, ImageView view, final ProgressBar mProgressBar)
    {
        //mProgressBar.setVisibility(View.VISIBLE);
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
                        // basically, keep track of how many are loaded by inc the var which
                        // will be tested against the total image amount
                        // so if list has 50 images, this should = 50 when all are loaded (or fails)

//                        Log.i("LOADING", "INC 1 : " + getItemCount() + " : " + mAllLoadedCount);

//                        mAllLoadedCount++;

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                      //  mProgressBar.setVisibility(View.GONE);
                        // basically, keep track of how many are loaded by inc the var which
                        // will be tested against the total image amount
                        // so if list has 50 images, this should = 50 when all are loaded (or fails)
//                        Log.i("LOADING", "INC 2 : " + getItemCount());

//                        mAllLoadedCount++;
//                        Log.i("LOADING", "INC 2 : " + getItemCount() + " : " + mAllLoadedCount);

                        return false;
                    }
                })
                .apply(cropOptions)
                .transition(transitionOptions)
                .into(view);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        PhotoResult mItem = mItems.get(position);
        setUpImage(mItem.getWebformatURL(), holder.image, holder.progress);
    }

    @Override
    public ImagesAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ImageViewHolder(mContext, view);
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ProgressBar progress;

        public ImageViewHolder(final Context mContext, View view) {
            super(view);

            image = view.findViewById(mRowId);
            progress = view.findViewById(mProgressBarId);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    int pos = getAdapterPosition();
                    PhotoResult result = mItems.get(getAdapterPosition());

                    Intent intent = new Intent(mContext, DisplayActivity.class);
                    intent.putExtra(DisplayActivity.ARTIST, result.getUser());
                    intent.putExtra(DisplayActivity.FULLIMAGE, result.getImageURL());
                    intent.putExtra(DisplayActivity.PREVIEW, result.getLargeImageURL());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}


