package io.tanners.taggedwallpaper.network.image.download;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;

public class ImageDownloader extends AsyncTask<String, Void, Boolean> {
    private ErrorCallBack mErrorCallBack;
    private File mFile;
    private Context mContext;
    private String error;

    public ImageDownloader(Context mContext, File mFile,  ErrorCallBack mErrorCallBack)
    {
        this.mFile = mFile;
        this.mContext = mContext;
        this.mErrorCallBack = mErrorCallBack;
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
            // todo use ConnectionRequester
            // connect to website (direct url to image)
            URL mUrl = new URL(strings[0]);
            // open stream to image
            InputStream mFileInputStream = mUrl.openStream();
            // check if file still exist
            if(!mFile.exists()) {
                FileUtils.copyInputStreamToFile(mFileInputStream, mFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            error = e.getLocalizedMessage();
            return false;
        }

        return true;
    }

    /**
     * let system be aware of newly downloaded file
     */
    protected void callMediaScanner()
    {
        // https://stackoverflow.com/questions/9414955/trigger-mediascanner-on-specific-path-folder-how-to
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MediaScannerConnection.scanFile(mContext, new String[]{mFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    // something that you may want to do
                }
            });
        } else {
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + mFile.getAbsolutePath())));
        }
    }

    /**
     * @param result
     */
    @Override
    protected void onPostExecute(Boolean result)
    {
        super.onPostExecute(result);

        if(result) {
            // call media scanner
            callMediaScanner();
            mErrorCallBack.displayNoError("Image downloaded");
        } else {
            mErrorCallBack.displayError(error);
        }
    }
}