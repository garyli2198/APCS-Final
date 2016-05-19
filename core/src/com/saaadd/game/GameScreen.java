package com.saaadd.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.saaadd.character.CharacterRenderer;
import com.saaadd.character.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.saaadd.item.Bullet;
import com.saaadd.item.Weapon;
import com.saaadd.ui.UserInterface;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class GameScreen extends ApplicationAdapter implements Screen {
    public static float stateTime;
    public static Player player;
    public static SAAADD game;
    private SpriteBatch batch;
    private TiledMapRenderer rend;
    private TiledMap tile;
    private ShapeRenderer shapeRend;
    public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public static MapObjects mapObjects;
    public static OrthographicCamera cam;
    public static CharacterRenderer characterRenderer;
    public static BitmapFont font;
    private Sound backgroundMusic;
    public static int gameStage = 0;
    private ArrayList<String> gameText = new ArrayList<String>();
    private Wave currentWave;
    private UserInterface userInterface;

    public GameScreen(final SAAADD game) {
        //weapon loading
        try {
            Weapon.loadWeapons();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //play background music
        backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("background.mp3"));
        backgroundMusic.loop();

        //shaperenderer initialization
        shapeRend = new ShapeRenderer();

        //camera initialization
        cam = new OrthographicCamera(1200, 1200 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        //tiledMap initialization
        tile = new TmxMapLoader().load("SAAADD TEST.tmx");
        rend = new OrthogonalTiledMapRenderer(tile);
        mapObjects = tile.getLayers().get("Object Layer 1").getObjects();

        //font initialization
        font = new BitmapFont(Gdx.files.internal("vidgamefont.fnt"));

        //gameScreen initilization
        this.game = game;
        stateTime = 0f;
        batch = new SpriteBatch();

        //test characters
        Weapon w = Weapon.copyOf(Weapon.weapons.get("minigun"));
        player = new Player(new Texture(Gdx.files.internal("legs.png")), new Texture(Gdx.files.internal("officerbody.png")),
                cam.position.x, cam.position.y, 0, 100, w);


        //Character Renderer initialization
        characterRenderer = new CharacterRenderer();
        characterRenderer.add(player);

        //wave init
        currentWave = new Wave(1);

        //ui init
        userInterface = new UserInterface(this);

    }

    public Wave getCurrentWave(){
        return currentWave;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        //update statetime
        stateTime += Gdx.graphics.getDeltaTime();
        //update color background
        Gdx.gl.glClearColor((float) (24 / 255.0), (float) (105 / 255.0), (float) (4 / 255.0), 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //update camera
        cam.update();
        //update shape render and batch
        batch.setProjectionMatrix(cam.combined);
        shapeRend.setProjectionMatrix(cam.combined);
        rend.setView(cam);
        rend.render();
        //render characters
        characterRenderer.renderCharacters(batch);
        //game intro text
        batch.begin();

        //wave handling

        if (!currentWave.hasStarted() && !player.shouldRemove()) {

            if (Gdx.input.isKeyPressed(Input.Keys.N) && gameStage > 2) {
                currentWave.start();
            }
        } else {
            currentWave.update();
            if (currentWave.isDone()) {
                currentWave = currentWave.nextWave();

            }
        }


        batch.end();
        //render bullets
        for (int i = 0; i < bullets.size(); i++) {
            Bullet s = bullets.get(i);
            if (s.shouldRemove()) {
                bullets.remove(i);
            } else {
                s.draw(shapeRend);
                s.update(Gdx.graphics.getDeltaTime());
            }
        }

        //ui rendering
        userInterface.update();
        userInterface.draw();

        //death handling
        if(player.shouldRemove()){
            backgroundMusic.dispose();
            gameStage = 0;
            game.setScreen(new DeathScreen(game,this));
        }

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
        batch.dispose();
        tile.dispose();


    }

}
