package model;

import java.awt.*;
import javax.swing.*;

public class GameView extends JPanel{
	private static final long serialVersionUID = 1L;
	public GameView(Card[] cards) {
		this.setLayout(new GridLayout(1,3,Game.pixel,Game.pixel));
		
	}
}
