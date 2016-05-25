package com.saaadd.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saaadd.game.GameScreen;
import com.saaadd.item.Bullet;
import com.saaadd.item.Weapon;
/**
 * Describes a character object that is rendered on a Screen. A character contains two sprites one for legs
 * and one for the body. A character has an x and y position and rotation angle.
 * @author Stanley Huang
 * @version April 24, 2016
 * @author Period - 6
 * @author Assignment - APCS Final
 * @author Sources - Gary Li, Wesley Pang
 */
public abstract class Character {
    public final static int defaultHealth = 100;
    public boolean isMoving;
    public final static float speed = 5.0f;
    private Sprite body, leg;
    private float angle;
    private Animation walkAnimation, bloodAnimation;
    private TextureRegion[] walkFrames, bodyFrames;
    private float x, y, width = 64, height = 64;
    private Weapon weapon;
    private int health;
    private boolean bleeding;
    private float bleedingTime;

    /**
     * constructs a Character object with body and leg sheets. Sets the default to no weapon in hand
     *
     * @param legSheet  = leg sheet with walking animations
     * @param bodySheet = body sheet with body types: dual, 1h, 2h
     */
    public Character(Texture legSheet, Texture bodySheet) {
        bodyFrames = TextureRegion.split(bodySheet, 64, 64)[0];
        body = new Sprite(bodyFrames[0]);

        walkFrames = TextureRegion.split(legSheet, 64, 64)[0];
        walkAnimation = new Animation(0.1f, walkFrames);
        GameScreen.stateTime = 0f;
        leg = new Sprite(walkAnimation.getKeyFrame(GameScreen.stateTime, true));
        isMoving = false;
        weapon = null;
        health = defaultHealth;
        TextureRegion[] bloodFrames = TextureRegion.split(new Texture(Gdx.files.internal("blood.png")), 512, 512)[0];
        bloodAnimation = new Animation(0.3f, bloodFrames);
    }

    /**
     * constructs a Character object with body/leg sheets, location, and angle. Sets the default to
     * no weapon in hand
     *
     * @param legSheet  = leg sheet with walking animations
     * @param bodySheet = body sheet with body types: dual, 1h, 2h
     * @param x         = x coordinate of world
     * @param y         = y coordinate of world
     * @param angle     = rotation angle
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
        leg = new Sprite(walkAnimation.getKeyFrame(GameScreen.stateTime, true));
        isMoving = false;
        weapon = null;
        health = defaultHealth;
        TextureRegion[] bloodFrames = TextureRegion.split(new Texture(Gdx.files.internal("blood.png")), 512, 512)[0];
        bloodAnimation = new Animation(0.3f, bloodFrames);

    }

    /**
     * Constructs a character with textures/animation sheets, position, angle, and weapon
     *
     * @param legSheet  = leg sheet with walking animations
     * @param bodySheet = body sheet with body types: dual, 1h, 2h
     * @param x         = x coordinate of world
     * @param y         = y coordinate of world
     * @param angle     = rotation angle
     * @param health    = health
     * @param weapon    = weapon to be held
     */
    public Character(Texture legSheet, Texture bodySheet, float x, float y, float angle, int health, Weapon weapon) {
        bodyFrames = TextureRegion.split(bodySheet, 64, 64)[0];
        body = new Sprite(bodyFrames[weapon.getType()]);
        leg = new Sprite(legSheet);

        walkFrames = TextureRegion.split(legSheet, 64, 64)[0];
        walkAnimation = new Animation(0.1f, walkFrames);
        GameScreen.stateTime = 0f;
        this.x = x;
        this.y = y;
        this.angle = angle;
        leg = new Sprite(walkAnimation.getKeyFrame(GameScreen.stateTime, true));
        isMoving = false;
        this.weapon = weapon;
        this.health = health;
        TextureRegion[] bloodFrames = TextureRegion.split(new Texture(Gdx.files.internal("blood.png")), 512, 512)[0];
        bloodAnimation = new Animation(0.05f, bloodFrames);

    }

    /**
     * Checks if the character should be removed from the CharacterRenderer (whether or not it should still update and
     * render
     * @return true if the character should be removed
     */
    public abstract boolean shouldRemove();

    /**
     * updates character (is called continuously by CharacterRenderer)
     */
    public abstract void update();

    /**
     * Called when character is hit by a bullet
     * @param bullet = bullet that hit this character
     */
    public abstract void onHit(Bullet bullet);

