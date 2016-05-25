package com.saaadd.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.saaadd.game.GameScreen;
import com.saaadd.item.Weapon;

/**
 * Created by stanl on 5/24/2016.
 */
public class WeaponDisplay {
    private Weapon weapon;
    private ShapeRenderer renderer;
    private Rectangle borderRectangle;
    private Sprite sprite;
    private SpriteBatch batch;
    private Texture ammoIcon;
    public WeaponDisplay(){
        weapon = GameScreen.player.getWeapon();
        renderer = new ShapeRenderer();
        borderRectangle = new Rectangle(Gdx.graphics.getWidth()/1.5f, Gdx.graphics.getHeight() * 0.05f,
                Gdx.graphics.getWidth() * 0.2f, Gdx.graphics.getHeight() * 0.1f);
        sprite = new Sprite(weapon.getImage());
        batch = new SpriteBatch();
        ammoIcon = new Texture(Gdx.files.internal("ammo.png"));
    }
    public void update(){
        weapon = GameScreen.player.getWeapon();
        sprite = new Sprite(weapon.getImage());
        sprite.setBounds(borderRectangle.getX() + 5, borderRectangle.getY() + 5, borderRectangle.getWidth() - 10,
                borderRectangle.getHeight() - 10 );
        sprite.setPosition(borderRectangle.getX() + 5, borderRectangle.getY() + 5);
        sprite.setSize( borderRectangle.getHeight() - 10,
                borderRectangle.getHeight() - 10);
    }
    public void draw(){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLACK);
        renderer.rect(borderRectangle.getX(), borderRectangle.getY(), borderRectangle.getWidth(),
                borderRectangle.getHeight());
        renderer.setColor(Color.WHITE);
        renderer.rect(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        renderer.end();
        batch.begin();
        GameScreen.font.draw(batch, weapon.getName(), sprite.getX() + sprite.getWidth() + 5, sprite.getY() +
                sprite.getHeight() - 5 );
        sprite.draw(batch);
        batch.draw(ammoIcon, sprite.getX() + sprite.getWidth() + 5, sprite.getY(), sprite.getWidth()/2f, sprite.getHeight()/2f);
        GameScreen.font.draw(batch, ": " + GameScreen.player.getAmmo(), sprite.getX() + sprite.getWidth() + 10 +
                ammoIcon.getWidth(), sprite.getY() + ammoIcon.getHeight()/2);
        batch.end();
    }
}
