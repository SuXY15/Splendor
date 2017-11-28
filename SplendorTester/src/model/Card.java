package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.Random;

/*
 * Card 类，对应于卡牌
 * 包括卡牌主要颜色、图片、声望值、兑换所需硬币数和种类
 */
public class Card {
	protected int value;
	protected int[] coins = new int[5];
	protected Image img;
	protected Color color;
	public static int CardImgWidth = 128;
	public static int CardImgHeight= 200;
	static String[] imagefiles = { 
			"img/card/Red", 
			"img/card/White", 
			"img/card/Green",
			"img/card/Blue", 
			"img/card/Black"
	};
	public Card(int value, Color color) {
		this.value = value;
		this.color = color;
		int rand = (new Random().nextInt(5)+1);
		try {
			this.img = ImageIO.read(new File(imagefiles[Coin.getPosition(color)]+rand+".BMP"));
			Graphics g = this.img.getGraphics();
			for (int i = 0; i < value; i++) {
				g.setColor(Color.YELLOW); g.fillOval(Card.CardImgWidth - 18, i * 18 + 6, 12, 12);
				g.setColor(color);        g.fillOval(Card.CardImgWidth - 17, i * 18 + 7, 10, 10);
			}
			g.dispose();
		} catch (IOException e) {
			this.img = null;
			e.printStackTrace();
		};
	}
	
	// 添加一个硬币需求
	public void addCoin(Coin coin, int num) {
		this.coins[Coin.getPosition(coin.getColor())] = num;
	}

	// 获取所需硬币数
	public int[] getCoinNumNeeded() {
		return coins;
	}

	// 设置卡牌点数
	public void setValue(int value) {
		this.value = value;
	}

	// 获取卡牌点数
	public int getValue() {
		return value;
	}

	// 设置卡牌图片
	public void setImg(Image img) {
		this.img = img;
	}

	// 获取卡牌图片
	public Image getImg() {
		return img;
	}

	// 设置卡牌颜色
	public void setColor(Color color) {
		this.color = color;
	}

	// 获取卡牌颜色
	public Color getColor() {
		return this.color;
	}
}
