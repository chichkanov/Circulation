package com.circulation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.circulation.Main;
import com.circulation.Settings;

public class Loading implements Screen{
	
	public static Music backgroundMusic; //фоновая музыка
	public static Sound pressSound;	//звук нажатия
	private Texture tmp; //временная текстура 
	private Image logo; //логотип
	private TextureParameter textureParam; //свойства текстур
	private Stage stage; //основная сцена
	private Main game; //экземпляр Main
	
	//конструктор
	public Loading(Main game){
		this.game = game;
	}

	//инициализация
	public void show() {	
		
		Settings.initSettings();
		Settings.chooseLanguage();
		stage = new Stage();
		stage.setViewport(new FitViewport(Main.WIDTH, Main.HEIGHT));
		
		textureParam = new TextureParameter();
		textureParam.minFilter = TextureFilter.Linear;
		textureParam.magFilter = TextureFilter.Linear;
		
		tmp = new Texture(Gdx.files.internal("img/logo.png"));	
		logo = new Image(tmp);
		logo.setPosition(Main.WIDTH/2-logo.getWidth()/2, Main.HEIGHT/2-logo.getHeight()/2);		
		
		FileHandleResolver resolver = new InternalFileHandleResolver();
		Main.getManager().setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		Main.getManager().setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		
		final String FONT_CHARS = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
		
		FreeTypeFontLoaderParameter fontParams = new FreeTypeFontLoaderParameter();		
		fontParams.fontParameters.characters = FONT_CHARS;
		fontParams.fontParameters.size = 70;
		fontParams.fontParameters.minFilter = TextureFilter.Linear;
		fontParams.fontParameters.magFilter = TextureFilter.Linear;
		fontParams.fontFileName = "font/OpenSansBold.ttf";		
		
		FreeTypeFontLoaderParameter fontParamsScore = new FreeTypeFontLoaderParameter();		
		fontParamsScore.fontParameters.characters = FONT_CHARS;
		fontParamsScore.fontParameters.size = 120;	
		fontParamsScore.fontParameters.minFilter = TextureFilter.Linear;
		fontParamsScore.fontParameters.magFilter = TextureFilter.Linear;
		fontParamsScore.fontFileName = "font/OpenSansBold.ttf";	
		
		FreeTypeFontLoaderParameter fontParamsBlack = new FreeTypeFontLoaderParameter();	
		fontParamsBlack.fontParameters.characters = FONT_CHARS;
		fontParamsBlack.fontParameters.size = 70;
		fontParamsBlack.fontParameters.color = Color.GRAY;
		fontParamsBlack.fontParameters.minFilter = TextureFilter.Linear;
		fontParamsBlack.fontParameters.magFilter = TextureFilter.Linear;
		fontParamsBlack.fontFileName = "font/OpenSansBold.ttf";		
		
		chooseBackgroundMusic();
			
		pressSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pressSound.mp3"));
		
		Main.getManager().load("font/OpenSansBold.ttf", BitmapFont.class, fontParams);
		Main.getManager().load("font/OpenSansBoldSmall.ttf", BitmapFont.class, fontParamsScore);
		Main.getManager().load("font/OpenSansBoldBlack.ttf", BitmapFont.class, fontParamsBlack);
		Main.getManager().load("img/barrier.png", Texture.class, textureParam);	
		Main.getManager().load("ui/uiSkin.json", Skin.class, new SkinLoader.SkinParameter("ui/buttons.pack"));
		Main.getManager().load("ui/buttons.pack", TextureAtlas.class);		
		Main.getManager().load("img/dot.png", Texture.class, textureParam);	
		Main.getManager().load("img/circle.png", Texture.class, textureParam);		
		Main.getManager().load("img/barrierDead.png", Texture.class, textureParam);	
		Main.getManager().load("img/slowMotionIndicator.png", Texture.class, textureParam);		
		Main.getManager().finishLoading();
		
		stage.addActor(logo);		
	}

	//переключение экрана в меню при условии загрузки
	private void update(float delta){				
	    if(Main.getManager().update()){	    	
	    	logo.addAction(Actions.sequence(Actions.delay(0.6f),Actions.fadeOut(0.3f), Actions.run(new Runnable(){				
				public void run() {
					game.setScreen(new Menu(game));					
				}	    		
	    	})));			
	    }
	}
	
	//рендер экрана
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);			
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	   
	    stage.act(delta);
	    update(delta);
		stage.draw();	   		
	}
	
	//рандомизация фоновой музыки
	public void chooseBackgroundMusic(){
		int numb = MathUtils.random(0, 3);
		switch(numb){
		case 0:backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundMusic.ogg")); break;
		case 1:backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundMusic1.ogg")); break;
		case 2:backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundMusic2.ogg")); break;
		case 3:backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundMusic3.ogg")); break;		
		}
		backgroundMusic.setLooping(true);
		backgroundMusic.setVolume(0.3f);	
	}

	//метод, вызываемый при изменении размера экрана
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		
	}

	//метод, вызываемый при паузе
	public void pause() {		

		
	}

	//метод, вызываемый при возобновлении
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	//метод, вызываемый при "перекрытии" приложения
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	//метод, вызываемый при закрытии
	public void dispose() {
		stage.dispose();	
		tmp.dispose();
		
	}

}
