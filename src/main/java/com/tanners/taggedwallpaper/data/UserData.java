package com.tanners.taggedwallpaper.data;

public class UserData
{
    private String real_name;
    private String username;

    public UserData()
    {
        real_name ="";
        username = "";
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
