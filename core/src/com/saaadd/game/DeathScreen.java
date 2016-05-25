package com.saaadd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Gary Li
 * @version 5/24/16
 *
 * @author Period - 6
 * @author Assignment - APCS Final
 *
 * @author Sources - Stanley Huang, Wesley Pang
 */
public class DeathScreen implements Screen{
    private Sound deathMusic;
    private SAAADD game;
    private BitmapFont font;
    private SpriteBatch batch;
    private GameScreen screen;

    /**
     * constructor for new DeathScreen
     * @param game game to restart into
     * @param screen previous gamescreen
     */
    public DeathScreen(SAAADD game, GameScreen screen){
        this.game = game;
        this.screen = screen;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("vidgamefont.fnt"));
        deathMusic = Gdx.audio.newSound(Gdx.files.internal("death_music.mp3"));
        deathMusic.loop();
    }

    @Override
    public void show() {

    }

    /**
     * Renders death screen
     * @param delta time
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.setColor(Color.RED);
        font.getData().setScale(2,3);
        if(screen.getCurrentWave().getRound() - 1 == 1) {
            font.draw(batch, "GAME OVER\n You survived 1 round\nPlay Again?\n (Y/N)", Gdx.graphics.getWidth() / 2,
                    Gdx.graphics.getHeight() / 1.5f, 10, 5, true);
        }
        else{
            font.draw(batch, "GAME OVER\n You survived " + String.valueOf(screen.getCurrentWave().getRound() - 1)
                    + " rounds\nPlay Again?\n (Y/N)", Gdx.graphics.getWidth() / 2,
                    Gdx.graphics.getHeight() / 1.5f, 10, 5, true);
        }
        batch.end();
        if(Gdx.input.isKeyPressed(Input.Keys.Y)){
            deathMusic.dispose();
            game.setScreen(new MainMenuScreen(game));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.N)){
            Gdx.app.exit();
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
