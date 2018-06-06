package com.jamian.theblog;

/**
 * Created by jamian on 3/15/2017.
 */

public class Pojo_Blog_Article {
    private String imageurl,title,shortdesc,fullcontent;

    public Pojo_Blog_Article(String imageurl, String title, String shortdesc, String fullcontent) {
        this.imageurl = imageurl;
        this.title = title;
        this.shortdesc = shortdesc;
        this.fullcontent = fullcontent;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getFullcontent() {
        return fullcontent;
    }

    public void setFullcontent(String fullcontent) {
        this.fullcontent = fullcontent;
    }
}
