package com.saaadd.character;

import com.badlogic.gdx.Game;
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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;


public class Player extends Character implements InputProcessor {
    private int money;
    private int pMoving;
    private boolean[] direction;
    private boolean[] overlapX;
    public final static int front = 0, left = 1, back = 2, right = 3;
    private boolean mouseDown;
    private LinkedList<Weapon> inventory;
    private ListIterator<Weapon> iter;
    private int singleAmmo;
    private int autoAmmo;
    private int healthAmmo;

    public Player(Texture legSheet, Texture bodySheet) {
        super(legSheet, bodySheet);
        Gdx.input.setInputProcessor(this);
        direction = new boolean[4];
        pMoving = 0;
        overlapX = new boolean[GameScreen.mapObjects.getCount()];
        money = 0;
    }
    public Player(Texture legSheet, Texture bodySheet, float x, float y, float angle){
        super(legSheet, bodySheet, x, y, angle);
        Gdx.input.setInputProcessor(this);
        direction = new boolean[4];
        pMoving = 0;
        overlapX = new boolean[GameScreen.mapObjects.getCount()];
        money = 0;


    }

    public Player(Texture legSheet, Texture bodySheet, float x, float y, float angle, int health, Weapon weapon){
        super(legSheet, bodySheet, x, y, angle, health, weapon);
        Gdx.input.setInputProcessor(this);
        direction = new boolean[4];
        pMoving = 0;
        overlapX = new boolean[GameScreen.mapObjects.getCount()];
        money = 0;
        inventory = new LinkedList<Weapon>();
        inventory.add(weapon);
        inventory.add(Weapon.copyOf(Weapon.weapons.get("healthgun")));

        iter = inventory.listIterator();
        singleAmmo = 100;
        autoAmmo = 100;
        healthAmmo = 2;
    }

    public int getMoney(){
        return money;
    }
    public void addMoney(int money){
        this.money += money;
    }
    public int getAmmo(){
        if(getWeapon().isAuto()){
            return autoAmmo;
        }
        else if(getWeapon().equals(Weapon.weapons.get("pistol"))){
            return 999999;
        }
        else if(getWeapon().equals(Weapon.weapons.get("healthgun"))){
            return healthAmmo;
        }
        else{
            return singleAmmo;
        }
    }
    @Override
    public boolean shouldRemove() {
        return getHealth() <= 0;
    }

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

    @Override
    public void onHit(Bullet bullet) {
        this.addHealth( -1 * bullet.getWeapon().getDamage());

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // input processor

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
        return true;
    }

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
        if(character == 'e'){
            if(iter.hasNext()) {
                setWeapon(iter.next());
            }
            else{
                setWeapon(inventory.getFirst());
                iter = inventory.listIterator();
            }
        }
        else if(character == 'q'){
            if(iter.hasPrevious()){
                setWeapon(iter.previous());
            }
            else{
                setWeapon(inventory.getLast());
                iter = inventory.listIterator(inventory.size() - 1);
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(!getWeapon().isAuto() && singleAmmo > 0 && !getWeapon().equals(Weapon.weapons.get("pistol"))) {
            if(getWeapon().equals(Weapon.weapons.get("healthgun"))){
                if(healthAmmo > 0 && !getWeapon().isFiring()) {
                    healthAmmo--;
                }
            }
            else {
                if(singleAmmo > 0 && !getWeapon().isFiring()) {
                    singleAmmo--;
                }
            }
        }
        if(getWeapon().equals(Weapon.weapons.get("pistol")) || getAmmo() >= 0) {
            this.getWeapon().fire();
        }
        mouseDown = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mouseDown = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 vect = new Vector2(screenX - Gdx.graphics.getWidth() / 2f, screenY - Gdx.graphics.getHeight() / 2f);
        this.setRotation(270 - vect.angle());
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector2 vect = new Vector2(screenX - Gdx.graphics.getWidth() / 2f, screenY - Gdx.graphics.getHeight() / 2f);
        this.setRotation(270 - vect.angle());

        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        if(amount == 1){
            if(iter.hasNext()) {
                setWeapon(iter.next());
            }
            else{
                setWeapon(inventory.getFirst());
                iter = inventory.listIterator();
            }

        }
        else if( amount == -1){
            if(iter.hasPrevious()){
                setWeapon(iter.previous());
            }
            else{
                setWeapon(inventory.getLast());
                iter = inventory.listIterator(inventory.size() - 1);
            }
        }

        return true;
    }

}
