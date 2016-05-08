package com.saaadd.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.saaadd.game.GameScreen;
import com.saaadd.item.Weapon;

public class Character {
    public boolean isMoving;
    private Sprite body, leg;
    private float angle;
    private Animation walkAnimation;
    private TextureRegion[] walkFrames, bodyFrames;
    private float x, y, width = 64, height = 64;
    public final static float speed = 5.0f;
    private Weapon weapon;

    /**constructs a Character object with body and leg sheets. Sets the default to no weapon in hand
     * @param legSheet = leg sheet with walking animations
     * @param bodySheet = body sheet with body types: dual, 1h, 2h
     */
    public Character(Texture legSheet, Texture bodySheet) {
        bodyFrames = TextureRegion.split(bodySheet, 64, 64)[0];
        body = new Sprite(bodyFrames[0]);

        walkFrames = TextureRegion.split(legSheet, 64, 64)[0];
        walkAnimation = new Animation(0.1f, walkFrames);
        GameScreen.stateTime = 0f;
        leg =  new Sprite(walkAnimation.getKeyFrame(GameScreen.stateTime, true));
        isMoving = false;
        weapon = null;
    }

    /**constructs a Character object with body/leg sheets, location, and angle. Sets the default to
     * no weapon in hand
     * @param legSheet = leg sheet with walking animations
     * @param bodySheet = body sheet with body types: dual, 1h, 2h
     * @param x = x coordinate of world
     * @param y = y coordinate of world
     * @param angle = rotation angle
     */
    public Character(Texture legSheet, Texture bodySheet, float x, float y, float angle) {
        bodyFrames = TextureRegion.split(bodySheet, 64, 64)[0];
        body = new Sprite(bodyFrames[0]);
        leg = new Sprite(legSheet);

        walkFrames = TextureRegion.split(legSheet, 64, 64)[0];
        walkAnimation = new Animation(0.1f, walkFrames);
        GameScreen.stateTime = 0f;
        this.x = x;
        this.y = y;
        this.angle = angle;
        leg =  new Sprite(walkAnimation.getKeyFrame(GameScreen.stateTime, true));
        isMoving = false;
        weapon = null;

    }
    public Character(Texture legSheet, Texture bodySheet, float x, float y, float angle, Weapon weapon) {
        bodyFrames = TextureRegion.split(bodySheet, 64, 64)[0];
        body = new Sprite(bodyFrames[weapon.getType()]);
        leg = new Sprite(legSheet);

        walkFrames = TextureRegion.split(legSheet, 64, 64)[0];
        walkAnimation = new Animation(0.1f, walkFrames);
        GameScreen.stateTime = 0f;
        this.x = x;
        this.y = y;
        this.angle = angle;
        leg =  new Sprite(walkAnimation.getKeyFrame(GameScreen.stateTime, true));
        isMoving = false;
        this.weapon = weapon;

    }

    public void draw(SpriteBatch batch) {

        if(isMoving){
            leg =  new Sprite(walkAnimation.getKeyFrame(GameScreen.stateTime, true));

        }
        else{
            leg =  new Sprite(walkFrames[1]);
        }
        leg.setSize(width, height);
        body.setSize(width, height);

        leg.setRotation(angle);
        body.setRotation(angle);

        leg.setCenter(x, y);
        body.setCenter(x, y);
        batch.begin();
        leg.draw(batch);
        body.draw(batch);
        batch.end();
        //gun position calculations and drawing
        if(weapon != null) {
            weapon.getWeaponSprite().setSize(width, height);
            // calculates gun and fire animation position
            float r = (float) Math.sqrt(5 * 5 + 30 * 30);
            float theta = (float) Math.atan2(30, 5);
            float rf = (float) Math.sqrt(5 * 5 + 45 * 45);
            float thetaf = (float) Math.atan2(45, 5);
            weapon.setWeaponPosition(x + (float)(r * Math.cos(theta + Math.toRadians(body.getRotation()))),
                   y + (float)(r * Math.sin(theta + Math.toRadians(body.getRotation()))));
            weapon.setWeaponRotation(angle);
            weapon.setFirePosition(x + (float)(rf * Math.cos(thetaf + Math.toRadians(body.getRotation() + 5))),
                    y + (float)(rf * Math.sin(thetaf + Math.toRadians(body.getRotation() + 5))));
            weapon.draw(batch); //draw gun
        }


    }

    public void translate(float dx, float dy) {
        x += dx;
        y += dy;
    }
    public void translate(Vector2 v){
        x += v.x;
        y += v.y;
        body.translate(v.x, v.y);
    }
    public void rotate(float dtheta){
        angle += dtheta;
    }
    public void setRotation(float angle){
        this.angle = angle;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public void setLocation(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void setSize(float sizex, float sizey){
        width = sizex;
        height = sizey;
    }
    public Weapon getWeapon(){
        return weapon;
    }
    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }


}
