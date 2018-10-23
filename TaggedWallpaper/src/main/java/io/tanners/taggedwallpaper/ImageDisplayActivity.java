package io.tanners.taggedwallpaper;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.dev.tanners.backgroundsetter.BackgroundSet;
import io.dev.tanners.backgroundsetter.BackgroundSetter;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;
import io.tanners.taggedwallpaper.network.image.download.ImageDownloader;
import io.tanners.taggedwallpaper.support.file.ExternalFileStorageUtil;
import io.tanners.taggedwallpaper.support.permissions.PermissionRequester;
import io.tanners.taggedwallpaper.support.builder.snackbar.SimpleSnackBarBuilder;

// TODO refactor the class to handle the permissions and background decoupled from the activity
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
    private final String ALBUMNAME = "Wallpaper";

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
        return new BackgroundSetter.BackgroundCallback() {
            @Override
            public void OnCompletedCallback(Boolean results) {
                if(results)
                {
                    final Snackbar mGoodSnackbar = SimpleSnackBarBuilder.createSnackBar(findViewById(R.id.display_actvity_container),
                            "Image set.",
                            Snackbar.LENGTH_INDEFINITE);

                    mGoodSnackbar.setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mGoodSnackbar.dismiss();
                        }
                    });
                    // show message
                    mGoodSnackbar.show();
                }
                else
                {
                    final Snackbar mBadSnackbar = SimpleSnackBarBuilder.createSnackBar(findViewById(R.id.display_actvity_container),
                            "Error has occurred, image not set.",
                            Snackbar.LENGTH_INDEFINITE);

                    mBadSnackbar.setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mBadSnackbar.dismiss();
                        }
                    });
                    // show message
                    mBadSnackbar.show();
                }
            }
        };
    }

    protected void onNetworkChange(boolean isOn) {
        if(isOn) {
            // nothing needed to do
        } else {
            // TODO throw error, where click ok closes activity
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
        // permissions denied for some reason
        else {
            // runtime permissions came in at sdk 23
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                // set wallpaper based on image stream
                downloadImage();
            }
            // no permissions, do nothing
        }
    }

    /**
     * Do something with image
     */
    private void downloadImage() {
//        ExternalFileStorageUtil mStorageUtil = new ExternalFileStorageUtil();
//        // check if external storage is writable
//        if (mStorageUtil.isExternalStorageWritable()) {
//            // use that newly created image file to share or download
//            // you need to download before sharing
//            new ImageDownloader(this,
//                    getNewFile(mStorageUtil),
//                    new ErrorCallBack() {
//                        @Override
//                        public void displayError(String mMessage) {
//                            final Snackbar mFailSnackbar = SimpleSnackBarBuilder.createSnackBar(findViewById(R.id.display_actvity_container),
//                                    mMessage,
//                                    Snackbar.LENGTH_INDEFINITE);
//
//                            mFailSnackbar.setAction("Close", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    mFailSnackbar.dismiss();
//                                }
//                            });
//
//                            mFailSnackbar.show();
//                        }
//
//                        @Override
//                        public void displayNoError(String mMessage) {
//                            final Snackbar mSnackbar = SimpleSnackBarBuilder.createSnackBar(findViewById(R.id.display_actvity_container),
//                                    mMessage,
//                                    Snackbar.LENGTH_INDEFINITE);
//
//                            mSnackbar.setAction("Close", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    mSnackbar.dismiss();
//                                }
//                            });
//
//                            mSnackbar.show();
//                        }
//                    }
//            ).execute(mPhotoInfo.getImageURL());
//        }
//        // cant read, connected to pc, ejected, etc
//        else {
//            // display error as snackbar
//            displayStorageErrorSnackBar();
//        }
    }

    private void shareImage() {
        // create new intent
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        // at this point, this is uri to file but actually writing to file is done
        // in the background task in the parent class
//        shareIntent.putExtra(Intent.EXTRA_TEXT, mPhotoInfo.getImageURL());
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Share too..."));
    }

//    private File getNewFile(ExternalFileStorageUtil mStorageUtil) {
//        // get filename
//        String[] mImageUrlSplit = mPhotoInfo.getImageURL().split("/");
//        String mImageUrlFileName = mImageUrlSplit[mImageUrlSplit.length - 1];
//        File mImageFile = null;
//        // create album
//        File mImageDir = mStorageUtil.getAlbumStorageDir(ALBUMNAME);
//        // create file based on name and album
//        mImageFile = new File(mImageDir, mImageUrlFileName);
//        // return image file reference
//        return mImageFile;
//    }

    private void displayStorageErrorSnackBar() {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(findViewById(R.id.display_actvity_container),
                "ERROR: Cannot Access External Storage",
                Snackbar.LENGTH_INDEFINITE,
                "Close");
    }
}