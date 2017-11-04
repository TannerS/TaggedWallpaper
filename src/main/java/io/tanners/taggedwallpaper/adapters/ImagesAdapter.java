package io.tanners.taggedwallpaper.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
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
import com.google.gson.Gson;

import java.util.ArrayList;

import io.tanners.taggedwallpaper.DisplayActivity;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.TabBuilder;
import io.tanners.taggedwallpaper.data.results.photo.PhotoResult;

/**
 * Class to handle a single array recyclerlist such that we will use this for the image categories
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {
    private Context mContext;
    private ArrayList<PhotoResult> mItems;
    private int mLayoutId;
    private int mRowId;
    private final int LOGOTYPE = 1;
    private final int NORMAL = 2;
    //private int mTextId;

    public ImagesAdapter(Context mContext, ArrayList<PhotoResult> mItems, int mLayoutId, int mRowId, int mTextId) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mLayoutId = mLayoutId;
        this.mRowId = mRowId;
        //this.mTextId = mTextId;
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


    private void setUpImage(int id, ImageView view)
    {
        // load image view using glide
        loadImage(Glide.with(mContext)
                .load(id), view);
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

        if(holder instanceof ImagesAdapter.ImageViewHolderLogo)
        {
//            setUpImage(R.drawable.ic_logo, ((ImageViewHolderLogo) holder).image);
            ((ImageViewHolderLogo) holder).image.setImageResource(R.drawable.ic_logo);
        }
        else if (holder instanceof ImagesAdapter.ImageViewHolder)
        {
            // get current list item
            PhotoResult mItem = mItems.get(position);

            Log.i("DEBUG", mItem.getWebformatURL());
            Log.i("DEBUG", holder.image == null ? "null" : "not null");



            // set up image
            setUpImage(mItem.getWebformatURL(), holder.image);
            // set author/uploader name
            // String mDisplayText = holder.text.getText() + mItem.getUser();
            // holder.text.setText(mDisplayText);
        }

    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ImagesAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == LOGOTYPE)
        {
            Log.i("DEBUG", "TEST 1");
            View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
            return new ImageViewHolderLogo(mContext, view);
        }
        else if (viewType == NORMAL)
        {
            Log.i("DEBUG", "TEST 2");

            View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
            return new ImageViewHolder(mContext, view);
        }
        else {
            Log.i("DEBUG", "TEST 3");

            return null;
        }


    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public ImageViewHolder(final Context mContext, View view) {
            super(view);
            image = view.findViewById(mRowId);
            // set onclick per image to load new activity to display full screen image
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotoResult result = mItems.get(getAdapterPosition());
                    Intent intent = new Intent(mContext, DisplayActivity.class);
                    intent.putExtra(DisplayActivity.RESULT, ((new Gson()).toJson(result)));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public class ImageViewHolderLogo extends ImageViewHolder {
        public ImageView image;

        public ImageViewHolderLogo(final Context mContext, View view) {
            super(mContext, view);
            // load resources
            image = view.findViewById(mRowId);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            // set onclick per image to load new activity to display full screen image
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("TABS", "DEBUG 1");
                    TabBuilder.buildAndLaunchCustomTab(mContext, "https://pixabay.com/");
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return LOGOTYPE;
        else return NORMAL;
    }
}


