package model;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;

public class Game extends JFrame implements ActionListener, Serializable {
	private static final long serialVersionUID = -3031349636134752779L;
	/*
	 * 成员变量：子对象=== canvas 用于显示动画的画布，Anime线程调用 cardPool 卡池，摆放一系列卡片 coinPool
	 * 币池，摆放一系列硬币 菜单栏按钮：start 开始，exit 退出，undo 撤销， about 关于 coinChecker/cardChecker
	 * 硬币和卡片的选择器，卡片只能选一张，硬币最多可以选三个
	 */
	transient Canvas canvas = new Canvas();
	transient JPanel cardPool, coinPool;
	JMenuItem start, exit, undo, about, save, load;
	Queue<Coin> coinChecker = new LinkedList<Coin>();
	Card cardChecker = null;

	/*
	 * 成员变量：游戏相关 coins 币池中的6种硬币，有数目 cards 卡池中的卡片，选取后属于玩家，并相当于对应颜色的永久币 players
	 * 两个玩家，玩家有兑换硬币、购买卡片、扣押卡片、赎回卡片等操作
	 */
	int nowTurn = 0;
	int cardNum = 25;
	LinkedList<Card>[] cards = new LinkedList[4];
	Coin[] coins = new Coin[6];
	Player[] players = new Player[2];

	// 初始化
	public Game() {
		this.variableInit();
		this.viewInit();
		this.setVisible(true);
		// 游戏初始化：玩家1先开局
		players[1].ChangeTurns();
	}
	
	public Game(Game game) {
		this.cards = game.cards;
		this.coins = game.coins;
		game.players[0].removeAll();
		game.players[1].removeAll();
		this.players = game.players;
		this.cardChecker = game.cardChecker;
		this.coinChecker = game.coinChecker;
		
		this.players[0].game = this;
		this.players[1].game = this;
		for (int i = 0; i < game.cards.length; i++)
			for (int j = 0; j < game.cards[i].size(); j++)
				this.cards[i].get(j).game = this;
		for (int i = 0; i < game.coins.length; i++)
			this.coins[i].game = this;
	}

	public void viewInit() {
		// 窗体初始化
		this.setTitle("SplendorTester");
		this.setSize(1440, 960);
		this.setResizable(false);
		this.setBackground(Color.GRAY);
		this.setLocationRelativeTo(getOwner());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 创建菜单
		JMenuBar jMenuBar = new JMenuBar();
		JMenu menu_game = new JMenu("Game");
		JMenu menu_data = new JMenu("Data");
		JMenu menu_help = new JMenu("Help");

		// 定义菜单控件
		start = new JMenuItem("Start");
		about = new JMenuItem("About");
		undo = new JMenuItem("Undo");
		exit = new JMenuItem("Exit");
		save = new JMenuItem("Save");
		load = new JMenuItem("Load");

		// 设置消息响应
		start.addActionListener(this);
		about.addActionListener(this);
		undo.addActionListener(this);
		exit.addActionListener(this);
		save.addActionListener(this);
		load.addActionListener(this);

		// 添加到菜单
		menu_game.add(start);
		menu_game.add(undo);
		menu_game.add(exit);
		menu_data.add(save);
		menu_data.add(load);
		menu_help.add(about);

		// 添加菜单栏
		jMenuBar.add(menu_game);
		jMenuBar.add(menu_data);
		jMenuBar.add(menu_help);
		this.setJMenuBar(jMenuBar);

		// 相关变量初始化
		cardPool = new JPanel();
		coinPool = new JPanel();
		if(players[0]==null) players[0] = new Player(this, LogIn.play1.getText());
		else players[0].viewInit();
		if(players[1]==null) players[1] = new Player(this, LogIn.play2.getText());
		else players[1].viewInit();
		
		// 容器初始化（多层容器，便于动画展示）
		JLayeredPane container = this.getLayeredPane();
		container.setLayout(null);
		container.add(players[0], 3, 1);
		container.add(players[1], 3, 1);
		container.add(cardPool, 3, 1);
		container.add(coinPool, 3, 1);
		container.add(canvas, 3, 0);

		// 玩家位置、边界设置
		players[0].setBounds(10, 10, 320, 870);
		players[0].setBorder(BorderFactory.createLineBorder(Color.red, 3));
		players[1].setBounds(1110, 10, 320, 870);
		players[1].setBorder(BorderFactory.createLineBorder(Color.blue, 3));

		// 卡池：摆放一系列不同声望和需求的卡片
		cardPool.setLayout(new GridLayout(4, 5, 5, 5));
		for (int i = 3; i >= 0; i--)
			for (int j = 0; j < 5; j++) {
				if (cards[i].size() > j)
					cardPool.add(cards[i].get(j));
				else
					cardPool.add(new JLabel());
			}

		cardPool.setBounds(350, 10, 740, 722);
		cardPool.setBorder(BorderFactory.createLineBorder(Color.yellow, 3));

		// 币池：摆放一系列颜色的硬币（不同色每次可换3枚，同色每次可换2枚，金色最多每次1枚）
		coinPool.setBounds(350, 740, 740, 140);
		coinPool.setBorder(BorderFactory.createLineBorder(Color.white, 3));
		coinPool.setLayout(new GridLayout(1, 4, 10, 10));
		for (int col = 0; col < 6; col++)
			coinPool.add(coins[col]);

		// 画布初始化，不可见
		canvas.setBounds(0, 0, 0, 0);
		canvas.setVisible(true);
	}

