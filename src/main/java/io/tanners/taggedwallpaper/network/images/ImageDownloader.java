package io.tanners.taggedwallpaper.network.images;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;

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

    public ImageDownloader(File mFile, Snackbar mSuccessSnackbar, Snackbar mFailSnackbar)
    {
        this.mFile = mFile;
        this.mSuccessSnackbar = mSuccessSnackbar;
        this.mFailSnackbar = mFailSnackbar;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // TODO error handling here
        if(result)
        {
            mSuccessSnackbar.show();
        }
        else
        {
            mFailSnackbar.show();
        }
    }

    @Override
    protected Boolean doInBackground(String... strings) {
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
    }

}