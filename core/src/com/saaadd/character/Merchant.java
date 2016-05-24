package com.saaadd.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.saaadd.game.GameScreen;
import com.saaadd.item.Bullet;
import com.saaadd.item.Weapon;

import java.util.ArrayList;


import static com.saaadd.game.GameScreen.player;

/**
 * Created by stanl on 5/13/2016.
 */
public class Merchant extends Character {
    public static final Texture nurseLegs = new Texture(Gdx.files.internal("nurseLegs.png"));
    public static Texture nurseBody = new Texture(Gdx.files.internal("nurseBody.png"));
    public static final int healthAmmo = 0, singleAmmo = 1, autoAmmo = 2;
    private boolean closeEnough;
    private String greeting;
    private Store store;
    private float time;
    private boolean direction;
    private boolean[] overlapX;
    private int ammoType;
    private Texture ammoIcon;
    public Merchant(Texture legSheet, Texture bodySheet, float x, float y, float angle, ArrayList<Weapon> storeList,
                    int ammoType) {
        super(legSheet, bodySheet, x, y, angle);
        closeEnough = false;
        GameScreen.userInterface.addStore(store);
        time = 0;
        direction = false;
        isMoving = true;
        overlapX = new boolean[GameScreen.mapObjects.getCount()];
        this.ammoType = ammoType;
        if(ammoType == healthAmmo){
            ammoIcon = new Texture(Gdx.files.internal("healthAmmo.png"));
        }
        else if(ammoType == singleAmmo){
            ammoIcon = new Texture(Gdx.files.internal("ammo.png"));
        }
        else{
            ammoIcon = new Texture(Gdx.files.internal("autoAmmo.png"));
        }
        store = new Store(storeList, ammoIcon);

    }
    public int getAmmoType(){
        return ammoType;
    }
    public Store getStore(){
        return store;
    }
    public void setGreeting(String s){
        greeting = s;
    }
    public String getGreeting(){
        return greeting;
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
        time += Gdx.graphics.getDeltaTime();
        //pace handling
        if(time > 4 && !closeEnough){
            direction = !direction;
            this.setRotation(this.getRotation() + 180);
            time = 0;
            isMoving = true;
        }

        //object detection
        boolean[] d = new boolean[4];
        d[Player.left] = !direction;
        d[Player.right] = direction;
        int count = 0;
        for(RectangleMapObject r : GameScreen.mapObjects.getByType(RectangleMapObject.class)){
            Rectangle rect = r.getRectangle();
            if(Intersector.overlaps(rect, getRectangle() )){
                if(overlapX[count]){
                    if(getY() > rect.getY()){
                        setY(rect.getY() + rect.getHeight() + getRectangle().getHeight()/2f + 1);
                    }
                    else if(getY() < rect.getY()){
                        setY(rect.getY() - getRectangle().getHeight()/2f - 1);
                    }
                }
                else{
                    if(getX() > rect.getX()){
                        setX(rect.getX() + rect.getWidth() + getRectangle().getWidth()/2f + 1);
                    }
                    else if(getX() < rect.getX()){
                        setX(rect.getX() - getRectangle().getWidth()/2f - 1);
                    }
                }
            }
            overlapX[count] = getRectangle().getX() > rect.getX() - getRectangle().getWidth() &&
                    getRectangle().getX() < rect.getX() + rect.getWidth();
            count++;

        }
        //move handling
        if(isMoving) {
            if (direction) {
                translate(1, 0);
            } else {
                translate(-1, 0);
            }
        }
        //player sensor
        float distanceFromPlayer = (float) (Math.sqrt(Math.pow(player.getX() - getX(), 2) + Math.pow(player.getY() - getY(), 2)));
        closeEnough = distanceFromPlayer < 50;
        if(closeEnough){
            isMoving = false;
            store.update();
        }
        else{
            isMoving = true;
        }

    }
    public void draw(SpriteBatch batch){
        super.draw(batch);
        if(closeEnough) {
            this.store.draw();
        }
    }

    @Override
    public void onHit(Bullet bullet) {

    }
}
