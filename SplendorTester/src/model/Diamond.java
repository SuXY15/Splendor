package model;

import java.awt.*;
/*
 * Diamond 类，对应于钻石
 * 包括钻石颜色和个数
 */
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
class Diamond{
	transient Color color;
	transient Image img;
	public Diamond(Color color) {
		this.color = color;
		try {
			this.img = ImageIO.read(new File(Common.diamondImg[Common.getPosition(color)]));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}