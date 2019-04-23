package model;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class Anime extends Canvas implements Runnable {
	private static final long serialVersionUID = 3314193308622783898L;
	Game game;
	Image img;
	int frame;
	int[] ti, tx, ty, tw, th;

	public Anime(Game game, int[] pos, int[] size, int frame, Image img) {
		this.game = game;
		this.frame = frame;
		this.img = img;
		tx = new int[frame];  // x pos in time i
		ty = new int[frame];  // y pos in time i
		tw = new int[frame];  // width in time i
		th = new int[frame];  // height in time i
		ti = new int[frame];  // time i to sleep
		for (int i = 0; i < frame; i++) {
			tx[i] = (pos[2] - pos[0]) * i / frame + pos[0];
			ty[i] = (pos[3] - pos[1]) * i / frame + pos[1];
			tw[i] = (size[2] - size[0]) * i / frame + size[0];
			th[i] = (size[3] - size[1]) * i / frame + size[1];
			ti[i] = 300 / frame;
		}
	}

	@Override
	public void run() {
		game.canvas.setVisible(true);
		Graphics g = game.canvas.getGraphics();
		for (int i = 0; i < frame; i++) {
			game.canvas.setBounds(tx[i], ty[i], tw[i], th[i]);
			g.drawImage(img, 0, 0, tw[i], th[i], null);
			try {
				Thread.sleep(ti[i]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		g.dispose();
		game.canvas.setBounds(0, 0, 0, 0);
		game.canvas.setVisible(false);
	}
}
