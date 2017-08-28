package com.tanners.taggedwallpaper.mappings.vision.response;

public class SafeSearchAnnotations {
    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getSpoof() {
        return spoof;
    }

    public void setSpoof(String spoof) {
        this.spoof = spoof;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public String getViolence() {
        return violence;
    }

    public void setViolence(String violence) {
        this.violence = violence;
    }

    private String adult;
    private String spoof;
    private String medical;
    private String violence;





}
