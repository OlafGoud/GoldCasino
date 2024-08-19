package git.olafgoud.casino.steenpapierschaar;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class SPSGame {

	private final Integer gameNumber;
	private final Integer beginSlot;
	private ArrayList<SPSPlayerObject> players = new ArrayList<>();
	
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
	
	public ArrayList<SPSPlayerObject> getPlayer() {
		return players;
	}
	
	public void removePlayer(Player p) {
		@SuppressWarnings("unchecked")
		ArrayList<SPSPlayerObject> clone = (ArrayList<SPSPlayerObject>) players.clone();
		for(SPSPlayerObject obj : clone) {
			if(obj.getPlayer() == p) {
				players.remove(obj);
			}
		}
	}
	
	public void addPlayer(Player p) {
		players.add(new SPSPlayerObject(p));
	}
	
	public SPSPlayerObject getSPSPlayer(Player p) {
		for(SPSPlayerObject obj : players) {
			if (obj.getPlayer().equals(p)) {
				return obj;
				
			}
		}
		return null;
	}

}
