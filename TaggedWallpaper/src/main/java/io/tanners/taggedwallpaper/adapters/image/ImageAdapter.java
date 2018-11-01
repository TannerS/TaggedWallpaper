package io.tanners.taggedwallpaper.adapters.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import io.tanners.taggedwallpaper.R;

public abstract class ImageAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context mContext;
    protected ArrayList<T> mItems;

    /**
     * Constructor
     *
     * @param mContext
     * @param mItems
     */
    public ImageAdapter(Context mContext, ArrayList<T> mItems)
    {
        this.mContext = mContext;

        if(mItems == null)
            this.mItems = new ArrayList<T>();
        else
            this.mItems = mItems;
    }

    public ImageAdapter(Context mContext)
    {
        this(mContext, null);
    }

    /**
     * Update adapter with new images
     *
     * @param mItems
     */
    public void updateAdapter(ArrayList<T> mItems) {
        if(mItems != null) {
            this.mItems = mItems;
            this.notifyDataSetChanged();
        }
    }

    /**
     * Set image to be loaded into current view
     *
     * @param mUrl
     * @param view
     */
    protected void setUpImage(String mUrl, ImageView view)
    {
        // load image view using glide
        loadImage(Glide.with(mContext)
                .load(mUrl), view);
    }

    /**
     * Set up any image
     *
     * @param id
     * @param view
     */
    private void setUpImage(int id, ImageView view)
    {
        // load image view using glide
        loadImage(Glide.with(mContext)
                .load(id), view);
    }

    /**
     * Load image per view
     *
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
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * @param holder
     * @param position
     */
    @Override
    public abstract void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) ;

    /**
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}


