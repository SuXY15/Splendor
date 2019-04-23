package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Coin extends JLabel implements MouseListener {
	private static final long serialVersionUID = -8973407146698495L;
	int num;
	protected Color color;
	protected Game game;
	protected Image img;
	protected Image bufferImage;
	protected int checked;
	int width, height;

	public Coin(Game game, int i) {
		this.game = game;
		this.color = Common.colors[i];
		try {
			this.img = ImageIO.read(new File(Common.coinImg[i]));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIcon(new ImageIcon(img));
		this.addMouseListener(this);
	}

	public Color getColor() {
		return color;
	}

	public void paint(Graphics g) {
		width = getWidth() > getHeight() * 89 / 94 ? getHeight() * 89 / 94 : getWidth();
		height = getHeight();
		g.drawImage(img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), getWidth() / 2 - width / 2, 0,
				null);
		if (checked == 1) {
			int gap = (getWidth() - width) / 2 + 2;
			g.setColor(color);
			g.fillRect(0, 0, gap, height);
			g.fillRect(0, 0, width, gap);
			g.fillRect(0, getHeight() - gap, width, gap);
			g.fillRect(getWidth() - gap, 0, gap, height);
		} else if (checked == 2) {
			int gap = (getWidth() - width) / 2 + 2;
			g.setColor(Color.magenta);
			g.fillRect(0, 0, gap, height);
			g.fillRect(0, 0, width, gap);
			g.fillRect(0, getHeight() - gap, width, gap);
			g.fillRect(getWidth() - gap, 0, gap, height);
		} else {
			int gap = (getWidth() - width) / 2;
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, gap, height);
			g.fillRect(getWidth() - gap, 0, gap, height);
		}
		g.setColor(Color.GRAY);
		g.setFont(Common.font_CardValue);
		if (num > 0)
			g.drawString(String.valueOf(num), width * 4 / 5, height / 5);
	}

	public void update(Graphics g) {
		if (bufferImage == null)
			bufferImage = this.createImage(img.getWidth(null), img.getHeight(null));
		Graphics gImage = bufferImage.getGraphics(); // 把它的画笔拿过来,给gImage保存着
		paint(gImage); // 将要画的东西画到图像缓存空间去
		g.drawImage(bufferImage, 0, 0, null); // 然后一次性显示出来
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (checked) {
		// 新选中的硬币之前未被选中
		case 0:
			if (game.coins[Common.getPosition(this.color)].num < 1) {
				game.MessageBox(game.players[game.nowTurn], "当前硬币数量不足\n");
				break;
			}
			// 判断选中的是否是金币，如果是就不能和其他硬币一起拿
			if (this.color == Common.colors[5]) {
				while (game.coinChecker.size() > 0) {
					Coin temp = game.coinChecker.remove();
					temp.checked = 0;
					temp.paint(temp.getGraphics());
				}
			}
			// 如果选中新硬币之前只有一种非金色硬币被选中，那么这个硬币无论check是1还是2，都重新置为1（选了不同种的硬币的话每种硬币就只能拿一个）
			else if (game.coinChecker.size() == 1) {
				Coin temp = game.coinChecker.remove();
				if (temp.color == Common.colors[5]) {
					temp.checked = 0;
					temp.paint(temp.getGraphics());
				}
				if (temp.checked == 2)
					temp.checked = 1;
				game.coinChecker.add(temp);
				temp.paint(temp.getGraphics());

				// 如果之前已选了三种硬币，则删去最早选中的硬币，然后将新选中的硬币加进来
			} else if (game.coinChecker.size() > 2) {
				Coin temp = game.coinChecker.remove();
				temp.checked = 0;
				temp.paint(temp.getGraphics());
			}
			checked = 1;
			game.coinChecker.add(this);
			break;
		// 新选中的硬币之前处于选中一次的状态
		case 1:
			// 如果是金币，则取消选中(金币一次只能拿一个)
			if (this.color == Common.colors[5]) {
				while (game.coinChecker.size() > 0) {
					Coin temp = game.coinChecker.remove();
					temp.checked = 0;
					temp.paint(temp.getGraphics());
					game.MessageBox(game.players[game.nowTurn], "友情提示：金色只能拿一张~");
				}
				checked = 0;
				break;
				// 非金币的硬币，如果余量足够的话，再点击一次表示拿两个同色的
			} else {
				if (game.coins[Common.getPosition(this.color)].num <= 2) {
					game.MessageBox(game.players[game.nowTurn], "当前硬币只能拿一个\n");
				} else {
					while (game.coinChecker.size() > 0) {
						Coin temp = game.coinChecker.remove();
						temp.checked = 0;
						temp.paint(temp.getGraphics());
					}
					checked = 2;
					game.coinChecker.add(this);
				}
				break;
			}
			// 新选中的硬币之前处于选中两次的状态，再点击则取消选中
		case 2:
			checked = 0;
			game.coinChecker.remove();
			break;
		default:
			break;
		}
		if (game.cardChecker != null) {
			game.cardChecker.checked = false;
			game.cardChecker.paint(game.cardChecker.getGraphics());
			game.cardChecker = null;
		}
		this.paint(this.getGraphics());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}