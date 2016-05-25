package com.saaadd.character;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.saaadd.game.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.saaadd.item.Bullet;
import com.saaadd.item.Weapon;

import java.util.ArrayList;

/**
 * Describes the main player character
 *
 * @author Stanley Huang
 * @version 5/24/16
 *
 * @author Period - 6
 * @author Assignment - APCS Final
 *
 * @author Sources - Gary Li, Wesley Pang
 */
public class Player extends Character implements InputProcessor {
    private int money;
    private int pMoving;
    private int weaponIndex = 0;
    private boolean[] direction;
    private boolean[] overlapX;
    public final static int front = 0, left = 1, back = 2, right = 3;
    private boolean mouseDown;
    private ArrayList<Weapon> inventory;
    private int singleAmmo;
    private int autoAmmo;
    private int healthAmmo;

    /**
     * player constructor
     * @param legSheet leg texture
     * @param bodySheet body texture
     * @param x x position
     * @param y y position
     * @param angle angle facing
     * @param health health
     * @param weapon initial weapon
     */
    public Player(Texture legSheet, Texture bodySheet, float x, float y, float angle, int health, Weapon weapon){
        super(legSheet, bodySheet, x, y, angle, health, weapon);
        Gdx.input.setInputProcessor(this);
        direction = new boolean[4];
        pMoving = 0;
        overlapX = new boolean[GameScreen.mapObjects.getCount()];
        money = 1000;
        inventory = new ArrayList<Weapon>();
        inventory.add(weapon);
        singleAmmo = 100;
        autoAmmo = 100;
        healthAmmo = 2;
    }

    /**
     * @return player inventory
     */
    public ArrayList<Weapon> getInventory(){
        return inventory;
    }

    /**
     * adds single shot ammo
     * @param i number of bullets to add
     */
    public void addSingleAmmo(int i){
        singleAmmo += i;
    }

    /**
     * adds automatic shot ammo
     * @param i number of bullets to add
     */
    public void addAutoAmmo(int i){
        autoAmmo +=i;
    }

    /**
     * adds health ammo
     * @param i num of health bullets to add
     */
    public void addHealthAmmo(int i){
        healthAmmo += i;
    }

    /**
     * @return player money
     */
    public int getMoney(){
        return money;
    }

    /**
     * adds money
     * @param money amount to add
     */
    public void addMoney(int money){
        this.money += money;
    }

    /**
     * gets ammo for each of the gun types
     * @return number of bullets
     */
    public int getAmmo(){
        if(getWeapon().isAuto()){
            return autoAmmo;
        }
        else if(getWeapon().equals(Weapon.weapons.get("pistol"))){
            return -1;
        }
        else if(getWeapon().equals(Weapon.weapons.get("healthgun"))){
            return healthAmmo;
        }
        else{
            return singleAmmo;
        }
    }
    /**
     * @return whether or not player should be removed based on health
     */
    @Override
    public boolean shouldRemove() {
        return getHealth() <= 0;
    }

    /**
     * updates player
     */
    @Override
    public void update() {
        //movement
        this.isMoving = pMoving > 0;
        //automatic fire handling
        if(mouseDown && getWeapon().isAuto() && autoAmmo > 0){
            autoAmmo--;
            getWeapon().fire();
        }
        //object detection
        boolean[] d = direction;
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

        //movement
        GameScreen.cam.position.x = getX();
        GameScreen.cam.position.y = getY();
        if (isMoving) {
            if (direction[back]) {
                translate(0,-speed);
            }
            if(direction[right]){
                translate(speed,0);
            }
            if(direction[left]){
                translate(-speed,0);
            }
            if(direction[front]) {
                translate(0,speed);
            }

        }
    }

