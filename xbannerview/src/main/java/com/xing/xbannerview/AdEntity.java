package com.xing.xbannerview;

import java.io.Serializable;

public class AdEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String url;
    private String img;
    private String type;

    public AdEntity(String title, String url, String img, String type) {
        this.title = title;
        this.url = url;
        this.img = img;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImg() {
        return img;
    }

    public String getType() {
        return type;
    }

}