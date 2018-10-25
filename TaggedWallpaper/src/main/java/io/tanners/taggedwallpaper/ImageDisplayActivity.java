package io.tanners.taggedwallpaper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.UUID;

import io.dev.tanners.backgroundsetter.BackgroundSet;
import io.dev.tanners.backgroundsetter.BackgroundSetter;
import io.dev.tanners.downloader.Downloader;
import io.dev.tanners.downloader.network.DownloaderConnection;
import io.dev.tanners.snackbarbuilder.SimpleSnackBarBuilder;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.support.permissions.PermissionRequester;

public class ImageDisplayActivity extends SupportActivity {
    public final static String RESULT = "RESULT";
    private Photo mPhoto;
    private ImageView mainImage;
    private ImageView profileImage;
    private TextView imageTitle;
    private TextView userName;
    private TextView userUsername;
    private View bottomBorder;
    private ScrollView mContainer;
    private final int IMAGE_DOWNLOAD = 256;

    /**
     * When activity is created
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        getPhotoData();
        setUpToolBar(R.id.display_toolbar, Color.parseColor(mPhoto.getColor()));
        loadResources();
        setResources();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.display_act_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.homescreen_menu_item:
                BackgroundSet.setBackground(
                        this,
                        mPhoto.getLinks().getDownload_location(),
                        getBackgroundCallBack()
                );
                return true;
            case R.id.lockscreen_menu_item:
                BackgroundSet.setLockScreen(
                        this,
                        mPhoto.getLinks().getDownload_location(),
                        getBackgroundCallBack()
                );
                return true;
            case R.id.download_menu_item:
                downloadImage(IMAGE_DOWNLOAD);
                return true;
            case R.id.share_menu_item:
                shareImage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private BackgroundSetter.BackgroundCallback getBackgroundCallBack()
    {
        return results -> {

            String mMessage = "";

            if(results) {
                mMessage = "Image set.";
            } else {
                mMessage = "Error has occurred, image not set.";
            }

            final Snackbar mResultMessageSnackBar = SimpleSnackBarBuilder.createSnackBar(findViewById(R.id.display_actvity_container),
                    mMessage,
                    Snackbar.LENGTH_INDEFINITE);

            mResultMessageSnackBar.setAction("Close", v -> mResultMessageSnackBar.dismiss());
            // show message
            mResultMessageSnackBar.show();
        };
    }

    protected void onNetworkChange(boolean isOn) {
        if(!isOn) {
            final Snackbar mSnackbar = SimpleSnackBarBuilder.createSnackBar(findViewById(R.id.display_actvity_container),
                    "NO NETWORK CONNECTION, CLOSING...",
                    Snackbar.LENGTH_INDEFINITE);

            mSnackbar.setAction("Close", (View.OnClickListener) v -> {
                // close snackbar
                mSnackbar.dismiss();
                // close activity
                finish();
            });

            mSnackbar.show();
        }
    }

    private void getPhotoData() {
        mPhoto = getIntent().getParcelableExtra(RESULT);
    }

    /**
     * Load resources for activity
     */
    private void loadResources() {
        mainImage = findViewById(R.id.main_image);
        profileImage = findViewById(R.id.profile_image);
        imageTitle = findViewById(R.id.main_image_title);
        userName = findViewById(R.id.user_name);
        userUsername = findViewById(R.id.user_username);
        bottomBorder = findViewById(R.id.bottom_border);
        mContainer = findViewById(R.id.display_actvity_container);
    }

    private void setResources() {
        loadMainImage();
        loadProfileImage();
        imageTitle.setText(mPhoto.getDescription());
        userName.setText(mPhoto.getUser().getName());
        userUsername.setText(mPhoto.getUser().getUsername());
        bottomBorder.setBackgroundColor(Color.parseColor(mPhoto.getColor()));
        mContainer.setBackgroundColor(Color.parseColor(mPhoto.getColor()));
    }

    private void loadMainImage() {
        // set up transition
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
        // load image view using glide
       Glide.with(this)
               .load(mPhoto.getUrls().getRegular())
               .apply(new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.ic_error_black_48dp)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                )
                .transition(transitionOptions)
                .into(mainImage);
    }

    private void loadProfileImage() {
        // set up transition
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
        // load image view using glide
        Glide.with(this)
                .load(
                        mPhoto.getUser()
                                .getProfile_image()
                                .getMedium()
                ).apply(new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_error_black_48dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        )
                .transition(transitionOptions)
                .into(profileImage);
    }

    /**
     * Load Actionbar
     */
    protected void setUpToolBar(int id)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // change icon to be a x not arrow
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_close);
        getSupportActionBar().setTitle("");
    }

    /**
     * Load Actionbar
     */
    protected void setUpToolBar(int id, int color)
    {
        super.setUpToolBar(id);

        mToolbar.setBackgroundColor(color);

        setUpToolBar(id);
    }

    /**
     * Check permission for given permission code.
     * The code lets the object know which set of permissions to load.
     *
     * @param permissionCode
     * @return
     */
    protected boolean checkPermissions(int permissionCode) {
        // request image downloading permissions
        // result will be in onRequestPermissionsResult
        // TODO handle all/needed permissions, someday ...
        return PermissionRequester.newInstance(this).requestNeededPermissions(new String[]{
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE},
                permissionCode);
    }

    /**
     * Check permissions if they are granted or not
     *
     * @param permissions
     * @param grantResults
     * @return
     */
    private boolean permissionGrantChecker(String permissions[], int[] grantResults) {
        // check if all permissions were granted
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                // found a non granted permissions
                return false;
        }
        // all permissions granted
        return true;
    }

    // this is called after ActivityCompat.requestPermissions located inside PermissionRequester
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        // check if ALL permissions were granted
        if (permissionGrantChecker(permissions, grantResults)) {
            // do task based on which granted permissions
            switch (requestCode) {
                case IMAGE_DOWNLOAD:
                    downloadImage(IMAGE_DOWNLOAD);
                    break;
            }
        }
    }

    /**
     * Recycle same code permissions for download and sharing image
     *
     * @param requestCode
     */
    private void downloadImage(int requestCode) {
        // check if permissions are granted
        if (checkPermissions(requestCode)) {
            // no permissions needed, call code
            downloadImage();
        }
    }

    private String generateUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * Download image
     */
    private void downloadImage() {

        Downloader mImageDownloader = new Downloader(this);
        mImageDownloader.downloadFile(
                generateUUID(),
                mPhoto.getLinks().getDownload_location(),
                new DownloaderConnection.ErrorCallBack() {
                    @Override
                    public void displayError(String message) {
                        displayCustomSnackbar(message);
                    }

                    @Override
                    public void displayNoError(String message) {
                        displayCustomSnackbar("Image " + message);
                    }
                }
        );
    }

    private String getShareMessage(String mData)
    {
        return "Please enjoy this wallpaper image!: " + mData;
    }

    private void shareImage() {
        // create new intent
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        // at this point, this is uri to file but actually writing to file is done
        // in the background task in the parent class
        shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                getShareMessage(
                        mPhoto.getLinks().getDownload_location()
                )
        );
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Share too..."));
    }

    private void displayCustomSnackbar(String mMessage) {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(findViewById(R.id.display_actvity_container),
                "ERROR: Cannot Access External Storage",
                Snackbar.LENGTH_INDEFINITE,
                "Close");
    }
}