    /**
     * called when hit by bullet; subtracts bullet damage from player
     * @param bullet bullet
     */
    @Override
    public void onHit(Bullet bullet) {
        this.addHealth( -1 * bullet.getWeapon().getDamage());

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // input processor

    /**
     * checks if key is pressed
     * @param keycode key
     * @return whether or not key is pressed
     */
    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Keys.W) {
            pMoving++;
            direction[front] = true;
        }
        else if (keycode == Keys.S) {
            pMoving++;
            direction[back] = true;
        }
        else if (keycode == Keys.A) {
            pMoving++;
            direction[left] = true;
        }
        else if (keycode == Keys.D) {
            pMoving++;
            direction[right] = true;
        }
        else if (keycode == Keys.N)
        {
            GameScreen.gameStage++;
        }
        else if (keycode == Keys.ESCAPE){
            Gdx.app.exit();
        }
        else if(keycode == Keys.E){
            if(weaponIndex < inventory.size() -1 ){
                weaponIndex++;
                setWeapon(inventory.get(weaponIndex));
            }else{
                weaponIndex = 0;
                setWeapon(inventory.get(weaponIndex));
            }
        }
        else if(keycode == Keys.Q){
            if(weaponIndex == 0){
                weaponIndex = inventory.size()- 1;
                setWeapon(inventory.get(weaponIndex));
            }else{
                weaponIndex--;
                setWeapon(inventory.get(weaponIndex));
            }
        }
        return true;
    }

    /**
     * checks to see if key is released to stop player movement
     * @param keycode key
     * @return whether or not key is released
     */
    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.W) {
            pMoving--;
            direction[front] = false;
        }
        else if (keycode == Keys.S) {
            pMoving--;
            direction[back] = false;
        }
        else if (keycode == Keys.A) {
            pMoving--;
            direction[left] = false;
        }
        else if (keycode == Keys.D) {
            pMoving--;
            direction[right] = false;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    /**
     * checks to see if mouse is clicked in a certain position on the screen
     * @param screenX x position
     * @param screenY y position
     * @param pointer mouse pointer
     * @param button button
     * @return whether or not mouse is clicked
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(getWeapon().equals(Weapon.weapons.get("pistol")) || getAmmo() > 0) {
            if(!getWeapon().equals(Weapon.weapons.get("pistol"))){
                if(getWeapon().equals(Weapon.weapons.get("healthgun")) && !getWeapon().isFiring()){
                    healthAmmo--;
                }
                else if(!getWeapon().isAuto() && !getWeapon().isFiring()){
                    singleAmmo--;
                }

            }
            if(!getWeapon().isAuto())
                this.getWeapon().fire();
        }

        mouseDown = true;
        return true;
    }

    /**
     * checks to see if mouse is released
     * @param screenX pos x
     * @param screenY pos y
     * @param pointer mouse pointer
     * @param button mouse button
     * @return whether or not button is released
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mouseDown = false;
        return true;
    }

    /**
     * checks to see if mouse is dragged
     * @param screenX pos x
     * @param screenY pos y
     * @param pointer mouse pointer
     * @return mouse dragged
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 vect = new Vector2(screenX - Gdx.graphics.getWidth() / 2f, screenY - Gdx.graphics.getHeight() / 2f);
        this.setRotation(270 - vect.angle());
        return true;
    }

    /**
     * checks to see if mouse is moved
     * @param screenX pos x
     * @param screenY pos y
     * @return mouse moved
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector2 vect = new Vector2(screenX - Gdx.graphics.getWidth() / 2f, screenY - Gdx.graphics.getHeight() / 2f);
        this.setRotation(270 - vect.angle());

        return true;
    }

    /**
     * checks to see if mouse is scrolled
     * @param amount up or down
     * @return mouse scrolled
     */
    @Override
    public boolean scrolled(int amount) {
        if(amount == 1){
            if(weaponIndex < inventory.size() -1 ){
                weaponIndex++;
                setWeapon(inventory.get(weaponIndex));
            }else{
                weaponIndex = 0;
                setWeapon(inventory.get(weaponIndex));
            }
        }
        else if(amount == -1){
            if(weaponIndex == 0){
                weaponIndex = inventory.size()- 1;
                setWeapon(inventory.get(weaponIndex));
            }else{
                weaponIndex--;
                setWeapon(inventory.get(weaponIndex));
            }
        }

        return true;
    }

}
