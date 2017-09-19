package io.tanners.taggedwallpaper.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;

import io.tanners.taggedwallpaper.ImageActivity;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.fragments.CategoryFragment;
//import io.tanners.taggedwallpaper.data.CategoryItem;

/**
 * Class to handle a single array recyclerlist such that we will use this for the image categories
 */
public class RowImageAdapter extends RecyclerView.Adapter<RowImageAdapter.CategoryViewHolder> {
    private Context mContext;
    private ArrayList<CategoryFragment.CategoryItem> mItems;
    private int mLayoutId;

    public RowImageAdapter(Context mContext, ArrayList<CategoryFragment.CategoryItem> mItems, int mLayoutId) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mLayoutId = mLayoutId;
    }

    /**
     *  Setup image based on url of image and object to present it in
     * @param mUrl
     * @param view
     */
    private void setUpImage(String mUrl, ImageView view)
    {
        // creat transition options
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
        // create request options
        RequestOptions cropOptions = new RequestOptions().centerCrop().placeholder(R.drawable.ic_menu_camera).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        // create settings through Glide
        Glide.with(mContext)
                .load(mUrl)
                .apply(cropOptions)
                .transition(transitionOptions)
                .into(view);
    }

    /**
     * Inflate row's layout
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new CategoryViewHolder(mContext, view);
    }

    /**
     * On each item, bind it to the proper object
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        // get category item
        CategoryFragment.CategoryItem mItem = mItems.get(position);
        // set current item's title
        holder.title.setText(mItem.getmTitle());
        // set up image at url with reference to where the image will be loaded into
        setUpImage(mItem.getmUrl(), holder.image);
    }

    /**
     * Get count of items
     * @return
     */
    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    /**
     * recycled views to handle images and text for the images category
     */
//    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public CategoryViewHolder(final Context mContext, View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        Log.i("ONCLICK", "POS 1: " + getLayoutPosition());
                        Log.i("ONCLICK", "POS 1.1: " + title.getText());
                        Log.i("ONCLICK", "POS 2: " + getAdapterPosition());
                        Log.i("ONCLICK", "POS 2.2: " + title.getText());

                        // TODO combine
                        ImageActivity.openIntentForQuery(mContext, title.getText().toString());

                    }
                    else
                    {
                        Log.i("ONCLICK", "DEBUG: " + " " + getPosition());
                        ImageActivity.openIntentForQuery(mContext, title.getText().toString());
                    }
                }

            });

            this.title = (TextView) view.findViewById(R.id.row_item_text);
            this.image = (ImageView) view.findViewById(R.id.row_image_background);
        }

//        @Override
//        public void onClick(final View view) {
//            int itemPosition = .getChildLayoutPosition(view);
//            String item = mList.get(itemPosition);
//            Toast.makeText(mContext, item, Toast.LENGTH_LONG).show();
//        }

//        @Override
//        public void onClick(View view) {
//            int id = view.getId();
//
//            Log.i("ONCLICK", "DEBUG: " + id + " " + getPosition());
//
////            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                Log.i("ONCLICK", "POS 1: " + getLayoutPosition());
//                Log.i("ONCLICK", "POS 1.1: " + title.getText());
//                Log.i("ONCLICK", "POS 2: " + getAdapterPosition());
//                Log.i("ONCLICK", "POS 2.2: " + title.getText());
//
//
//                //getLayoutPosition() or getAdapterPosition()
//
////            }
////            else
////            {
//                Log.i("ONCLICK", "DEBUG: " + id + " " + getPosition());
////            }
//
//        }
    }

//    public class CategoryItem
//    {
//        public String getmUrl() {
//            return mUrl;
//        }
//
//        public void setmUrl(String mUrl) {
//            this.mUrl = mUrl;
//        }
//
//        public String getmTitle() {
//            return mTitle;
//        }
//
//        public void setmTitle(String mTitle) {
//            this.mTitle = mTitle;
//        }
//
//        public CategoryItem(String mTitle, String mUrl) {
//            this.mUrl = mUrl;
//            this.mTitle = mTitle;
//        }
//
//        private String mUrl;
//        private String mTitle;
//
//    }

}
