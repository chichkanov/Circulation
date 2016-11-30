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
	private Texture texture; //�������� �������
	private Barrier barrier; //������
	private Dot dot; //�����	
	private int score; //���� ������
	private boolean prevCollision; //���������� ������������
	private Color background; //��� ����
	private Color wrongColor; //���� ��������� �����������
	private boolean putWrongColor; //�������� �� �������� �����������
	private boolean putSlowMotion; //����������� �� ����������� ����
	private boolean isSlow; //����������� �� ����������� �����
	
	//����������� ������
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
	
	//���������
	public void draw(Batch batch, float parentAlpha){			
		batch.setColor(this.getColor());		
		batch.draw(texture, Main.WIDTH/2 - texture.getWidth()/2, Main.HEIGHT/2 - texture.getHeight()/2);
		if(putWrongColor)barrier.draw(batch, parentAlpha, wrongColor,isSlow);	
		else barrier.draw(batch, parentAlpha, background, isSlow);		
		dot.draw(batch, parentAlpha, background);		
	}	
	
	//������
	public void act(float delta){			
		super.act(delta);				
		score = dot.getNumberOfTurnovers();		
		dot.act(delta);
		barrier.act(delta);	
		
		 if(MathUtils.isEqual(outDistanceToBarrier(), 90, 10) && putSlowMotion){
		    	dot.setSlowMotion();
		    }
	}	
	
	//����������
	public void fadeOut(){
		addAction(Actions.fadeOut(0.3f));
	}
	
	//���������
	public void fadeIn(){
		addAction(Actions.fadeOut(0.3f));
	}	

	//��������� ����	
	public void setBackground(Color color){
		background = color;
	}
	
	//��������� �����
	public int getScore(){
		return score;
	}
	
	//�������� ������������
	public boolean checkCollision(){
		return Intersector.overlaps(dot.getBounds(), barrier.getBounds());
		
	}	
	
	//��������� �� �������
	public float outDistanceToBarrier(){
		float a = (float) Math.sqrt((dot.getBounds().x-barrier.getBounds().x)*(dot.getBounds().x-barrier.getBounds().x) + (dot.getBounds().y-barrier.getBounds().y)*(dot.getBounds().y-barrier.getBounds().y));
		return a;
	}
	
	//��������� �������
	public void stop(){		
		dot.stop();
	}

	//���������� �������� ������ 
	public void setWrongBattier(){
		if(putWrongColor == false && score >= 5){
			if(MathUtils.random(1, 7) == 1){
				putWrongColor = true;
			}
		}
		else putWrongColor = false;
	}
	
	//����������� �����
	public void setSlowMotion(){			
	    if(MathUtils.random(1, 7) == 1 && score>=8){			
			isSlow = true;
			putSlowMotion = true;
		}	
	}	
	
	//������� �� ������������ ����� �� ����� (����)
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
