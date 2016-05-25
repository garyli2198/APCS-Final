package com.saaadd.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.saaadd.character.Character;
import com.saaadd.game.GameScreen;

/**
 * @author Stanley Huang
 * @version 5/24/16
 *
 * @author Period - 6
 * @author Assignment - APCS Final
 *
 * @author Sources - Gary Li, Wesley Pang
 */
public class HealthBar {
    private ShapeRenderer renderer;
    private int maxHealth;
    private float maxHealthWidth;
    private Rectangle borderRectangle;
    private int currentHealth;
    private Rectangle currentHealthRectangle;

    /**
     * constructs a health bar
     */
    public HealthBar(){
        renderer = new ShapeRenderer();
        maxHealth = Character.defaultHealth;
        currentHealth = GameScreen.player.getHealth();
        borderRectangle = new Rectangle(Gdx.graphics.getWidth() * 0.1f, Gdx.graphics.getHeight() * 0.05f,
                Gdx.graphics.getWidth() * 0.2f, Gdx.graphics.getHeight() * 0.02f);
        currentHealthRectangle = new Rectangle(borderRectangle.getX() + 5, borderRectangle.getY() + 5,
                borderRectangle.getWidth() - 10, borderRectangle.getHeight() - 10);
        maxHealthWidth = currentHealthRectangle.getWidth();
    }

    /**
     * updates health bar
     */
    public void update(){
        currentHealthRectangle.setWidth(GameScreen.player.getHealth()/100f * maxHealthWidth);
    }

    /**
     * draws health bar
     */
    public void draw(){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLACK);
        renderer.rect(borderRectangle.getX(), borderRectangle.getY(), borderRectangle.getWidth(),
                borderRectangle.getHeight());
        renderer.setColor(Color.RED);
        renderer.rect(currentHealthRectangle.getX(), currentHealthRectangle.getY(), currentHealthRectangle.getWidth(),
                currentHealthRectangle.getHeight());

        renderer.end();
    }

}
