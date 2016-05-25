package com.saaadd.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.saaadd.game.GameScreen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Weapon {
    public static HashMap<String, Weapon> weapons = new HashMap<String , Weapon>();
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
    private boolean auto;
    private int length;
    private int bulletSpeed;
    private Sound bulletFire, healthFire;
    private String name;
    private Texture image;
    private int price;
    public Weapon(String name, Texture image, int weaponType, float fireRate, int damage, int length,
                  int bulletSpeed, boolean isAuto, int price) {
        this.name = name;
        this.image = image;
        type = weaponType;
        weaponSprite = new Sprite(image);
        TextureRegion[] fireFrames = TextureRegion.split(new Texture(Gdx.files.internal("weapons/gunfire.png")), 61, 61)[0];
        gunfire = new Animation(0.3f/6f, fireFrames);
        firing = false;
        firingTime = 0;
        this.fireRate = fireRate;
        this.damage = damage;
        auto = isAuto;
        bulletFire = Gdx.audio.newSound(Gdx.files.internal("bulletsound.mp3"));
        healthFire = Gdx.audio.newSound(Gdx.files.internal("healthsound.mp3"));
        this.length = length;
        this.bulletSpeed = bulletSpeed;
        this.price = price;
    }
    public static Weapon copyOf( Weapon weapon ){
        return new Weapon(weapon.getName(), weapon.getImage(), weapon.getType(), weapon.getFireRate(),weapon.getDamage(),
                weapon.getLength(), weapon.getBulletSpeed(), weapon.isAuto(), weapon.getPrice());
    }
    public static void loadWeapons() throws FileNotFoundException {
        File file = new File("weapons.data");
        Scanner scan = new Scanner(file);
        while(scan.hasNextLine()){
            String name = scan.next();
            Texture texture = new Texture(Gdx.files.internal("weapons/" + scan.next()));
            int type = scan.nextInt();
            float fireRate = scan.nextFloat();
            int damage = scan.nextInt();
            int length = scan.nextInt();
            int bulletSpeed = scan.nextInt();
            boolean auto = scan.nextBoolean();
            int price = scan.nextInt();
            Weapon weapon = new Weapon(name, texture, type, fireRate, damage, length, bulletSpeed, auto, price);
            weapons.put(name, weapon);
        }

    }

    public boolean equals(Object other){
        if(other instanceof Weapon) {
            return this.getName().equals(((Weapon) other).getName());
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public Texture getImage() {
        return image;
    }
    public int getType(){
        return type;
    }
    public Sprite getWeaponSprite(){
        return weaponSprite;
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
            else if(firingTime <= 0.3f) {
                fire.setRotation(angle);
                fire.setCenter(firex, firey);
                batch.begin();
                fire.draw(batch);
                batch.end();
            }
        }



        batch.begin();
        weaponSprite.draw(batch);
        batch.end();
    }

    public void fire(){
        if(!firing) {
            firing = true;
            if(damage < 0   ){
                healthFire.play(1);
            }else{
                bulletFire.play(1);
            }
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
    public boolean isAuto(){
        return auto;
    }
    public String toString(){
        return getName();
    }
    public int getLength(){
        return length;
    }
    public int getBulletSpeed(){
        return  bulletSpeed;
    }
    public float getFireRate(){
        return fireRate;
    }
    public boolean isFiring(){
        return firing;
    }
    public int getPrice(){
        return price;
    }
}
