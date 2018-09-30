package io.tanners.taggedwallpaper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.tanners.taggedwallpaper.support.network.NetworkUtil;

/**
 * https://stackoverflow.com/a/51019783/2449314
 */
public abstract class NetworkActivity extends AppCompatActivity {
    NetworkChangeReceiver mNetworkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNetworkChangeReceiver = new NetworkChangeReceiver();
    }

    /**
     * https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
     *
     * Method to check if a network connection is available
     *
     * @return boolean
     */
//    protected boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo mNetworkInfo = null;
//
//        if (connectivityManager != null) {
//            mNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        }
//
//        return mNetworkInfo != null && mNetworkInfo.isConnected();
//    }

    protected class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(NetworkUtil.isNetworkAvailable(getBaseContext())) {
                onNetworkChange(true);
            } else {
                onNetworkChange(false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        if(mNetworkChangeReceiver != null) {
            registerReceiver(mNetworkChangeReceiver, intentFilter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver);
        }
    }

    protected void onNetworkChange(boolean isOn) {
        throw new IllegalStateException("Needs to be implemented in child class");
    }
}
