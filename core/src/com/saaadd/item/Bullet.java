package com.saaadd.item;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.saaadd.game.GameScreen;


/**
 * Bullet class
 * @author Gary Li
 * @version 5/24/16
 *
 * @author Period - 6
 * @author Assignment - APCS Final
 *
 * @author Sources - Wesley Pang, Stanley Huang
 */
public class Bullet {

    private float radians;
    private Weapon weapon;
    private float posX,posY;
    private float dx,dy;
    private boolean remove;
    private float lifeTime = 2, lifeTimer;

    /**
     * constructs a bullet of weapon w
     * @param w weapon
     */
    public Bullet(Weapon w)
    {
        radians = (float) Math.toRadians(w.getWeaponSprite().getRotation() + 90);
        weapon = w;
        float speed = w.getBulletSpeed();
        dx = (float) Math.cos(radians)*speed;
        dy = (float) Math.sin(radians)*speed;
        posX = w.getFireX();
        posY = w.getFireY();
    }

    /**
     *
     * @return
     */
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
