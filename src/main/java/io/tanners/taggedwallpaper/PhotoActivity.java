package io.tanners.taggedwallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import io.tanners.taggedwallpaper.async.CollectUserData;
import io.tanners.taggedwallpaper.mappings.photodata.PhotoItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PhotoActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private String image_url;

    private void setUpToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.photo_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Photo Information");
    }

    private PhotoItem getExtras()
    {
        // TODO is this proper? or do we use savedInstanceState
        Intent intent = getIntent();

        // TODO may be info or data for key
        ArrayList<PhotoItem> url_list = (ArrayList<PhotoItem>) intent.getBundleExtra("extra").getSerializable("photoData");

        return url_list.get(0);
    }

    private void setUpImage(PhotoItem data, ImageView image)
    {
        if (data.getUrl_z() == null || (data.getUrl_z().length() <= 0))
        {
            if (data.getUrl_o() == null || (data.getUrl_o().length() <= 0))
                image_url = data.getUrl_n();
            else
                image_url = data.getUrl_o();
        }
        else
            image_url = data.getUrl_z();

        Glide.with(this).load(image_url).fitCenter().into(image);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        setUpToolbar();
        final PhotoItem data = getExtras();
        TextView userInfo = (TextView) findViewById(R.id.text_view);
        new CollectUserData(userInfo).execute(data.getOwner());
        ImageView image = (ImageView) findViewById(R.id.image);
        Button wallpaper_btn = (Button) findViewById(R.id.set_wallpaper);
        setUpImage(data, image);

        // TODO find a better way
        wallpaper_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (data.getUrl_k() == null || (data.getUrl_k().length() <= 0)) {
                    if (data.getUrl_h() == null || (data.getUrl_h().length() <= 0)) {
                        if (data.getUrl_b() == null || (data.getUrl_b().length() <= 0)) {
                            if (data.getUrl_c() == null || (data.getUrl_c().length() <= 0)) {
                                if (data.getUrl_z() == null || (data.getUrl_z().length() <= 0)) {
                                    new setWallpaper(PhotoActivity.this, data.getUrl_o()).execute();
                                } else {
                                    new setWallpaper(getApplicationContext(), data.getUrl_z()).execute();
                                }
                            } else {
                                new setWallpaper(getApplicationContext(), data.getUrl_c()).execute();
                            }
                        } else {
                            new setWallpaper(getApplicationContext(), data.getUrl_b()).execute();
                        }
                    } else {
                        new setWallpaper(getApplicationContext(), data.getUrl_h()).execute();
                    }
                }
                else {
                    new setWallpaper(getApplicationContext(), data.getUrl_k()).execute();
                }

                Toast.makeText(PhotoActivity.this, "Wallpaper Set", Toast.LENGTH_LONG).show();
            }


        });


    }


    // TODO look up newer way
    private class setWallpaper extends AsyncTask<Void, Void, Bitmap>
    {
        private Context context;
        private String url;

        public setWallpaper(Context context, String url)
        {
            this.context = context;
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(Void... position)
        {
            Bitmap bitmap = null;


            try {
                bitmap = Glide.with(PhotoActivity.this).load(url).asBitmap().into(-1,-1).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            super.onPostExecute(bitmap);
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);

            try
            {
                wallpaperManager.setBitmap(bitmap);
                // wallpaperManager.suggestDesiredDimensions(width, height);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
