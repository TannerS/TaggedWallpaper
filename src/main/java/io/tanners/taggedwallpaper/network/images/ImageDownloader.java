package io.tanners.taggedwallpaper.network.images;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Void, Boolean> {
    private File mFile;
    private Snackbar mSuccessSnackbar;
    private Snackbar mFailSnackbar;
    private Context mContext;

    public ImageDownloader(Context mContext, File mFile, Snackbar mSuccessSnackbar, Snackbar mFailSnackbar)
//    public ImageDownloader(Snackbar mSuccessSnackbar, Snackbar mFailSnackbar)
    {
        this.mFile = mFile;
        this.mSuccessSnackbar = mSuccessSnackbar;
        this.mFailSnackbar = mFailSnackbar;
        this.mContext = mContext;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // TODO error handling here
        if(result)
        {
            Log.i("SNACKBAR", "GOOD DNACKBAR");
            mSuccessSnackbar.show();
        }
        else
        {
            Log.i("SNACKBAR", "BAD DNACKBAR");

            mFailSnackbar.show();
        }
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    @Override
    protected void onPreExecute() {

        Log.i("MEDIA", mFile.getAbsolutePath());

        // https://stackoverflow.com/questions/9414955/trigger-mediascanner-on-specific-path-folder-how-to
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            MediaScannerConnection.scanFile(mContext, new String[]{mFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    //something that you want to do
                }
            });
        } else {
//            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + imagePath)));
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + mFile.getAbsolutePath())));
        }


    }

}