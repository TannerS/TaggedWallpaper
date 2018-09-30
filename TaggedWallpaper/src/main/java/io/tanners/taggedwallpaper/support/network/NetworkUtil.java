package io.tanners.taggedwallpaper.support.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    /**
     * https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
     *
     * Method to check if a network connection is available
     *
     * @return boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mNetworkInfo = null;

        if (connectivityManager != null) {
            mNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

}
