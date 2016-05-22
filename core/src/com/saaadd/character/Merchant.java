package com.saaadd.character;

import com.badlogic.gdx.graphics.Texture;
import com.saaadd.item.Bullet;
import com.saaadd.ui.Store;

import static com.saaadd.game.GameScreen.player;

/**
 * Created by stanl on 5/13/2016.
 */
public class Merchant extends Character {
    private Store store;
    private boolean closeEnough;
    public Merchant(Texture legSheet, Texture bodySheet, float x, float y, float angle) {
        super(legSheet, bodySheet, x, y, angle);
        closeEnough = false;
    }

    public boolean isCloseEnough(){
        return closeEnough;
    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    public void update() {
        float distanceFromPlayer = (float) (Math.sqrt(Math.pow(player.getX() - getX(), 2) + Math.pow(player.getY() - getY(), 2)));
        closeEnough = distanceFromPlayer < 50;

    }

    @Override
    public void onHit(Bullet bullet) {

    }
}
