package io.tanners.taggedwallpaper.mappings.vision.request.base;

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
