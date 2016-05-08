package com.saaadd.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.saaadd.character.Player;
import com.saaadd.character.Character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.saaadd.item.Weapon;

public class GameScreen extends ApplicationAdapter implements Screen {
    public static float stateTime;
    private Player c;
    private Character cuck;
    private SAAADD game;
    private SpriteBatch batch;
    private TiledMapRenderer rend;
    private TiledMap tile;
    public static OrthographicCamera cam;
    public static Sprite map;
    public GameScreen(final SAAADD game){
        //camera initialization
        cam = new OrthographicCamera(800, 800 * ((float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth()));
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
        //tiledMap initialization
        tile = new TmxMapLoader().load("SAAADD TEST.tmx");
        rend = new OrthogonalTiledMapRenderer(tile);
        //gameScreen initilization
        this.game = game;
        stateTime = 0f;
        batch = new SpriteBatch();
        //test characters
        Weapon w = new Weapon(1, "pistol", new Texture(Gdx.files.internal("weapons/1h_pistol.png")), Weapon.oneH);
        c = new Player(new Texture(Gdx.files.internal("legs.png")), new Texture(Gdx.files.internal("officerbody.png")),
                cam.position.x, cam.position.y,0, w);
        cuck = new Character(new Texture(Gdx.files.internal("legs.png")), new Texture(Gdx.files.internal("officerbody.png")));
        cuck.setLocation(0, 0);
    }



    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        c.update();

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        rend.setView(cam);
        rend.render();
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
