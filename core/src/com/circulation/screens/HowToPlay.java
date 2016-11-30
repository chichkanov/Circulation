package com.circulation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.circulation.Main;
import com.circulation.Settings;

public class HowToPlay implements Screen {
	
	private Main game; //экземпл€р Main
	private Stage stage; //основна€ сцена
	private Color background; //цвет фона 
	private Label heading; //заголовок
	private Skin skin; //скин
	private Button backButton; //кнопка назад
	private Label tutorialText; //текст об игре

	//конструктор
	public HowToPlay(Main game, Color background){
		this.game = game;
		this.background = background;
	}
	
	//инициализаци€
	public void show() {
		stage = new Stage();
		stage.setViewport(new FitViewport(Main.WIDTH, Main.HEIGHT));
		Gdx.input.setInputProcessor(stage);		
		Gdx.input.setCatchBackKey(true);	
		
		LabelStyle labelStyle = new LabelStyle();	
		labelStyle.font = Main.getManager().get("font/OpenSansBold.ttf", BitmapFont.class);			
		
		heading = new Label(Settings.myBundle.get("howToPlayScreen"), labelStyle);		
		heading.setFontScale(0.8f);
		heading.setStyle(labelStyle);		
		heading.setAlignment(Align.center);	
		heading.setPosition(Main.WIDTH/2-heading.getWidth()/2, -Main.HEIGHT+Main.HEIGHT/1.2f);
		
        skin = Main.getManager().get("ui/uiSkin.json", Skin.class);	
		
        LabelStyle labelStyleScore = new LabelStyle();	
		labelStyleScore.font = Main.getManager().get("font/OpenSansBoldSmall.ttf", BitmapFont.class);		
		tutorialText = new Label(Settings.myBundle.get("tutorial"), labelStyle);	
		tutorialText.setWidth(400);
		tutorialText.setWrap(true);		
		tutorialText.setFontScale(0.4f);
		tutorialText.setPosition(Main.WIDTH/2-tutorialText.getWidth()/2, -Main.HEIGHT+Main.HEIGHT-tutorialText.getHeight()-heading.getHeight()*1.4f);		
		        
		backButton = new Button(skin, "back");
		backButton.setPosition(Main.WIDTH/2-backButton.getWidth()/2, -Main.HEIGHT+backButton.getHeight()/12);			
		backButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {   
	        	fadeOutAnimation();
	        	}
	    });	
		
		stage.addActor(heading);
		stage.addActor(tutorialText);		
		stage.addActor(backButton);
		fadeInAnimation();		
	}
	
	//анимаци€ по€влени€
	public void fadeInAnimation(){			
		heading.addAction(Actions.moveTo(Main.WIDTH/2-heading.getWidth()/2, Main.HEIGHT/1.2f , 0.4f));		
		backButton.addAction(Actions.moveTo(Main.WIDTH/2-backButton.getWidth()/2, Main.HEIGHT/12f , 0.4f));
		tutorialText.addAction(Actions.moveTo(Main.WIDTH/2-tutorialText.getWidth()/2, Main.HEIGHT-tutorialText.getHeight()-heading.getHeight()*1.4f, 0.4f));
	}
	
	//анимаци€ исчзновени€
	public void fadeOutAnimation(){				
		heading.addAction(Actions.moveTo(Main.WIDTH/2-heading.getWidth()/2, -Main.HEIGHT+Main.HEIGHT/1.2f, 0.4f));	
		tutorialText.addAction(Actions.moveTo(Main.WIDTH/2-tutorialText.getWidth()/2, -Main.HEIGHT+Main.HEIGHT-tutorialText.getHeight()-heading.getHeight()*1.4f, 0.4f));	
		backButton.addAction(Actions.sequence(Actions.moveTo(Main.WIDTH/2-backButton.getWidth()/2, -Main.HEIGHT+backButton.getHeight()/12, 0.4f), Actions.run(new Runnable(){
			@Override
			public void run() {
				game.setScreen(new Menu(game));				
			}			
		})));
	}
	
	//рендер
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);	
		checkBackButton();		
		stage.act(delta);
		stage.draw();
		
	}
	
	//обработчик кнопки назад
	public void checkBackButton(){
		if(Gdx.input.isKeyJustPressed(Keys.BACK) || Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			fadeOutAnimation();
		}
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
		
	}

}
