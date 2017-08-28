package com.tanners.taggedwallpaper.mappings.vision.request;

import java.util.ArrayList;

public class Request
{
    public Request(Image image, ArrayList<Feature> features) {
        this.image = image;
        this.features = features;
    }

    private Image image;
    private ArrayList<Feature> features;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }
}
