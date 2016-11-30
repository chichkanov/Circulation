package com.circulation;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.circulation.screens.Loading;


public class Main extends Game {	
	

	public static final int WIDTH = 480, HEIGHT = 854; //������ � ������ ����	
	private static AssetManager manager; //�������� �������
	private Main game; //����� ����
	
	//����������� ������
	public Main(){
		game = this;		
	}
	
	//�����, ���������� ��� �������������
	public void create () {		
		manager = new AssetManager();
		setScreen(new Loading(game));
	}

	//�����, ���������� ��� ��������
	public void dispose() {	
		super.dispose();
		manager.dispose();
	}
	
	//������
	public void render () {	
		super.render();
	}
	
	//�����, ���������� ��� ��������� ������� ������
	public void resize(int width, int height) {
		super.resize(width, height);
	}
        
	//�����, ���������� ��� �����
	public void pause() {
		super.pause();
	}

	//�����, ���������� ��� �������������
    public void resume() {
    	super.resume();
	}
    
    //������ ��� �������
    public static AssetManager getManager(){
    	return manager;
    }
}
