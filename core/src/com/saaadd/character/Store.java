package com.saaadd.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.saaadd.game.GameScreen;
import com.saaadd.item.Weapon;

import java.util.ArrayList;

/**
 * Created by stanl on 5/22/2016.
 */
public class Store {
    private ArrayList<Weapon> weapons;
    private Rectangle window;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private Texture ammoIcon;
    private int select;
    public Store(ArrayList<Weapon> weapons, Texture ammoIcon){
        this.weapons = weapons;
        window = new Rectangle(Gdx.graphics.getWidth()/1.7f, Gdx.graphics.getHeight()/1.5f, Gdx.graphics.getWidth()/4f,
                Gdx.graphics.getHeight()/5f);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        this.ammoIcon = ammoIcon;
        select = 1;
    }
    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
    }
    public ArrayList<Weapon> getWeapons(){
        return weapons;
    }
    public void update(){

    }
    public void draw(){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLACK);
        renderer.rect(window.getX(), window.getY(), window.getWidth(), window.getHeight());
        float side = (window.getWidth() - 10)/8f;
        float x = window.getX() + 5;
        float y = window.getY() + 5;
        renderer.setColor(Color.WHITE);
        renderer.rect(x, y, side * 8, side);
        renderer.end();

        for(int i = 0; i < 8; i++){


            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.GRAY);
            if(select == i + 1){
                renderer.setColor(Color.YELLOW);
                renderer.set(ShapeRenderer.ShapeType.Filled);
            }

            renderer.rect(x + i * side, y, side, side);
            renderer.end();
            if(i < weapons.size()) {
                batch.begin();
                batch.draw(weapons.get(i).getImage(), x + i * side + 5, y + 5, side - 10, side - 10);
                GameScreen.font.draw(batch, "" + weapons.get(i).getPrice(), x + i * side, y + side * 1.5f);
                batch.end();
            }

        }
        if(select == 0){
            renderer.begin();
            renderer.set(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(Color.YELLOW);
            renderer.rect(x, window.getY() + window.getHeight()/1.7f, side, side);
            renderer.end();
        }

        batch.begin();
        GameScreen.font.draw(batch, "Buy Ammo:", x, window.getY() + window.getHeight() - 10);
        GameScreen.font.draw(batch, "Buy weapons:", x, y + window.getHeight()/1.75f);
        batch.draw(ammoIcon, x, window.getY() + window.getHeight()/1.7f, side, side);
        batch.end();
    }


}