    /**
     * plays bleeding animation
     */
    public void bleed() {
        bleeding = true;
        bleedingTime = 0;
    }

    /**
     * returns the bullet the character was hit by
     * @return bullet that character was hit by or null if not hit by a bullet
     */
    public Bullet hitByBullet() {
        for (Bullet bullet : GameScreen.bullets) {
            if (getRectangle().contains(bullet.getX(), bullet.getY())) {
                bullet.setRemove(true);
                return bullet;
            }
        }
        return null;
    }

    /**
     * adds specified amount to character health
     * @param h = amount to be added to health
     */
    public void addHealth(int h) {
        health += h;
        if(health > Character.defaultHealth){
            health = Character.defaultHealth;
        }
    }

    /**
     * gets health of character
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * handles and draws all images and animations of the character. Namely the body sprite, the leg animations, the
     * weapon sprite, and the bleeding animations
     * @param batch = batch used for drawing
     */
    public void draw(SpriteBatch batch) {

        if (isMoving) {
            leg = new Sprite(walkAnimation.getKeyFrame(GameScreen.stateTime, true));

        } else {
            leg = new Sprite(walkFrames[1]);
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
        if (weapon != null) {
            weapon.getWeaponSprite().setSize(width, height);
            int offset = 3;
            if(weapon.getType() == 1){
                offset = 5;
            }
            

            // calculates gun and fire animation position
            float r = (float) Math.sqrt(offset * offset + 30 * 30);
            float theta = (float) Math.atan2(30, offset);
            float rf = (float) Math.sqrt(offset * offset + weapon.getLength() * weapon.getLength());
            float thetaf = (float) Math.atan2(weapon.getLength(), offset);
            weapon.setWeaponPosition(x + (float) (r * Math.cos(theta + Math.toRadians(body.getRotation()))),
                    y + (float) (r * Math.sin(theta + Math.toRadians(body.getRotation()))));
            weapon.setWeaponRotation(angle);
            weapon.setFirePosition(x + (float) (rf * Math.cos(thetaf + Math.toRadians(body.getRotation() + offset))),
                    y + (float) (rf * Math.sin(thetaf + Math.toRadians(body.getRotation() + offset))));
            weapon.draw(batch); //draw gun
        }
        if (bleeding) {
            Sprite blood = new Sprite(bloodAnimation.getKeyFrame(bleedingTime, false));
            blood.setSize(64, 64);
            blood.setCenter(x, y);

            if (bleedingTime > 0.3f) {
                bleeding = false;
            }
            batch.begin();
            blood.draw(batch);
            batch.end();
            bleedingTime += Gdx.graphics.getDeltaTime();
        }
    }

    /**
     * gets the hit box/collision box of the character.
     * the rectangle is centered at the character's position and its width is 3/4 of its sprite's width
     * @return returns a rectangle representing hit/collision box
     */
    public Rectangle getRectangle() {
        float x = getX() - body.getWidth() * 0.375f;
        float y = getY() - body.getHeight() * 0.375f;
        return new Rectangle(x, y, body.getWidth() * 0.75f, body.getHeight() * 0.75f);
    }

    /**
     * moves the character a certain distance in x and y axis
     * @param dx = x distance
     * @param dy = y distance
     */
    public void translate(float dx, float dy) {
        x += dx;
        y += dy;
    }

    /**
     * sets rotation
     * @param angle = rotation
     */
    public void setRotation(float angle) {
        this.angle = angle;
    }

    /**
     * gets rotation
     * @return angle in degrees
     */
    public float getRotation() {
        return angle;
    }

    /**
     * gets x position
     * @return x coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * sets x position
     * @param x = new x position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * gets y position
     * @return y posotion
     */
    public float getY() {
        return y;
    }

    /**
     * sets y position
     * @param y = position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * gets the currently equipped weapon by character
     * @return equipped weapon
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * sets the currently equppied weapon
     * @param weapon = weapon to be equipped
     */
    public void setWeapon(Weapon weapon) {
        if(this.weapon == null || this.weapon.getType() != weapon.getType()){
            body = new Sprite(bodyFrames[weapon.getType()]);
        }
        this.weapon = weapon;
    }

    /**
     * gets width of sprite
     * @return width of sprite
     */
    public float getWidth(){
        return width;
    }

    /**
     * gets height of sprite
     * @return height of sprite
     */
    public float getHeight(){
        return height;
    }

}
