package com.saaadd.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.saaadd.character.CharacterRenderer;
import com.saaadd.character.Enemy;
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
import com.saaadd.item.Bullet;
import com.saaadd.item.Weapon;
import com.saaadd.game.Text;

import java.awt.*;
import java.util.ArrayList;


public class GameScreen extends ApplicationAdapter implements Screen {
    public static float stateTime;
    public static Player player;
    private SAAADD game;
    private SpriteBatch batch;
    private TiledMapRenderer rend;
    private TiledMap tile;
    private ShapeRenderer shapeRend;
    public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public static MapObjects mapObjects;
    public static OrthographicCamera cam;
    private CharacterRenderer characterRenderer;
    private BitmapFont font;
    private Text text;
    public static int introCounter  = 0;

    public GameScreen(final SAAADD game){
        //play background music
        Sound backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("background.mp3"));
        backgroundMusic.loop();

        //shaperenderer initialization
        shapeRend = new ShapeRenderer();

        //camera initialization
        cam = new OrthographicCamera(1200, 1200 * ((float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth()));
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        //tiledMap initialization
        tile = new TmxMapLoader().load("SAAADD TEST.tmx");
        rend = new OrthogonalTiledMapRenderer(tile);
        mapObjects = tile.getLayers().get("Object Layer 1").getObjects();

        //font initialization
        font = new BitmapFont(Gdx.files.internal("vidgamefont.fnt"));
        text = new Text();

        //gameScreen initilization
        this.game = game;
        stateTime = 0f;
        batch = new SpriteBatch();

        //test characters
        Weapon w = new Weapon(1, "pistol", new Texture(Gdx.files.internal("weapons/1h_pistol.png")), Weapon.oneH, 0.3f, 10);
        Weapon w1 = new Weapon(1, "pistol", new Texture(Gdx.files.internal("weapons/1h_pistol.png")), Weapon.oneH, 0.3f, 10);
        player = new Player(new Texture(Gdx.files.internal("legs.png")), new Texture(Gdx.files.internal("officerbody.png")),
                cam.position.x, cam.position.y,0,100, w);
        Enemy enemy = new Enemy(new Texture(Gdx.files.internal("legs.png")), new Texture(Gdx.files.internal("officerbody.png")),
                50, 50, 0, 100, w1);
        //Character Renderer initialization
        characterRenderer = new CharacterRenderer();
        characterRenderer.add(player);
        characterRenderer.add(enemy);
    }



    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        //update statetime
        stateTime += Gdx.graphics.getDeltaTime();
        //update color background
        Gdx.gl.glClearColor((float)(24/255.0), (float)(105/255.0), (float)(4/255.0), 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //update camera
        cam.update();
        //update chape render and batch
        batch.setProjectionMatrix(cam.combined);
        shapeRend.setProjectionMatrix(cam.combined);
        rend.setView(cam);
        rend.render();
        //render characters
        characterRenderer.renderCharacters(batch);
        //game intro text
        batch.begin();
        if(introCounter == 0){
            font.draw(batch,text.getGameIntro(),player.getX(),player.getY()+300,10,5,true);
        }
        else if(introCounter == 1){
            font.draw(batch,text.getGameIntro2(),player.getX(),player.getY()+300,10,5,true);
        }
        else if(introCounter == 2)
        {
            font.draw(batch,text.getGameIntro3(),player.getX(),player.getY()+300,10,5,true);
        }
        batch.end();
        //render bullets
        for(int i =0; i<bullets.size();i++) {
            Bullet s = bullets.get(i);
            if(s.shouldRemove()) {
                bullets.remove(i);
            }
            else {
                s.draw(shapeRend);
                s.update(Gdx.graphics.getDeltaTime());
            }
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
