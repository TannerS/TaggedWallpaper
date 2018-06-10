package io.tanners.taggedwallpaper.network.image.download;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.File;
import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.Util.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.interfaces.ErrorCallBack;
import io.tanners.taggedwallpaper.network.image.ImageDownloaderBase;

public class ImageDownloader extends ImageDownloaderBase {
    private ErrorCallBack mErrorCallBack;

    public ImageDownloader(Context mContext, File mFile, ErrorCallBack mErrorCallBack)
    {
        super(mContext, mFile);
        this.mErrorCallBack = mErrorCallBack;
    }

    /**
     * @param result
     */
    @Override
    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);

        if(result)
        {
            mErrorCallBack.displayNoError();
        }
        else
        {
            mErrorCallBack.displayError();
        }
    }
}