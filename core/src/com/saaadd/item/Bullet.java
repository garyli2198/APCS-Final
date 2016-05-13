package com.saaadd.item;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saaadd.game.GameScreen;


/**
 * Created by Gary on 5/10/2016.
 */
public class Bullet {

    private float radians;
    private Weapon weapon;
    private float posX,posY;
    private float dx,dy;
    private boolean remove;
    private float lifeTime = 2, lifeTimer;
    public Bullet(Weapon w)
    {
        radians = (float) Math.toRadians(w.getWeaponSprite().getRotation() + 90);
        weapon = w;
        float speed = 10;
        dx = (float) Math.cos(radians)*speed;
        dy = (float) Math.sin(radians)*speed;
        posX = w.getFireX();
        posY = w.getFireY();
    }
    public Weapon getWeapon(){
        return weapon;
    }
    public float getX(){
        return posX;
    }
    public float getY(){
        return posY;
    }
    public Vector2 getVector(){
        return new Vector2(dx, dy);
    }
    public boolean shouldRemove()
    {
        return remove;
    }
    public void setRemove(boolean remove){
        this.remove = remove;
    }

    public void update(float dt)
    {
        for(RectangleMapObject r : GameScreen.mapObjects.getByType(RectangleMapObject.class)){
            Rectangle rect = r.getRectangle();
            if(posX > rect.getX() && posX < rect.getX() + rect.getWidth() &&
                    posY > rect.getY() && posY < rect.getY() + rect.getHeight()){
                remove = true;
            }
        }
        posX += dx;
        posY += dy;

        lifeTimer += dt;
        if(lifeTimer > lifeTime)
        {
            remove = true;
        }
    }

    public void draw(ShapeRenderer sr)
    {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(238, 238, 0, 1);
        sr.ellipse(posX,posY,3,4);
        sr.end();
    }
}
