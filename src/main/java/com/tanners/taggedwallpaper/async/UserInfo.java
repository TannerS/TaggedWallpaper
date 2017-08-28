package com.tanners.taggedwallpaper.async;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.tanners.taggedwallpaper.flickrdata.UserData;
import com.tanners.taggedwallpaper.network.ConnectionResponse;
import com.tanners.taggedwallpaper.network.FlickrURLBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;

public class UserInfo extends AsyncTask<String, Void, UserData>
{
    private UserData user_data;
    private FlickrURLBuilder builder;

    @Override
    protected void onPreExecute() {
        user_data = new UserData();
        builder = new FlickrURLBuilder();
    }

    @Override
    protected UserData doInBackground(String... str)
    {
        ConnectionResponse connection = null;
        try {
            connection = new ConnectionResponse(builder.getUserInfo(str[0]));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        String json = null;

//        try {
//            // TODO find correct class to map based on info below
////            json = gson.fromJson(connection.getResponse(), );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try
//        {
//            JSONObject root = new JSONObject(json_info).getJSONObject("person");
//            JSONObject username_attribute = root.getJSONObject("username");
//            this.username = username_attribute.getString("_content");
//            JSONObject realname_attribute = root.getJSONObject("realname");
//            this.real_name = realname_attribute.getString("_content");
//        }
//        catch (JSONException e)
//        {
//            e.printStackTrace();
//        }

        return user_data;
    }
}