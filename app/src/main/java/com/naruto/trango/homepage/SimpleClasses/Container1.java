package com.naruto.trango.homepage.SimpleClasses;

public class Container1 {

    int image_id;
    String title;
    String desc;

    public Container1(int image_id, String title, String desc) {
        this.image_id = image_id;
        this.title = title;
        this.desc = desc;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