	// 主测试函数
	public static void main(String[] args) {
		new LogIn().initFrame();
	}

	// 硬币、卡片初始化：随机得到声望值、兑换需求
	public void variableInit() {
		for (int i = 0; i < 6; i++) {
			coins[i] = new Coin(this, i);
			coins[i].num = 5;
		}
		for (int i = 0; i < 4; i++) {
			cards[i] = new LinkedList<Card>();
			for (int j = 0; j < 10; j++)
				cards[i].add(new Card(this, i + 1, coins[new Random().nextInt(5)].getColor()));
		}
	}

	// 局轮换：检测是否游戏结束，若未结束，则交换游戏局次
	public void ChangeTurns() {
		if (!win()) {
			players[0].ChangeTurns();
			players[1].ChangeTurns();
			nowTurn = (nowTurn + 1) % 2;
		} else {
			for (int i = 0; i < 2; i++) {
				players[i].player_buy.setVisible(false);
				players[i].player_exchange.setVisible(false);
				players[i].player_escort.setVisible(false);
			}
		}
	}

	// 卡动画：卡片取出并左移
	public void CardRefresh() {
		cardPool.removeAll();
		for (int i = 3; i >= 0; i--)
			for (int j = 0; j < 5; j++) {
				if (cards[i].size() > j)
					cardPool.add(cards[i].get(j));
				else
					cardPool.add(new JLabel());
			}
	}

	// 消息框：将需要现实的消息插入到末尾
	public void MessageBox(Player player, String msg) {
		try {
			player.player_msg.insertString(player.player_msg.getLength(), msg, new SimpleAttributeSet());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 检测是否有一方已经获胜
	public boolean win() {
		for (int i = 0; i < 2; i++)
			if (players[i].score >= 15) {
				JOptionPane.showMessageDialog(this, "玩家" + (i + 1) + players[i].name + "赢了！游戏结束！");
				return true;
			}
		return false;
	}

	// 菜单的检测
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			this.dispose();
		}
		if (e.getSource() == about) {
			String helpMsg = "本游戏来自轩神和他的小渣友\n"
					+ "开源代码：https://github.com/SuXY15/Splendor";
			JOptionPane.showMessageDialog(this, helpMsg);
		}
		if (e.getSource() == start) {
			new Game();
			this.dispose();
		}
		if (e.getSource() == undo) {
			JOptionPane.showMessageDialog(this, "膜轩神！");
		}
		if (e.getSource() == save) {
			Data.quickSave(this);
		}
		if (e.getSource() == load) {
			this.dispose();
			Game loadGame = new Game(Data.quickLoad());
			loadGame.revalidate();
			loadGame.viewInit();
			loadGame.setVisible(true);
		}
	}
}
