package com.saaadd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

/**
 * Created by Gary on 5/13/2016.
 */
public class MainMenuScreen implements Screen {
    private SAAADD game;
    private SpriteBatch batch;
    private BitmapFont font;
    private boolean isLoading;
    private Sound mainBackground, mainClick;
    public MainMenuScreen(final SAAADD game) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("vidgamefont.fnt"));
        isLoading = false;
        mainBackground = Gdx.audio.newSound(Gdx.files.internal("MainMenuScreenBackground.mp3"));
        mainClick = Gdx.audio.newSound(Gdx.files.internal("MainMenuPlay.mp3"));
        mainBackground.loop();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(isLoading) {
            game.setScreen(new GameScreen(game));
            mainClick.dispose();
            mainBackground.dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            mainClick.play();
            isLoading = true;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        batch.begin();
        font.draw(batch, "CLICK TO START \nPRESS ESC TO EXIT", Gdx.graphics.getWidth() / 2,
                Gdx.graphics.getHeight() / 2, 10, 5, true);
        batch.end();

        if(isLoading) {
            batch.begin();
            font.draw(batch, "GAME LOADING...", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 4, 10, 5, true);
            batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
