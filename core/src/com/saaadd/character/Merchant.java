package com.saaadd.character;

import com.badlogic.gdx.graphics.Texture;
import com.saaadd.item.Bullet;

/**
 * Created by stanl on 5/13/2016.
 */
public class Merchant extends Character {


    public Merchant(Texture legSheet, Texture bodySheet, float x, float y, float angle) {
        super(legSheet, bodySheet, x, y, angle);
    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void onHit(Bullet bullet) {

    }
}
