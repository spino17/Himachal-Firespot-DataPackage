package com.example.coder1704.himachalfirehotspotsdatapackage;

public class Place {

    private String placename;
    private String district;
    private int economy;
    private String pineavailablity;
    private String about;
    public Place(String placename, String district, int economy, String pineavailablity, String about) {
        //
        this.placename = placename;
        this.district = district;
        this.economy = economy;
        this.pineavailablity = pineavailablity;
        this.about = about;
    }

    public String getPlacename() {
        return placename;
    }

    public String getDistrict() {
        return district;
    }

    public int getEconomy() {
        return economy;
    }

    public String getPineavailablity() {
        return pineavailablity;
    }

    public String getAbout() {
        return about;
    }
}
