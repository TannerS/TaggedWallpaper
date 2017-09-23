package io.tanners.taggedwallpaper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

public class DisplayActivity extends AppCompatActivity {
    public final static String ARTIST = "ARTIST";
    public final static String FULLIMAGE = "FULLIMAGE";
    public final static String PREVIEW = "PREVIEW";
    private TextView artistTextView;
    private ImageView mainImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        loadToolBar();
        loadBottomNavigation();
        loadResources();
    }

    private void loadResources()
    {
        artistTextView = (TextView) findViewById(R.id.artist_text_id);
        String artist = "Photo by: " + getIntent().getStringExtra(ARTIST);
        artistTextView.setText(artist);

        Log.i("DISPLAY", "ARTIST: " + artist);

        mainImageView = (ImageView) findViewById(R.id.main_image_id);

        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
        RequestOptions cropOptions = new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.ic_photo_camera_black_48dp)

                .error(R.drawable.ic_error_black_48dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        Log.i("DISPLAY", "URL: " + getIntent().getStringExtra(PREVIEW));

        Glide.with(this)
            .load(getIntent().getStringExtra(PREVIEW))
            .apply(cropOptions)
            .transition(transitionOptions)
            .into(mainImageView);
    }

    private void loadToolBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.display_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_close);

    }

    private void loadBottomNavigation()
    {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.display_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_download:
                        downloadImage();
                        return true;
                    case R.id.navigation_set:
                        setImage();
                        return true;
                    case R.id.navigation_share:
                        shareImage();
                        return true;
                }
                return false;
            }

        });
    }

    private void downloadImage()
    {
        Log.i("NAV", "DOWNLOAD");

    }

    private void setImage()
    {
        Log.i("NAV", "SET");

    }

    private void shareImage()
    {
        Log.i("NAV", "SHARE");

    }

//
//    /**
//     * @param item
//     * @return
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//
//        int id = item.getItemId();
//
//        if (id == R.id.navigation_set) {
//            // Handle the camera action
//        } else if (id == R.id.navigation_download) {
//
//        } else if (id == R.id.navigation_share) {
//
//        }
//
//
//        return true;
//    }

//    /**
//     * @param menu
//     * @return
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.navigation, menu);
//        return true;
//    }


}
