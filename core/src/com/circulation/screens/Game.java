package com.circulation.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.circulation.Main;
import com.circulation.Colors;
import com.circulation.Settings;
import com.circulation.objects.MainCircle;

public class Game implements Screen{
	private boolean gameOver; //статус игры
	private Main game; //экземпл€р Main
	private Stage stage; //сцена игры
	private Texture texture; //текстура
	private Label scoreText, motivationText, highscoreText; //пол€ дл€ текста
	private Skin skin; //скин приложени€
	private MainCircle circle; //экземпл€р большого круга
	private Button repeatButton, backButton, rateButton; //кнопки
	private ColorAction colorAction; //плавное переключение цветов
	
	//конструктор
	public Game(Main game) {
		this.game = game;		
	}

	//инициализаци€
	public void show() {			
		Settings.incGamesPlayed();
		gameOver = false;		
		Colors.resetBlue();
		colorAction = new ColorAction();
	    colorAction.setDuration(0.3f);
	    colorAction.setColor(Colors.blue);		    
	    
		stage = new Stage();		
		stage.setViewport(new FitViewport(Main.WIDTH, Main.HEIGHT));				
		Gdx.input.setInputProcessor(stage);		
		Gdx.input.setCatchBackKey(true);
		
		circle = new MainCircle(1.5f, true, Colors.blue);
		
		skin = Main.getManager().get("ui/uiSkin.json", Skin.class);	
		
		LabelStyle labelStyle = new LabelStyle();	
		labelStyle.font = Main.getManager().get("font/OpenSansBold.ttf", BitmapFont.class);
		
		LabelStyle labelStyleScore = new LabelStyle();	
		labelStyleScore.font = Main.getManager().get("font/OpenSansBoldSmall.ttf", BitmapFont.class);
		
		motivationText = new Label("l", labelStyle);	
		motivationText.setAlignment(Align.center);		
		motivationText.setFontScale(0.7f);
		motivationText.setPosition(Main.WIDTH/2-motivationText.getWidth()/2, Main.HEIGHT);
		motivationText.setVisible(false);
		
		highscoreText = new Label(Settings.myBundle.get("highscore")+Settings.getHighScore(), labelStyle);	
		highscoreText.setFontScale(0.4f);
		highscoreText.setAlignment(Align.center);
		highscoreText.setPosition(Main.WIDTH/2-highscoreText.getWidth()/2, Main.HEIGHT-motivationText.getHeight());
	
		highscoreText.setVisible(false);
		
		scoreText = new Label(Integer.toString(circle.getScore()), labelStyleScore);				
		scoreText.setPosition(Main.WIDTH/2 - scoreText.getWidth()/2, Main.HEIGHT/2 - scoreText.getHeight()/2);
		scoreText.setAlignment(Align.center);
		scoreText.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.2f)));			
					
		repeatButton = new Button(skin, "repeat");
		repeatButton.setPosition(Main.WIDTH/2-repeatButton.getWidth()/2, -repeatButton.getHeight()/2);
		repeatButton.setVisible(false);
		repeatButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {   
	        	game.setScreen(new Game(game));
	        	}
	    });	
		
		backButton = new Button(skin, "back");
		backButton.setPosition(Main.WIDTH/2-backButton.getWidth()/2-repeatButton.getWidth(), -backButton.getHeight()/2);
		backButton.setVisible(false);
		backButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {   
	        	game.setScreen(new Menu(game));
	        	}
	    });		
		
		rateButton = new Button(skin, "rate");
		rateButton.setPosition(Main.WIDTH/2-rateButton.getWidth()/2+repeatButton.getWidth(), -rateButton.getHeight()/2);
		rateButton.setVisible(false);
		rateButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {   
	        	Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.google.android.googlequicksearchbox");
	        	}
	    });	
		
			
		stage.addActor(scoreText);		
		stage.addActor(circle);	
		stage.addActor(motivationText);
		stage.addActor(highscoreText);
		stage.addActor(repeatButton);
		stage.addActor(backButton);
		stage.addActor(rateButton);
	}
	
	//рендер
	public void render(float delta) {			
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		colorChangeMechanic(delta);		
		
	    if(gameOver) updateGameOver();		
		stage.act(delta);
		stage.draw();		
		scoreText.setText(Integer.toString(circle.getScore()));		
		checkGameOver(); 
		checkBackButton();   
	}
	
	//выбор фразы при проигрыше
	public String  motivationPhrase(){			
		if(circle.getScore() == 0)return Settings.myBundle.get("soSad");
		else{
			if(Settings.getHighScore() - circle.getScore() == 1)return Settings.myBundle.get("soClose");
			else{
				if(circle.getScore() >= Settings.getHighScore() && circle.getScore() >= 1)return Settings.myBundle.get("newRecord");
				else{
					if(circle.getScore() >= 6)return Settings.myBundle.get("motivationMore6");
				}
			}
		}
		return Settings.myBundle.get("defaultMotivation");
	}
	
	//обновление экрана при проигырше
	public void updateGameOver(){
		Gdx.gl.glClearColor(Colors.deadColor.r, Colors.deadColor.g, Colors.deadColor.b, 1);
		circle.setBackground(Colors.deadColor);		
    	circle.stop();	      	
    	if(circle.getScore() > Settings.getLowestScore()){    
    		Settings.setNewScore(circle.getScore());    	
    		highscoreText.setText(Settings.myBundle.get("highscore")+Settings.getHighScore());
    	}
    	motivationText.setText(motivationPhrase());
    	motivationText.setVisible(true);
    	highscoreText.setVisible(true);
    	repeatButton.setVisible(true);
    	backButton.setVisible(true);
    	rateButton.setVisible(true);
    	
    	motivationText.addAction(Actions.moveTo(Main.WIDTH/2-motivationText.getWidth()/2, Main.HEIGHT - motivationText.getHeight()*1.45f, 0.3f));
    	highscoreText.addAction(Actions.moveTo(Main.WIDTH/2-highscoreText.getWidth()/2, Main.HEIGHT - motivationText.getHeight()*1.45f-motivationText.getHeight()/1.5f, 0.3f));      	
    	repeatButton.addAction(Actions.moveTo(Main.WIDTH/2-repeatButton.getWidth()/2, (Main.HEIGHT/2-200)/2-repeatButton.getHeight()/2 , 0.3f));
    	backButton.addAction(Actions.moveTo(Main.WIDTH/2-backButton.getWidth()/2-repeatButton.getWidth(), (Main.HEIGHT/2-200)/2-backButton.getHeight()/2 , 0.3f));
    	rateButton.addAction(Actions.moveTo(Main.WIDTH/2-rateButton.getWidth()/2+repeatButton.getWidth(), (Main.HEIGHT/2-200)/2-rateButton.getHeight()/2 , 0.3f));
		
	}	
	
	//изменение цвета
	public void colorChangeMechanic(float delta){
		if(!gameOver){					
			Gdx.gl.glClearColor(Colors.blue.r, Colors.blue.g, Colors.blue.b, 1);	
			if(circle.getScore() >= 5){
			if(circle.getScore() == 5){					
				circle.setBackground(Colors.greenLight);	
				colorAction.setEndColor(Colors.greenLight);							
			}
			if(circle.getScore() == 10){
				colorAction.restart();
				circle.setBackground(Colors.amethyst);	
				colorAction.setEndColor(Colors.amethyst);					
			}
			if(circle.getScore() == 15){
				colorAction.restart();
				circle.setBackground(Colors.orange);	
				colorAction.setEndColor(Colors.orange);					
			}	
			if(circle.getScore() == 20){
				colorAction.restart();
				circle.setBackground(Colors.greenDark);	
				colorAction.setEndColor(Colors.greenDark);					
			}	
			if(circle.getScore() == 25){
				colorAction.restart();
				circle.setBackground(Colors.purple);	
				colorAction.setEndColor(Colors.purple);					
			}	
			if(circle.getScore() == 30){
				colorAction.restart();
				circle.setBackground(Colors.darkBlue);	
				colorAction.setEndColor(Colors.darkBlue);					
			}	
			if(circle.getScore() == 35){
				colorAction.restart();
				circle.setBackground(Colors.asphalt);	
				colorAction.setEndColor(Colors.asphalt);					
			}
			colorAction.act(delta);	
			}
		}		
		
	}
	
	//обработка кнопки назад
	public void checkBackButton(){
		if(Gdx.input.isKeyJustPressed(Keys.BACK) || Gdx.input.isKeyJustPressed(Keys.ESCAPE))game.setScreen(new Menu(game));
	}
	
	//проверка проигрыша
	public void checkGameOver(){
		if(!circle.allGood()){
			gameOver = true;	
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
		texture.dispose();
	}

}
