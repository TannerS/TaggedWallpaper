package io.tanners.taggedwallpaper.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import io.tanners.taggedwallpaper.MainActivity;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.model.categories.CategoryItem;

/**
 * Class to handle a single array recyclerlist such that we will use this for the image categories
 */
public class CategoryImageAdapter extends ImageAdapterBase<CategoryItem> {

    public CategoryImageAdapter(Context mContext, ArrayList<CategoryItem> mItems) {
        super(mContext, mItems);
    }

    /**
     * Inflate row's layout
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(mContext, view);
    }

    /**
     * On each item, bind it to the proper object
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        CategoryViewHolder mHolder = (CategoryViewHolder) holder;
        // get category item
        CategoryItem mItem = mItems.get(position);
        // set current item's title
        mHolder.title.setText(mItem.getmTitle());
        // set up image at url with reference to where the image will be loaded into
        setUpImage(mItem.getmUrl(), mHolder.image);
    }

    /**
     * recycled views to handle images and text for the images category
     */
    public class CategoryViewHolder extends ImageAdapterBase.ImageViewHolder {
        public TextView title;
        public ImageView image;

        public CategoryViewHolder(final Context mContext, View view) {
            super(view);
            // each category gets handled as a search to the next activity
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        MainActivity.openIntentForQuery(mContext, title.getText().toString());
                    } else {
                        MainActivity.openIntentForQuery(mContext, title.getText().toString());
                    }
                }

            });
            // load resources
            this.title = (TextView) view.findViewById(R.id.row_item_text);
            this.image = (ImageView) view.findViewById(R.id.row_image_background);
        }
    }
}
