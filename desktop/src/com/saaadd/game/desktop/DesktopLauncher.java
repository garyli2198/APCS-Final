package com.saaadd.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.saaadd.game.SAAADD;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "S.A.A.A.D.D.";
        config.height = 1280;
        config.width = 800;
        config.fullscreen = true;
		new LwjglApplication(new SAAADD(), config);
	}
}
