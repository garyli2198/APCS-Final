package com.SAAADD.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.SAAADD.game.GameScreen;

public class Character {
	public boolean isMoving;
	private Sprite body, leg, idle;
	private float angle;
	private Animation walkAnimation;
	private TextureRegion[] walkFrames;
	private float x, y, width = 64, height = 64;
	public final static float speed = 5.0f;
	/**
	 * @param bodySheet
	 * @param legSheet
	 */
	public Character(Texture legSheet, Texture bodySheet) {
		body = new Sprite(bodySheet);
		
		walkFrames = TextureRegion.split(legSheet, 64, 64)[0];
		walkAnimation = new Animation(0.1f, walkFrames);
		GameScreen.stateTime = 0f;
		leg =  new Sprite(walkAnimation.getKeyFrame(GameScreen.stateTime, true));
		isMoving = false;
	}

	
	public Character(Texture legSheet, Texture bodySheet, float x, float y, float angle) {
		body = new Sprite(bodySheet);
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
		batch.begin();
		if(isMoving){
			leg =  new Sprite(walkAnimation.getKeyFrame(GameScreen.stateTime, true));

		}
		else{
			leg =  new Sprite(walkFrames[1]);
		}
		leg.setSize(width, height);
		body.setSize(width, height);
		leg.setRotation(angle);
		body.setRotation(angle);
		leg.setCenter(x, y);
		body.setCenter(x, y);
		leg.draw(batch);
		body.draw(batch);
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
