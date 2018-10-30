package io.tanners.taggedwallpaper.support.validation;

import java.io.UnsupportedEncodingException;
import io.tanners.taggedwallpaper.support.network.encoder.EncoderUtil;

public class UrlSearchValidation {
    public void UrlQueryValidation(String mInput) throws
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


    public class MaxLimitException extends Exception {
        public MaxLimitException(String message) {
            super(message);
        }
    }

    public class MinLimitException extends Exception {
        public MinLimitException(String message) {
            super(message);
        }
    }
}
