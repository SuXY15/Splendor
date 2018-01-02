package app;

import model.Coin;
import model.ImageCanvas;
import model.Player;
import model.Card;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.Random;

public class GameViewTester extends JFrame {
	private static final long serialVersionUID = 1L;
	int cardNum = 10;
	Coin[] coins = new Coin[6];
	Card[] cards = new Card[cardNum];
	Player[] players = new Player[2];

	public GameViewTester() {
		// 绐楀彛鍒涘缓
		this.setTitle("鐠€鐠ㄥ疂鐭�");
		this.setSize(1024, 768);
		this.setResizable(false);
		this.setBackground(Color.BLACK);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 鍙橀噺鍒濆鍖�
		for (int i = 0; i < 6; i++)
			this.coins[i] = new Coin(i);
		for (int i = 0; i < cardNum; i++)
			this.cards[i] = new Card(new Random().nextInt(3) + 1, coins[i % 5].getColor());
		JPanel player1 = new JPanel();
		JPanel player2 = new JPanel();
		JPanel cardPool = new JPanel();
		JPanel coinPool = new JPanel();
		// 甯冨眬
		Container container = this.getContentPane();
		container.setLayout(null);
		container.add(player1);
		container.add(player2);
		container.add(cardPool);
		container.add(coinPool);
		
		
		// 鍙橀噺鍒濆鍖�
		for (int i = 0; i < 6; i++) this.coins[i] = new Coin(i);
		for (int i = 0; i < cardNum; i++) this.cards[i] = new Card(new Random().nextInt(3) + 1, coins[i%5].getColor());

		player1.setBounds(5, 5, 255, 677);
		player1.setBorder(BorderFactory.createLineBorder(Color.red, 3));
		player2.setBounds(756,5,255,677);
		player2.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
		player2.setLayout(null);
		cardPool.setBounds(275, 5, 468, 538);
		coinPool.setBounds(274, 549, 468, 133);
		cardPool.setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
		coinPool.setBorder(BorderFactory.createLineBorder(Color.white, 3));
		coinPool.setLayout(new GridLayout(1, 4, 10, 10));


		// 甯冨眬
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setFont(new Font("寰蒋闆呴粦", Font.BOLD, 22));
		textPane_2.setEditable(false);
		textPane_2.setText("濮撳悕");
		textPane_2.setBounds(27, 15, 185, 40);
		player2.add(textPane_2);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setFont(new Font("寰蒋闆呴粦", Font.BOLD, 22));
		textPane_3.setEditable(false);
		textPane_3.setText("鍒嗘暟");
		textPane_3.setBounds(27, 70, 185, 40);
		player2.add(textPane_3);
		
		JTextPane textPane_4 = new JTextPane();
		textPane_4.setBounds(15, 485, 225, 179);
		player2.add(textPane_4);
		
		JButton button_3 = new JButton("鍏戞崲");
		button_3.setBounds(15, 441, 69, 29);
		player2.add(button_3);
		
		JButton button_4 = new JButton("鍏戞崲");
		button_4.setBounds(95, 441, 69, 29);
		player2.add(button_4);
		
		JButton button_5 = new JButton("鍏戞崲");
		button_5.setBounds(171, 441, 69, 29);
		player2.add(button_5);
		
		JPanel player2_coins = new JPanel();
		player2_coins.setBounds(27, 125, 203, 301);
		player2_coins.setBorder(BorderFactory.createLineBorder(Color.green, 3));
		player2.add(player2_coins);
		player1.setLayout(null);
		cardPool.setLayout(new GridLayout(4, 5, 5, 5));
		
		for(int i=0;i<20;i++)
		cardPool.add( new ImageCanvas(cards[new Random().nextInt(cardNum)].getImg()) );	
	
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("寰蒋闆呴粦", Font.BOLD, 22));
		textPane.setEditable(false);
		textPane.setBounds(25, 15, 185, 40);
	
		textPane.setText("濮撳悕");
		player1.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setFont(new Font("寰蒋闆呴粦", Font.BOLD, 22));
		textPane_1.setEditable(false);
		textPane_1.setBounds(25, 70, 185, 40);
		player1.add(textPane_1);
		textPane_1.setText("鍒嗘暟");
		
		JTextPane textPane_5 = new JTextPane();
		textPane_5.setBounds(15, 485, 225, 177);
		player1.add(textPane_5);
		
		JButton button = new JButton("鍏戞崲");
		button.setBounds(15, 441, 69, 29);
		player1.add(button);
		
		JButton button_1 = new JButton("鍏戞崲");
		button_1.setBounds(92, 441, 69, 29);
		player1.add(button_1);
		
		JButton button_2 = new JButton("鍏戞崲");
		button_2.setBounds(171, 441, 69, 29);
		player1.add(button_2);
		
		JPanel player1_coins = new JPanel();
		player1_coins.setBounds(25, 125, 203, 301);
		player1_coins.setBorder(BorderFactory.createLineBorder(Color.green, 3));
		player1_coins.setLayout(new GridLayout(4, 2, 5, 5));
		for(int i=0;i<8;i++)
		player1_coins.add( new ImageCanvas(cards[new Random().nextInt(cardNum)].getImg()) );	
		player1.add(player1_coins);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("鑶滃仴绁�");
		mnNewMenu.setFont(new Font("鏂板畫浣�", Font.PLAIN, 21));
		menuBar.add(mnNewMenu);
		
		JMenuItem menuItem = new JMenuItem("鏂版父鎴�");
		menuItem.setFont(new Font("鏂板畫浣�", Font.PLAIN, 21));
		mnNewMenu.add(menuItem);
	}

	public static void main(String[] args) {
		GameViewTester gvt = new GameViewTester();
		gvt.setVisible(true);
	}
}
