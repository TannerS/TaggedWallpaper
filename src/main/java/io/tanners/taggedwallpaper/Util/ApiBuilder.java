package io.tanners.taggedwallpaper.Util;

import java.util.HashMap;

public class ApiBuilder {

    public static enum OrderBy {
        POPULAR("popular"),
        NEWEST("newest");

        private String order;

        OrderBy(String order) {
            this.order = order;
        }

        public String order() {
            return order;
        }

    }

    private int mPerPage;//https://api.unsplash.com/photos?per_page=50&page=1&order_by=newest";
    private int mPage; //https://api.unsplash.com/photos?per_page=50&page=1&order_by=newest";
    private String mBase = "https://api.unsplash.com/search/photos";
    private String mApiRules = "&utm_source=TaggedWallpaper&utm_medium=referral&utm_campaign=api-credit";
    private final String APIKEY = "Client-ID 53bec55730b75b73e5f615222f83e498e7645300c2b10949e6f8e25442a2fccc";
    private String mOrderBy;

    public String buildRestfulUrl()
    {
        return mBase + "?" + "per_page=" + mPerPage + "&page=" + mPage + mApiRules + "&order_by=" + mOrderBy;
    }

    public ApiBuilder(int mPerPage, int mPage, OrderBy order)
    {
        this.mPerPage = mPerPage;
        this.mPage = mPage;
        this.mOrderBy = order.name();
    }

    public void setPage(int mPage)
    {
        this.mPage = mPage;
    }

    public void setPerPage(int mPerPage)
    {
        this.mPerPage = mPerPage;
    }

    public void increasePage()
    {
        this.mPage++;
    }

    public HashMap<String, String> getHeaders() {
        return new HashMap<String, String>() {{
            // TODO PKI for token
            put("Authorization", APIKEY);
            put("Accept-Language", "en-US,en;q=0.5");
            put("Connection", "keep-alive");
            put("Accept", "text/html,application/xhtml+xm…plication/xml;q=0.9,*/*;q=0.8");
            put("content-type", "text/html; charset=utf-8");
            put("content-type", "application/x-www-form-urlencoded; charset=utf-8");
            put("Content-Language", "en-US");
        }};
    }


}
