package app;

import model.Coin;
import model.Player;
import model.Card;

import java.awt.*;
import java.awt.image.ImageObserver;

import javax.swing.*;
import java.util.Random;

public class GameViewTester extends JFrame {
	private static final long serialVersionUID = 1L;
	Coin[] coins = new Coin[6];
	Card[] cards = new Card[5];
	Player[] players = new Player[2];

	Canvas pool = new Canvas() {
		private static final long serialVersionUID = 1L;
		private Image bufferImage;
		public void paint(Graphics g) {
			Image act = cards[new Random().nextInt(5)].getImg();
			g.drawImage(act, getWidth() / 2 - act.getWidth(null) / 2, getHeight() / 2 - act.getHeight(null) / 2,
					act.getWidth(null), act.getHeight(null), this);
		}

		public void update(Graphics g) {
			if (bufferImage == null)
				bufferImage = this.createImage(Card.CardImgWidth, Card.CardImgHeight);
			Graphics gImage = bufferImage.getGraphics(); // 把它的画笔拿过来,给gImage保存着
			paint(gImage); // 将要画的东西画到图像缓存空间去
			g.drawImage(bufferImage, 0, 0, null); // 然后一次性显示出来
		}
	};

	public GameViewTester() {
		// 窗口创建
		this.setTitle("ImgTester");
		this.setSize(1024, 1536);
		this.setBackground(Color.BLACK);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		// 变量初始化
		for (int i = 0; i < 6; i++) this.coins[i] = new Coin(i);
		for (int i = 0; i < 5; i++) this.cards[i] = new Card(new Random().nextInt(3) + 1, coins[i].getColor());
		JPanel player1 = new JPanel();
		JPanel player2 = new JPanel();
		JPanel gamePool = new JPanel(new GridLayout(1,3,10,10));
		
		// 布局
		Container container = this.getContentPane();
		container.setLayout( new GridLayout(3,1,10,10));
		gamePool.add(pool);gamePool.add(pool);gamePool.add(pool);
		player1.add(new JButton("aaa"));
		player2.add(new JButton("bbb"));
		container.add(player1);
		container.add(gamePool);
		container.add(player2);
	}

	public static void main(String[] args) {
		GameViewTester gvt = new GameViewTester();
		gvt.setVisible(true);
		gvt.pool.repaint();
	}
}
