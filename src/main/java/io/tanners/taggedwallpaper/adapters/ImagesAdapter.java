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

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.data.results.photo.Photo;


public class ImagesAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Photo> mItems;
    private int mLayoutId;
    private int mRowId;

    public ImagesAdapter(Context mContext, ArrayList<Photo> mItems, int mLayoutId, int mRowId) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mLayoutId = mLayoutId;
        this.mRowId = mRowId;
    }

    public int getCount() {
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
            mItemContainerView.image = mItem.findViewById(mRowId);

            mItem.setTag(mItemContainerView);
        }
        else
        {
            mItemContainerView = (PhotoCategoryContainerView) mItem.getTag();
        }

        Photo mCurrentItem = (Photo) mItems.get(position);

//        mItemContainerView.image.setBackgroundResource(mCurrentItem.getmResourceId());

        setUpImage(mCurrentItem.getUrls().getSmall(), mItemContainerView.image);

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
        public ImageView image;
    }

}