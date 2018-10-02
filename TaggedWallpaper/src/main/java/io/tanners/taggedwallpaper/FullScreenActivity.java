package io.tanners.taggedwallpaper;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.tanners.taggedwallpaper.support.builder.snackbar.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.support.file.ExternalFileStorageUtil;
import io.tanners.taggedwallpaper.support.permissions.PermissionRequester;

/**
 * This uses the code from Android's prebuilt fullscreen activity as part of the "Create new activity" window when in Android Studio
 */
// https://developer.android.com/reference/android/support/v4/app/ActivityCompat.OnRequestPermissionsResultCallback.html
public class FullScreenActivity extends AppCompatActivity {
    private boolean mVisible;

    /**
     * the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int UI_ANIMATION_DELAY = 50;
    private final Handler mHideHandler = new Handler();
    private View mControlsView;

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
//            mMainImageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

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

    /**
     * When activity is created
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

//        loadToolBar();
//        loadResources();
//        loadSelectedPhoto();
//        loadUserProfilePhoto();
        setUpUiInteraction();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.display_act_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.homescreen_menu_item:
//                setImageWithLoader(WallpaperSetter.WALLPAPER);
//                return true;
//            case R.id.lockscreen_menu_item:
//                setImageWithLoader(WallpaperSetter.LOCK_SCREEN);
//                return true;
//            case R.id.download_menu_item:
//                downloadImage(IMAGE_DOWNLOAD);
//                return true;
//            case R.id.share_menu_item:
//                shareImage();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


    /**
     * set up fullscreen user interaction
     */
    private void setUpUiInteraction() {
        mVisible = true;
        mControlsView = findViewById(R.id.wallpaper_options_layout);
        // Set up the user interaction to manually show or hide the system UI.
//        mProgressBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle();
//            }
//        });
//        mMainImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle();
//            }
//        });
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

//        FitSystemWindowsLayout mainLayout = (FitSystemWindowsLayout) findViewById(R.id.display_activity_main_id);
//        FrameLayout mainLayout = (FitSystemWindowsLayout) findViewById(R.id.display_activity_main_id);

//        mainLayout.setFit(false);

    }

    /**
     * Show UI
     */
    @SuppressLint("InlinedApi")
    private void show() {
//        // Show the system bar
//        mMainImageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
////        mProgressBar.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mControlsView.setVisibility(View.VISIBLE);
        mVisible = true;
//        // Schedule a runnable to display UI elements after a delay
//        mHideHandler.removeCallbacks(mHidePart2Runnable);
//        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);

//        FitSystemWindowsLayout mainLayout = (FitSystemWindowsLayout) findViewById(R.id.display_activity_main_id);
//        FrameLayout mainLayout = (FitSystemWindowsLayout) findViewById(R.id.display_activity_main_id);
//        mainLayout.setFit(true);

    }
}