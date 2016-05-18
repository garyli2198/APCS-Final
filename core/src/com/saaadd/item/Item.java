package com.saaadd.item;

import com.badlogic.gdx.graphics.Texture;

public class Item {
    private String name;
    private Texture image;
    public Item(String name, Texture image){
        this.name = name;
        this.image = image;

    }


    public String getName() {
        return name;
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }
}
