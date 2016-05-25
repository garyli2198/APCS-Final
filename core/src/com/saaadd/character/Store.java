package com.saaadd.character;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.saaadd.game.GameScreen;
import com.saaadd.item.Weapon;

import java.util.ArrayList;

/**
 * Store method that contains the game store mechanics
 *
 * @author Stanley Huang
 * @version 5/24/16
 *
 * @author Period - 6
 * @author Assignment - APCS Final
 *
 * @author Sources - Gary Li, Wesley Pang
 */
public class Store {
    private ArrayList<Weapon> weapons;
    private Rectangle window;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private Texture ammoIcon;
    private boolean cannotBuy;
    private int select;
    private int ammoType;
    private Sound bought = Gdx.audio.newSound(Gdx.files.internal("bought.mp3"));
    private Sound denied = Gdx.audio.newSound(Gdx.files.internal("denied.mp3"));

    /**
     * constructor
     * @param weapons weapons to sell
     * @param ammoIcon ammo icon display
     * @param ammoType ammo type
     */
    public Store(ArrayList<Weapon> weapons, Texture ammoIcon, int ammoType){
        this.weapons = weapons;
        window = new Rectangle(Gdx.graphics.getWidth()/1.7f, Gdx.graphics.getHeight()/1.5f, Gdx.graphics.getWidth()/4f,
                Gdx.graphics.getHeight()/5f);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        this.ammoIcon = ammoIcon;
        select = 1;
        this.ammoType = ammoType;
    }

    /**
     * adds weapon to store's weapon array
     * @param weapon weapon to put in
     */
    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
    }

    /**
     * updates store
     */
    public void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            if(select < weapons.size()){
                select++;
            }
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(select != 1){
                select--;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(!cannotBuy){
                bought.play(1);
            }else{
                denied.play(1);
            }
            if(select == 0){
                if(ammoType == 0 && !cannotBuy){
                    GameScreen.player.addHealthAmmo(2);
                    GameScreen.player.addMoney(-20);

                }
                else if(ammoType == 1 && !cannotBuy){

                    GameScreen.player.addSingleAmmo(20);
                    GameScreen.player.addMoney(-40);

                }else{
                    if(!cannotBuy){
                        GameScreen.player.addAutoAmmo(100);
                        GameScreen.player.addMoney(-40);
                    }
                }
            } else {
                if (!cannotBuy) {
                    GameScreen.player.getInventory().add(weapons.get(select - 1));
                    GameScreen.player.addMoney(weapons.get(select - 1).getPrice() * -1);
                }
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            select = 0;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            select = 1;
        }


        if(select == 0){
            if(ammoType == 0 && GameScreen.player.getInventory().contains(Weapon.weapons.get("healthgun")) && GameScreen.player.getMoney()>20)
            {
                cannotBuy = false;
            }else if(ammoType == 0){
                cannotBuy = true;
            }
            else if(ammoType == 1 && GameScreen.player.getInventory().contains(Weapon.weapons.get("sniper")) && GameScreen.player.getMoney()>40){
                cannotBuy = false;
            }else if(ammoType == 1){
                cannotBuy = true;
            }
            else if(ammoType == 2 && GameScreen.player.getMoney()>40 && (GameScreen.player.getInventory().contains(Weapon.weapons.get("smg"))
                    || GameScreen.player.getInventory().contains(Weapon.weapons.get("machinegun")) ||
                    GameScreen.player.getInventory().contains(Weapon.weapons.get("minigun")))){
                cannotBuy = false;
            }else if(ammoType == 2){
                cannotBuy = true;
            }
        }
        else{
            cannotBuy = GameScreen.player.getInventory().contains(weapons.get(select - 1)) ||
                    GameScreen.player.getMoney() < weapons.get(select - 1).getPrice();
        }



    }

    /**
     * draws store ui
     */
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
                if (cannotBuy){
                    renderer.setColor(Color.RED);
                }else {
                    renderer.setColor(Color.YELLOW);
                }
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
            if(cannotBuy == true){
                renderer.setColor(Color.RED);
            }else{
                renderer.setColor(Color.YELLOW);
            }
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
