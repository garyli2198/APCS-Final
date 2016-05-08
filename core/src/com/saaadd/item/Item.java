package com.saaadd.item;

import com.badlogic.gdx.graphics.Texture;

public class Item {
    private int id;
    private String name;
    private Texture image;
    public Item(int id, String name, Texture image){
        this.id = id;
        this.name = name;
        this.image = image;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Texture getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }
}
