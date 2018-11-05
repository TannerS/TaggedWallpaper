package io.tanners.taggedwallpaper.support.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

public class PermissionRequester {
    private Context mActivity;

    public PermissionRequester(Activity mActivity)
    {
        this.mActivity = mActivity;
    }

    /**
     * creates new instance
     * @param mActivity
     * @return
     */
    public static PermissionRequester newInstance(Activity mActivity) {
        return new PermissionRequester(mActivity);
    }

    /**
     * request permissions
     * @param permissions
     * @param code
     * @return
     */
    public boolean requestNeededPermissions(String[] permissions, int code)
    {
        // check version of android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            ArrayList<String> needed_permissions = new ArrayList<String>();
            // loop all request permissions
            for (final String permission : permissions) {
                // if permission is not granted
                if (ActivityCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                    // add to list of not granted permissions
                    needed_permissions.add(permission);
                }
            }

            // request if any non granted permissions
            if (needed_permissions.size() >= 1)
                ActivityCompat.requestPermissions(
                        (Activity) mActivity,
                        needed_permissions.toArray(
                                new String[needed_permissions.size()]
                        ),
                        code
                );
            // return a bool if any permissions needed
            return needed_permissions.size() == 0;
        }
        // default return value
        return false;
    }
}



