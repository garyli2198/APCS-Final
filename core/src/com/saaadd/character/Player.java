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
import com.saaadd.item.Weapon;

public class Player extends Character implements InputProcessor {
    private Vector2 movementVector;
    private int pMoving;
    private boolean[] direction;
    private boolean[] overlapX;
    public final static int front = 0, left = 1, back = 2, right = 3;
    public Player(Texture legSheet, Texture bodySheet) {
        super(legSheet, bodySheet);
        Gdx.input.setInputProcessor(this);
        movementVector = new Vector2();
        direction = new boolean[4];
        pMoving = 0;
        overlapX = new boolean[GameScreen.mapObjects.getCount()];
    }
    public Player(Texture legSheet, Texture bodySheet, float x, float y, float angle){
        super(legSheet, bodySheet, x, y, angle);
        Gdx.input.setInputProcessor(this);
        movementVector = new Vector2();
        direction = new boolean[4];
        pMoving = 0;
        overlapX = new boolean[GameScreen.mapObjects.getCount()];

    }
    public Player(Texture legSheet, Texture bodySheet, float x, float y, float angle, Weapon weapon){
        super(legSheet, bodySheet, x, y, angle, weapon);
        Gdx.input.setInputProcessor(this);
        movementVector = new Vector2();
        direction = new boolean[4];
        pMoving = 0;
        overlapX = new boolean[GameScreen.mapObjects.getCount()];

    }

    public void update() {
        this.isMoving = pMoving > 0;
        boolean[] d = direction;
        int count = 0;
        for(RectangleMapObject r : GameScreen.mapObjects.getByType(RectangleMapObject.class)){
            Rectangle rect = r.getRectangle();
            if(Intersector.overlaps(rect, getRectangle() )){
                if(overlapX[count]){
                    if(getY() > rect.getY()){
                        GameScreen.cam.position.y = rect.getY() + rect.getHeight() + getRectangle().getHeight()/2f + 1;
                    }
                    else if(getY() < rect.getY()){
                        GameScreen.cam.position.y = rect.getY() - getRectangle().getHeight()/2f - 1;
                    }
                }
                else{
                    if(getX() > rect.getX()){
                        GameScreen.cam.position.x = rect.getX() + rect.getWidth() + getRectangle().getWidth()/2f + 1;
                    }
                    else if(getX() < rect.getX()){
                        GameScreen.cam.position.x = rect.getX() - getRectangle().getWidth()/2f - 1;
                    }
                }
            }
            overlapX[count] = getRectangle().getX() > rect.getX() - getRectangle().getWidth() &&
                    getRectangle().getX() < rect.getX() + rect.getWidth();
            count++;

        }
        this.setLocation(GameScreen.cam.position.x, GameScreen.cam.position.y);
        if (isMoving) {
            if (direction[back]) {
                GameScreen.cam.translate(0,-5);
            }
            if(direction[right]){
                GameScreen.cam.translate(5,0);
            }
            if(direction[left]){
                GameScreen.cam.translate(-5,0);
            }
            if(direction[front]) {
                GameScreen.cam.translate(0,5);
            }

        }
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.getWeapon().fire();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector2 vect = new Vector2(screenX - Gdx.graphics.getWidth() / 2f, screenY - Gdx.graphics.getHeight() / 2f);
        this.setRotation(270 - vect.angle());
        movementVector = vect.setLength(speed).scl(1, -1);

        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

}
