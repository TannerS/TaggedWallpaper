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
    protected View view;

    public ImageDownloader(Context mContext, View view, File mFile)
    {
        super(mContext, view, mFile);
        this.view = view;
    }

    /**
     * @param result
     */
    @Override
    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);

        if(result)
        {
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

    /**
     * display success snackbar
     * @return
     */
    private Snackbar displaySuccessDownloadSnackBar()
    {
        return SimpleSnackBarBuilder.createSnackBar(view.findViewById(R.id.display_activity_main_id),
                "Image Downloaded",
                Snackbar.LENGTH_LONG);
    }

    /**
     * display error snackbar
     * @return
     */
    private Snackbar displayFailedDownloadSnackBar()
    {
        return SimpleSnackBarBuilder.createSnackBar(view.findViewById(R.id.display_activity_main_id),
                "ERROR: Image Cannot Be Downloaded",
                Snackbar.LENGTH_INDEFINITE);
    }
}