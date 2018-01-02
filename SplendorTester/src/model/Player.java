package model;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Queue;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.Document;

//玩家属性
public class Player extends JLabel implements Serializable{
	private static final long serialVersionUID = -1749317213488970787L;
	Game game;
	transient JPanel player_gains; // 战利品区
	transient Canvas player_cArea; // 已有卡片存放区
	transient Canvas player_dArea; // 已有钻石存放区
	transient Canvas player_eArea; // 扣押卡片存放区
	transient JTextPane player_grade;
	transient JTextPane player_name;
	transient JTextPane player_mArea; // 消息通知区
	transient Document player_msg;
	transient JButton player_exchange;
	transient JButton player_escort;
	transient JButton player_buy;

	String name;
	Player MySelf = this;
	boolean myTurn = true;
	int score = 0;
	int mypoints;
	int[] mydiamonds = new int[6]; // 硬币数（可消费）
	int[] mycoins = new int[6]; // 硬币+已有卡所含宝石数
	int[] mycards = new int[5]; // 卡牌数量
	int[] myowes = new int[5]; // 赎回所需要的硬币数量
	Stack<Card> eCard = new Stack<Card>();

	// 初始化
	public Player(final Game game, String name) {
		this.game = game;
		this.name = name;
		mypoints = 0;
		for (int i = 0; i < 6; i++) {
			mydiamonds[i] = 0;
			mycoins[i] = 0;
		}
		viewInit();
	}

