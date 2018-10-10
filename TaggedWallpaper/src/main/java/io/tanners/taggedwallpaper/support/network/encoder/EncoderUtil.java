package io.tanners.taggedwallpaper.support.network.encoder;

import android.os.Build;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class EncoderUtil {
    public static String encode(String mInput) throws UnsupportedEncodingException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            mInput = URLEncoder.encode(mInput, StandardCharsets.UTF_8.name());
        else
            mInput = URLEncoder.encode(mInput, "utf-8");

        return mInput;
    }

    public static String decode(String mInput) throws UnsupportedEncodingException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            mInput = URLDecoder.decode(mInput, StandardCharsets.UTF_8.name());
        else
            mInput = URLDecoder.decode(mInput, "utf-8");

        return mInput;
    }
}

