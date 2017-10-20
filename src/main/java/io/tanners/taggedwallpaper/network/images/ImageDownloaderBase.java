package io.tanners.taggedwallpaper.network.images;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
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

    /**
     *
     */
    @Override
    protected void onPreExecute() {
        // hide image
        mImage.setVisibility(View.GONE);
        // show progressbar
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * @param result
     */
    @Override
    protected void onPostExecute(Boolean result) {
        if(result)
            // call media scanner
            callMediaScanner();
        // show image
        mImage.setVisibility(View.VISIBLE);
        // hide progressbar
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * @param strings
     * @return
     */
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

    /**
     * updates gallery by scanner the newly added image
     */
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
}