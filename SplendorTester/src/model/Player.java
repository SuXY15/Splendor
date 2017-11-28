package model;

import java.awt.Color;

//玩家属性
public class Player {
	protected String name;
	protected int mypoints;
	protected int[] mycoins=new int[6]; // 硬币数（可消费）
	protected int[] mydiamonds=new int[6]; // 硬币+已有卡所含宝石数

	// 初始化
	public Player() {
		mypoints = 0;
		for (int i = 0; i < 6; i++) {
			mycoins[i] = 0;
			mydiamonds[i] = 0;
		}
	}

	public int getMypoints() {
		return mypoints;
	}

	public void setMypoints(int mypoints) {
		this.mypoints = mypoints;
	}

	public int getMycoins(Color color) {
		return mycoins[Coin.getPosition(color)];
	}

	public void setMycoins(Color color, int coinNum) {
		this.mycoins[Coin.getPosition(color)] = coinNum;
	}

	public int getMydiamonds(Color color) {
		return mydiamonds[Coin.getPosition(color)];
	}

	public void setMydiamonds(Color color, int diamondNum) {
		this.mydiamonds[Coin.getPosition(color)] = diamondNum;
	}

	// 拿硬币
	void receiveCoin() {
		
	}

	// 买牌
	void buyCard() {

	}

	// 押牌
	void robCard() {
		if (this.mycoins[5] == 0) {
			// 显示手中没有金色宝石

			// 重新进入执行选项界面
		}

	}
}