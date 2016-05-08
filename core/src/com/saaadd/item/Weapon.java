package com.saaadd.item;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Weapon extends Item {
    //weapontypes
    public static final int oneH = 0, twoH = 1, dual = 2;

    private int type;
    private Sprite weaponSprite;
    public Weapon(int id, String name, Texture image, int weaponType) {
        super(id, name, image);
        type = weaponType;
        weaponSprite = new Sprite(image);

    }
    public Sprite getWeaponSprite(){
        return weaponSprite;
    }
    public void draw(SpriteBatch batch){

        batch.begin();
        weaponSprite.draw(batch);
        batch.end();

    }
}
