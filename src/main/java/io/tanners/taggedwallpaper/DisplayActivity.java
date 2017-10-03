package io.tanners.taggedwallpaper;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import io.tanners.taggedwallpaper.Util.ExternalFileStorageUtil;
import io.tanners.taggedwallpaper.Util.PermissionRequester;
import io.tanners.taggedwallpaper.Util.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.network.images.ImageDownloader;

// https://developer.android.com/reference/android/support/v4/app/ActivityCompat.OnRequestPermissionsResultCallback.html
public class DisplayActivity extends AppCompatActivity implements android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback {
    public final static String ARTIST = "ARTIST";
    public final static String FULLIMAGE = "FULLIMAGE";
    public final static String PREVIEW = "PREVIEW";
    private TextView artistTextView;
    private ImageView mainImageView;
    private final int STORAGE_PERMISSIONS = 2;
    private final String MALBUMNAME = "Wallpaper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        loadToolBar();
        loadBottomNavigation();
        loadResources();
    }

    private ViewGroup getRootView()
    {
        return (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
    }

    private void loadResources()
    {
        // get values passsed in from intent
        artistTextView = (TextView) findViewById(R.id.artist_text_id);
        String artist = "Photo by: " + getIntent().getStringExtra(ARTIST);
        artistTextView.setText(artist);
        mainImageView = (ImageView) findViewById(R.id.main_image_id);
        // set image into imageview
        loadImage(getIntent().getStringExtra(PREVIEW));
    }

    // TODO this is used few places, combine? also used in similarimages
    private void loadImage(String mImageUrl)
    {
        // Load image into imageview with options
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
        RequestOptions cropOptions = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_error_black_48dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        Glide.with(this)
                .load(mImageUrl)
                .apply(cropOptions)
                .transition(transitionOptions)
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

    private void loadBottomNavigation()
    {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.display_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            // load nav items click listeners
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    // download image
                    case R.id.navigation_download:
                        downloadImage();
                        return true;
                    // set as wallpaper
                    case R.id.navigation_set:
                        setImage();
                        return true;
                    // share image
                    case R.id.navigation_share:
                        shareImage(new ImageDownloader.imageDownloaderCallBack() {
                            @Override
                            public void shareImage(Uri uri) {
                                Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                shareIntent.setType("image/jpeg");
//                    startActivity(Intent.createChooser(shareIntent, getResources().getText("Share too"));
                                startActivity(Intent.createChooser(shareIntent, "Share too..."));
                            }
                        });
                        return true;
                }
                return false;
            }

        });
    }

    private void downloadImage()
    {
        downloadImage(null);
    }


    private void downloadImage(ImageDownloader.imageDownloaderCallBack mCallback)
    {
        // request image downloading permissions
        // result will be in onRequestPermissionsResult
        if(PermissionRequester.newInstance(this).requestNeededPermissions(new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE},
                STORAGE_PERMISSIONS))
        {
            savePhoto(mCallback);
            Log.i("PHOTO", "DOWNLOAD 1");


        }
        else
            Log.i("PHOTO", "DOWNLOAD 2");

    }


    private void setImage()
    {
        Log.i("NAV", "SET");
    }

    private void shareImage(ImageDownloader.imageDownloaderCallBack mCallback)
    {
        downloadImage(mCallback);

        Log.i("NAV", "SHARE");
    }

    public void savePhoto() {
        savePhoto(null);
    }



    // this is called after ActivityCompat.requestPermissions located inside PermissionRequester
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        // do task based on which granted permissions
        switch(requestCode)
        {
            // user chose to download image
            case STORAGE_PERMISSIONS:
                // check if ALL permissions were granted
                if(permissionGrantChecker(permissions, grantResults))
                    // do download task
                    onGrantedDownloadPermissions();
                break;
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

    private void onGrantedDownloadPermissions()
    {
        savePhoto();
    }

//    public void savePhoto(){


    public void savePhoto(ImageDownloader.imageDownloaderCallBack mCallback){
        ExternalFileStorageUtil mStorageUtil = new ExternalFileStorageUtil();
        // check if external storage is writable
        if(mStorageUtil.isExternalStorageWritable())
        {

            Log.i("PHOTO", getIntent().getStringExtra(FULLIMAGE));


            String[] mImageUrlSplit = getIntent().getStringExtra(FULLIMAGE).split("/");
            String mImageUrlFileName = mImageUrlSplit[mImageUrlSplit.length-1];

            File mImageDir = mStorageUtil.getAlbumStorageDir(MALBUMNAME);

            File mImageFile = new File(mImageDir, mImageUrlFileName);

            new ImageDownloader(this, findViewById(R.id.display_activity_main_id), mImageFile, mCallback).execute(getIntent().getStringExtra(FULLIMAGE));
//            new ImageDownloader(mImageFile, mGoodSnackbar, mBadSnackbar).execute();

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
//
//    private Snackbar displaySuccessDownloadSnackBar()
//    {
//        return SimpleSnackBarBuilder.createSnackBar(findViewById(R.id.display_activity_main_id),
//                "Image Downloaded",
//                Snackbar.LENGTH_LONG);
//    }

//    private Snackbar displayFailedDownloadSnackBar()
//    {
//        return SimpleSnackBarBuilder.createSnackBar(findViewById(R.id.display_activity_main_id),
//                "ERROR: Image Cannot Be Downloaded",
//                Snackbar.LENGTH_INDEFINITE);
//    }
//
    private void displayStorageErrorSnackBar() {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(findViewById(R.id.display_activity_main_id),
                "ERROR: Cannot Access External Storage",
                Snackbar.LENGTH_INDEFINITE,
                "Close");
    }


}
