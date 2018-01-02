package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Common {
	public static String[] cardImg = { 
			"img/card/Red",
			"img/card/White",
			"img/card/Green",
			"img/card/Blue",
			"img/card/Black"
	};
	public static String[] coinImg = {
			"img/coin/red_coin.bmp",
			"img/coin/white_coin.bmp",
			"img/coin/green_coin.bmp",
			"img/coin/blue_coin.bmp",
			"img/coin/black_coin.bmp",
			"img/coin/gold_coin.bmp"
	};
	public static String[] diamondImg = {
			"img/diamond/red_diamond.bmp",
			"img/diamond/white_diamond.bmp",
			"img/diamond/green_diamond.bmp",
			"img/diamond/blue_diamond.bmp",
			"img/diamond/black_diamond.bmp",
			"img/diamond/gold_diamond.bmp"
	};
	public static int[] coins = new int[5];
	public static Color[] colors = {
			new Color(228,   0,   0), // Red
			new Color(200, 200, 200), // White
			new Color(  0, 228,   0), // Green
			new Color(  0,   0, 228), // Blue
			new Color( 12,  12,  12), // Black
			new Color(238, 199,  16), // Gold
	};
	public static String[] colorName = { "红色","白色","绿色","蓝色","黑色","金色" };
	public static Diamond[] diamonds = {
			new Diamond(colors[0]), // Red
			new Diamond(colors[1]), // White
			new Diamond(colors[2]), // Green
			new Diamond(colors[3]), // Blue
			new Diamond(colors[4]), // Black
			new Diamond(colors[5])  // Gold
	};
	
	public static Font font_CoinInCard = new Font("TimesNewRoman",Font.BOLD,16); 
	public static Font font_CardValue = new Font("Airal",Font.BOLD,25);
	public static Font font_DiamondValue = new Font("Airal",Font.BOLD,30);

	// 静态方法：获取序号所在颜色
	public static Color getColor(int i) {
		return (i > -1) ? colors[i%colors.length] : null;
	}
	
	// 静态方法：获取序号所在颜色名称
	public static String getColorName(int i) {
		return (i > -1) ? colorName[i%colorName.length] : null;
	}

	// 静态方法：获取颜色对应序号
	public static int getPosition(Color color) {
		for (int i = 0; i < colors.length; i++)
			if (colors[i].equals(color))
				return i;
		return -1;
	}

}
