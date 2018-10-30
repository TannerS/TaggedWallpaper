package io.tanners.taggedwallpaper.support.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import io.tanners.taggedwallpaper.db.ImageDatabase;
import io.tanners.taggedwallpaper.db.ImageEntry;

public class FavoriteImageLoader extends AsyncTaskLoader<Boolean> {
    // instance to database
    private ImageDatabase mDb;
    // image id
    private String mImageId;
    // image id key
    public static String IMAGE_ID_BUNDLE_KEY = "IMAGE_ID_KEY";

    /**
     * Constructor
     * @param context
     * @param mBundle
     */
    public FavoriteImageLoader(@NonNull Context context, Bundle mBundle) {
        super(context);
        // can't be used if bundle is empty
        if (mBundle == null)
            return;
        // get image id from bundle
        mImageId = mBundle.getString(IMAGE_ID_BUNDLE_KEY);
        // load data base instance
        mDb = ImageDatabase.getInstance(context);
    }

    /**
     * load this in background, to query database
     *
     * @return
     */
    @Nullable
    @Override
    public Boolean loadInBackground() {
        return isFavorite();
    }

    /**
     * Images can be loaded from db or rest,
     * the rest images do not have the favorite
     * variable set even if it is in db, since
     * it is loaded from rest not db, so we do a
     * simple query to check for the page properties
     * can be loaded correctly
     *
     * @return
     */
    private boolean isFavorite()
    {
        // call database to query for the object, this will check if it exist
        ImageEntry mPhoto = mDb.getImageDao().loadPhotoEntryById(mImageId);
        // return result if image is in db
        return mPhoto != null;
    }
}
