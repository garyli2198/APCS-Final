package com.saaadd.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saaadd.game.GameScreen;
import com.saaadd.item.Bullet;
import com.saaadd.item.Weapon;

import java.util.Random;

import static com.saaadd.game.GameScreen.bullets;
import static com.saaadd.game.GameScreen.player;

/**
 * Created by stanl on 5/11/2016.
 */
public class Enemy extends Character {

    public final static Texture enemyLegs = new Texture(Gdx.files.internal("enemyLegs.png"));
    public final static Texture enemyBody = new Texture(Gdx.files.internal("enemyBody.png"));

    private Random rand = new Random();
    private float time = 0;
    private float radians;
    private Sound onHitSound = Gdx.audio.newSound(Gdx.files.internal("onhitsound.mp3"));
    private Sound deathSound = Gdx.audio.newSound(Gdx.files.internal("deathsound.mp3"));
    private float prevX = 0, prevY = 0;
    private boolean[] overlapX;
    private float randTurn;
    private int turnTime = rand.nextInt(4) + 1;
    int counter = 0;
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
        time += Gdx.graphics.getDeltaTime();
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

        radians = (float) Math.atan((player.getY() - getY()) / (player.getX() - getX()));
        if (player.getX() - getX() < 0) {
            radians += Math.PI;
        }
        float unchanged = radians;
        int neg;
        if (distanceFromPlayer <= 600) {
            if(counter % 2 == 0){
                neg = -1;
            }
            else{
                neg = 1;
            }
            if(time % turnTime < 0.01)
            {
                randTurn = (float)(radians + Math.random() * Math.PI/2 * neg);
                counter++;
                getWeapon().fire();
            }

            setRotation((float)Math.toDegrees(unchanged) - 90);
            isMoving = true;

            translate((float) (Math.cos(randTurn) * speed * 0.6), (float) (Math.sin(randTurn) * speed * 0.6));


        }
        else if(distanceFromPlayer>600 && distanceFromPlayer <1000){
            for (Bullet b : bullets) {
                if (Math.abs(b.getX() - getX()) < 250 && Math.abs(b.getY() - getY()) < 250) {
                    radians = (float) Math.atan((b.getY() - getY()) / (b.getX() - getX()));
                    if (b.getVector().angleRad() < Math.PI / 2 && b.getVector().angleRad() > -1 * Math.PI / 2 &&
                            b.getVector().angleRad() < unchanged - Math.PI) {
                        radians += Math.PI / 2;
                    } else if (b.getVector().angleRad() < Math.PI / 2 && b.getVector().angleRad() > -1 * Math.PI / 2 &&
                            b.getVector().angleRad() > unchanged - Math.PI) {
                        radians -= Math.PI / 2;
                    } else if (b.getVector().angleRad() > Math.abs(unchanged)) {
                        radians -= Math.PI / 2;
                    } else if (b.getVector().angleRad() < Math.abs(unchanged)) {
                        radians += Math.PI / 2;
                    }
                }
            }

            //obstacle avoidance
            if (Math.abs(getX() - prevX) < 2 && Math.abs(getY() - prevY) < 2) {
                radians += Math.PI / 2;
            }

            prevX = getX();
            prevY = getY();
            setRotation((float) Math.toDegrees(unchanged) - 90);
            isMoving = true;
            translate((float) (Math.cos(radians) * speed * .6), (float) (Math.sin(radians) * speed * .6));
        }
        else {
            isMoving = true;
            translate((float) (Math.cos(radians) * speed * 5), (float) (Math.sin(radians) * speed * 5));
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
