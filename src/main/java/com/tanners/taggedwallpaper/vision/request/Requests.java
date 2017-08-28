package com.tanners.taggedwallpaper.vision.request;

import java.util.ArrayList;

public class Requests
{
    public Requests(ArrayList<Request> requests) {
        this.requests = requests;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }

    ArrayList<Request> requests;
}
