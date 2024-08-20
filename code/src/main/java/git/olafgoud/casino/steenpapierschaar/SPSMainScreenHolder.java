package git.olafgoud.casino.steenpapierschaar;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class SPSMainScreenHolder implements InventoryHolder{

	private SPSGame game;
	
	@Override
	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	public SPSGame getGame() {
		return game;
	}

	public void setGame(SPSGame game) {
		this.game = game;
	}

}
