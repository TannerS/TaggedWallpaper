package io.tanners.taggedwallpaper.adapters.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;

import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.R;

//public abstract class ImageAdapter<T> extends RecyclerView.Adapter<ImageAdapter<T>.ImageViewHolder> {
public abstract class ImageAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context mContext;
    protected ArrayList<T> mItems;

    public ImageAdapter(Context mContext, ArrayList<T> mItems)
    {

        this.mContext = mContext;

        if(mItems == null)
            this.mItems = new ArrayList<T>();
        else
            this.mItems = mItems;

        setHasStableIds(true);
    }

    public ImageAdapter(Context mContext)
    {
        this(mContext, null);
    }

    /**
     * update adapter with new images
     *
     * @param mItems
     */
    public void updateAdapter(ArrayList<T> mItems) {
//        Log.i("ADAPTER", "UPDATING");
//        debugList(mItems);
//
//        int pos = this.mItems.size() + 1;
//
//        if(mItems == null)
//            return;
//
//        this.mItems.addAll(mItems);
//        notifyItemRangeInserted(pos, mItems.size());
//
//        debugList();
//        Log.i("ADAPTER", "SIZE: " + this.mItems.size());

        this.mItems.clear();
        this.mItems.addAll(mItems);


        notifyDataSetChanged();
    }

    public void debugList(ArrayList<T> mItems) {
        for(int i = 0; i < mItems.size(); i++)
            Log.i("ADAPTER", "NEW DATA: " + ((Photo)mItems.get(i)).getId());
    }

    public void debugList() {
        for(int i = 0; i < mItems.size(); i++)
            Log.i("ADAPTER", "CURRENT DATA: " + ((Photo)mItems.get(i)).getId());
    }

    /**
     * set image to be loaded into current view
     * @param mUrl
     * @param view
     */
    protected void setUpImage(String mUrl, ImageView view)
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

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) ;

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    /**
     * View holder for images in list
     */
//    public abstract class ImageViewHolder extends RecyclerView.ViewHolder {
//        public ImageViewHolder(View view) {
//            super(view);
//        }
//    }
}


