//package io.tanners.taggedwallpaper.network.image.share;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.view.View;
//
//import java.io.File;
//
//import io.tanners.taggedwallpaper.network.image.ImageDownloaderBase;
//
//public class ImageSharer extends ImageDownloaderBase
//{
////    private Uri mImageUri;
//    private String mUrl;
//
////    public ImageSharer(Context mContext, View view, File mFile, Uri mImageUri)
//    public ImageSharer(Context mContext, View view, String mUrl)
//    {
////        super(mContext, view, mFile);
//        super(mContext, view);
////        this.mImageUri = mImageUri;
//    }
//
//    @Override
//    protected void onPostExecute(Boolean result) {
//        // create new intent
//        Intent shareIntent = new Intent();
//        // grant uri permissions
////        if (mImageUri != null) {
//            // Grant temporary read permission to the content URI
////            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
////        }
//        shareIntent.setAction(Intent.ACTION_SEND);
//        // at this point, this is uri to file but actually writing to file is done
//        // in the background task in the parent class
////        shareIntent.putExtra(Intent.EXTRA_STREAM, mImageUri);
//        shareIntent.putExtra(Intent.EXTRA_TEXT, mImageUri);
//        shareIntent.setType("image/jpeg");
//        // call media scanner to allow image to show up in gallery and filesystem
//        if(result)
//            callMediaScanner();
//        // start chooser
//        mContext.startActivity(Intent.createChooser(shareIntent, "Share too..."));
//    }
//}
////mPhotoInfo.getImageURL()