package com.saaadd.character;

import com.badlogic.gdx.graphics.Texture;
import com.saaadd.item.Bullet;
import com.saaadd.item.Weapon;

/**
 * Created by stanl on 5/11/2016.
 */
public class Enemy extends Character {

    public Enemy(Texture legSheet, Texture bodySheet, float x, float y, float angle, int health, Weapon weapon) {
        super(legSheet, bodySheet, x, y, angle, health, weapon);
    }

    @Override
    public boolean shouldRemove() {
        return getHealth() <= 0;
    }

    @Override
    public void update() {

    }

    @Override
    public void onHit(Bullet bullet) {
        addHealth( -1 * bullet.getWeapon().getDamage());
        bleed();
    }


}
