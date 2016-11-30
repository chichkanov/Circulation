package com.circulation.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.circulation.Main;
import com.circulation.Settings;
import com.circulation.screens.Loading;

public class MainCircle extends Actor{
	private Texture texture; //текстура объекта
	private Barrier barrier; //барьер
	private Dot dot; //точка	
	private int score; //счет игрока
	private boolean prevCollision; //предыдущее столкновение
	private Color background; //фон игры
	private Color wrongColor; //цвет неверного препятствия
	private boolean putWrongColor; //помещено ли невернео препятствие
	private boolean putSlowMotion; //установлено ли замедленное врмя
	private boolean isSlow; //установлено ли замедленное время
	
	//конструктор класса
	public MainCircle(float speed, boolean showBarrier, Color background){				
		texture = Main.getManager().get("img/circle.png", Texture.class);
		this.background =  new Color(background);
		wrongColor =  new Color(231/255f, 76/255f, 60/255f, 1);
		score = 0;		
		dot = new Dot(speed);
		barrier = new Barrier(showBarrier);		
		putWrongColor = false;	
		putSlowMotion = false;
		isSlow = false;
	}
	
	//отрисовка
	public void draw(Batch batch, float parentAlpha){			
		batch.setColor(this.getColor());		
		batch.draw(texture, Main.WIDTH/2 - texture.getWidth()/2, Main.HEIGHT/2 - texture.getHeight()/2);
		if(putWrongColor)barrier.draw(batch, parentAlpha, wrongColor,isSlow);	
		else barrier.draw(batch, parentAlpha, background, isSlow);		
		dot.draw(batch, parentAlpha, background);		
	}	
	
	//логика
	public void act(float delta){			
		super.act(delta);				
		score = dot.getNumberOfTurnovers();		
		dot.act(delta);
		barrier.act(delta);	
		
		 if(MathUtils.isEqual(outDistanceToBarrier(), 90, 10) && putSlowMotion){
		    	dot.setSlowMotion();
		    }
	}	
	
	//затемнение
	public void fadeOut(){
		addAction(Actions.fadeOut(0.3f));
	}
	
	//появление
	public void fadeIn(){
		addAction(Actions.fadeOut(0.3f));
	}	

	//установка фона	
	public void setBackground(Color color){
		background = color;
	}
	
	//получение счета
	public int getScore(){
		return score;
	}
	
	//проверка столкновений
	public boolean checkCollision(){
		return Intersector.overlaps(dot.getBounds(), barrier.getBounds());
		
	}	
	
	//дистанция до барьера
	public float outDistanceToBarrier(){
		float a = (float) Math.sqrt((dot.getBounds().x-barrier.getBounds().x)*(dot.getBounds().x-barrier.getBounds().x) + (dot.getBounds().y-barrier.getBounds().y)*(dot.getBounds().y-barrier.getBounds().y));
		return a;
	}
	
	//остановка объекта
	public void stop(){		
		dot.stop();
	}

	//установить неверный барьер 
	public void setWrongBattier(){
		if(putWrongColor == false && score >= 5){
			if(MathUtils.random(1, 7) == 1){
				putWrongColor = true;
			}
		}
		else putWrongColor = false;
	}
	
	//замедленное время
	public void setSlowMotion(){			
	    if(MathUtils.random(1, 7) == 1 && score>=8){			
			isSlow = true;
			putSlowMotion = true;
		}	
	}	
	
	//вовремя ли пользователь нажал на экран (мышь)
	public boolean allGood(){			
		if(Gdx.input.justTouched()){			
			if(checkCollision() && !putWrongColor){
				prevCollision = false;	
				putSlowMotion = false;
				dot.removeSlowMotion();
				barrier.setNext(dot.getRadian());
				dot.incSpeed();
				if(Settings.getSound())Loading.pressSound.play(1);
				setWrongBattier();					
				isSlow = false;				
				setSlowMotion();
				
				return true;
			}
			else{				
				return false;
			}
		}	

		if(!checkCollision() && prevCollision && putWrongColor){		
			prevCollision = checkCollision();
			putWrongColor = false;				
			barrier.setNext(dot.getRadian());
			dot.removeSlowMotion();
			putSlowMotion = false;
			isSlow = false;
			return true;
		}
		
		if(!checkCollision() && prevCollision){		
			prevCollision = checkCollision();				
			return false;
		}
		else{
			prevCollision = checkCollision();	
			return true;
		}
		
	}
}
