package model;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import org.w3c.dom.css.Rect;

public class Anime extends Canvas implements Runnable {
	private static final long serialVersionUID = 3314193308622783898L;
	Game game;
	Image img;
	int frame, ord;
	int[] ti, tx, ty, tw, th;

	public Anime(Game game, int[] pos, int[] size, int frame, Image img) {
		this.game = game;
		this.frame = frame;
		this.img = img;
		tx = new int[frame];
		ty = new int[frame];
		tw = new int[frame];
		th = new int[frame];
		ti = new int[frame];
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
		for (ord = 0; ord < frame; ord++) {
			game.canvas.setBounds(tx[ord], ty[ord], tw[ord], th[ord]);
			g.drawImage(img, 0, 0, tw[ord], th[ord], null);
			try {
				Thread.sleep(ti[ord]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		g.dispose();
		game.canvas.setBounds(0, 0, 0, 0);
		game.canvas.setVisible(false);
	}
}
