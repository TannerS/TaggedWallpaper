package io.tanners.taggedwallpaper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
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
import java.io.File;
import java.util.Date;
import io.dev.tanners.backgroundsetter.BackgroundSet;
import io.dev.tanners.backgroundsetter.BackgroundSetter;
import io.dev.tanners.snackbarbuilder.SimpleSnackBarBuilder;
import io.dev.tanners.wallpaperresources.ImageRequester;
import io.dev.tanners.wallpaperresources.callbacks.post.download.OnPostDownload;
import io.dev.tanners.wallpaperresources.callbacks.post.single.OnPostSingle;
import io.dev.tanners.wallpaperresources.loader.rest.download.RestDownloadLoader;
import io.dev.tanners.wallpaperresources.models.photos.download.Download;
import io.dev.tanners.wallpaperresources.models.photos.photo.Photo;
import io.tanners.taggedwallpaper.db.ImageDatabase;
import io.tanners.taggedwallpaper.db.ImageEntry;
import io.tanners.taggedwallpaper.db.ImageExecutor;
import io.tanners.taggedwallpaper.support.permissions.PermissionRequester;

//public class ImageDisplayActivity extends SupportActivity implements LoaderManager.LoaderCallbacks<Boolean> {
public class ImageDisplayActivity extends SupportActivity {
    public final static String PHOTO_ITEM_ENTRY_POINT = "PHOTO_ITEM_ENTRY_POINT";
    public final static String DATABASE_ITEM_ENTRY_POINT = "DATABASE_ITEM_ENTRY_POINT";

    private Photo mPhoto;
    private ImageView mainImage;
    private ImageView profileImage;
    private TextView imageTitle;
    private TextView userName;
    private TextView userUsername;
    private View bottomBorder;
    private ScrollView mContainer;
    private final int IMAGE_DOWNLOAD = 256;
    private ImageDatabase mDb;
//    private int imageLoaderId = 99999;
    private ImageView mFavoriteStar;
    private boolean isFavorite = false;

    /**
     * When activity is created
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        loadResources();
        dbInit();
        getEntryPoint();
        //loadLoader();
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.display_act_menu, menu);
        return true;
    }

    /**
     * Able to set home/lock screen download and share
     *
     * @param item
     * @return
     */
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

    /**
     * Init the database object to check for favorites
     */
    protected void dbInit()
    {
        mDb = ImageDatabase.getInstance(getApplicationContext());
    }

    /**
     * Save current photo to db
     */
    private void saveCurrentImage()
    {
        // get executor to be able to run insert on separate thread
        ImageExecutor.getInstance().mDiskIO().execute(() -> {
            Log.i("DATABASE_STORAGE", "SAVING***********************************************************************");
            mDb.getImageDao().insertPhotoEntry(createDatabaseObject());
        });
    }

    /**
     * Del current photo from db
     */
    private void delCurrentImage()
    {
        // get executor to be able to run insert on separate thread
        ImageExecutor.getInstance().mDiskIO().execute(() -> {
            Log.i("DATABASE_STORAGE", "REMOVING***********************************************************************");
            mDb.getImageDao().deletePhotoEntry(createDatabaseObject());
        });
    }

    /**
     * Convert photo object to ImageEntry to insert into database
     *
     * @return
     */
    private ImageEntry createDatabaseObject()
    {
        ImageEntry mEntry = new ImageEntry();
        mEntry.setId(mPhoto.getId());
        mEntry.setImageUrl(mPhoto.getUrls().getRegular());
        mEntry.setTimestamp(new Date());

        return mEntry;
    }

    /**
     * Custom background callback on wallpaper set
     * @return
     */
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

    /**
     * Behavior of activity depends on if there is a current network connection
     *
     * @param isOn
     */
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

    /**
     * Activity can be started via photo object click or database object click.
     * This will handle both cases and load page accordingly
     */
    private void getEntryPoint() {
        if(getIntent().hasExtra(PHOTO_ITEM_ENTRY_POINT)) {
            mPhoto = getIntent().getParcelableExtra(PHOTO_ITEM_ENTRY_POINT);
            setResources();
        }
        else if (getIntent().hasExtra(DATABASE_ITEM_ENTRY_POINT)) {
            loadDataFromDataBase(getIntent().getParcelableExtra(DATABASE_ITEM_ENTRY_POINT));
        }
    }

    /**
     * Use ImageEntry object to load photo object from api
     */
    private void loadDataFromDataBase(ImageEntry mEntry)
    {
        ImageRequester mImageRequester = new ImageRequester(this);

        mImageRequester.getPhoto(mEntry.getId(), mData -> {
            mPhoto = mData;
            setResources();
            // since this only happens via database call, then it must been a favorite
            setFavoriteStar(true);
        });
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
        mFavoriteStar = findViewById(R.id.favorite_star);
        // assume not a favorite till noted other wise
        setFavoriteStar(false);
        // set actions when liking / un-liking image
        mFavoriteStar.setOnClickListener(
                new View.OnClickListener() {
                    /**
                     * Click action for favorites button
                     * @param v
                     */
                    @Override
                    public void onClick(View v) {
                        if(!isFavorite) {
                            // change icon to show saved image
                            setFavoriteStar(true);
                            // save movie
                            saveCurrentImage();
                            // display message to UI
                            displayCustomSnackbar("Wallpaper saved", Snackbar.LENGTH_SHORT);
                        } else {
                            // change icon to show removed image
                            setFavoriteStar(false);
                            // remove movie
                            delCurrentImage();
                            // display message to UI
                            displayCustomSnackbar("Wallpaper removed", Snackbar.LENGTH_SHORT);
                        }
                    }
                }
        );
    }

