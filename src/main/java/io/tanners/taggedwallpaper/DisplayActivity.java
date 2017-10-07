package io.tanners.taggedwallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import io.tanners.taggedwallpaper.Util.ExternalFileStorageUtil;
import io.tanners.taggedwallpaper.Util.PermissionRequester;
import io.tanners.taggedwallpaper.Util.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.network.images.ImageDownloader;
import io.tanners.taggedwallpaper.network.images.ImageSharer;

// https://developer.android.com/reference/android/support/v4/app/ActivityCompat.OnRequestPermissionsResultCallback.html
public class DisplayActivity extends AppCompatActivity implements android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback {
    public final static String ARTIST = "ARTIST";
    public final static String FULLIMAGE = "FULLIMAGE";
    public final static String PREVIEW = "PREVIEW";
    private TextView artistTextView;
    private ImageView mainImageView;
    private final int STORAGE_PERMISSIONS = 128;
    private final int IMAGE_DOWNLOAD = 256;
    private final int IMAGE_SHARE = 512;
    private final String MALBUMNAME = "Wallpaper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        loadToolBar();
        //loadBottomNavigation();
        loadResources();
    }

//    private ViewGroup getRootView()
    private View getRootView()
    {
        return this.findViewById(android.R.id.content);
//        return (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
//    }
    }

    private void loadResources()
    {
        // get values passsed in from intent
//        artistTextView = (TextView) findViewById(R.id.artist_text_id);
//        String artist = "Photo by: " + getIntent().getStringExtra(ARTIST);
//        artistTextView.setText(artist);
        mainImageView = (ImageView) findViewById(R.id.main_image_id);
        // set image into imageview
        loadImage(getIntent().getStringExtra(PREVIEW));



        findViewById(R.id.wallpaper_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage(WallpaperSetter.WALLPAPER);

            }
        });

        findViewById(R.id.share_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadOrShareImage(STORAGE_PERMISSIONS|IMAGE_SHARE);

            }
        });

        findViewById(R.id.lockscreen_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage(WallpaperSetter.LOCK_SCREEN);

            }
        });

        findViewById(R.id.download_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadOrShareImage(STORAGE_PERMISSIONS|IMAGE_DOWNLOAD);

            }
        });


    }

    // TODO this is used few places, combine? also used in similarimages
    private void loadImage(String mImageUrl)
    {
        // Load image into imageview with options
//        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
        RequestOptions cropOptions = new RequestOptions()
//                .centerCrop()
//                .fitCenter()
                .error(R.drawable.ic_error_black_48dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        Glide.with(this)
                .load(mImageUrl)
                .apply(cropOptions)
//                .transition(transitionOptions)
                .into(mainImageView);
    }

    private void loadToolBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.display_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // change icon to be a x not arrow
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_close);
    }
//
//    private void enableNewFeatures()
//    {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//
//        }
//    }

