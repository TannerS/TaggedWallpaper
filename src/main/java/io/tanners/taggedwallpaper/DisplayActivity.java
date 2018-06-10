package io.tanners.taggedwallpaper;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import io.tanners.taggedwallpaper.Util.ExternalFileStorageUtil;
import io.tanners.taggedwallpaper.Util.FitSystemWindowsLayout;
import io.tanners.taggedwallpaper.Util.PermissionRequester;
import io.tanners.taggedwallpaper.Util.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;
import io.tanners.taggedwallpaper.model.results.photo.PhotoResult;
import io.tanners.taggedwallpaper.network.image.download.ImageDownloader;
import io.tanners.taggedwallpaper.interfaces.IImageLoadOptions;

// https://developer.android.com/reference/android/support/v4/app/ActivityCompat.OnRequestPermissionsResultCallback.html
public class DisplayActivity extends AppCompatActivity implements android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback {
    public final static String RESULT = "RESULT";
    private ImageView mMainImageView;
    private final int IMAGE_DOWNLOAD = 256;
    private final int IMAGE_SHARE = 512;
    private final String MALBUMNAME = "Wallpaper";
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;
    private PhotoResult mPhotoInfo;

    /**
     * Whether or not the system UI should be auto-hidden after
     */
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar
            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mMainImageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }

            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;

    /**
     * the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int UI_ANIMATION_DELAY = 50;
    private final Handler mHideHandler = new Handler();


    /**
     * When activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        loadToolBar();
        loadResources();
        loadSelectedPhoto();
        loadUserProfilePhoto();
        setUpUiInteraction();
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
                setImage(WallpaperSetter.WALLPAPER);
                return true;
            case R.id.lockscreen_menu_item:
                setImage(WallpaperSetter.LOCK_SCREEN);
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

    /**
     * Load resources for activity
     */
    private void loadResources() {
//        mPhotoInfo = (new Gson()).fromJson(getIntent().getStringExtra(RESULT), PhotoResult.class);
        mPhotoInfo = getIntent().getParcelableExtra(RESULT);
        mMainImageView = (ImageView) findViewById(R.id.main_image_id);
        mProgressBar = (ProgressBar) findViewById(R.id.display_progress_bar);
        ((TextView)findViewById(R.id.user_name)).setText(mPhotoInfo.getUser());
    }

    private void loadSelectedPhoto()
    {
        loadImage(mPhotoInfo.getLargeImageURL(), mMainImageView, new IImageLoadOptions() {
            @Override
            public void loadingImage() {
                mProgressBar.setVisibility(View.GONE);
                mMainImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void errorLoadingImage() {
                mProgressBar.setVisibility(View.GONE);
                mMainImageView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadUserProfilePhoto() {
        if (mPhotoInfo.getUserImageURL() == null || mPhotoInfo.getUserImageURL().length() <= 0) {
            ((ImageView) findViewById(R.id.user_profile_image)).setImageResource(R.drawable.ic_face_white_48dp);
        } else {
            loadImage(mPhotoInfo.getUserImageURL(), ((ImageView) findViewById(R.id.user_profile_image)), null);
        }
    }

    private void loadImage(String mUrl, final ImageView mView, final IImageLoadOptions mCallback) {
        Glide.with(DisplayActivity.this)
                .load(mUrl)
                .apply(loadGlideRequestOptions())
                .transition(loadGlideTransitions())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // on image load error
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                            Glide.with(DisplayActivity.this).load(ContextCompat.getDrawable(DisplayActivity.this, R.drawable.ic_error_black_48dp))
                                    .apply(loadGlideRequestOptions())
                                    .transition(loadGlideTransitions())
                                    .into(mView);
                        }
                        else {
                            Glide.with(DisplayActivity.this).load(getResources().getDrawable(R.drawable.ic_error_black_48dp))
                                    .apply(loadGlideRequestOptions())
                                    .transition(loadGlideTransitions())
                                    .into(mView);
                        }
                        // hide progress bar
                        if(mCallback != null)
                            mCallback.errorLoadingImage();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if(mCallback != null)
                            mCallback.loadingImage();
                        // https://stackoverflow.com/questions/32503327/glide-listener-doesnt-work
                        return false;
                    }
                })
                .into(mView);
    }

    /**
     * set up fullscreen user interaction
     */
    private void setUpUiInteraction()
    {
        mVisible = true;
        mControlsView = findViewById(R.id.wallpaper_options_layout);
        // Set up the user interaction to manually show or hide the system UI.
//        mProgressBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle();
//            }
//        });
        mMainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
    }

    /**
     * toggle fullscreen
     */
    private void toggle() {
        if (mVisible) {
            hide();
        // else show
        } else {
            show();
        }
    }

    /**
     * Hide UI
     */
    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();

        // hide controls
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        if (actionBar != null) {
            actionBar.hide();
        }

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);

        FitSystemWindowsLayout mainLayout = (FitSystemWindowsLayout) findViewById(R.id.display_activity_main_id);

        mainLayout.setFit(false);

    }

    /**
     * Show UI
     */
    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mMainImageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//        mProgressBar.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mControlsView.setVisibility(View.VISIBLE);
        mVisible = true;
        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);

        FitSystemWindowsLayout mainLayout = (FitSystemWindowsLayout) findViewById(R.id.display_activity_main_id);
        mainLayout.setFit(true);

    }

    private DrawableTransitionOptions loadGlideTransitions()
    {
        return  new DrawableTransitionOptions().crossFade();
    }

    private RequestOptions loadGlideRequestOptions()
    {
        return new RequestOptions()
                .error(R.drawable.ic_error_black_48dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).fitCenter();
    }

    /**
     * Load Actionbar
     */
    private void loadToolBar()
    {
        mToolbar = (Toolbar) findViewById(R.id.display_toolbar);
        setSupportActionBar(mToolbar);
        //setTranslucentStatusBar(getWindow());
       // mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // change icon to be a x not arrow
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_close);
        getSupportActionBar().setTitle("");

    }

    /**
     * http://blog.raffaeu.com/archive/2015/04/11/android-and-the-transparent-status-bar.aspx
     * for kitkat fix for transparent statusbar
     */
