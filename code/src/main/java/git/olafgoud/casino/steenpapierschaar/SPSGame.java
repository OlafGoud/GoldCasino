package git.olafgoud.casino.steenpapierschaar;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class SPSGame {

	private final Integer gameNumber;
	private final Integer beginSlot;
	private HashMap<Player, Integer> players = new HashMap<>();
	
	public SPSGame(int number, Integer slot) {
		this.gameNumber = number;
		this.beginSlot = slot;
	}

	public Integer getGameNumber() {
		return gameNumber;
	}

	public Integer getBeginSlot() {
		return beginSlot;
	}
	
	public HashMap<Player, Integer> getPlayer() {
		return players;
	}
	
	public void removePlayer(Player p) {
		players.remove(p);
	}
	
	public void addPlayer(Player p) {
		players.put(p, 0);
	}

}
