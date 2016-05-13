package com.saaadd.item;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.saaadd.game.GameScreen;

public class Weapon extends Item {
    //weapontypes
    public static final int oneH = 1, twoH = 2, dual = 0;

    private int type;
    private Sprite weaponSprite;
    private Animation gunfire;
    private boolean firing;
    private float firingTime;
    private float centerx;
    private float centery;
    private float firex, firey;
    private float fireRate;
    private int damage;
    private Sound bulletFire = Gdx.audio.newSound(Gdx.files.internal("bulletsound.mp3"));
    public Weapon(int id, String name, Texture image, int weaponType, float fireRate, int damage) {
        super(id, name, image);
        type = weaponType;
        weaponSprite = new Sprite(image);
        TextureRegion[] fireFrames = TextureRegion.split(new Texture(Gdx.files.internal("weapons/gunfire.png")), 61, 61)[0];
        gunfire = new Animation(fireRate/6f, fireFrames);
        firing = false;
        firingTime = 0;
        this.fireRate = fireRate;
        this.damage = damage;
    }
    public int getType(){
        return type;
    }
    public Sprite getWeaponSprite(){
        return weaponSprite;
    }
    public void setWeaponSprite(Sprite sprite){
        this.weaponSprite = sprite;
    }
    public int getDamage(){
        return damage;
    }

    public void draw(SpriteBatch batch){
        float angle = weaponSprite.getRotation();
        if(firing){
            firingTime += Gdx.graphics.getDeltaTime();
            Sprite fire = new Sprite(gunfire.getKeyFrame(firingTime, false));
            fire.setSize(64, 64);
            if(firingTime > fireRate){
                firing = false;
                firingTime = 0;
            }
            fire.setRotation(angle);
            fire.setCenter(firex, firey);
            batch.begin();
            fire.draw(batch);
            batch.end();
        }



        batch.begin();
        weaponSprite.draw(batch);
        batch.end();
    }

    public void fire(){
        if(!firing) {
            firing = true;
            bulletFire.play();
            GameScreen.bullets.add(new Bullet(this));
        }

    }

    public void setWeaponPosition(float x, float y){
        weaponSprite.setCenter(x, y);
        centerx = x;
        centery = y;

    }
    public void setWeaponRotation(float angle){
        weaponSprite.setRotation(angle);
    }
    public void setFirePosition(float x, float y){
        firex = x;
        firey = y;
    }

    public float getFireX()
    {
        return firex;
    }
    public float getFireY()
    {
        return firey;
    }

}
