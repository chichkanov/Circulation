package com.circulation.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.circulation.Main;

public class Dot extends Actor{
	private Texture texture; //текстура объекта
	private float x, y; //координаты объекта
	private float radius;	//радиус окружности
	private float speed; //скорость перемещения
    private float degree; //угол в градусах
    private float radian; //угол в радианах
    private int numberOfTurnovers; //количество оборотов
    private Circle bounds; //границы объекта
    private boolean alreadySumScore; //увеличено ли уже кол-во очков
    private float slowMotionCoeff;  //коэффицент замедленного времени
    

    //конструктор класса
	public Dot(float speed){		
		numberOfTurnovers = 0;
		degree = 0;
		radian = 0;
		radius = 164.6f;
		this.speed = speed;	
		texture = Main.getManager().get("img/dot.png", Texture.class);		
		bounds = new Circle();
        bounds.setRadius(texture.getWidth()/2);        
        x=(float) (Math.sin(radian)*radius)+Main.WIDTH/2-texture.getWidth()/2;
		y=(float) (Math.cos(radian)*radius)+Main.HEIGHT/2-texture.getHeight()/2;	
		alreadySumScore = true;
		slowMotionCoeff = 1f;
	}
	
	//отрисовка
	public void draw(Batch batch, float parentAlpha, Color background){
		batch.setColor(background.r,background.g,background.b,this.getColor().a);		
		batch.draw(texture, x, y);			
	}	
	
	//логика
	public void act(float delta){		
		super.act(delta);				
		degree += speed*slowMotionCoeff;
		radian = (float) Math.toRadians(degree);		
		x=(float) (Math.sin(radian)*radius)+Main.WIDTH/2-texture.getWidth()/2;
		y=(float) (Math.cos(radian)*radius)+Main.HEIGHT/2-texture.getHeight()/2;	
		detectTurnover();			
		setBounds();	
	}

	//установка границ
	public void setBounds(){
		bounds.setX(x+texture.getWidth()/2);
		bounds.setY(y+texture.getWidth()/2);	
	}
	
	//проверка прохождения полного оборота
	public void detectTurnover(){	
		if(MathUtils.isEqual(y, 239, 1f)){
			alreadySumScore = false;
		}		
		
		if(y>=568 && speed != 0 && !alreadySumScore){	
			alreadySumScore = true;
			numberOfTurnovers++;
		}
	}	
	
	//установить замедленное время
	public void setSlowMotion(){
		slowMotionCoeff = 0.5f;
	}
	
	//убрать замедленное время
	public void removeSlowMotion(){
		slowMotionCoeff = 1f;
	}
	
	//увеличение скорости
	public void incSpeed(){
		if(speed <= 1.8f) speed+=0.01f;
		else speed += 0.002f;		
	}
	
	//получение угла в радианах
	public float getRadian(){
		return radian;
	}
	
	//получение границ
	public Circle getBounds() {
        return bounds;
    
	}
	
	//остановка объекта
	public void stop(){
		speed = 0;
	}

	//получение числа обротов
	public int getNumberOfTurnovers() {
		return numberOfTurnovers;	
	}

}
