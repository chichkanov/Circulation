package com.circulation.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.circulation.Main;

public class Barrier extends Actor{
	private Texture texture; //картинка для создания спрайта
	private int x,y; //координаты объекта
    private Rectangle bounds; //границы объекта
    private boolean active; //показывать ли барьер
    private float radius; //радиус окружности
    private float randAngle; //случайный угол для показа барьера	 
    private Vector2 vectorCenter; //координаты центра барьера для проверки столкновений    
    private Texture slowMotionIndicator; //текстура змедленного времени
    private int xs, ys; //координты текстуры замедленного времени
    
    //конструктор класса
	public Barrier(boolean showBarrier){		
		
		randAngle = MathUtils.random((float)(Math.PI/2), (float)(Math.PI));
		radius = 166.2f;
		active = showBarrier;
		
		texture = Main.getManager().get("img/barrier.png", Texture.class);	
		slowMotionIndicator = Main.getManager().get("img/slowMotionIndicator.png", Texture.class);
		
		x=(int) (MathUtils.sin(randAngle)*radius)+Main.WIDTH/2-texture.getWidth()/2;
		y=(int) (MathUtils.cos(randAngle)*radius)+Main.HEIGHT/2-texture.getHeight()/2-1;
		xs=(int) (MathUtils.sin(randAngle-0.5f)*radius)+Main.WIDTH/2-texture.getWidth()/2;
		ys=(int) (MathUtils.cos(randAngle-0.5f)*radius)+Main.HEIGHT/2-texture.getHeight()/2-1;
		vectorCenter = new Vector2();
		vectorCenter.set(texture.getWidth()/2+x, texture.getHeight()/2+y);
	
		bounds = new Rectangle(vectorCenter.x-texture.getWidth()/2, vectorCenter.y-texture.getWidth()/2, texture.getWidth(), texture.getWidth());
		this.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.17f, Interpolation.pow2)));
		   
		    
	}
	
	
	//отрисовка объекта
	public void draw(Batch batch, float parentAlpha, Color background, boolean drawSlowMotion){			
		if(active){				
			batch.setColor(background.r,background.g,background.b,this.getColor().a);
			batch.draw(texture, x, y, texture.getWidth()/2, texture.getHeight()/2, texture.getWidth(), texture.getHeight(), 1f, 1f, -(float) Math.toDegrees(randAngle), 0, 0, texture.getWidth(), texture.getHeight(), false, false);
			if(drawSlowMotion)batch.draw(slowMotionIndicator, xs, ys, slowMotionIndicator.getWidth()/2, slowMotionIndicator.getHeight()/2, slowMotionIndicator.getWidth(), slowMotionIndicator.getHeight(), 1f, 1f, -(float) Math.toDegrees(randAngle-0.5f), 0, 0, slowMotionIndicator.getWidth(), slowMotionIndicator.getHeight(), false, false);
		}		
    }	
	
	//логика
	public void act(float delta){
		super.act(delta);		
		xs=(int) (MathUtils.sin(randAngle-0.5f)*radius)+Main.WIDTH/2-texture.getWidth()/2;
		ys=(int) (MathUtils.cos(randAngle-0.5f)*radius)+Main.HEIGHT/2-texture.getHeight()/2-1;
		setBounds();
	}
	
	//установка границ
	public void setBounds(){
		vectorCenter.set(texture.getWidth()/2+x, texture.getHeight()/2+y);
		bounds.setPosition(vectorCenter.x-texture.getWidth()/2, vectorCenter.y-texture.getWidth()/2);
	}
		
	//получение границ
	public Rectangle getBounds() {
        return bounds;
    }
	
	
	//генерация следующего барьера
	public void setNext(float angle){		
		this.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.17f, Interpolation.pow2)));		
	    randAngle = MathUtils.random((float)(angle+Math.PI/4), (float)(angle+Math.PI/2));
		x=(int) (MathUtils.sin(randAngle)*radius)+Main.WIDTH/2-texture.getWidth()/2;
		y=(int) (MathUtils.cos(randAngle)*radius)+Main.HEIGHT/2-texture.getHeight()/2;			
	}

}
