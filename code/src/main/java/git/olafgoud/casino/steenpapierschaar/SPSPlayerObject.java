package git.olafgoud.casino.steenpapierschaar;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SPSPlayerObject {
	
	private Player p;
	private String inzet;
	private ItemStack inzetItem;
	private boolean done;
	
	public SPSPlayerObject(Player player) {
		p = player;
		inzet = null;
		inzetItem = new ItemStack(Material.AIR);
		done = false;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public ItemStack getInzetItem() {
		return inzetItem;
	}

	public void setInzetItem(ItemStack inzetItem) {
		this.inzetItem = inzetItem;
	}

	public String getInzet() {
		return inzet;
	}

	public void setInzet(String inzet) {
		this.inzet = inzet;
	}

	public Player getPlayer() {
		return p;
	}

	public void setPlayer(Player p) {
		this.p = p;
	}
	
}
