package io.tanners.taggedwallpaper.adapters.favorite;

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
import io.tanners.taggedwallpaper.ImageDisplayActivity;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.adapters.image.ImageAdapter;
import io.tanners.taggedwallpaper.db.ImageEntry;

public class ImageFavoriteAdapter extends ImageAdapter<ImageEntry> {

    /**
     * Constructor
     *
     * @param mContext
     * @param mItems
     */
    public ImageFavoriteAdapter(Context mContext, ArrayList<ImageEntry> mItems) {
        super(mContext, mItems);
    }

    /**
     * Constructor
     *
     * @param mContext
     */
    public ImageFavoriteAdapter(Context mContext) {
        this(mContext, null);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ResultImageViewHolder mHolder = (ResultImageViewHolder) holder;
        // get current list item
        ImageEntry mItem = mItems.get(position);
        // set up image
        setUpImage(mItem.getImageUrl(), mHolder.image);
        // set up for accessibility
        mHolder.image.setContentDescription(mItem.getDesc());
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
     * Constructor
     */
    public class ResultImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public ResultImageViewHolder(final Context mContext, View view) {
            super(view);

            image = view.findViewById(R.id.image_background);

            view.setOnClickListener(view1 -> {
                ImageEntry result = mItems.get(getAdapterPosition());

                Intent intent = new Intent(mContext, ImageDisplayActivity.class);
                intent.putExtra(ImageDisplayActivity.DATABASE_ITEM_ENTRY_POINT, (Parcelable) result);

                mContext.startActivity(intent);
            });
        }
    }
}