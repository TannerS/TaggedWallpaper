package io.dev.tanners.wallpaperresources.config;

import android.net.Uri;

public class ConfigBase {
    public enum Protocol {
        HTTP("http"),
        HTTPS("https");

        private String protocol;

        Protocol(String protocol) {
            this.protocol = protocol;
        }

        public String protocol() {
            return protocol;
        }
    }

    public static final String BASE_URL = "api.unsplash.com";
    public static final String HEADER_VERSION_KEY = "Accept-Version";
    public static final String HEADER_VERSION_VALUE = "v1";

    public static final String HEADER_AUTH_KEY = "Authorization";
    public static final String HEADER_AUTH_VALUE = "Client-ID";

    public static final String QUERY_AUTH_KEY = "client_id";
    public static final String QUERY_AUTH_VALUE = "YOUR_ACCESS_KEY";

    public static final String HEADER_PER_PAGE_KEY = "X-Per-Page";
    public static final String HEADER_PER_PAGE_VALUE = "5";

    public static final String HEADER_TOTAL_RESULTS_KEY = "X-Total";
    public static final String HEADER_TOTAL_RESULTS_VALUE = "10";

    public static final String QUERY_PER_PAGE_KEY = "per_page";
    public static final String QUERY_PER_PAGE_VALUE = HEADER_PER_PAGE_VALUE;

    public static final String QUERY_PAGE_KEY = "page";
    public static final String QUERY_PAGE_VALUE = "1";

    public static final String QUERY_TOTAL_RESULTS_KEY = "X-Total";
    public static final String QUERY_TOTAL_RESULTS_VALUE = "10";
}
