package io.tanners.taggedwallpaper.model.categories;

/**
 * Can be inner class but for now we will try here to find it better
 */
public class CategoryItem
{
    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public CategoryItem(String mTitle, String mUrl) {
        this.mUrl = mUrl;
        this.mTitle = mTitle;
    }

    private String mUrl;
    private String mTitle;
}