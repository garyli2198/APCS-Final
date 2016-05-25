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
/**
 * @author Stanley Huang
 * @version 5/24/16
 *
 * @author Period - 6
 * @author Assignment - APCS Final
 *
 * @author Sources - Gary Li, Wesley Pang
 */
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

    /**
     * Constructs a weapon
     * @param name weapon name
     * @param image weapon image
     * @param weaponType weapon type (e.g. single handed, two handed)
     * @param fireRate weapon fire rate
     * @param damage weapon damage
     * @param length weapon length
     * @param bulletSpeed bullet speed
     * @param isAuto whether or not weapon is automatic
     * @param price weapon price
     */
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

    /**
     * returns a copy of a weapon object specified in the parameter
     * @param weapon weapon name
     * @return weapon object with name in parameter
     */
    public static Weapon copyOf( Weapon weapon ){
        return new Weapon(weapon.getName(), weapon.getImage(), weapon.getType(), weapon.getFireRate(),weapon.getDamage(),
                weapon.getLength(), weapon.getBulletSpeed(), weapon.isAuto(), weapon.getPrice());
    }

    /**
     * loads weapon data from weapons.data and stores them in the weapons HashMap
     * @throws FileNotFoundException
     */
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

    /**
     * compares two weapons
     * @param other other weapon
     * @return if same weapon
     */
    public boolean equals(Object other){
        if(other instanceof Weapon) {
            return this.getName().equals(((Weapon) other).getName());
        }
        return false;
    }

    /**
     * gets weapon name
     * @return weapon name
     */
    public String getName() {
        return name;
    }

    /**
     * gets weapon image
     * @return weapon image
     */
    public Texture getImage() {
        return image;
    }

    /**
     * gets weapon type
     * @return weapon type
     */
    public int getType(){
        return type;
    }

    /**
     * gets weapon sprite
     * @return weapon sprite
     */
    public Sprite getWeaponSprite(){
        return weaponSprite;
    }

    /**
     * gets weapon damage
     * @return weapon damage
     */
    public int getDamage(){
        return damage;
    }

    /**
     * draws weapon
     * @param batch SpriteBatch
     */
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

    /**
     * fires weapon
     */
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

    /**
     * sets weapon position
     * @param x x position
     * @param y y position
     */
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

    /**
     * @return firing x position
     */
    public float getFireX()
    {
        return firex;
    }

    /**
     * @return firing y position
     */
    public float getFireY()
    {
        return firey;
    }

    /**
     * @return whether or not weapon is automatic
     */
    public boolean isAuto(){
        return auto;
    }

    /**
     * @return name
     */
    public String toString(){
        return getName();
    }

    /**
     * @return weapon length
     */
    public int getLength(){
        return length;
    }

    /**
     * @return weapon bullet speed
     */
    public int getBulletSpeed(){
        return  bulletSpeed;
    }

    /**
     * @return weapon fire rate
     */
    public float getFireRate(){
        return fireRate;
    }

    /**
     * @return whether or not weapon is firing
     */
    public boolean isFiring(){
        return firing;
    }

    /**
     * @return weapon price
     */
    public int getPrice(){
        return price;
    }
}
