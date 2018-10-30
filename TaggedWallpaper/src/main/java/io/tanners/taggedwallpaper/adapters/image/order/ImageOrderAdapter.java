package io.tanners.taggedwallpaper.adapters.image.order;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.ImageDisplayActivity;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.adapters.image.ImageAdapter;

/**
 * Image adapter to handle generic adapter functionality for images of object type T
 */
public class ImageOrderAdapter extends ImageAdapter<Photo> {

    /**
     * @param mContext
     * @param mItems
     */
    public ImageOrderAdapter(Context mContext, ArrayList<Photo> mItems) {
        super(mContext, mItems);
    }

    /**
     * @param mContext
     */
    public ImageOrderAdapter(Context mContext) {
        this(mContext, null);
        // https://stackoverflow.com/a/38138206/2449314
        setHasStableIds(true);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ResultImageViewHolder mHolder = (ResultImageViewHolder) holder;
        // get current list item
        Photo mItem = mItems.get(position);
        // set up image
        setUpImage(mItem.getUrls().getRegular(), mHolder.image);
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ResultImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);
        return new ResultImageViewHolder(mContext, view);
    }

    /**
     *
     */
    public class ResultImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        /**
         * @param mContext
         * @param view
         */
        public ResultImageViewHolder(final Context mContext, View view) {
            super(view);

            image = view.findViewById(R.id.image_background);

            view.setOnClickListener(view1 -> {
                Photo result = mItems.get(getAdapterPosition());

                Intent intent = new Intent(mContext, ImageDisplayActivity.class);
                intent.putExtra(ImageDisplayActivity.PHOTO_ITEM_ENTRY_POINT, (Parcelable) result);

                mContext.startActivity(intent);
            });
        }
    }
}


