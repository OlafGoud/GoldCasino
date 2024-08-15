package git.olafgoud.casino.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import git.olafgoud.casino.games.roulette.RouletteMainScreen;
import git.olafgoud.casino.games.roulette.RouletteMainScreenHolder;

public class InventoryClick implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getClickedInventory() != null && e.getClickedInventory().getHolder() != null) {
			InventoryHolder holder1 = e.getClickedInventory().getHolder();
			if(holder1 instanceof RouletteMainScreenHolder) {
				RouletteMainScreen.onClick(e, (RouletteMainScreenHolder) holder1);
			}
		} 
	}
}
