package com.circulation;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

public class Settings {
	
	private static Preferences prefs=Gdx.app.getPreferences("CirculationSettings"); //���� ��������
	private static Integer highscores[]; //������ ��������
	private static boolean scoreSet; // ���������� �� ������
	public static I18NBundle myBundle; //�����������
    public static Locale localeRU = new Locale("ru", "RU"); //������� �����������
    public static Locale localeEN = new Locale("en", "EN"); //���������� �����������
	public static FileHandle baseFileHandle = Gdx.files.internal("i18n/MyBundle"); //���������� ������

	//������������� ��� �������
	public static void initSettings(){
		scoreSet = false;	
		
		if(!prefs.contains("adsEnabled")){
			prefs.putBoolean("adsEnabled", true);	
			prefs.flush();
		}
		
		if(!prefs.contains("gamesPlayed")){
			prefs.putInteger("gamesPlayed", 0);	
			prefs.flush();
		}
		if(!prefs.contains("soundOn")){
			prefs.putBoolean("soundOn", true);	
			prefs.flush();
		}
		if(!prefs.contains("musicOn")){
			prefs.putBoolean("musicOn", true);	
			prefs.flush();
		}
		highscores = new Integer[6];
		initHighscores();	
	}
	
	//����� �����
	public static void chooseLanguage(){
		if(!prefs.contains("lang")){					
			if(localeRU.getLanguage().equals(Locale.getDefault().getLanguage())){					
				prefs.putString("lang", "ru");		
				myBundle = I18NBundle.createBundle(baseFileHandle, localeRU);
			}
			else {
				prefs.putString("lang", "en");	
				myBundle = I18NBundle.createBundle(baseFileHandle, localeEN);
			}
			
			prefs.flush();	
		}
		else{
			if(getLanguage().equals("ru"))myBundle = I18NBundle.createBundle(baseFileHandle, localeRU);
			else myBundle = I18NBundle.createBundle(baseFileHandle, localeEN);
		}
	}
	
	//������ �������
	public static boolean getAdsStatus(){
		return prefs.getBoolean("adsEnabled");
	}
	
	//���������� �������
	public static void disableAds(){
		prefs.putBoolean("adsEnabled", false);
		prefs.flush();
	}
	
	//����� �����
	public static void changeLanguage(){
		if(getLanguage().equals("ru")){
			prefs.putString("lang", "en");
			prefs.flush();		
			myBundle = I18NBundle.createBundle(baseFileHandle, localeEN);
			}
		else{
			prefs.putString("lang", "ru");
			myBundle = I18NBundle.createBundle(baseFileHandle, localeRU);
			prefs.flush();	
			}		
	}
	
	//������������� �����
	public static void setSound(){				
		prefs.putBoolean("soundOn", !prefs.getBoolean("soundOn"));		
		prefs.flush();			
	}
	
	//��������� ������� ������
	public static boolean getSound(){
		return prefs.getBoolean("soundOn");	    
	}
	
	//������������� ������
	public static void setMusic(){			
		prefs.putBoolean("musicOn", !prefs.getBoolean("musicOn"));		
		prefs.flush();			
	}
	
	//��������� ������� ������
	public static boolean getMusic(){
		return prefs.getBoolean("musicOn");	    
	}	
	
	//������� ���
	public static void incGamesPlayed(){
		prefs.putInteger("gamesPlayed", prefs.getInteger("gamesPlayed")+1);
		prefs.flush();		
	}
	
	//��������� �������� ���
	public static Integer getGamesPlayed(){
		return prefs.getInteger("gamesPlayed");
	}
	
	//��������� �����
	public static void setLanguage(){
		if(prefs.getString("lang").equals("ru"))prefs.putString("lang", "en");
		else prefs.putString("lang", "ru");
		prefs.flush();		
	}
	
	//��������� �������� �����
	public static String getLanguage(){
		return prefs.getString("lang");
	}
	
	//������������� ��������
	public static void initHighscores(){	
		if(!prefs.contains("score1")){
			for(int i = 0; i < 6; i++)prefs.putInteger("score"+i, 0);
			prefs.flush();
		}
		for(int i = 0; i < 6; i++){
			highscores[i] = prefs.getInteger("score"+i);
		}		
	}
		
	//��������� ������ �������
	public static void setNewScore(int score){		
		if(!scoreSet){
			if(!isRepeat(highscores, score)){
				highscores[5]=score;
				Arrays.sort(highscores, Collections.reverseOrder());			
				for(int i=0;i<5;i++){
					prefs.putInteger("score"+i, highscores[i]);				
				}				
				prefs.flush();	
				scoreSet = true;
				}
			}
		scoreSet = false;
			
	}	
	
	//��� �� ��� ����� �� ������
	public static boolean isRepeat(Integer array[], int a){
		for(int i=0;i<array.length;i++){
			if(array[i] == a)return true;
		}
		return false;
	}
	
	//�������� ��� �������
	public static Integer[] getAllScores(){
		return highscores;
	}
	
	//�������� ������ ������
	public static int getLowestScore(){
		return prefs.getInteger("score4");
	}
	
	//�������� ������ ������
	public static int getHighScore(){
		return prefs.getInteger("score0");
	}
	
}
