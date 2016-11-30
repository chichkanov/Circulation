package com.circulation;

import com.badlogic.gdx.graphics.Color;

public class Colors {
	public static Color blue = new Color(0/255f, 174/255f, 255/255f, 1); //0-5
	public static Color greenDark = new Color(22/255f, 160/255f, 133/255f, 1); //5-10
	public static Color amethyst = new Color(155/255f, 89/255f, 182/255f, 1); //10-15
	public static Color orange = new Color(230/255f, 126/255f, 34/255f, 1); //15-20
	public static Color greenLight = new Color(39/255f, 174/255f, 96/255f, 1); //20-25
	public static Color deadColor = new Color(68/255f, 68/255f, 68/255f, 1); //grey when dead
	public static Color purple = new Color(120/255f, 94/255f, 221/255f, 1);	 //25-30	
	public static Color darkBlue = new Color(44/255f, 130/255f, 201/255f, 1);	//30-35
	public static Color asphalt = new Color(52/255f, 73/255f, 94/255f, 1);	//35-...
	
	//сброс голубого цвета
	public static void resetBlue() {
	     blue = new Color(0/255f, 174/255f, 255/255f, 1);		
	}
}
