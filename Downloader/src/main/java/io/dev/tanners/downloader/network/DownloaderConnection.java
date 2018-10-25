package io.dev.tanners.downloader.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import io.dev.tanners.connectionrequester.ConnectionRequester;
import static io.dev.tanners.downloader.Downloader.callMediaScanner;

public class DownloaderConnection extends AsyncTask<String, Void, Boolean> {
    private ErrorCallBack mErrorCallBack;
    private File mFile;
    private Context mContext;

    public DownloaderConnection(Context mContext, File mFile, ErrorCallBack mErrorCallBack)
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
    protected Boolean doInBackground(String... strings) {
        ConnectionRequester mRequest = null;
        String mEncoding = "utf-8";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mEncoding = StandardCharsets.UTF_8.name().toLowerCase(Locale.getDefault());
        }

        mRequest = new ConnectionRequester(strings[0], mEncoding);

        try {
            mRequest = mRequest.addBasicBody(null)
                    .setRequestType(ConnectionRequester.RequestType.GET)
                    .setConnectionTimeOut(5000)
                    .setReadTimeOut(15000);

            if (mRequest.connect() == HttpURLConnection.HTTP_OK) {
                if (mRequest.isConnectionOk()) {
                    try {
                        mRequest.fileDownloader(mFile);
                    } catch (FileNotFoundException e) {
                        mErrorCallBack.displayError("Error has occurred: " + e.getLocalizedMessage());
                        e.printStackTrace();
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            mErrorCallBack.displayError("Error has occurred: " + e.getLocalizedMessage());
            e.printStackTrace();
            return false;
        } finally {
            mRequest.closeConnection();
        }

        return true;
    }

    /**
     * @param result
     */
    @Override
    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);

        if(result && mFile != null) {
            // call media scanner
            callMediaScanner(mContext, mFile);
            //
            mErrorCallBack.displayNoError("Downloaded");
        } else {
            // error handled in doInBackground
        }
    }

    /**
     * Callback for error messages when fragments attempt to populate images
     * This used in the LatestImage and PopulateImages fragments
     */
    public interface ErrorCallBack
    {
        public void displayError(String message);
        public void displayNoError(String message);
    }
}
