package io.tanners.taggedwallpaper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.WallpaperManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
//import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import io.tanners.taggedwallpaper.Util.ExternalFileStorageUtil;
import io.tanners.taggedwallpaper.Util.PermissionRequester;
import io.tanners.taggedwallpaper.Util.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.data.results.photo.PhotoResult;
import io.tanners.taggedwallpaper.network.images.ImageDownloader;
import io.tanners.taggedwallpaper.network.images.ImageSharer;

// https://developer.android.com/reference/android/support/v4/app/ActivityCompat.OnRequestPermissionsResultCallback.html
public class DisplayActivity extends AppCompatActivity implements android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback {
//    public final static String ARTIST = "ARTIST";
//    public final static String FULLIMAGE = "FULLIMAGE";
//    public final static String PREVIEW = "PREVIEW";
    public final static String RESULT = "RESULT";
    private ImageView mMainImageView;
    private final int STORAGE_PERMISSIONS = 128;
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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        loadToolBar();
        loadResources();
        setUpUiInteraction();
    }

    /**
     * http://blog.raffaeu.com/archive/2015/04/11/android-and-the-transparent-status-bar.aspx
     *
     * @return
     */
//    public int getStatusBarHeight() {
//        int result = 0;
//        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }

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
                downloadOrShareImage(IMAGE_DOWNLOAD|STORAGE_PERMISSIONS);
                return true;
            case R.id.share_menu_item:
                downloadOrShareImage(IMAGE_SHARE|STORAGE_PERMISSIONS);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Load resources for activity
     */
    private void loadResources()
    {
        mPhotoInfo = (new Gson()).fromJson(getIntent().getStringExtra(RESULT), PhotoResult.class);

        mMainImageView = (ImageView) findViewById(R.id.main_image_id);
        mProgressBar = (ProgressBar) findViewById(R.id.display_progress_bar);
        // set image into imageview
//        loadPreviewImage(getIntent().getStringExtra(PREVIEW));
        loadPreviewImage(mPhotoInfo.getLargeImageURL());
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

    /**
     * Load image via URL
     * @param mImageUrl
     */
    private void loadPreviewImage(String mImageUrl)
    {
        // Load image into imageview with options
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();
        RequestOptions cropOptions = new RequestOptions()
                .error(R.drawable.ic_error_black_48dp)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).centerCrop();
        // set up image
        Glide.with(this)
                .load(mImageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // on image load error
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                            mMainImageView.setImageDrawable(ContextCompat.getDrawable(DisplayActivity.this, R.drawable.ic_error_black_48dp));
                        }
                        else
                        {
                            mMainImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_error_black_48dp));
                        }

                        // hide progress bar
                        mProgressBar.setVisibility(View.GONE);
                        // show image
                        mMainImageView.setVisibility(View.VISIBLE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        mMainImageView.setVisibility(View.VISIBLE);
                        Log.i("DISPLAY", "OK11");
                        return false;
                    }
                })
                .apply(cropOptions)
                .transition(transitionOptions)
                .into(mMainImageView);
        // show imageview
        mMainImageView.setVisibility(View.VISIBLE);
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



        // set set transparent background
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //getSupportActionBar().setElevation(0);
       // enableStatusBarTransparent();

    }

    /**
     * http://blog.raffaeu.com/archive/2015/04/11/android-and-the-transparent-status-bar.aspx
     * for kitkat fix for transparent statusbar
     */
    private void enableStatusBarTransparent()
    {

//         create our manager instance after the content view is set
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//         enable status bar tint
//        tintManager.setStatusBarTintEnabled(true);
//         enable navigation bar tint
//        tintManager.setNavigationBarTintEnabled(true);
//         set the transparent color of the status bar, 20% darker
//        tintManager.setTintColor(Color.parseColor("#20000000"));

    }

    public static void setTranslucentStatusBar(Window window) {
        if (window == null) return;
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.LOLLIPOP) {
            setTranslucentStatusBarLollipop(window);
        } else if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatusBarKiKat(window);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setTranslucentStatusBarLollipop(Window window) {
        window.setStatusBarColor(
                window.getContext()
                        .getResources()
                        .getColor(R.color.transparent));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void setTranslucentStatusBarKiKat(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }


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
//                new WallpaperSetter(findViewById(R.id.display_activity_main_id), which, (ProgressBar) findViewById(R.id.lockscreen_progressbar), (ImageView) findViewById(R.id.lockscreen_image)).execute(getIntent().getStringExtra(FULLIMAGE));
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
    private void downloadOrShareImage(int requestCode)
    {
        // check if permissions are granted
        if(checkPermissions(requestCode))
        {
            Log.i("PERMISSIONS", "DEBUG 1");

            // no permissions needed, call code
            usePhoto(requestCode);
        }
        // permissions denied for some reason
        else
        {
            // runtime permissions came in at sdk 23
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                // set wallpaper based on image stream
                usePhoto(requestCode);

                Log.i("PERMISSIONS", "DEBUG 2");
            }
            // no permissions, do nothing
            Log.i("PERMISSIONS", "DEBUG 3");
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
                case IMAGE_DOWNLOAD|STORAGE_PERMISSIONS:
                case IMAGE_SHARE|STORAGE_PERMISSIONS:
                    usePhoto(requestCode);
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
     * @param code
     */
    private void usePhoto(int code){
        ExternalFileStorageUtil mStorageUtil = new ExternalFileStorageUtil();
        // check if external storage is writable
        if(mStorageUtil.isExternalStorageWritable())
        {
            // determine what to do per request
            switch(code)
            {
                case IMAGE_DOWNLOAD|STORAGE_PERMISSIONS:
                    // use that newly created image file to share or download
                    // you need to download before sharing
                    new ImageDownloader(this, findViewById(R.id.display_activity_main_id), getNewFile(code, mStorageUtil)).execute(mPhotoInfo.getImageURL());
                    break;
                case IMAGE_SHARE|STORAGE_PERMISSIONS:
                    // use that newly created image file to share or download
                    File mImageFile = getNewFile(code, mStorageUtil);
                    // create URI based off file provider
                    Uri mImageUri = null;
                    try {
                        // get uri from file provider
                        mImageUri = FileProvider.getUriForFile(DisplayActivity.this, "io.tanners.taggedwallpaper.fileprovider", mImageFile);
                    } catch (IllegalArgumentException e) {
                        Log.e("File_PROVIDER", "The selected file can't be shared");
                    }
                    // share image
                    new ImageSharer(this, findViewById(R.id.display_activity_main_id), mImageFile, mImageUri).execute(mPhotoInfo.getImageURL());
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


    private File getNewFile(int code, ExternalFileStorageUtil mStorageUtil) {
        // get filename
        String[] mImageUrlSplit = mPhotoInfo.getImageURL().split("/");
        String mImageUrlFileName = mImageUrlSplit[mImageUrlSplit.length-1];
        File mImageFile = null;

        switch (code) {
            case IMAGE_DOWNLOAD|STORAGE_PERMISSIONS:
                // create album
                File mImageDir = mStorageUtil.getAlbumStorageDir(MALBUMNAME);
                // create file based on name and album
                mImageFile = new File(mImageDir, mImageUrlFileName);
            case IMAGE_SHARE|STORAGE_PERMISSIONS:
                // create temp cache file
                try {
                    // mImageFile = new File(getFilesDir(), mImageUrlFileName);
                    mImageFile = File.createTempFile(mImageUrlFileName, null, getCacheDir());
                } catch (IOException e) {
                    // Error while creating file
                    Log.e("FILE_SHARE", "Error sharing image");
                }
        }
        // return image file reference
        return mImageFile;
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
