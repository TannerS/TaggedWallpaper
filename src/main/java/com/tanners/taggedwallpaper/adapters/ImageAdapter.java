package com.tanners.taggedwallpaper.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tanners.taggedwallpaper.PhotoActivity;
import com.tanners.taggedwallpaper.R;
import com.tanners.taggedwallpaper.async.UserInfo;
import com.tanners.taggedwallpaper.data.photodata.PhotoItem;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>
{
    private Context context;
    private List<PhotoItem> photos;
//    private UserData user_data;
    private View.OnClickListener listener;

//    public ImageAdapter(Context context, List<PhotoItem> photos, DisplayMetrics metrics)
    public ImageAdapter(Context context, List<PhotoItem> photos, View.OnClickListener listener)
    {
        super();
        this.context = context;
        this.photos = photos;
//        this.metrics = metrics;
//        user_data = new UserData();
        this.listener = listener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flickr_grid_image_layout, null);
        return new ImageViewHolder(view, metrics);
    }

    // TODO put in callback
//    public String getInfo(int position)
//    {
//        StringBuilder photo_info = new StringBuilder("");
//        PhotoItem data = photos.get(position);
//
//        new UserInfo().execute(data.getOwner());
//
//        photo_info.append("Name: " + user_data.getFullName() + "\n");
//        photo_info.append("Username: " + user_data.getUsername() + "\n");
//        photo_info.append("ID: " + data.getId() + "\n");
//        photo_info.append("Title: " + data.getTitle() + "\n");
//        photo_info.append("Owner: " + data.getOwner() + "\n");
//        return photo_info.toString();
//    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {

        holder.image_button.setOnClickListener(listener);

        // TODO put in callback
//        holder.image_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                PhotoItem data = photos.get(position);
//
//                ArrayList<PhotoItem> photoData = new ArrayList<PhotoItem>();
//
//                photoData.add(data);
//
//                Bundle urls = new Bundle();
//
//                urls.putSerializable("urls", photoData);
//
//                final Intent extra_intent = new Intent(context, PhotoActivity.class);
//
//                extra_intent.putExtra("extra", urls);
//
//                extra_intent.putExtra("info", getInfo(position));
//
//                extra_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                context.startActivity(extra_intent);
//            }
//        });

        PhotoItem data = photos.get(position);
        String url = "";

        if (data.getUrl_z() == null || (data.getUrl_z().length() <= 0))
        {
            if (data.getUrl_n() == null || (data.getUrl_n().length() <= 0))
                url = data.getUrl_o();
            else
                url = data.getUrl_n();
        }
        else
            url = data.getUrl_z();

        // TODO research the cache stuff
//        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.image_button);
        Glide.with(context).load(url).into(holder.image_button);
    }

    @Override
    public int getItemCount() {
        return this.photos != null ? this.photos.size() : 0;
    }

    public void clear()
    {
        photos.clear();
        notifyDataSetChanged();
    }

    public static class ImageViewHolder extends ViewHolder
    {
        private final ImageButton image_button;

        public ImageViewHolder(View view, DisplayMetrics metrics)
        {
            super(view);
            image_button = (ImageButton) view.findViewById(R.id.image_button);
            // TODO see if glide handles this
//            int screen_width = (metrics.widthPixels / 4);
//            image_button.setLayoutParams(new RelativeLayout.LayoutParams(screen_width, screen_width));
        }
    }


}

