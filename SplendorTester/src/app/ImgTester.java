package app;

import model.Coin;
import model.Card;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class ImgTester extends Frame {
	private static final long serialVersionUID = 1L;
	private Image bufferImage;
	int num = 0;
	Coin[] coins = new Coin[5];
	Card[] cards = new Card[5];
	Image act;
	Canvas canvas = new Canvas() {
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			// 参数分别为加载图像，所在Graphics上的left,所在top,图像width,图像height,目标对象
			act = cards[num].getImg();
			g.drawImage(act, getWidth() / 2 - act.getWidth(null) / 2, getHeight() / 2 - act.getHeight(null) / 2,
					act.getWidth(null), act.getHeight(null), this);
			for (int i = 0; i < cards[num].getValue(); i++) {
				g.setColor(Color.YELLOW);
				g.fillOval(Card.CardImgWidth - 36, i * 36 + 12, 24, 24);
				g.setColor(cards[num].getColor());
				g.fillOval(Card.CardImgWidth - 34, i * 36 + 14, 20, 20);
			}
		}

		public void update(Graphics g) {
			if (bufferImage == null)
				bufferImage = this.createImage(Card.CardImgWidth, Card.CardImgHeight);
			Graphics gImage = bufferImage.getGraphics(); // 把它的画笔拿过来,给gImage保存着
			paint(gImage); // 将要画的东西画到图像缓存空间去
			g.drawImage(bufferImage, 0, 0, null); // 然后一次性显示出来
		}
	};

	public ImgTester() {
		this.setTitle("ImgTester");
		this.setSize(256, 400);
		this.setBackground(Color.BLACK);
		for (int i = 0; i < 5; i++)
			this.coins[i] = new Coin(i);

		for (int i = 0; i < 5; i++)
			this.cards[i] = new Card(new Random().nextInt(3) + 1, coins[i].getColor());
		this.add(canvas);
	}

	public static void main(String[] args) {
		ImgTester it = new ImgTester();
		it.setVisible(true);
		int i = 100;
		long timer = 0;
		while (i-- > 0) {
			it.num = (int) (Math.random() * 5);
			(it.canvas).repaint();
			timer = System.currentTimeMillis();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(0);
			}
			System.out.println(System.currentTimeMillis() - timer);
		}
	}
}
