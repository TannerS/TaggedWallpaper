package io.dev.tanners.downloader;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import io.dev.tanners.downloader.network.DownloaderConnection;

/**
 * Some methods provided by
 * https://developer.android.com/training/basics/data-storage/files.html
 * TODO maybe put isExternalStorageWritable, isExternalStorageReadable, getAlbumStorageDir into it's own library?
 */
public class Downloader {
    private Context mContext;
    // this should never have a hard coded album name in a library, but I don't care for now
    private static final String ALBUM_NAME = "TaggedWallpaper";

    public Downloader(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Download file of any type
     */
    public void downloadFile(
            String mFilename,
            String mUrl,
            DownloaderConnection.ErrorCallBack mCallback
    ) throws IllegalStateException {
        // check if external storage is writable
        if (isExternalStorageWritable()) {
            // use that newly created image file to share or download
            // you need to download before sharing
            new DownloaderConnection(
                    mContext,
                    getNewFile(mFilename),
                    mCallback
            ).execute(mUrl);
        }
        // cant read, connected to pc, ejected, etc
        else {
            throw new IllegalStateException("Storage is not in a writable state");
        }
    }

    private File getNewFile(String mFileName) {
        // get filename
//        String[] mImageUrlSplit = mPhotoInfo.getImageURL().split("/");
//        String mImageUrlFileName = mImageUrlSplit[mImageUrlSplit.length - 1];
        File mImageFile = null;
        // create album
        File mImageDir = getAlbumStorageDir(ALBUM_NAME);
        // create file based on name and album
        mImageFile = new File(mImageDir, mFileName);
        // return image file reference
        return mImageFile;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String mAlbumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), mAlbumName);
        if (!file.mkdirs()) {
            // TODO error handling here
            Log.e("STORAGE", "Directory not created");
        }
        return file;
    }

    /**
     * updates gallery by scanner the newly added image
     */
    public static void callMediaScanner(Context mContext, File mFile) {
        // https://stackoverflow.com/questions/9414955/trigger-mediascanner-on-specific-path-folder-how-to
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MediaScannerConnection.scanFile(
                    mContext, new String[]{
                            mFile.getAbsolutePath()
                    },
                    null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    //something that you want to do
                }
            });
        } else {
            mContext.sendBroadcast(
                    new Intent(Intent.ACTION_MEDIA_MOUNTED,
                            Uri.parse("file://" + mFile.getAbsolutePath()
                            )
                    )
            );
        }
    }
}

