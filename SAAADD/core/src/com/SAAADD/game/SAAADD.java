package com.SAAADD.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SAAADD extends Game {
	@Override
	public void create () {
		this.setScreen(new GameScreen(this));
	}
	
	@Override
	public void render () {
		super.render();
	}
}