    private void setFavoriteStar(boolean mSet)
    {
        if(mSet)
            mFavoriteStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_black_24dp));
        else
            mFavoriteStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_black_24dp));

        isFavorite = mSet;
    }

    /**
     * Set UI resources
     */
    private void setResources() {
        loadMainImage();
        loadProfileImage();
        setUpToolBar(R.id.display_toolbar, Color.parseColor(mPhoto.getColor()));

        imageTitle.setText(mPhoto.getDescription());
        userName.setText(mPhoto.getUser().getName());
        userUsername.setText(mPhoto.getUser().getUsername());
        bottomBorder.setBackgroundColor(Color.parseColor(mPhoto.getColor()));
        mContainer.setBackgroundColor(Color.parseColor(mPhoto.getColor()));
    }

    /**
     * Load main image for activity
     */
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

    /**
     * Load profile image of user who supplied the photo
     */
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

    /**
     * Download image click action
     */
    private void downloadImage() {
        ImageRequester mImageRequester = new ImageRequester(this);
        mImageRequester.getDownloadPhoto(
                mPhoto.getId(),
                mPhoto.getLinks().getDownload_location(),
                "wallpaper",
                new OnPostDownload() {
                    @Override
                    public void onPostCall(Download mData) {
                        // not needed, may remove
                    }

                    @Override
                    public void onPostCall(RestDownloadLoader.RestDownloadLoaderReturn mData) {
                        if(mData.isGood && mData.mFile != null) {
                            displayCustomSnackbar("Image downloaded");
                            callMediaScanner(ImageDisplayActivity.this, mData.mFile);
                        } else if(mData.errorMessage != null) {
                            try {
                                throw new Exception(mData.errorMessage);
                            } catch (Exception e) {
                                displayCustomSnackbar(e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
    }

    /**
     * updates gallery by scanner the newly added image
     */
    public void callMediaScanner(Context mContext, File mFile) {
        Log.i("DOWNLOAD", "FILE LOCATION: " + mFile.getAbsolutePath());

        // https://stackoverflow.com/questions/9414955/trigger-mediascanner-on-specific-path-folder-how-to
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MediaScannerConnection.scanFile(
                    mContext, new String[]{
                            mFile.getAbsolutePath()
                    },
                    null,
                    (path, uri) -> {
                        //something that you want to do
                    });
        } else {
            mContext.sendBroadcast(
                    new Intent(Intent.ACTION_MEDIA_MOUNTED,
                            Uri.parse("file://" + mFile.getAbsolutePath()
                            )
                    )
            );
        }
    }

    /**
     * Share message for image share action
     * @param mData
     * @return
     */
    private String getShareMessage(String mData)
    {
        return "Please enjoy this wallpaper!: " + mData;
    }

    /**
     * Share image action
     */
    private void shareImage() {
        // create new intent
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        // at this point, this is uri to file but actually writing to file is done
        // in the background task in the parent class
        shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                getShareMessage(
                        mPhoto.getUrls().getFull()
                )
        );
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Share too..."));
    }

    /**
     * Snack bar message for current activity
     * @param mMessage
     */
    private void displayCustomSnackbar(String mMessage) {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(findViewById(R.id.display_actvity_container),
                mMessage,
                Snackbar.LENGTH_INDEFINITE,
                "Close");
    }

    /**
     * Snack bar message for current activity
     * @param mMessage
     */
    private void displayCustomSnackbar(String mMessage, int length) {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(findViewById(R.id.display_actvity_container),
                mMessage,
                length,
                "Close");
    }

//
//    /**
//     * Load the loader for the favorites (need loader for UI interaction)
//     */
//    private void loadLoader()
//    {
//        // create bundle to pass into loader
//        Bundle mBundle = new Bundle();
//        mBundle.putString(FavoriteImageLoader.IMAGE_ID_BUNDLE_KEY, mPhoto.getId());
//        // use loader to get page data
//        LoaderManager mLoaderManager = getSupportLoaderManager();
//        Loader<Boolean> mMovieLoader = mLoaderManager.getLoader(imageLoaderId);
//        // check loader instance
//        if(mMovieLoader != null)
//            mLoaderManager.initLoader(imageLoaderId, null, this).forceLoad();
//        else
//            mLoaderManager.restartLoader(imageLoaderId, null, this).forceLoad();
//    }
//
//    /**
//     * Load loader
//     * @param id
//     * @param args
//     * @return
//     */
//    @NonNull
//    @Override
//    public Loader<Boolean> onCreateLoader(int id, @Nullable Bundle args) {
//        return new FavoriteImageLoader(this, args);
//    }
//
//    /**
//     * The loader looks to see if movie is a favorite, this will do the needed
//     * task after those results
//     *
//     * @param loader
//     * @param data
//     */
//    @Override
//    public void onLoadFinished(@NonNull Loader<Boolean> loader, Boolean data) {
//        // data is the result if move is a favorite or not
//        if(data)
//        {
//            // set bool that will be used for the onclick to favorite or un-favorite a movie
//            isFavorite = true;
//            // set image to show it is a favorite
//            mFavoriteStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_black_24dp));
//        }
//    }
//
//    /**
//     * @param loader
//     */
//    @Override
//    public void onLoaderReset(@NonNull Loader<Boolean> loader) {
//        // not needed
//    }
}