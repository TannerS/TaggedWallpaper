package io.tanners.taggedwallpaper.support.validation;

import android.content.Context;

import java.io.UnsupportedEncodingException;

import io.tanners.taggedwallpaper.R;
import io.tanners.taggedwallpaper.support.network.encoder.EncoderUtil;

public class UrlSearchValidation {
    public void UrlQueryValidation(Context mContext, String mInput) throws
            MinLimitException,
            MaxLimitException {
        if(mInput == null)
            throw new NullPointerException(mContext.getString(R.string.ERR_QUERY_NULL));

        if(mInput.length() == 0)
            throw new MinLimitException(mContext.getString(R.string.ERR_EMPTY_SEARCH));

        // some random char limit
        if(mInput.length() > 10)
            throw new MaxLimitException(mContext.getString(R.string.ERR_OVER_SEARCH_QUERY_LIMIT));
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
