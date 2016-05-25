package com.saaadd.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.saaadd.character.Store;
import com.saaadd.game.GameScreen;

import java.util.ArrayList;

/**
 * Creates a user interface including methods to display healthbar and intro text.
 *
 * @author Stanley Huang
 * @version 5/24/16
 *
 * @author Period - 6
 * @author Assignment - APCS Final
 *
 * @author Sources - Gary Li, Wesley Pang
 */
public class UserInterface {

    private SpriteBatch batch;
    private HealthBar healthBar;
    private GameScreen screen;
    private String[] introText;
    private ArrayList<Store> stores;
    private WeaponDisplay weaponDisplay;
    public UserInterface(GameScreen screen){
        healthBar = new HealthBar();
        weaponDisplay = new WeaponDisplay();
        batch = new SpriteBatch();
        this.screen = screen;
        introText = new String[]{
                "Hello, and welcome to the\n" + "Super Amazing Awesome Adventures of" + "\nDylan Dykklace!\n" +
                        "(Press N to continue or ESC to quit)",
                "Walk around with W,A,S,D and shoot with Left Click\n" +
                        "(Press N to continue or ESC to quit)",
                "Your name is Dylan Dykklace.\n You are the biggest Cuckold in the universe.\n" +
                        "Try to survive as long as possible. Good Luck!\n" + "(Press N to continue or ESC to quit)"
        };
        stores = new ArrayList<Store>();
    }

    public void update(){
        healthBar.update();
        weaponDisplay.update();
    }
    public void draw(){
        batch.setProjectionMatrix(GameScreen.cam.combined);

        batch.begin();
        if(GameScreen.gameStage < 3){
            GameScreen.font.draw(batch, introText[GameScreen.gameStage], GameScreen.player.getX(),
                    GameScreen.player.getY() + 300, 10, 5, true);
        }
        if(GameScreen.gameStage > 2) {

            if(!screen.getCurrentWave().hasStarted()){
                GameScreen.font.draw(batch, "PRESS N TO START NEXT ROUND",
                        GameScreen.player.getX(), GameScreen.player.getY() + 300, 10, 5, true);
            }
            GameScreen.font.draw(batch, "ROUND: " + String.valueOf(screen.getCurrentWave().getRound()),
                    GameScreen.player.getX() - Gdx.graphics.getWidth() / 3.5f,
                    GameScreen.player.getY() + Gdx.graphics.getHeight() / 3.5f);
            GameScreen.font.draw(batch, "MONEY: " + String.valueOf(GameScreen.player.getMoney()),
                    GameScreen.player.getX() - Gdx.graphics.getWidth() / 3.5f,
                    GameScreen.player.getY() + Gdx.graphics.getHeight() / 4);

        }
        batch.end();
        healthBar.draw();
        weaponDisplay.draw();

    }
}
