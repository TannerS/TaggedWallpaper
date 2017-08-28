package com.tanners.taggedwallpaper.vision.response;

import java.util.ArrayList;
import java.util.List;

public class Response
{
    private ArrayList<LabelAnnotations> labelAnnotations;
    private SafeSearchAnnotations safeSearchAnnotations;

    public Response(ArrayList<LabelAnnotations> labelAnnotations, SafeSearchAnnotations safeSearchAnnotations) {
        this.labelAnnotations = labelAnnotations;
        this.safeSearchAnnotations = safeSearchAnnotations;
    }

    public ArrayList<LabelAnnotations> getLabelAnnotations() {

        return labelAnnotations;
    }

    public void setLabelAnnotations(ArrayList<LabelAnnotations> labelAnnotations) {
        this.labelAnnotations = labelAnnotations;
    }

    public SafeSearchAnnotations getSafeSearchAnnotations() {
        return safeSearchAnnotations;
    }

    public void setSafeSearchAnnotations(SafeSearchAnnotations safeSearchAnnotations) {
        this.safeSearchAnnotations = safeSearchAnnotations;
    }
}
