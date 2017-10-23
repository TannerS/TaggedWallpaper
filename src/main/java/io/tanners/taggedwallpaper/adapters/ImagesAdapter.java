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
import android.widget.TextView;

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
    private int mTextId;

    public ImagesAdapter(Context mContext, ArrayList<PhotoResult> mItems, int mLayoutId, int mRowId, int mTextId) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mLayoutId = mLayoutId;
        this.mRowId = mRowId;
        this.mTextId = mTextId;
    }

    /**
     * update adapter with new images
     * @param mItems
     */
    public void updateAdapter(ArrayList<PhotoResult> mItems) {
        int startPos = this.mItems.size() + 1;
        this.mItems.addAll(mItems);
        notifyItemRangeInserted(startPos, mItems.size());
    }

    /**
     * set image to be loaded into current view
     * @param mUrl
     * @param view
     */
    private void setUpImage(String mUrl, ImageView view)
    {
        // load image view using glide
        loadImage(Glide.with(mContext)
                .load(mUrl), view);
    }

    /**
     * @param mRequest
     * @param view
     */
    private void loadImage(RequestBuilder<Drawable> mRequest, ImageView view)
    {
        // set up transition
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
        // set up request options
        RequestOptions cropOptions = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_error_black_48dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        // apply
        mRequest
                .apply(cropOptions)
                .transition(transitionOptions)
                .into(view);
    }

    /**
     * get number of items in list
     * @return
     */
    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        // get current list item
        PhotoResult mItem = mItems.get(position);
        // set up image
        setUpImage(mItem.getWebformatURL(), holder.image);
        // set author/uploader name
        String mDisplayText = holder.text.getText() + mItem.getUser();
        holder.text.setText(mDisplayText);
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ImagesAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ImageViewHolder(mContext, view);
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;

        public ImageViewHolder(final Context mContext, View view) {
            super(view);
            // load resources
            image = view.findViewById(mRowId);
            text = view.findViewById(mTextId);
            // set onclick per image to load new activity to display full screen image
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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


