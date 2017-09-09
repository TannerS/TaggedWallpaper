package io.tanners.taggedwallpaper.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
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

//public class CategoryAdapter extends BaseAdapter {
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context mContext;
    private ArrayList<CategoryItem> mItems;
    private int mLayoutId;
//    private int mBackBarLayoutId;

//    public CategoryAdapter(Context mContext, ArrayList<CategoryItem> mItems, int mLayoutId, int mBackBarLayoutId) {
//    public CategoryAdapter(Context mContext, ArrayList<CategoryItem> mItems, int mLayoutId, int mBackBarLayoutId) {
    public CategoryAdapter(Context mContext, ArrayList<CategoryItem> mItems, int mLayoutId) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mLayoutId = mLayoutId;
//        this.mBackBarLayoutId = mBackBarLayoutId;
    }

    private void setUpImage(String mUrl, ImageView view)
    {
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
        RequestOptions cropOptions = new RequestOptions().centerCrop();

        Glide.with(mContext)
                .load(mUrl)
                .apply(cropOptions)
                //                .thumbnail()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .asBitmap()
                //                .placeholder(R.drawable.)
//                .error()
//                .fallback(new ColorDrawable(Color.GREY))

                .transition(transitionOptions)

                .into(view);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new CategoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryItem mItem = mItems.get(position);
        holder.title.setText(mItem.getmTitle());
        setUpImage(mItem.getmUrl(), holder.image);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView image;

        public CategoryViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.row_item_text);
            this.image = (ImageView) view.findViewById(R.id.row_image_background);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
