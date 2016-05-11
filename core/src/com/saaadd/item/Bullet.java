package com.saaadd.item;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


/**
 * Created by Gary on 5/10/2016.
 */
public class Bullet {

    private float radians;
    private Weapon weapon;
    private float posX,posY;
    double dx,dy;
    private boolean remove;
    private float lifeTime = 2, lifeTimer;
    public Bullet(Weapon w)
    {
        radians = (float) Math.toRadians(w.getWeaponSprite().getRotation() + 90);
        weapon = w;
        float speed = 10;
        dx = Math.cos(radians)*speed;
        dy = Math.sin(radians)*speed;
        posX = w.getFireX();
        posY = w.getFireY();
    }

    public boolean shouldRemove()
    {
        return remove;
    }

    public void update(float dt)
    {
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
