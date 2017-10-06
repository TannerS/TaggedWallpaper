package io.tanners.taggedwallpaper.network.images;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;


public class ImageSharer extends ImageDownloader
{
    public ImageSharer(Context mContext, View view, ProgressBar mProgressBar, ImageView mImage, File mFile)
    {
        super(mContext, view, mProgressBar, mImage, mFile);



    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mFile));
        shareIntent.setType("image/jpeg");

        mContext.startActivity(Intent.createChooser(shareIntent, "Share too..."));
    }
}