	public void viewInit() {
		setLayout(null);
		// 玩家 姓名
		player_name = new JTextPane();
		player_name.setFont(new Font("微软雅黑", Font.BOLD, 26));
		player_name.setEditable(false);
		player_name.setBounds(15, 10, 280, 40);
		player_name.setText("玩家：" + name);
		this.add(player_name);

		// 玩家 分数
		player_grade = new JTextPane();
		player_grade.setFont(new Font("微软雅黑", Font.BOLD, 26));
		player_grade.setEditable(false);
		player_grade.setBounds(15, 56, 280, 40);
		player_grade.setText("分数：" + score);
		this.add(player_grade);

		// 玩家 消息
		player_mArea = new JTextPane();
		player_mArea.setFont(new Font("宋体", Font.BOLD, 20));
		player_mArea.setBounds(15, 650, 280, 197);
		this.add(player_mArea);
		player_msg = player_mArea.getDocument();

		// 玩家 兑换
		player_exchange = new JButton("兑换");
		player_exchange.setBounds(40, 610, 69, 30);
		player_exchange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (exchangeCoin(game.coinChecker))
					MySelf.player_mArea.setText("兑换硬币成功.\n");
			}
		});
		this.add(player_exchange);

		// 玩家 扣押
		player_escort = new JButton("扣押");
		player_escort.setBounds(117, 610, 69, 30);
		player_escort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (escortCard(game.cardChecker)) {
					if (eCard.size() > 0)
						MySelf.player_mArea.setText("扣押卡片成功.\n");
					else
						MySelf.player_mArea.setText("赎回卡片成功.\n");
				}
			}
		});
		this.add(player_escort);

		// 玩家 买卡
		player_buy = new JButton("买卡");
		player_buy.setBounds(196, 610, 69, 30);
		player_buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(game.cardChecker);
				if (buyCard(game.cardChecker))
					MySelf.player_mArea.setText("购买卡片成功.\n");
			}
		});
		this.add(player_buy);

		// 玩家 收获区
		player_gains = new JPanel();
		player_gains.setBounds(10, 100, 300, 500);
		player_gains.setBorder(BorderFactory.createLineBorder(Color.green, 3));
		player_gains.setLayout(null);

		// 玩家 已有钻石区
		player_dArea = new Canvas() {
			private static final long serialVersionUID = -7568465901328814204L;
			private Image bufferImage;
			private int width = 120;
			private int height = 330;

			@Override
			public void paint(Graphics g) {
				g.clearRect(0, 0, width, height);
				g.setFont(Common.font_DiamondValue);
				for (int i = 0; i < 6; i++) {
					g.drawImage(Common.diamonds[i].img, 10, i * 54 + 5, 62, 49, null);
					g.drawString(String.valueOf(mydiamonds[i]), 80, i * 54 + 40);
					for (int j = 0; i < 5 && j < myowes[i]; j++)
						g.fillRect(5, i * 54 + j * 10 + 10, 5, 5);
				}
			}

			@Override
			public void update(Graphics g) {
				if (bufferImage == null)
					bufferImage = this.createImage(width, height);
				Graphics gImage = bufferImage.getGraphics(); // 把它的画笔拿过来,给gImage保存着
				paint(gImage); // 将要画的东西画到图像缓存空间去
				g.drawImage(bufferImage, 0, 0, null); // 然后一次性显示出来
			}
		};
		player_dArea.setBounds(5, 5, 120, 330);

		// 玩家 扣押区
		player_eArea = new Canvas() {
			private static final long serialVersionUID = -7565901328814204L;
			private Image bufferImage;
			private int width = 160;
			private int height = 250;

			@Override
			public void paint(Graphics g) {
				if (eCard.size() != 0) {
					width = getWidth() > getHeight() * 16 / 25 ? getHeight() * 16 / 25 : getWidth();
					height = getHeight();
					g.drawImage(eCard.peek().img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH),
							getWidth() / 2 - width / 2, 0, null);
				} else {
					g.clearRect(0, 0, width - 1, height - 1);
					g.drawRect(0, 0, width - 1, height - 1);
				}
			}

			@Override
			public void update(Graphics g) {
				if (bufferImage == null)
					bufferImage = this.createImage(width, height);
				Graphics gImage = bufferImage.getGraphics(); // 把它的画笔拿过来,给gImage保存着
				paint(gImage); // 将要画的东西画到图像缓存空间去
				g.drawImage(bufferImage, 0, 0, null); // 然后一次性显示出来
			}
		};
		player_eArea.setBounds(135, 40, 160, 250);

		// 玩家 已有卡牌区
		player_cArea = new Canvas() {
			private static final long serialVersionUID = -7568461328814204L;
			private Image bufferImage;
			private int width = 290;
			private int height = 160;

			@Override
			public void paint(Graphics g) {
				for (int i = 0; i < 5; i++) {
					g.setColor(Common.getColor(i));
					g.fillRect(i * 56 + 9, 5, 48, 75);
					for (int j = 0; j < mycards[i]; j++)
						g.fillRect(i * 56 + 10 + (j % 4) * 12, 82 + (j / 4) * 17, 10, 15);
				}
			}

			@Override
			public void update(Graphics g) {
				if (bufferImage == null)
					bufferImage = this.createImage(width, height);
				Graphics gImage = bufferImage.getGraphics(); // 把它的画笔拿过来,给gImage保存着
				paint(gImage); // 将要画的东西画到图像缓存空间去
				g.drawImage(bufferImage, 0, 0, null); // 然后一次性显示出来
			}
		};
		player_cArea.setBounds(5, 335, 290, 160);
		player_gains.add(player_dArea);
		player_gains.add(player_eArea);
		player_gains.add(player_cArea);
		this.add(player_gains);
		player_gains.repaint();
		
		if(!myTurn){
			player_buy.setVisible(false);
			player_escort.setVisible(false);
			player_exchange.setVisible(false);
		}
	}
	// 买牌
	boolean buyCard(Card card) {
		// 如果没有选中卡片
		if (card == null) {
			game.MessageBox(MySelf, "请选择一张卡片！！\n");
			return false;
		}
		// 检查硬币数量
		for (int i = 0; i < 5; i++)
			if (mycoins[i] < card.getCoinNumNeeded(i)) {
				game.MessageBox(MySelf, "硬币数量不足！\n");
				return false;
			}
		// 减去消耗硬币，放回币堆
		for (int i = 0; i < 5; i++) {
			int temp = card.getCoinNumNeeded(i) - mycards[i];
			mydiamonds[i] -= (temp > 0 ? temp : 0);
			game.coins[i].num += (temp > 0 ? temp : 0);
			game.coins[i].repaint();
		}
		// 更新卡片和总购买额度
		mycards[Common.getPosition(card.color)] += 1;
		this.score += card.value;
		
		// 买完的卡片清出卡片区
		card.checked = false;
		game.cardChecker = null;
		game.cards[card.rank-1].remove(card);
		game.CardRefresh();
		
		// 动画现实卡片走向
		int clr = Common.getPosition(card.color);
		int[] pos = { card.getX() + 350, card.getY() + 10,
				25 + game.nowTurn * 1100 + clr * 56 + (mycards[clr] % 4) * 12, 527 + (mycards[clr] / 4) * 17 };
		int[] size = { card.getWidth(), card.getHeight(), 10, 15 };
		new Anime(game, pos, size, 10, card.img).run();

		this.refresh();
		return true;
	}

	// 拿硬币
	boolean exchangeCoin(Queue<Coin> coinChecker) {
		if (coinChecker.size() == 0) {
			game.MessageBox(MySelf, "请选择硬币！\n");
			return false;
		} else {
			while (game.coinChecker.size() > 0) {
				Coin temp = game.coinChecker.remove();
				this.mydiamonds[Common.getPosition(temp.color)] += temp.checked;
				game.coins[Common.getPosition(temp.color)].num -= temp.checked;
				temp.checked = 0;
				temp.repaint();
			}
			this.refresh();
			return true;
		}
	}

	// 押牌
	boolean escortCard(Card card) {
		// 如果当前为扣押操作
		if (this.eCard.size() == 0) {
			// 检查金币数目是否足够
			int goldNeeded = 0;
			for (int i = 0; i < 5; i++) {
				int temp = card.getCoinNumNeeded(i) - mycoins[i];
				goldNeeded += (temp > 0 ? temp : 0);
			}
			if (goldNeeded > mydiamonds[5]) {
				game.MessageBox(MySelf, "拥有金币数不足！\n");
				return false;
			} else if (goldNeeded == 0) {
				game.MessageBox(MySelf, "该卡片无需扣押！\n");
				return false;
			}
			for (int i = 0; i < 5; ++i) {
				int temp = card.getCoinNumNeeded(i) - mycoins[i];
				myowes[i] += (temp > 0 ? temp : 0);
				mydiamonds[5] -= (temp > 0 ? temp : 0);
			}
			// 减去消耗硬币
			for (int i = 0; i < 5; i++) {
				int temp = card.getCoinNumNeeded(i) - mycards[i];
				mydiamonds[i] -= (temp > 0 ? temp : 0);
				mydiamonds[i] += myowes[i];
			}
			
			// 买完的卡片清出卡片区
			card.checked = false;
			game.cardChecker = null;
			game.cards[card.rank-1].remove(card);
			game.CardRefresh();
			
			// 动画现实卡片走向
			int[] pos = { card.getX() + 350, card.getY() + 10, game.nowTurn * 1100 + 145, 140 };
			int[] size = { card.getWidth(), card.getHeight(), 160, 250 };
			new Anime(game, pos, size, 10, card.img).run();
			eCard.push(card);
			player_escort.setText("赎回");
		}
		// 当前为赎回操作
		else {
			// 检查是否满足赎回条件
			for (int i = 0; i < 5; i++)
				if (myowes[i] > mydiamonds[i]) {
					game.MessageBox(MySelf, Common.getColorName(i) + "硬币不足，无法赎回！\n");
					return false;
				}
			// 减去硬币，加回金币
			for (int i = 0; i < 5; i++) {
				mydiamonds[i] -= myowes[i];
				mydiamonds[5] += myowes[i];
				myowes[i] = 0;
			}
			Card temp = eCard.pop();
			mycards[Common.getPosition(temp.color)] += 1;
			this.mypoints += temp.value;
			// 动画现实卡片走向
			int clr = Common.getPosition(temp.color);
			int[] pos = { game.nowTurn * 1100 + 145, 140, 25 + game.nowTurn * 1100 + clr * 56 + (mycards[clr] % 4) * 12,
					527 + (mycards[clr] / 4) * 17 };
			int[] size = { 160, 250, 10, 15 };
			new Anime(game, pos, size, 10, temp.img).run();
			player_escort.setText("扣押");
		}
		this.refresh();
		return true;
	}

	public void refresh() {
		for (int i = 0; i < 5; i++)
			mycoins[i] = mydiamonds[i] + mycards[i];
		player_grade.setText("分数  ：" + score);
		player_cArea.repaint();
		player_eArea.repaint();
		player_dArea.repaint();
		game.ChangeTurns();
	}

	void ChangeTurns() {
		myTurn = !myTurn;
		if (myTurn) {
			player_buy.setVisible(true);
			player_exchange.setVisible(true);
			player_escort.setVisible(true);
			this.player_mArea.setText("我的回合：\n");
		} else {
			player_buy.setVisible(false);
			player_exchange.setVisible(false);
			player_escort.setVisible(false);
		}
	}
}