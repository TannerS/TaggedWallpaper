package io.tanners.taggedwallpaper.network.images;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.SimpleSnackBarBuilder;

public class ImageDownloader extends ImageDownloaderBase {
    private File mFile;
    private Context mContext;
    protected View view;
    private ProgressBar mProgressBar;
    private ImageView mImage;

    public ImageDownloader(Context mContext, View view, ProgressBar mProgressBar, ImageView mImage, File mFile)
    {
        super(mContext, view, mProgressBar, mImage, mFile);
        this.view = view;
        this.mFile = mFile;
        this.mContext = mContext;
        this.mProgressBar = mProgressBar;
        this.mImage = mImage;
    }

    @Override
    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);
        // TODO error handling here
        if(result)
        {
            Log.i("SNACKBAR", "GOOD DNACKBAR");

            final Snackbar mGoodSnackbar = displaySuccessDownloadSnackBar();

            mGoodSnackbar.setAction("Close", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGoodSnackbar.dismiss();
                }
            });

            mGoodSnackbar.show();
        }
        else
        {
            Log.i("SNACKBAR", "BAD DNACKBAR");
            final Snackbar mFailSnackbar = displayFailedDownloadSnackBar();

            mFailSnackbar.setAction("Close", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFailSnackbar.dismiss();
                }
            });

            mFailSnackbar.show();
        }
    }

    private Snackbar displaySuccessDownloadSnackBar()
    {
        return SimpleSnackBarBuilder.createSnackBar(view.findViewById(R.id.display_activity_main_id),
                "Image Downloaded",
                Snackbar.LENGTH_LONG);
    }

    private Snackbar displayFailedDownloadSnackBar()
    {
        return SimpleSnackBarBuilder.createSnackBar(view.findViewById(R.id.display_activity_main_id),
                "ERROR: Image Cannot Be Downloaded",
                Snackbar.LENGTH_INDEFINITE);
    }

//    private void displayStorageErrorSnackBar() {
//        SimpleSnackBarBuilder.createAndDisplaySnackBar(view.findViewById(R.id.display_activity_main_id),
//                "ERROR: Cannot Access External Storage",
//                Snackbar.LENGTH_INDEFINITE,
//                "Close");
//    }

}