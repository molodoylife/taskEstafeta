package ua.hanasaka.testestafeta.model.data;


import io.realm.RealmObject;

/**
 * Created by Oleksandr Molodykh on 18.07.2017.
 */

public class ImageDB extends RealmObject{
    private String title;
    private String date;
    private String url;
    private Double lat;
    private Double lon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
