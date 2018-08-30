package io.tanners.taggedwallpaper.interfaces;

/**
 * Callback for error messages when fragments attempt to populate images
 * This used in the LatestImage and PopulateImages fragments
 */
public interface ErrorCallBack
{
    public void displayError();
    public void displayNoError();
}
