package io.tanners.taggedwallpaper.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.data.CategoryItem;

public class CategoryAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<CategoryItem> mItems;
    private int mLayoutId;
    private int mBackBarLayoutId;

    public CategoryAdapter(Context mContext, ArrayList<CategoryItem> mItems, int mLayoutId, int mBackBarLayoutId) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mLayoutId = mLayoutId;
        this.mBackBarLayoutId = mBackBarLayoutId;
    }

    public int getCount() {
        Log.i("POSITION", "GETCOUNT: " + mItems.size());
        return mItems == null ? 0 : mItems.size();
    }

    public Object getItem(int position) {
        Log.i("POSITION", "GETITEM: " + String.valueOf(position));

        return null;
    }

    public long getItemId(int position) {
        Log.i("POSITION", "GETITEMID: " + String.valueOf(position));
        return 0;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        Log.i("POSITION", "getAutofillOptions: ");

        return new CharSequence[0];
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View mItem = convertView;
        PhotoCategoryContainerView mItemContainerView = null;

        if (mItem == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            mItem = inflater.inflate(mLayoutId, parent, false);

            mItemContainerView = new PhotoCategoryContainerView();

            mItemContainerView.bar = mItem.findViewById(R.id.grid_image_bar);
            mItemContainerView.image = mItem.findViewById(R.id.grid_image_background);
            mItemContainerView.title = mItem.findViewById(R.id.grid_item_text);

            mItem.setTag(mItemContainerView);
        }
        else
        {
            mItemContainerView = (PhotoCategoryContainerView) mItem.getTag();
        }

        CategoryItem mCurrentItem = mItems.get(position);

//        mItemContainerView.image.setBackgroundResource(mCurrentItem.getmResourceId());

        setUpImage(mCurrentItem.getmUrl(), mItemContainerView.image);
//        setUpImage(mBackBarLayoutId, mItemContainerView.bar);

//        mItemContainerView.bar.);
//        mItemContainerView.bar.setBackgroundResource(R.color.transparentALpha80);
//        mItemContainerView.bar.setBackgroundResource(R.color.black);
//        mItemContainerView.bar.getBackground().setAlpha(80);
        mItemContainerView.title.setText(mCurrentItem.getmTitle());

        return mItem;
    }

    private void setUpImage(int mElement, ImageView view)
    {
        loadImage(Glide.with(mContext)
                .load(mElement), view);
    }

    private void setUpImage(String mUrl, ImageView view)
    {
        loadImage(Glide.with(mContext)
                .load(mUrl), view);
    }

    private void loadImage(RequestBuilder<Drawable> mRequest, ImageView view)
    {
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
        RequestOptions cropOptions = new RequestOptions().centerCrop();

        mRequest.apply(cropOptions)
                //                .thumbnail()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .asBitmap()
                //                .placeholder(R.drawable.)
//                .error()
//                .fallback(new ColorDrawable(Color.GREY))

                .transition(transitionOptions)

                .into(view);
    }

    static class PhotoCategoryContainerView
    {
        public TextView title;
        public ImageView image;
        public ImageView bar;
    }

}