//    private void enableStatusBarTransparent()
//    {
//
////         create our manager instance after the content view is set
////        SystemBarTintManager tintManager = new SystemBarTintManager(this);
////         enable status bar tint
////        tintManager.setStatusBarTintEnabled(true);
////         enable navigation bar tint
////        tintManager.setNavigationBarTintEnabled(true);
////         set the transparent color of the status bar, 20% darker
////        tintManager.setTintColor(Color.parseColor("#20000000"));
//
//    }

//    public static void setTranslucentStatusBar(Window window) {
//        if (window == null) return;
//        int sdkInt = Build.VERSION.SDK_INT;
//        if (sdkInt >= Build.VERSION_CODES.LOLLIPOP) {
//            setTranslucentStatusBarLollipop(window);
//        } else if (sdkInt >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatusBarKiKat(window);
//        }
//    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private static void setTranslucentStatusBarLollipop(Window window) {
//        window.setStatusBarColor(
//                window.getContext()
//                        .getResources()
//                        .getColor(R.color.transparent));
//    }
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private static void setTranslucentStatusBarKiKat(Window window) {
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//    }
//

    /**
     * Check permission for given permission code.
     * The code lets the object know which set of permissions to load.
     * @param permissionCode
     * @return
     */
    private boolean checkPermissions(int permissionCode)
    {
        // request image downloading permissions
        // result will be in onRequestPermissionsResult
        return PermissionRequester.newInstance(this).requestNeededPermissions(new String[]{
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE},
                permissionCode);
    }

    /**
     * Set wallpaper based on kind
     * @param which
     */
    private void setImage(int which)
    {
        switch(which)
        {
            case WallpaperSetter.LOCK_SCREEN:
                new WallpaperSetter(findViewById(R.id.display_activity_main_id), which).execute(mPhotoInfo.getImageURL());
            case WallpaperSetter.WALLPAPER:
                new WallpaperSetter(findViewById(R.id.display_activity_main_id), which).execute(mPhotoInfo.getImageURL());
                break;
        }
    }

    /**
     * Recycle same code permissions for download and sharing image
     * @param requestCode
     */
    private void downloadImage(int requestCode)
    {
        // check if permissions are granted
        if(checkPermissions(requestCode))
        {
            // no permissions needed, call code
            downloadImage();
        }
        // permissions denied for some reason
        else
        {
            // runtime permissions came in at sdk 23
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                // set wallpaper based on image stream
                downloadImage();
            }
            // no permissions, do nothing
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
                case IMAGE_DOWNLOAD:
                    downloadImage();
                    break;
            }
        }
    }

    /**
     * Check permissions if they are granted or not
     * @param permissions
     * @param grantResults
     * @return
     */
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

    /**
     * Do something with image
     */
    private void downloadImage(){
        ExternalFileStorageUtil mStorageUtil = new ExternalFileStorageUtil();
        // check if external storage is writable
        if (mStorageUtil.isExternalStorageWritable()) {
            // use that newly created image file to share or download
            // you need to download before sharing
            new ImageDownloader(this,
                    getNewFile(mStorageUtil),
                    new ErrorCallBack() {
                        @Override
                        public void displayError() {
                            final Snackbar mFailSnackbar = SimpleSnackBarBuilder.createSnackBar(findViewById(R.id.display_activity_main_id),
                                    "ERROR: Image Cannot Be Downloaded",
                                    Snackbar.LENGTH_INDEFINITE);

                            mFailSnackbar.setAction("Close", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mFailSnackbar.dismiss();
                                }
                            });

                            mFailSnackbar.show();
                        }

                        @Override
                        public void displayNoError() {
                            final Snackbar mSnackbar = SimpleSnackBarBuilder.createSnackBar(findViewById(R.id.display_activity_main_id),
                                    "Image downloaded",
                                    Snackbar.LENGTH_INDEFINITE);

                            mSnackbar.setAction("Close", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mSnackbar.dismiss();
                                }
                            });

                            mSnackbar.show();
                        }
                    }
            ).execute(mPhotoInfo.getImageURL());
        }
        // cant read, connected to pc, ejected, etc
        else
        {
            // display error as snackbar
            displayStorageErrorSnackBar();
        }
    }

    private void shareImage()
    {
        // create new intent
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        // at this point, this is uri to file but actually writing to file is done
        // in the background task in the parent class
        shareIntent.putExtra(Intent.EXTRA_TEXT, mPhotoInfo.getImageURL());
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Share too..."));
    }


