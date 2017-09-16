package com.tanners.taggedwallpaper.async;

import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tanners.taggedwallpaper.mappings.user.UserData;
import com.tanners.taggedwallpaper.network.vision.ConnectionRequest;
import com.tanners.taggedwallpaper.util.EndpointRestBuilder;

import java.io.IOException;

public class CollectUserData extends AsyncTask<String, Void, UserData>
{
//    private UserData userData;
    private EndpointRestBuilder builder;
    private TextView userInfo;

    public CollectUserData(TextView userInfo)
    {
        this.userInfo = userInfo;
//        this.userData = userData;
    }

    @Override
    protected void onPreExecute() {
        //            user_data = new UserData();
        builder = new EndpointRestBuilder();
    }

    // TODO might need to make it return a value rather then pass in object
    @Override
//    protected UserData doInBackground(String... str)
    protected UserData doInBackground(String... str)
    {
        ConnectionRequest connection = null;

        try {
            connection = new ConnectionRequest(builder.getUserInfo(str[0]));
            Gson gson = new Gson();

            return gson.fromJson(connection.getResponse(), UserData.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(UserData userData)
    {
        if(userData != null)
        {
            StringBuilder photo_info = new StringBuilder("");
            photo_info.append("Name: ").append(userData.getFullName()).append("\n");
            photo_info.append("Username: ").append(userData.getUsername()).append("\n");
            photo_info.append("ID: ").append(userData.getId()).append("\n");
            photo_info.append("Title: ").append(userData.getTitle()).append("\n");
            photo_info.append("Owner: ").append(userData.getOwner()).append("\n");
            userInfo.setText(photo_info.toString());
        }
    }
}