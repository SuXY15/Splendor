package model;

import model.Common;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.util.Random;

/*
 * Card 类，对应于卡牌
 * 包括卡牌主要颜色、图片、声望值、兑换所需硬币数和种类
 */
public class Card extends JLabel implements MouseListener, Serializable{
	private static final long serialVersionUID = 8945059333338875873L;
	Game game;
	protected int rank;
	protected int value;
	protected int height, width;
	protected int[] coins = new int[5];
	protected Color color;
	protected boolean checked;
	transient protected Image img;
	transient protected Image bufferImage;
	public static int CardImgWidth = 128;
	public static int CardImgHeight = 200;

	public Card(Game game, int rank, Color color) {
		Random r = new Random();
		this.game = game;
		this.rank = rank;
		this.value = rank - 1 + r.nextInt(2) + (rank == 3 ? (r.nextInt(2) + 1) : 0);
		this.color = color;
		int i = 0;
		// 设置购买
		int num = rank * 2 + r.nextInt(2);
		int type[] = new int[4];
		for (i = 0; i < 4; i++)
			type[i] = r.nextInt(5);
		while (num-- > 0)
			this.coins[type[r.nextInt(4)]]++;
		// 画好卡片
		try {
			this.img = ImageIO
					.read(new File(Common.cardImg[Common.getPosition(color)] + (new Random().nextInt(5) + 1) + ".BMP"));
			Graphics g = this.img.getGraphics();
			g.setColor(Color.black);
			g.setFont(Common.font_CardValue);
			if (this.value != 0)
				g.drawString(String.valueOf(value), CardImgWidth - 22, 30);
			int pos = 0;
			for (i = 0; i < 5; i++) {
				if (coins[i] > 0) {
					pos++;
					g.setColor(Common.getColor(i));
					g.fillOval(3, CardImgHeight - pos * 30 + 5, 22, 22);
					g.setColor(i != 1 ? Color.white : Color.black);
					g.setFont(Common.font_CoinInCard);
					g.drawString(String.valueOf(coins[i]), 10, CardImgHeight - pos * 30 + 23);
				}
			}
			g.dispose();
		} catch (IOException e) {
			this.img = null;
			e.printStackTrace();
		}
		this.setIcon(new ImageIcon(img));
		this.addMouseListener(this);
	}

	public void loadImg() {
		try {
			this.img = ImageIO
					.read(new File(Common.cardImg[Common.getPosition(color)] + (new Random().nextInt(5) + 1) + ".BMP"));
			Graphics g = this.img.getGraphics();
			g.setColor(Color.black);
			g.setFont(Common.font_CardValue);
			if (this.value != 0)
				g.drawString(String.valueOf(value), CardImgWidth - 22, 30);
			int pos = 0;
			for (int i = 0; i < 5; i++) {
				if (coins[i] > 0) {
					pos++;
					g.setColor(Common.getColor(i));
					g.fillOval(3, CardImgHeight - pos * 30 + 5, 22, 22);
					g.setColor(i != 1 ? Color.white : Color.black);
					g.setFont(Common.font_CoinInCard);
					g.drawString(String.valueOf(coins[i]), 10, CardImgHeight - pos * 30 + 23);
				}
			}
			g.dispose();
		} catch (IOException e) {
			this.img = null;
			e.printStackTrace();
		}
		this.setIcon(new ImageIcon(img));
	}

	public void paint(Graphics g) {
		width = getWidth() > getHeight() * 16 / 25 ? getHeight() * 16 / 25 : getWidth();
		height = getHeight();
		g.drawImage(img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), getWidth() / 2 - width / 2, 0,
				null);
		if (checked) {
			int gap = (getWidth() - width) / 2 + 2;
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, gap, height);
			g.fillRect(getWidth() - gap, 0, gap, height);
		} else {
			int gap = (getWidth() - width) / 2;
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, gap, height);
			g.fillRect(getWidth() - gap, 0, gap, height);
		}

	}

	public void update(Graphics g) {
		if (bufferImage == null)
			bufferImage = this.createImage(img.getWidth(null), img.getHeight(null));
		Graphics gImage = bufferImage.getGraphics(); // 把它的画笔拿过来,给gImage保存着
		paint(gImage); // 将要画的东西画到图像缓存空间去
		g.drawImage(bufferImage, 0, 0, null); // 然后一次性显示出来
	}

	// 设置硬币需求
	public void setCoin(Coin[] coins, int num) {
		for (Coin coin : coins)
			this.coins[Common.getPosition(coin.getColor())] = num;
	}

	// 获取所需硬币数
	public int getCoinNumNeeded(int i) {
		return coins[i];
	}

	// 获取卡牌点数
	public int getValue() {
		return value;
	}

	// 鼠标消息响应
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (!checked) {
			if (game.cardChecker != null) {
				game.cardChecker.checked = !game.cardChecker.checked;
				game.cardChecker.paint(game.cardChecker.getGraphics());
			}
			Coin temp;
			while (game.coinChecker.size() > 0) {
				temp = game.coinChecker.remove();
				temp.checked = 0;
				temp.paint(temp.getGraphics());
			}
		}
		checked = !checked;
		this.paint(this.getGraphics());
		if (checked)
			game.cardChecker = this;
		else
			game.cardChecker = null;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
