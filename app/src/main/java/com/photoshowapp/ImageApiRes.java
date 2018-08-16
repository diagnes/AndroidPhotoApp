package com.photoshowapp;

/**
 * Created by Nefast on 12/04/2018.
 */

public class ImageApiRes {

    private Integer positions;
    private Integer id;
    private String Urlmin;
    private String Urlbig;

    public Integer getPositions() {
        return positions;
    }

    public void setPositions(Integer positions) {
        this.positions = positions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlmin() {
        return Urlmin;
    }

    public void setUrlmin(String urlmin) {
        Urlmin = urlmin;
    }

    public String getUrlbig() {
        return Urlbig;
    }

    public void setUrlbig(String urlbig) {
        Urlbig = urlbig;
    }
}
