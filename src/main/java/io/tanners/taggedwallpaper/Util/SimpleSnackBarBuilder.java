package io.tanners.taggedwallpaper.Util;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SimpleSnackBarBuilder {

    public static void createAndDisplaySnackBar(View view, String message, int length, String mDismissMessage,  View.OnClickListener mCallback)
    {
        final Snackbar mSnackBar = SimpleSnackBarBuilder.createSnackBar(view, message, length, mDismissMessage, mCallback);

        mSnackBar.show();
    }

    public static void createAndDisplaySnackBar(View view, String message, int length, String mDismissMessage)
    {
        final Snackbar mSnackBar = SimpleSnackBarBuilder.createSnackBar(view, message, length);

        mSnackBar.setAction(mDismissMessage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackBar.dismiss();
            }
        });

        mSnackBar.show();
    }

    public static Snackbar createSnackBar(View view, String message, int length)
    {
        final Snackbar mSnackBar = Snackbar.make(view, message, length);

        return mSnackBar;
    }

    public static Snackbar createSnackBar(View view, String message, int length, String mDismissMessage,  View.OnClickListener mCallback)
    {
        final Snackbar mSnackBar = Snackbar.make(view, message, length);

        mSnackBar.setAction(mDismissMessage, mCallback);

        return mSnackBar;
    }
}
