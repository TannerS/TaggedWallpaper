package io.tanners.taggedwallpaper.interfaces;

import android.view.View;

/**
 * Callback for error messages when fragments attempt to populate images
 * This used in the LatestImage and PopulateImages fragments
 */
public interface ErrorCallBack
{
//  https://stackoverflow.com/questions/31519695/how-can-a-snackbar-be-shown-from-a-fragment-in-the-correct-view
    public void displayError();
}
