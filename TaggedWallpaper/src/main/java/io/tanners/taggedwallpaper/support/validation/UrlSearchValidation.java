package io.tanners.taggedwallpaper.support.validation;

import android.os.Build;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import io.tanners.taggedwallpaper.support.exceptions.MaxLimitException;
import io.tanners.taggedwallpaper.support.exceptions.MinLimitException;
import io.tanners.taggedwallpaper.support.network.encoder.EncoderUtil;

public class UrlSearchValidation {
    public static void UrlQueryValidation(String mInput) throws
            MinLimitException,
            MaxLimitException {
        if(mInput == null)
            throw new NullPointerException("Query must not be null");

        if(mInput.length() == 0)
            throw new MinLimitException("Must provide a search result");

        // some random char limit
        if(mInput.length() > 10)
            throw new MaxLimitException("Over limit of 10 char in search query");
    }

    public static String UrlQueryFormatter(String mInput) throws UnsupportedEncodingException {
        String mQuery = "";

        mInput = mInput.trim();

        mQuery = EncoderUtil.encode(mInput);

        return mQuery;
    }
}
