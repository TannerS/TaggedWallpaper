package io.tanners.taggedwallpaper.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import io.tanners.taggedwallpaper.ImageDisplayActivity;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.model.categories.CategoryItem;

/**
 * Image adapter to handle generic adapter functionality for images of object type T
 */
public class ResultImageAdapter extends ImageAdapterBase<Object>{

    public ResultImageAdapter(Context mContext, ArrayList<Object> mItems) {
        super(mContext, mItems);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        // get current list item
//        T mItem = mItems.get(position);
        // set up image
//        setUpImage(mItem.getUrl(), holder.image);
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ResultImageAdapter.ResultImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);
        return new ResultImageViewHolder(mContext, view);
    }

    public class ResultImageViewHolder extends ImageAdapterBase.ImageViewHolder {
        public ImageView image;

        public ResultImageViewHolder(final Context mContext, View view) {
            super(view);
            image = view.findViewById(R.id.image_background);
            // set onclick per image to load new activity to display full screen image
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Object result = mItems.get(getAdapterPosition());
                    Intent intent = new Intent(mContext, ImageDisplayActivity.class);
                    intent.putExtra(ImageDisplayActivity.RESULT, (Parcelable) result);
                    mContext.startActivity(intent);
                }
            });
        }
    }

}


