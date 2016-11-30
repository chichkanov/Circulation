package com.circulation.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.circulation.Main;

public class Dot extends Actor{
	private Texture texture; //�������� �������
	private float x, y; //���������� �������
	private float radius;	//������ ����������
	private float speed; //�������� �����������
    private float degree; //���� � ��������
    private float radian; //���� � ��������
    private int numberOfTurnovers; //���������� ��������
    private Circle bounds; //������� �������
    private boolean alreadySumScore; //��������� �� ��� ���-�� �����
    private float slowMotionCoeff;  //���������� ������������ �������
    

    //����������� ������
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
	
	//���������
	public void draw(Batch batch, float parentAlpha, Color background){
		batch.setColor(background.r,background.g,background.b,this.getColor().a);		
		batch.draw(texture, x, y);			
	}	
	
	//������
	public void act(float delta){		
		super.act(delta);				
		degree += speed*slowMotionCoeff;
		radian = (float) Math.toRadians(degree);		
		x=(float) (Math.sin(radian)*radius)+Main.WIDTH/2-texture.getWidth()/2;
		y=(float) (Math.cos(radian)*radius)+Main.HEIGHT/2-texture.getHeight()/2;	
		detectTurnover();			
		setBounds();	
	}

	//��������� ������
	public void setBounds(){
		bounds.setX(x+texture.getWidth()/2);
		bounds.setY(y+texture.getWidth()/2);	
	}
	
	//�������� ����������� ������� �������
	public void detectTurnover(){	
		if(MathUtils.isEqual(y, 239, 1f)){
			alreadySumScore = false;
		}		
		
		if(y>=568 && speed != 0 && !alreadySumScore){	
			alreadySumScore = true;
			numberOfTurnovers++;
		}
	}	
	
	//���������� ����������� �����
	public void setSlowMotion(){
		slowMotionCoeff = 0.5f;
	}
	
	//������ ����������� �����
	public void removeSlowMotion(){
		slowMotionCoeff = 1f;
	}
	
	//���������� ��������
	public void incSpeed(){
		if(speed <= 1.8f) speed+=0.01f;
		else speed += 0.002f;		
	}
	
	//��������� ���� � ��������
	public float getRadian(){
		return radian;
	}
	
	//��������� ������
	public Circle getBounds() {
        return bounds;
    
	}
	
	//��������� �������
	public void stop(){
		speed = 0;
	}

	//��������� ����� �������
	public int getNumberOfTurnovers() {
		return numberOfTurnovers;	
	}

}
