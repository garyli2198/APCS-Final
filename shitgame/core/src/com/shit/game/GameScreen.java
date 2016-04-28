package com.shit.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.shit.game.character.Character;

public class GameScreen implements Screen {
	public static float stateTime;
	private Character c;
	private ShitGame game;
	private SpriteBatch batch;
	public GameScreen(final ShitGame game){
		this.game = game;
		stateTime = 0f;
		batch = new SpriteBatch();
		c = new Character(new Texture(Gdx.files.internal("legs.png")), new Texture(Gdx.files.internal("officer_torso_DW.png")));

	}
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);        
        stateTime += Gdx.graphics.getDeltaTime();
        c.draw(batch);

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
