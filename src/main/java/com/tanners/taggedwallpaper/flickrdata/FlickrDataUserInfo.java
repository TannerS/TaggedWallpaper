package com.tanners.taggedwallpaper.flickrdata;

import com.tanners.taggedwallpaper.flickrdata.urldata.FlickrURLBuilder;
import com.tanners.taggedwallpaper.util.URLConnection;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;

public class FlickrDataUserInfo
{
    private String real_name;
    private String username;
    FlickrURLBuilder url;

    public FlickrDataUserInfo()
    {
        real_name ="";
        username = "";
        url = new FlickrURLBuilder();
    }

    public void generateUserInfo(String temp)
    {
        URLConnection connection = new URLConnection(url.getUserInfo(temp));

        if(connection.isGood())
        {
            ByteArrayOutputStream output = connection.readData();
            String json_info = output.toString();

            try
            {
                JSONObject root = new JSONObject(json_info).getJSONObject("person");
                JSONObject username_attribute = root.getJSONObject("username");
                this.username = username_attribute.getString("_content");
                JSONObject realname_attribute = root.getJSONObject("realname");
                this.real_name = realname_attribute.getString("_content");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public String getUsername()
    {
        return username;
    }

    public String getFullName()
    {
        return this.real_name;
    }
}
