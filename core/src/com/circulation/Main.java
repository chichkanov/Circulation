package com.circulation;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.circulation.screens.Loading;


public class Main extends Game {	
	

	public static final int WIDTH = 480, HEIGHT = 854; //ширины и высота игры	
	private static AssetManager manager; //менеджер ассетов
	private Main game; //класс игры
	
	//конструктор класса
	public Main(){
		game = this;		
	}
	
	//метод, вызываемый при инициализации
	public void create () {		
		manager = new AssetManager();
		setScreen(new Loading(game));
	}

	//метод, вызываемый при закрытии
	public void dispose() {	
		super.dispose();
		manager.dispose();
	}
	
	//рендер
	public void render () {	
		super.render();
	}
	
	//метод, вызываемый при изменении размера экрана
	public void resize(int width, int height) {
		super.resize(width, height);
	}
        
	//метод, вызываемый при паузе
	public void pause() {
		super.pause();
	}

	//метод, вызываемый при возобновлении
    public void resume() {
    	super.resume();
	}
    
    //геттер для ассетов
    public static AssetManager getManager(){
    	return manager;
    }
}
