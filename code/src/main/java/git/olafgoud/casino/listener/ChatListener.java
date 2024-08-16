package git.olafgoud.casino.listener;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import git.olafgoud.casino.games.roulette.RouletteMainScreenHolder;
import git.olafgoud.casino.utils.CreateItemStack;

public class ChatListener implements Listener {
	
	public static HashMap<Player, Inventory> players = new HashMap<>();

	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if(players.containsKey(e.getPlayer())) {
			e.getPlayer().sendMessage(e.getMessage());

			try {
				Integer number = Integer.valueOf(e.getMessage());

				if(number > 36 || number < 0) {
					e.getPlayer().sendMessage(ChatColor.RED + "This must be a number between 0 and 36");
					e.setCancelled(true);
					return;
				}
				
				
				e.setCancelled(true);
				e.getPlayer().openInventory(players.get(e.getPlayer()));
				CreateItemStack paperCreate = new CreateItemStack(Material.PAPER);
				paperCreate.setLore(ChatColor.AQUA + "Click Here to change te number");
				paperCreate.setName(ChatColor.AQUA + number.toString());
				
				InventoryHolder holder = players.get(e.getPlayer()).getHolder();
				if(!(holder instanceof RouletteMainScreenHolder)) {
					players.remove(e.getPlayer());
					return;
				}
				
				RouletteMainScreenHolder rouletteHolder = (RouletteMainScreenHolder) holder;
				rouletteHolder.roulette.number = number;
				rouletteHolder.roulette.fullList.set(0, number);
				players.get(e.getPlayer()).setItem(53, paperCreate.getItem());
				players.remove(e.getPlayer());
			} catch (Exception a) {
				e.getPlayer().sendMessage(ChatColor.RED + "This must be a number");
				e.setCancelled(true);
				return;
			}
			
			
			
		}
	}
	
}
