package com.circulation.screens;


import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.circulation.Main;
import com.circulation.Colors;
import com.circulation.Settings;
import com.circulation.objects.MainCircle;

public class Menu implements Screen {	
	private Main game; //экземпл€р Main
	private Stage stage; //основна€ сцена
	private TextureAtlas atlas; //атлас текстур
	private Skin skin; //скин
	private Label heading, text, settingsLabel; //поле текста
	private Button settingsButton, topButton, achButton; //кнопки
	private ImageTextButton soundButton, musicButton, languageButton, howToButton, removeAdsButton; //кнопки с иконкой
	private MainCircle circle; //основной круг
	private Color background; //фон
	private Table settingsWindow; //всплывающие настройки
	private ImageTextButtonStyle textTextButtonStyle; //стиль кнопок

	//конструктор
	public Menu(Main game){
		this.game = game;
	}	

	//инициализаци€
	public void show() {	
		if(Settings.getMusic())Loading.backgroundMusic.play();		
		background = chooseBackground();		
		
		stage = new Stage();		
		stage.setViewport(new FitViewport(Main.WIDTH, Main.HEIGHT));
		
		Gdx.input.setInputProcessor(stage);		
		Gdx.input.setCatchBackKey(false);
		
		atlas = Main.getManager().get("ui/buttons.pack", TextureAtlas.class);
		skin = Main.getManager().get("ui/uiSkin.json", Skin.class);	
		
		circle = new MainCircle(0, false, background);

		settingsButton = new Button(skin, "settings");
		topButton = new Button(skin, "top");
		achButton = new Button(skin, "ach");		
		
		topButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {   	            
	        	fadeOutAnimation("highscores");       	         
	        }
	    });	
		
		settingsButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {   	        	
	        	if(!settingsWindow.isVisible()){
	        	circle.addAction(Actions.alpha(0.3f, 0.2f)); 
	        	alphaControlElements(0.4f);   	        	
	        	settingsWindow.setVisible(true); 	        	
	        	settingsButton.setTouchable(Touchable.disabled);
	        	topButton.setTouchable(Touchable.disabled);
	        	achButton.setTouchable(Touchable.disabled);
	        	}	           
	        }
	    });		
		
		achButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {   	        	
	        	   
	        }
	    });


		settingsButton.setPosition(Main.WIDTH/2-settingsButton.getWidth()/2-settingsButton.getWidth()*1.1f, -settingsButton.getHeight());
		topButton.setPosition(Main.WIDTH/2-settingsButton.getWidth()/2, -settingsButton.getHeight());
		achButton.setPosition(Main.WIDTH/2-settingsButton.getWidth()/2+settingsButton.getWidth()*1.1f, -settingsButton.getHeight());
			
		LabelStyle labelStyle = new LabelStyle();	
		labelStyle.font = Main.getManager().get("font/OpenSansBold.ttf", BitmapFont.class);
		
		heading = new Label("Circulation", labelStyle);		
		heading.setFontScale(0.8f);			
		heading.setAlignment(Align.center);		
		heading.setPosition(Main.WIDTH/2-heading.getWidth()/2, Main.HEIGHT);
		
		text = new Label(Settings.myBundle.get("tapToPlay"), labelStyle);		
		text.setFontScale(0.5f);			
		text.setAlignment(Align.center);
		text.setWrap(true);			
		text.setPosition(Main.WIDTH/2-text.getWidth()/2, Main.HEIGHT/2-text.getHeight()/2);		
		
		NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("ui/settingsWindowBackground.png")),
			   30, 30, 30, 30);		
		NinePatchDrawable settingsWindowBackground = new NinePatchDrawable(patch);

		
		settingsWindow = new Table();
        settingsWindow.setBackground(settingsWindowBackground);           
        settingsWindow.setVisible(false);    

        LabelStyle settingsLabelStyle = new LabelStyle();	
        settingsLabelStyle.font = Main.getManager().get("font/OpenSansBoldBlack.ttf", BitmapFont.class);  
				
		textTextButtonStyle = new ImageTextButtonStyle();	
		textTextButtonStyle.font = Main.getManager().get("font/OpenSansBoldBlack.ttf", BitmapFont.class);   
		textTextButtonStyle.up = skin.getDrawable("button");
		if(Settings.getLanguage().equals("en"))textTextButtonStyle.font.getData().setScale(0.35f);
		else textTextButtonStyle.font.getData().setScale(0.35f);
		
		settingsLabel = new Label(Settings.myBundle.get("settings"), settingsLabelStyle);		
		settingsLabel.setFontScale(0.5f);	
		soundButton = Settings.getSound()? new ImageTextButton(Settings.myBundle.get("soundOn"), textTextButtonStyle) : new ImageTextButton(Settings.myBundle.get("soundOff"), textTextButtonStyle);	
		musicButton = Settings.getMusic()? new ImageTextButton(Settings.myBundle.get("musicOn"), textTextButtonStyle) : new ImageTextButton(Settings.myBundle.get("musicOff"), textTextButtonStyle);	
		languageButton = Settings.getLanguage().equals("ru")? new ImageTextButton(Settings.myBundle.get("russian"), textTextButtonStyle): new ImageTextButton(Settings.myBundle.get("english"), textTextButtonStyle);
		removeAdsButton =new ImageTextButton(Settings.myBundle.get("removeAds"), textTextButtonStyle);		
		howToButton = new ImageTextButton(Settings.myBundle.get("howToPlay"), textTextButtonStyle);	
		
		soundButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {   
	        	  Settings.setSound();
	        	  if(Settings.getSound())soundButton.setText(Settings.myBundle.get("soundOn"));     
	        	  else soundButton.setText(Settings.myBundle.get("soundOff"));     
	        	  
	        }
	    });	
		musicButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) { 
	        	 Settings.setMusic();	        	 
	        	 if(Settings.getMusic())musicButton.setText(Settings.myBundle.get("musicOn"));     
	        	 else musicButton.setText(Settings.myBundle.get("musicOff"));   
	        	 
	        }
	    });	
		languageButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {   
	        	Settings.changeLanguage();  	        	
	        	updateText();     
	        }
	    });	
		howToButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {   	            
	        	fadeOutAnimation("howtoplay");             
	        }
	    });	
		removeAdsButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {   	            
	        	  Settings.disableAds();  
	        	  settingsWindow.removeActor(removeAdsButton);
	        	  settingsWindow.setSize(300, (settingsWindow.getRows()-1)*78-24);
	        	  settingsWindow.setPosition(Main.WIDTH/2-settingsWindow.getWidth()/2, Main.HEIGHT/2-settingsWindow.getHeight()/2);  
	        	  settingsWindow.padBottom(10);
	        }
	    });				
		
		settingsWindow.add(settingsLabel).padBottom(24).row();		
		settingsWindow.add(howToButton).padBottom(24).row();
		settingsWindow.add(soundButton).padBottom(24).row();	
		settingsWindow.add(musicButton).padBottom(24).row();
		
		if(Settings.getAdsStatus()){
			if(Locale.getDefault().getLanguage().equals("ru"))settingsWindow.add(languageButton).padBottom(24).row();
			//settingsWindow.add(removeAdsButton).row();
		}
		else{
			if(Locale.getDefault().getLanguage().equals("ru"))settingsWindow.add(languageButton).row();
		}
		settingsWindow.setSize(300, settingsWindow.getRows()*78);	
		settingsWindow.setPosition(Main.WIDTH/2-settingsWindow.getWidth()/2, Main.HEIGHT/2-settingsWindow.getHeight()/2);

		stage.addActor(settingsButton);	
		stage.addActor(topButton);	
		stage.addActor(achButton);	
		stage.addActor(heading);
		stage.addActor(text);
		stage.addActor(circle);
		stage.addActor(settingsWindow);
		
		fadeInAnimation();
		
		
		
	}	
	
	//рендер
	public void render(float delta) {		
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);	
				
		if(Settings.getMusic())Loading.backgroundMusic.play();
		else Loading.backgroundMusic.pause();;
		
		stage.act(delta);
		stage.draw();		
		
		if(Gdx.input.justTouched() && settingsWindow.isVisible() && !soundButton.isPressed() && !musicButton.isPressed() && !languageButton.isPressed() && !howToButton.isPressed() && !removeAdsButton.isPressed()){
			settingsWindow.setVisible(false);			
			circle.addAction(Actions.alpha(1f, 0.2f));  
			alphaControlElements(1f);   	
			settingsButton.setTouchable(Touchable.enabled);
        	topButton.setTouchable(Touchable.enabled);
        	achButton.setTouchable(Touchable.enabled);
		}
		else
		if(Gdx.input.justTouched() && !settingsWindow.isVisible() && !topButton.isPressed() && !achButton.isPressed() && !settingsButton.isPressed()){		
			fadeOutAnimation("game");				
		}
	
		
	}

	//изменение текста кнопок
	public void updateText(){	
		text.setText(Settings.myBundle.get("tapToPlay"));
		text.setAlignment(Align.center);
		settingsLabel.setText(Settings.myBundle.get("settings"));
		soundButton.setText(Settings.getSound()? Settings.myBundle.get("soundOn") : Settings.myBundle.get("soundOff"));	
		musicButton.setText(Settings.getMusic()? Settings.myBundle.get("musicOn") : Settings.myBundle.get("musicOff"));
		removeAdsButton.setText(Settings.myBundle.get("removeAds"));		
		howToButton.setText(Settings.myBundle.get("howToPlay"));	
		if(Settings.getLanguage().equals("ru"))languageButton.setText(Settings.myBundle.get("russian"));
    	else languageButton.setText(Settings.myBundle.get("english"));
	}
	
	//выбор фона
	public Color chooseBackground(){
		if(Settings.getHighScore() >= 5 && Settings.getHighScore() < 10)return Colors.greenLight;
		if(Settings.getHighScore() >= 10 && Settings.getHighScore() < 15)return Colors.amethyst;
		if(Settings.getHighScore() >= 15 && Settings.getHighScore() < 20)return Colors.orange;
		if(Settings.getHighScore() >= 20 && Settings.getHighScore() < 25)return Colors.greenDark;
		if(Settings.getHighScore() >= 25 && Settings.getHighScore() < 30)return Colors.purple;
		if(Settings.getHighScore() >= 30 && Settings.getHighScore() < 35)return Colors.darkBlue;
		if(Settings.getHighScore() >= 35)return Colors.asphalt;
	    return Colors.blue;
	}
	
	//анимаци€ по€влени€
	public void fadeInAnimation(){
		settingsButton.addAction(Actions.moveTo(Main.WIDTH/2-settingsButton.getWidth()/2-settingsButton.getWidth()*1.1f, settingsButton.getHeight(), 0.3f));
		topButton.addAction(Actions.moveTo(Main.WIDTH/2-settingsButton.getWidth()/2, settingsButton.getHeight(), 0.3f));
		achButton.addAction(Actions.moveTo(Main.WIDTH/2-settingsButton.getWidth()/2+settingsButton.getWidth()*1.1f, settingsButton.getHeight(), 0.3f));
		heading.addAction(Actions.moveTo(Main.WIDTH/2-heading.getWidth()/2, Main.HEIGHT/1.2f , 0.3f));
		text.addAction(Actions.forever(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(0.7f), Actions.fadeOut(0.7f))));
	}
	
	//анимаи€ исчезновени€
	public void fadeOutAnimation(final String to){
		settingsButton.addAction(Actions.moveTo(Main.WIDTH/2-settingsButton.getWidth()/2-settingsButton.getWidth()*1.1f, -settingsButton.getHeight(), 0.3f));
		topButton.addAction(Actions.moveTo(Main.WIDTH/2-settingsButton.getWidth()/2, -settingsButton.getHeight(), 0.3f));		
		heading.addAction(Actions.moveTo(Main.WIDTH/2-heading.getWidth()/2, Main.HEIGHT, 0.3f));
		text.addAction(Actions.forever(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(0.9f), Actions.fadeOut(0.9f))));
		text.addAction(Actions.sequence(Actions.fadeOut(0.3f), Actions.alpha(0)));		
		settingsWindow.addAction(Actions.sequence(Actions.fadeOut(0.3f), Actions.alpha(0)));		
		if(to == "highscores" || to == "settings" || to == "credits")circle.fadeOut();
		achButton.addAction(Actions.sequence(Actions.moveTo(Main.WIDTH/2-settingsButton.getWidth()/2+settingsButton.getWidth()*1.1f, -settingsButton.getHeight(), 0.3f), Actions.run(new Runnable(){
			public void run() {				
				if(to == "game")game.setScreen(new Game(game));	
				if(to == "highscores")game.setScreen(new Highscores(game, background));	
				if(to == "howtoplay")game.setScreen(new HowToPlay(game, background));	
			}
			
		})));		
	}
	
	//затемнение элементов
	public void alphaControlElements(float alpha){
		settingsButton.addAction(Actions.alpha(alpha, 0.2f));
		topButton.addAction(Actions.alpha(alpha, 0.2f));
		achButton.addAction(Actions.alpha(alpha, 0.2f));
		heading.addAction(Actions.alpha(alpha, 0.2f));
	}
	
	//метод, вызываемый при изменении размера экрана
	public void resize(int width, int height) {		
		 stage.getViewport().update(width, height, true);
	}

	//метод, вызываемый при паузе
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	//метод, вызываемый при возобновлении
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	//метод, вызываемый при "перекрытии" приложени€
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	//метод, вызываемый при закрытии
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		skin.dispose();
	}

}