//    private void loadBottomNavigation()
//    {
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.display_navigation);
//
//        // fix to make no items selected by default
////        navigation.getMenu().getItem(0).setCheckable(false);
//        navigation.getMenu().getItem(0).setChecked(false);
////        navigation.getMenu().getItem(0).setVisible(false);
//
//
//        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            // load nav items click listeners
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    // download image
//                    case R.id.navigation_download:
//                        item.setChecked(false);
//                        downloadOrShareImage(STORAGE_PERMISSIONS|IMAGE_DOWNLOAD);
//                        return true;
////                        break;
//
//                    // set as wallpaper
//                    case R.id.navigation_set:
//                        item.setChecked(false);
//                        setImage(WallpaperSetter.WALLPAPER);
//                        break;
//                    // share image
//                    case R.id.navigation_share:
//                        item.setChecked(false);
//                        downloadOrShareImage(STORAGE_PERMISSIONS|IMAGE_SHARE);
//                        break;
//                    // set as lockscreen
////                    case R.id.navigation_set_lock:
////                        setImage(WallpaperSetter.LOCK_SCREEN);
////                        break;
//                }
//                return false;
//            }
//        });
//    }

    private boolean checkPermissions(int permissionCode)
    {
        // request image downloading permissions
        // result will be in onRequestPermissionsResult
        return PermissionRequester.newInstance(this).requestNeededPermissions(new String[]{
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE},
                permissionCode);
    }

    private void setImage(int which)
    {
        //WallpaperManager wallpaperManager = WallpaperManager.getInstance(DisplayActivity.this);
        switch(which)
        {
            case WallpaperSetter.LOCK_SCREEN:
                new WallpaperSetter(which, (ProgressBar) findViewById(R.id.lockscreen_progressbar), (ImageView) findViewById(R.id.lockscreen_image)).execute(getIntent().getStringExtra(FULLIMAGE));
                break;
            case WallpaperSetter.WALLPAPER:
                new WallpaperSetter(which, (ProgressBar) findViewById(R.id.wallpaper_progressbar), (ImageView) findViewById(R.id.wallpaper_image)).execute(getIntent().getStringExtra(FULLIMAGE));
                break;
        }
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //if(wallpaperManager.isSetWallpaperAllowed())
//                new WallpaperSetter(which, f).execute(getIntent().getStringExtra(FULLIMAGE));
       // }
        //else
            // TODO for now
          //  new WallpaperSetter(which).execute(getIntent().getStringExtra(FULLIMAGE));

    }

    private void downloadOrShareImage(int requestCode)
    {
        if(checkPermissions(STORAGE_PERMISSIONS))
        {
            usePhoto(requestCode);
            Log.i("PHOTO", "DOWNLOAD 1");
        }
    }

    // this is called after ActivityCompat.requestPermissions located inside PermissionRequester
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        // check if ALL permissions were granted
        if(permissionGrantChecker(permissions, grantResults)) {
            // do task based on which granted permissions
            switch(requestCode)
            {
                case STORAGE_PERMISSIONS|IMAGE_DOWNLOAD:
                case STORAGE_PERMISSIONS|IMAGE_SHARE:
                    usePhoto(requestCode);
                    break;
            }
        }
    }

    private boolean permissionGrantChecker(String permissions[], int[] grantResults)
    {
        // check if all permissions were granted
        for(int i = 0; i < permissions.length; i++)
        {
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
                // found a non granted permissions
                return false;
        }
        // all permissions granted
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(final Menu menu)
//    {
////        getMenuInflater().inflate(R.menu.navigation, menu);
////
////        MenuItem item = menu.findItem(R.id.navigation_set_lock);
////
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//////            item.setVisible(true);
////            // for now
////            item.setVisible(false);
////        }
////        else
////        {
////            item.setVisible(false);
////        }
//        return true;
//    }

    private void usePhoto(int code){
        ExternalFileStorageUtil mStorageUtil = new ExternalFileStorageUtil();
        // check if external storage is writable
        if(mStorageUtil.isExternalStorageWritable())
        {
            String[] mImageUrlSplit = getIntent().getStringExtra(FULLIMAGE).split("/");
            String mImageUrlFileName = mImageUrlSplit[mImageUrlSplit.length-1];

            File mImageDir = mStorageUtil.getAlbumStorageDir(MALBUMNAME);

            File mImageFile = new File(mImageDir, mImageUrlFileName);

            switch(code)
            {
                case STORAGE_PERMISSIONS|IMAGE_DOWNLOAD:
                    new ImageDownloader(this, findViewById(R.id.display_activity_main_id), (ProgressBar) findViewById(R.id.download_progressbar), (ImageView) findViewById(R.id.download_image), mImageFile).execute(getIntent().getStringExtra(FULLIMAGE));
                    break;
                case STORAGE_PERMISSIONS|IMAGE_SHARE:
                    new ImageSharer(this, findViewById(R.id.display_activity_main_id), (ProgressBar) findViewById(R.id.share_progressbar), (ImageView) findViewById(R.id.share_image), mImageFile).execute(getIntent().getStringExtra(FULLIMAGE));
                    break;
            }
        }
        // cant read, connected to pc, ejected, etc
        else
        {
            // display error as snackbar
            displayStorageErrorSnackBar();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {

            if (requestCode == STORAGE_PERMISSIONS) {

            }
        }
    }

    private void displayStorageErrorSnackBar() {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(findViewById(R.id.display_activity_main_id),
                "ERROR: Cannot Access External Storage",
                Snackbar.LENGTH_INDEFINITE,
                "Close");
    }

    private class WallpaperSetter extends AsyncTask<String, Void, Boolean>
    {
        public final static int LOCK_SCREEN = 100;
        public final static int WALLPAPER = 200;
        private int which = -1;
        private ProgressBar mProgressBar;
        private ImageView mImageView;

        public WallpaperSetter(int which, ProgressBar mProgressBar, ImageView mImageView)
        {
            this.which = which;
            this.mImageView = mImageView;
            this.mProgressBar =mProgressBar;
        }

        @Override
        protected void onPreExecute()
        {
            mProgressBar.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.GONE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                switch (this.which) {
                    case LOCK_SCREEN:
                        this.which = WallpaperManager.FLAG_LOCK;
                        break;
                    case WALLPAPER:
                        this.which = WallpaperManager.FLAG_SYSTEM;
                        break;
                }
            }
            else
            {
                this.which = WallpaperManager.FLAG_SYSTEM;
            }
        }

        private InputStream getNetworkConnection(String strUrl)
        {
            URL url = null;

            try {
                url = new URL(strUrl);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                return connection.getInputStream();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected Boolean doInBackground(String... strs)
        {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    WallpaperManager.getInstance(DisplayActivity.this).setStream(getNetworkConnection(strs[0]), null, true, which);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            else
            {
                Bitmap bitmap = BitmapFactory.decodeStream(getNetworkConnection(strs[0]));
                try {
                    WallpaperManager.getInstance(DisplayActivity.this).setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            super.onPostExecute(result);

            mImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);

            if(result)
            {
                SimpleSnackBarBuilder.createSnackBar(getRootView().findViewById(R.id.display_container),
                        "Image set!",
                        Snackbar.LENGTH_LONG);
            }
            else
            {
                SimpleSnackBarBuilder.createSnackBar(getRootView().findViewById(R.id.display_container),
                        "Error setting image!",
                        Snackbar.LENGTH_LONG);
            }
        }
    }
}
