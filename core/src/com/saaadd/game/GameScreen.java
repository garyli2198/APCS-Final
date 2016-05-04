package com.saaadd.game;

import com.saaadd.character.Player;
import com.saaadd.character.Character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    public static float stateTime;
    private Player c;
    private Character cuck;
    private SAAADD game;
    private SpriteBatch batch;
    public static OrthographicCamera cam;
    public static Sprite map;
    public GameScreen(final SAAADD game){
        //test map
        map = new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));
        map.setSize(800, 800);
        map.setPosition(0, 0);
        //
        cam = new OrthographicCamera(600, 600 * ((float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth()));
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
        this.game = game;
        stateTime = 0f;
        batch = new SpriteBatch();
        //
        //test character
        c = new Player(new Texture(Gdx.files.internal("legs.png")), new Texture(Gdx.files.internal("officerbody.png")));
        c.setLocation(cam.position.x, cam.position.y);
        cuck = new Character(new Texture(Gdx.files.internal("legs.png")), new Texture(Gdx.files.internal("officerbody.png")));
        cuck.setLocation(0, 0);
    }



    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        c.update();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        stateTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        map.draw(batch);
        batch.end();
        c.draw(batch);
        cuck.draw(batch);
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
