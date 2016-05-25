package com.saaadd.game;

import com.badlogic.gdx.Game;

/**
 * game class
 * @author Stanley Huang
 * @version 5/24/16
 *
 * @author Period - 6
 * @author Assignment - APCS Final
 *
 * @author Sources - Gary Li, Wesley Pang
 */
public class SAAADD extends Game {
	/**
	 * creates new main menu screen
	 */
	@Override
	public void create () {
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
