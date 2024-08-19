package git.olafgoud.casino.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

import git.olafgoud.casino.games.roulette.RouletteMainScreen;
import git.olafgoud.casino.games.roulette.RouletteMainScreenHolder;
import git.olafgoud.casino.steenpapierschaar.SPSQueeScreen;
import git.olafgoud.casino.steenpapierschaar.SPSQueeScreenHolder;

public class InventoryClose implements Listener{

	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		InventoryHolder holder1 = e.getInventory().getHolder();
		if(holder1 instanceof RouletteMainScreenHolder) {
			RouletteMainScreen.onClose(e, (RouletteMainScreenHolder) holder1);
		} else if (holder1 instanceof SPSQueeScreenHolder) {
			SPSQueeScreen.onClose(e, (SPSQueeScreenHolder) holder1);
		}

	}
}
