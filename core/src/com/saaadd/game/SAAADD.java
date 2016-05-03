package com.saaadd.game;

import com.badlogic.gdx.Game;


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