//
//    private File getNewFile(int code, ExternalFileStorageUtil mStorageUtil) {
    private File getNewFile(ExternalFileStorageUtil mStorageUtil) {
        // get filename
        String[] mImageUrlSplit = mPhotoInfo.getImageURL().split("/");
        String mImageUrlFileName = mImageUrlSplit[mImageUrlSplit.length-1];
        File mImageFile = null;

//        switch (code) {
//            case IMAGE_DOWNLOAD|STORAGE_PERMISSIONS:
                // create album
                File mImageDir = mStorageUtil.getAlbumStorageDir(MALBUMNAME);
                // create file based on name and album
                mImageFile = new File(mImageDir, mImageUrlFileName);
//            case IMAGE_SHARE|STORAGE_PERMISSIONS:
//                // create temp cache file
//                try {
//                    // mImageFile = new File(getFilesDir(), mImageUrlFileName);
//                    mImageFile = File.createTempFile(mImageUrlFileName, null, getCacheDir());
//                } catch (IOException e) {
//                    // Error while creating file
//                    Log.e("FILE_SHARE", "Error sharing image");
//                }
//        }
        // return image file reference
        return mImageFile;
    }

    private void displayStorageErrorSnackBar() {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(findViewById(R.id.display_activity_main_id),
                "ERROR: Cannot Access External Storage",
                Snackbar.LENGTH_INDEFINITE,
                "Close");
    }

    // TODO loader in future implmentation
    private class WallpaperSetter extends AsyncTask<String, Void, Boolean>
    {
        public final static int LOCK_SCREEN = 100;
        public final static int WALLPAPER = 200;
        private int which = -1;
        private View mRootView;

        /**
         * constructor
         * @param view
         * @param which
         */
        public WallpaperSetter(View view, int which)
        {
            this.mRootView = view;
            this.which = which;
        }

        /**
         *
         */
        @Override
        protected void onPreExecute()
        {
            // choose which wallpaper to set
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

        /**
         * connect to image to download
         * @param strUrl
         * @return
         */
        private InputStream getNetworkConnection(String strUrl)
        {
            URL url = null;

            try {
                url = new URL(strUrl);
                // connect to image url
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // set to get input
                connection.setDoInput(true);
                connection.connect();
                // return stream
                return connection.getInputStream();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * @param strs
         * @return
         */
        @Override
        protected Boolean doInBackground(String... strs)
        {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    // set wallpaper based on image stream
                    WallpaperManager.getInstance(DisplayActivity.this).setStream(getNetworkConnection(strs[0]), null, true, which);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            else
            {
                // get bitmap from stream
                Bitmap bitmap = BitmapFactory.decodeStream(getNetworkConnection(strs[0]));
                try {
                    // set wallpaper
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
            // if success
            if(result)
            {
                // display snackbar success message
                final Snackbar mGoodSnackbar = displaySuccessDownloadSnackBar();

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
                // display snackbar fail message
                final Snackbar mFailSnackbar = displayFailedDownloadSnackBar();

                mFailSnackbar.setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFailSnackbar.dismiss();
                    }
                });
                // show message
                mFailSnackbar.show();
            }
        }

        /**
         * display success snackbar
         * @return
         */
        private Snackbar displaySuccessDownloadSnackBar()
        {
            switch (this.which) {
                case WallpaperManager.FLAG_LOCK:
                    return SimpleSnackBarBuilder.createSnackBar(mRootView.findViewById(R.id.display_activity_main_id),
                            "Lock screen set!",
                            Snackbar.LENGTH_LONG);
                case WallpaperManager.FLAG_SYSTEM:
                    this.which = WallpaperManager.FLAG_SYSTEM;
                    return SimpleSnackBarBuilder.createSnackBar(mRootView.findViewById(R.id.display_activity_main_id),
                            "Wallpaper set!",
                            Snackbar.LENGTH_LONG);
            }

            return null;
        }

        /**
         * display error snackbar
         * @return
         */
        private Snackbar displayFailedDownloadSnackBar()
        {
            switch (this.which) {
                case WallpaperManager.FLAG_LOCK:
                    return SimpleSnackBarBuilder.createSnackBar(mRootView.findViewById(R.id.display_activity_main_id),
                            "ERROR: Lock screen cannot be set",
                            Snackbar.LENGTH_INDEFINITE);
                case WallpaperManager.FLAG_SYSTEM:
                    return SimpleSnackBarBuilder.createSnackBar(mRootView.findViewById(R.id.display_activity_main_id),
                            "ERROR: wallpaper cannot be set",
                            Snackbar.LENGTH_INDEFINITE);
            }

            return null;
        }
    }

}
