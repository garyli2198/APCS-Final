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
 * A Merchant is a character that paces back and forth and sells weapons and ammo to the player, when the player
  * is close enough
 * @author Stanley Huang
 * @version April 24, 2016
 * @author Period - 6
 * @author Assignment - APCS Final
 * @author Sources - Gary Li, Wesley Pang
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

     /**
      * constructs a Merchant with Textures, position and angle, and an ArrayList of weapons, as well as the type of
      * ammo that are going to be sold by the Merchant
      * @param legSheet  = leg sheet with walking animations
      * @param bodySheet = body sheet with body types: dual, 1h, 2h
      * @param x         = x coordinate of world
      * @param y         = y coordinate of world
      * @param angle     = rotation angle
      * @param storeList = ArrayList of weapons to be sold
      * @param ammoType  = ammo type to be sold
      */
    public Merchant(Texture legSheet, Texture bodySheet, float x, float y, float angle, ArrayList<Weapon> storeList,
                    int ammoType) {
        super(legSheet, bodySheet, x, y, angle);
        closeEnough = false;
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
        store = new Store(storeList, ammoIcon, ammoType);

    }

     /**
      * gets Store of Merchant
      * @return store
      */
     public Store getStore() {
         return store;
     }

     /**
      * is never removed
      * @return false
      */
    @Override
    public boolean shouldRemove() {
        return false;
    }

     /**
      * updates Merchant:
      * tracks player distance
      * checks object collision
      * changes direction every few seconds to simulate pacing
      */
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
        closeEnough = distanceFromPlayer < 150;
        if(closeEnough){
            isMoving = false;
            store.update();
        }
        else{
            isMoving = true;
        }

    }

     /**
      * draws Merchant character, draws its store when player is close enough.
       * @param batch = batch used for drawing
      */
    public void draw(SpriteBatch batch){
        super.draw(batch);
        if(closeEnough) {
            this.store.draw();
        }
    }

     /**
      * bleeds when hit
      * @param bullet = bullet that hit this character
      */
    @Override
    public void onHit(Bullet bullet) {
        bleed();
    }
}
