package io.tanners.taggedwallpaper.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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

    public ImagesAdapter(Context mContext, ArrayList<PhotoResult> mItems, int mLayoutId, int mRowId, int mProgressBarId) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mLayoutId = mLayoutId;
        this.mRowId = mRowId;
        this.mProgressBarId = mProgressBarId;
    }

    public void updateAdapter(ArrayList<PhotoResult> mItems) {
        int startPos = this.mItems.size() + 1;
        this.mItems.addAll(mItems);
        notifyItemRangeInserted(startPos, mItems.size());
    }

    /**
     *  Setup image based on url of image and object to present it in
     * @param mUrl
     * @param view
     */
    private void setUpImage(String mUrl, ImageView view)
    {
        // create transition options
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
        // create request options
//        RequestOptions cropOptions = new RequestOptions().centerCrop().placeholder(R.drawable.ic_menu_camera).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        RequestOptions cropOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        // create settings through Glide
        Glide.with(mContext)
                .load(mUrl)
                .apply(cropOptions)
                .transition(transitionOptions)
                .into(view);
    }

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

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        PhotoResult mItem = mItems.get(position);
        setUpImage(mItem.getPreviewURL(), holder.image, holder.progress);
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

















//    public View getView(int position, View convertView, ViewGroup parent) {
//        View mItem = convertView;
//        PhotoCategoryContainerView mItemContainerView = null;
//
//        // if item is new
//        if (mItem == null) {
//            // inflate layout
//            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
//            mItem = inflater.inflate(mLayoutId, parent, false);
//            // create object to hold view
//            mItemContainerView = new PhotoCategoryContainerView();
//            mItemContainerView.image = mItem.findViewById(mRowId);
//            mItemContainerView.progress = mItem.findViewById(mProgressBarId);
//            // set item that holds view
//            mItem.setTag(mItemContainerView);
//        }
//        // view is being recycled
//        else
//        {
//            // get view hold item
//            mItemContainerView = (PhotoCategoryContainerView) mItem.getTag();
//        }
//
//        mItem.setOnClickListener(loadOnClickListener(mItems.get(position)));
//
//        // get photo data at location position
//        PhotoResult mCurrentItem = (PhotoResult) mItems.get(position);
//        // set image to be loaded into current view
////        setUpImage(mCurrentItem.getUrls().getSmall(), mItemContainerView.image);
//        setUpImage(mCurrentItem.getWebformatURL(), mItemContainerView.image, mItemContainerView.progress);
//
//        return mItem;
//    }
//
//
//

