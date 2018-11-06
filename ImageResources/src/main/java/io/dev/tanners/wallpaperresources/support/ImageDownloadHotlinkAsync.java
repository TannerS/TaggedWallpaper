package io.dev.tanners.wallpaperresources.support;

import android.os.AsyncTask;
import android.os.Build;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import io.dev.tanners.connectionrequester.ConnectionRequester;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_AUTH_KEY;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_AUTH_VALUE;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_VERSION_KEY;
import static io.dev.tanners.wallpaperresources.config.ConfigBase.HEADER_VERSION_VALUE;

public class ImageDownloadHotlinkAsync extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... strings) {
        String mUrl = strings[0];
        String mEncoding = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mEncoding = StandardCharsets.UTF_8.name().toLowerCase(Locale.getDefault());
        } else {
            mEncoding = "utf-8";
        }

        ConnectionRequester mRequest = new ConnectionRequester(mUrl, mEncoding);

        mRequest = mRequest.addBasicBody(null)
                .addRequestHeader(HEADER_VERSION_KEY, HEADER_VERSION_VALUE)
                .addRequestHeader(HEADER_AUTH_KEY, HEADER_AUTH_VALUE)
                .setRequestType(ConnectionRequester.RequestType.GET)
                .setConnectionTimeOut(3000)
                .setReadTimeOut(5000);

        try {
            mRequest.connect();
        } catch (IOException e) {
            // don't care for this
        }

        return null;
    }
}
