package io.tanners.taggedwallpaper.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import io.tanners.taggedwallpaper.R;

public abstract class ImageAdapterBase<T> extends RecyclerView.Adapter<ImageAdapterBase<T>.ImageViewHolder> {
    protected Context mContext;
    protected ArrayList<T> mItems;

    public ImageAdapterBase(Context mContext, ArrayList<T> mItems)
    {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    /**
     * update adapter with new images
     *
     * @param mItems
     */
    public void updateAdapter(ArrayList<T> mItems) {
        this.mItems.addAll(mItems);
        notifyItemRangeInserted(this.mItems.size() + 1, mItems.size());
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

//    \    @Override
//    public ImageAdapterBase.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
//        return new ImageViewHolder(view);
//    }

    @Override
    public abstract ImageAdapterBase.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull ImageViewHolder holder, int position) ;


//    /**
//     * View holder for images in list
//     */
//    public class ImageViewHolder extends RecyclerView.ViewHolder {
//        public ImageView image;
//
//        public ImageViewHolder(final Context mContext, View view) {
//            super(view);
//            image = view.findViewById(mRowId);
//            // set onclick per image to load new activity to display full screen image
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickHelper.onClick(mContext, mItems, getAdapterPosition());
//                }
//            });
////            view.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    T result = mItems.get(getAdapterPosition());
////                    Intent intent = new Intent(mContext, ImageDisplayActivity.class);
////                    intent.putExtra(ImageDisplayActivity.RESULT, (Parcelable) result);
////                    mContext.startActivity(intent);
////                }
////            });
//        }
//    }

    /**
     * View holder for images in list
     */
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageViewHolder(View view) {
            super(view);
//            image = view.findViewById(mRowId);
//            // set onclick per image to load new activity to display full screen image
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickHelper.onClick(mContext, mItems, getAdapterPosition());
//                }
//            });
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    T result = mItems.get(getAdapterPosition());
//                    Intent intent = new Intent(mContext, ImageDisplayActivity.class);
//                    intent.putExtra(ImageDisplayActivity.RESULT, (Parcelable) result);
//                    mContext.startActivity(intent);
//                }
//            });
        }
    }

//    interface ImageOnClickHelper<T> {
//        public void onClick(final Context mContext, ArrayList<T> mItems, int pos);
//    }
}


