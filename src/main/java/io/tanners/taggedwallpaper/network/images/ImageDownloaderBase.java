package io.tanners.taggedwallpaper.network.images;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.SimpleSnackBarBuilder;

public class ImageDownloaderBase extends AsyncTask<String, Void, Boolean> {
    protected File mFile;
    protected Context mContext;
    protected View view;
    protected ProgressBar mProgressBar;
    protected ImageView mImage;

    public ImageDownloaderBase(Context mContext, View view, ProgressBar mProgressBar, ImageView mImage, File mFile)
    {
        this.view = view;
        this.mFile = mFile;
        this.mContext = mContext;
        this.mProgressBar = mProgressBar;
        this.mImage = mImage;
    }

    @Override
    protected void onPreExecute() {
        mImage.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Boolean result) {

//        if(result)
//            callMediaScanner();
//


//        // TODO error handling here
//        if(result)
//        {
//            callMediaScanner();
//
//            Log.i("SNACKBAR", "GOOD DNACKBAR");
//
//            final Snackbar mGoodSnackbar = displaySuccessDownloadSnackBar();
//
//            mGoodSnackbar.setAction("Close", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mGoodSnackbar.dismiss();
//                }
//            });
//
//            mGoodSnackbar.show();
//        }
//        else
//        {
//            Log.i("SNACKBAR", "BAD DNACKBAR");
//            final Snackbar mFailSnackbar = displayFailedDownloadSnackBar();
//
//            mFailSnackbar.setAction("Close", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mFailSnackbar.dismiss();
//                }
//            });
//
//            mFailSnackbar.show();
//        }
        if(result)
            callMediaScanner();

        mImage.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);


    }

    @Override
    protected Boolean doInBackground(String... strings)
    {
        try
        {
            // connect to website (direct url to image)
            URL mUrl = new URL(strings[0]);
            // open stream to image
            InputStream is = mUrl.openStream();




            if(!mFile.exists()) {
                // set stream to write to passed in file
                OutputStream os = new FileOutputStream(mFile);
                // write buffer size at 1024 bytes at a time
                byte[] bytes = new byte[1024];
                int length;
                // write to file until end
                while ((length = is.read(bytes)) != -1) {
                    os.write(bytes, 0, length);
                }
                // close streams
                is.close();
                os.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    protected void callMediaScanner()
    {
        // https://stackoverflow.com/questions/9414955/trigger-mediascanner-on-specific-path-folder-how-to
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            MediaScannerConnection.scanFile(mContext, new String[]{mFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    //something that you want to do
                }
            });
        } else {
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + mFile.getAbsolutePath())));
        }
    }

//    private Snackbar displaySuccessDownloadSnackBar()
//    {
//        return SimpleSnackBarBuilder.createSnackBar(view.findViewById(R.id.display_activity_main_id),
//                "Image Downloaded",
//                Snackbar.LENGTH_LONG);
//    }
//
//    private Snackbar displayFailedDownloadSnackBar()
//    {
//        return SimpleSnackBarBuilder.createSnackBar(view.findViewById(R.id.display_activity_main_id),
//                "ERROR: Image Cannot Be Downloaded",
//                Snackbar.LENGTH_INDEFINITE);
//    }

//    private void displayStorageErrorSnackBar() {
//        SimpleSnackBarBuilder.createAndDisplaySnackBar(view.findViewById(R.id.display_activity_main_id),
//                "ERROR: Cannot Access External Storage",
//                Snackbar.LENGTH_INDEFINITE,
//                "Close");
//    }

//    public static interface imageDownloaderCallBack
//    {
//        public void shareImage(Uri uri);
//        // TODO add more here
//    }
}