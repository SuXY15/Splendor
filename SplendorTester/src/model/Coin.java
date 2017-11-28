package model;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Coin {
	static Color[] colors = { 
			new Color(228,   0,   0),  // Red
			new Color(228, 228, 228),  // White
			new Color(  0, 228,   0),  // Green
			new Color(  0,   0, 228),  // Blue
			new Color( 12,  12,  12),  // Black
			new Color(238, 199,  16),  // Gold
	};
	static String[] imagefiles = { 
			"img/coin/Red.jpg", 
			"img/coin/White.jpg", 
			"img/coin/Green.jpg",
			"img/coin/Blue.jpg", 
			"img/coin/Black.jpg",
			"img/coin/Gold.jpg"
	};
	protected Color color;
	protected Image img;

	// 初始化
	public Coin() {}; 
	public Coin(int i) {
		this.color = colors[i];
		try {
			this.img = ImageIO.read(new File(imagefiles[i]));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("???ReadError:"+i);
		}
	}

	// 获取硬币颜色
	public Color getColor() {
		return color;
	}

	// 设置硬币颜色
	public void setColor(int i) {
		this.color = colors[i];
	}

	// 获取硬币图像
	public Image getImg() {
		return img;
	}

	// 设置硬币图像
	public void setImg(int i) {
		try {
			this.img = ImageIO.read(new File(imagefiles[i]));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 静态方法：获取序号所在颜色
	public static Color getColor(int i) {
		return (i > -1 && i < colors.length) ? colors[i] : null;
	}

	// 静态方法：获取颜色对应序号
	public static int getPosition(Color color) {
		for (int i = 0; i < colors.length; i++)
			if (colors[i].equals(color))
				return i;
		return -1;
	}
}