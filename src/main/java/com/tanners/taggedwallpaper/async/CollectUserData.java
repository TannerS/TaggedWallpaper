package com.tanners.taggedwallpaper.async;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.tanners.taggedwallpaper.data.UserData;
import com.tanners.taggedwallpaper.network.ConnectionRequest;
import com.tanners.taggedwallpaper.network.FlickrURLBuilder;

import java.io.IOException;

public class CollectUserData extends AsyncTask<String, Void, UserData>
{
    private UserData userData;
    private FlickrURLBuilder builder;

    public CollectUserData(UserData userData)
    {
        this.userData = userData;
    }

    @Override
    protected void onPreExecute() {
        //            user_data = new UserData();
        builder = new FlickrURLBuilder();
    }

    @Override
    protected UserData doInBackground(String... str)
    {
        ConnectionRequest connection = null;

        try {
            connection = new ConnectionRequest(builder.getUserInfo(str[0]));

            Gson gson = new Gson();
            userData = gson.fromJson(connection.getResponse(), UserData.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}