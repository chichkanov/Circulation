package com.circulation.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.circulation.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Circulation";				
		config.samples = 4;
		config.resizable = false;		
		config.vSyncEnabled = true;	
		config.addIcon("ui/icon.png", FileType.Internal);
		System.setProperty("user.name", "EnglishWords");
		new LwjglApplication(new Main(), config);
		
		Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width,
	    Gdx.graphics.getDesktopDisplayMode().height, false);
	}
}
