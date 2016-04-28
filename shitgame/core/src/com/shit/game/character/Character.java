package com.shit.game.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.shit.game.GameScreen;

public class Character{
	private Texture body, leg;
	private Animation walkAnimation;
	private TextureRegion currentFrame;
	private TextureRegion[] walkFrames;
	/**
	 * @param bodySheet
	 * @param legSheet
	 */
	public Character(Texture legSheet, Texture bodySheet){
		body = bodySheet;
		leg = legSheet;
		walkFrames = TextureRegion.split(leg, 64, 64)[0];
		walkAnimation = new Animation(0.1f, walkFrames);
		GameScreen.stateTime = 0f;
	}
	
	public void draw(SpriteBatch batch){
		batch.begin();
		currentFrame = walkAnimation.getKeyFrame(GameScreen.stateTime, true);
		batch.draw(currentFrame, 0, 0);
		batch.draw(body, 0, 0);
		batch.end();
	}
}
