package model;

import java.io.*;

public class Data {
	public static void dataSaver(Game game, String dataname) {
		Game savegame = new Game(game);
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(dataname + ".dat"));
			oos.writeObject(savegame);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Game dataLoader(String dataname) {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(dataname + ".dat"));
			Game loadgame = (Game) ois.readObject();
			ois.close();
			// load
			for (int i = 0; i < loadgame.cards.length; i++)
				for (int j = 0; j < loadgame.cards[i].size(); j++)
					loadgame.cards[i].get(j).loadImg();
			for (int i = 0; i < loadgame.coins.length; i++)
				loadgame.coins[i].loadImg();
			while (!loadgame.coinChecker.isEmpty())
				loadgame.coinChecker.remove();
			return loadgame;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Game quickLoad() {
		Game temp = Data.dataLoader("temp");
		return temp;
	}

	public static void quickSave(Game game) {
		Data.dataSaver(game, "temp");
	}
}