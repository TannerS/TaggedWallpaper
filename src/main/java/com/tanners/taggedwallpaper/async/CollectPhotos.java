package com.tanners.taggedwallpaper.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tanners.taggedwallpaper.R;
import com.tanners.taggedwallpaper.adapters.ImageAdapter;
import com.tanners.taggedwallpaper.data.photodata.PhotoContainer;
import com.tanners.taggedwallpaper.data.photodata.PhotoItem;
import com.tanners.taggedwallpaper.network.ConnectionRequest;
import com.tanners.taggedwallpaper.network.FlickrURLBuilder;

import java.io.IOException;
import java.util.List;

public class CollectPhotos extends AsyncTask<Void, Void, PhotoContainer>
{
    //        private FlickrDataPhotosRecent photos;
    private ProgressDialog dialog;
    //        private FlickrURLBuilder builder;
    private PhotoContainer container;
    //        private ConnectionRequest connection;
    private RecyclerView recycle_view;
    private Context context;
    //
    public CollectPhotos(Context context, RecyclerView recycle_view)
    {
        this.recycle_view = recycle_view;
        this.container = new PhotoContainer();
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {
        initDialog();
//            builder = new FlickrURLBuilder();
    }

    @Override
    protected PhotoContainer doInBackground(Void... v)
    {
//            PhotoContainer container = null;

        try
        {
            // TODO switch statment
            ConnectionRequest connection = new ConnectionRequest((new FlickrURLBuilder()).getRecentPhotos(200, 1));
            String responseStr = connection.getResponse();
            Gson gson = new Gson();
            container = gson.fromJson(responseStr, PhotoContainer.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return container;
    }

    @Override
    protected void onPostExecute(PhotoContainer result)
    {
        super.onPostExecute(result);

//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = layoutInflater.inflate(R.layout.flickr_grid_image_layout, null, false);

        if(result != null)
        {
            List<PhotoItem> photoItems = result.getPhotos().getPhoto();

            if (photoItems == null || (photoItems.size() == 0))
            {
                displayToast("Unable to get images");
            }
            else
            {
//                    final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//                    ImageAdapter adapter = new ImageAdapter(context, photoItems);
                ImageAdapter adapter = new ImageAdapter(context, photoItems);
//                    adapter = new ImageAdapter(context, photoItems, );
                recycle_view.setAdapter(adapter);
            }
        }
        else
            displayToast("Unable to get images");

        dialog.dismiss();
    }


    private void initDialog()
    {
        dialog = new ProgressDialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.MainBGColor)));
        dialog.setTitle("Gathering photos");
        dialog.setMessage("Please wait, this depends on your internet connection");
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
    }

    private void displayToast(String str)
    {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0, 0);
        toast.show();
    }
}