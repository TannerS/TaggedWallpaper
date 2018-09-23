package io.tanners.taggedwallpaper.adapters.image.order;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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

    public ImageOrderAdapter(Context mContext, ArrayList<Photo> mItems) {
        super(mContext, mItems);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
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
    @Override
    public ImageOrderAdapter.ResultImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);
        return new ResultImageViewHolder(mContext, view);
    }

    public class ResultImageViewHolder extends ImageAdapter.ImageViewHolder {
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


