package com.saaadd.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.saaadd.game.GameScreen;

public class Character {
    public boolean isMoving;
    private Sprite body, leg;
    private float angle;
    private Animation walkAnimation;
    private TextureRegion[] walkFrames, bodyFrames;
    private float x, y, width = 64, height = 64;
    public final static float speed = 5.0f;
    /**
     * @param bodySheet
     * @param legSheet
     */
    public Character(Texture legSheet, Texture bodySheet) {
        bodyFrames = TextureRegion.split(bodySheet, 64, 64)[0];
        body = new Sprite(bodyFrames[1]);

        walkFrames = TextureRegion.split(legSheet, 64, 64)[0];
        walkAnimation = new Animation(0.1f, walkFrames);
        GameScreen.stateTime = 0f;
        leg =  new Sprite(walkAnimation.getKeyFrame(GameScreen.stateTime, true));
        isMoving = false;
    }


    public Character(Texture legSheet, Texture bodySheet, float x, float y, float angle) {
        bodyFrames = TextureRegion.split(bodySheet, 64, 64)[0];
        body = new Sprite(bodyFrames[0]);
        leg = new Sprite(legSheet);

        walkFrames = TextureRegion.split(legSheet, 64, 64)[0];
        walkAnimation = new Animation(0.1f, walkFrames);
        GameScreen.stateTime = 0f;
        this.x = x;
        this.y = y;
        this.angle = angle;
        leg =  new Sprite(walkAnimation.getKeyFrame(GameScreen.stateTime, true));
        isMoving = false;

    }

    public void draw(SpriteBatch batch) {
        Sprite pistol = new Sprite(new Texture(Gdx.files.internal("weapons/1h_smg.png")));
        batch.begin();
        if(isMoving){
            leg =  new Sprite(walkAnimation.getKeyFrame(GameScreen.stateTime, true));

        }
        else{
            leg =  new Sprite(walkFrames[1]);
        }
        leg.setSize(width, height);
        body.setSize(width, height);
        pistol.setSize(width, height);
        leg.setRotation(angle);
        body.setRotation(angle);

        leg.setCenter(x, y);
        body.setCenter(x, y);

        //gun position calculations
        float r = (float) Math.sqrt( 5 * 5 + 30 * 30);
        float theta = (float) Math.atan2(30, 5);
        pistol.setCenter((float) (x + r * Math.cos(theta + Math.toRadians(body.getRotation()))),
                (float) (y + r * Math.sin(theta + Math.toRadians(body.getRotation()))));
        pistol.setRotation(angle);

        leg.draw(batch);
        body.draw(batch);
        pistol.draw(batch);
        batch.end();

    }

    public void translate(float dx, float dy) {
        x += dx;
        y += dy;
    }
    public void translate(Vector2 v){
        x += v.x;
        y += v.y;
        body.translate(v.x, v.y);
    }
    public void rotate(float dtheta){
        angle += dtheta;
    }
    public void setRotation(float angle){
        this.angle = angle;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public void setLocation(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void setSize(float sizex, float sizey){
        width = sizex;
        height = sizey;
    }



}
