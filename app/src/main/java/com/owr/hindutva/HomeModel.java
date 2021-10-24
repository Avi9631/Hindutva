package com.owr.hindutva;

public class HomeModel {

    private String image;
    private String name;
    private String id;
    private String text;

    public HomeModel(String image, String name, String id, String text) {
        this.image = image;
        this.name = name;
        this.id = id;
        this.text = text;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
