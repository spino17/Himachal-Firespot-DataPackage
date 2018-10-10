package com.example.coder1704.himachalfirehotspotsdatapackage;

public class GeoClass {
    private String district;
    private float area;
    private float dense;
    private float open;
    private float moderate;
    private float total;

    public GeoClass(String district, float area, float dense, float open, float moderate, float total) {
        this.district = district;
        this.area = area;
        this.dense = dense;
        this.open = open;
        this.moderate = moderate;
        this.total = total;
    }

    public float getArea() {
        return area;
    }

    public String getDistrict() {

        return district;
    }

    public float getTotal() {
        return total;
    }

    public float getModerate() {

        return moderate;
    }

    public float getOpen() {

        return open;
    }

    public float getDense() {

        return dense;
    }
}
