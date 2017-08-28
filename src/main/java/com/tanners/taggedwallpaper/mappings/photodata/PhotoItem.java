package com.tanners.taggedwallpaper.mappings.photodata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoItem implements Serializable
{
    private float id;
    private String title;
    private String url_z;
    private String url_c;
    private String url_b;
    private String url_h;
    private String url_k;
    private String url_o;
    private String url_n;
    private String owner;

    public String getUrl_z()
    {
        return url_z;
    }

    public String getTitle()
    {
        return title;
    }

    public float getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getUrl_c() {
        return url_c;
    }

    public String getUrl_b() {
        return url_b;
    }

    public String getUrl_h() {
        return url_h;
    }

    public String getUrl_k() {
        return url_k;
    }

    public String getUrl_o() {
        return url_k;
    }

    public String getUrl_n() {
        return url_n;
    }
}
