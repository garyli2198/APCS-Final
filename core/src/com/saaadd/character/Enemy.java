package com.saaadd.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.saaadd.game.GameScreen;
import com.saaadd.item.Bullet;
import com.saaadd.item.Weapon;

import java.util.Random;

import static com.saaadd.game.GameScreen.player;

/**
 * Created by stanl on 5/11/2016.
 */
public class Enemy extends Character {

    public final static Texture enemyLegs = new Texture(Gdx.files.internal("enemyLegs.png"));
    public final static Texture enemyBody = new Texture(Gdx.files.internal("enemyBody.png"));

    private Sound onHitSound = Gdx.audio.newSound(Gdx.files.internal("onhitsound.mp3"));
    private Sound deathSound = Gdx.audio.newSound(Gdx.files.internal("deathsound.mp3"));
    private float prevX = 0, prevY = 0;
    private boolean[] overlapX;

    public Enemy(Texture legSheet, Texture bodySheet, float x, float y, float angle, int health, Weapon weapon) {
        super(legSheet, bodySheet, x, y, angle, health, weapon);
        overlapX = new boolean[GameScreen.mapObjects.getCount()];
    }

    @Override
    public boolean shouldRemove() {
        return getHealth() <= 0;
    }

    @Override
    public void update() {
        int count = 0;
        float distanceFromPlayer = (float) (Math.sqrt(Math.pow(player.getX() - getX(), 2) + Math.pow(player.getY() - getY(), 2)));
        for (RectangleMapObject r : GameScreen.mapObjects.getByType(RectangleMapObject.class)) {
            Rectangle rect = r.getRectangle();
            if (Intersector.overlaps(rect, getRectangle())) {
                if (overlapX[count]) {
                    if (getY() > rect.getY()) {
                        setY(rect.getY() + rect.getHeight() + getRectangle().getHeight() / 2f + 1);
                    } else if (getY() < rect.getY()) {
                        setY(rect.getY() - getRectangle().getHeight() / 2f - 1);
                    }
                } else {
                    if (getX() > rect.getX()) {
                        setX(rect.getX() + rect.getWidth() + getRectangle().getWidth() / 2f + 1);
                    } else if (getX() < rect.getX()) {
                        setX(rect.getX() - getRectangle().getWidth() / 2f - 1);
                    }
                }
            }
            overlapX[count] = getRectangle().getX() > rect.getX() - getRectangle().getWidth() &&
                    getRectangle().getX() < rect.getX() + rect.getWidth();
            count++;

        }
        float radians = (float) Math.atan((player.getY() - getY()) / (player.getX() - getX()));

        if (player.getX() - getX() < 0) {
            radians += 3.14159;
        }

        if (distanceFromPlayer <= 1000) {
            float rotate = radians;
            isMoving = true;
            if (Math.abs(getX() - prevX) < 2 && Math.abs(getY() - prevY) < 2) {
                radians += 3.14159 / 2;
            }
            prevX = getX();
            prevY = getY();
            setRotation((float) Math.toDegrees(rotate) - 90);
            translate((float) (Math.cos(radians) * speed * .8), (float) (Math.sin(radians) * speed * .8));
        } else {
            isMoving = false;
        }
    }

    @Override
    public void onHit(Bullet bullet) {
        addHealth(-1 * bullet.getWeapon().getDamage());
        bleed();
        if (getHealth() <= 0) {
            deathSound.play();
            return;
        }
        onHitSound.play();
    }


}
