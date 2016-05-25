package com.saaadd.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saaadd.game.GameScreen;
import com.saaadd.item.Bullet;
import com.saaadd.item.Weapon;

public abstract class Character {
    public final static int defaultHealth = 100;
    public boolean isMoving;
    private Sprite body, leg;
    private float angle;
    private Animation walkAnimation, bloodAnimation;
    private TextureRegion[] walkFrames, bodyFrames;
    private float x, y, width = 64, height = 64;
    public final static float speed = 5.0f;
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

    public abstract boolean shouldRemove();

    public abstract void update();

    public abstract void onHit(Bullet bullet);


    public void bleed() {
        bleeding = true;
        bleedingTime = 0;
    }

    public Bullet hitByBullet() {
        for (Bullet bullet : GameScreen.bullets) {
            if (getRectangle().contains(bullet.getX(), bullet.getY())) {
                bullet.setRemove(true);
                return bullet;
            }
        }
        return null;
    }

    public void addHealth(int h) {
        health += h;
        if(health > Character.defaultHealth){
            health = Character.defaultHealth;
        }
    }

    public int getHealth() {
        return health;
    }

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

    public Rectangle getRectangle() {
        float x = getX() - body.getWidth() * 0.375f;
        float y = getY() - body.getHeight() * 0.375f;
        return new Rectangle(x, y, body.getWidth() * 0.75f, body.getHeight() * 0.75f);
    }

    public void translate(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public void setRotation(float angle) {
        this.angle = angle;
    }

    public float getRotation() {
        return angle;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }


    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        if(this.weapon == null || this.weapon.getType() != weapon.getType()){
            body = new Sprite(bodyFrames[weapon.getType()]);
        }
        this.weapon = weapon;
    }
    public float getWidth(){
        return width;
    }
    public float getHeight(){
        return height;
    }

}
