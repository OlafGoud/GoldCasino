package git.olafgoud.casino.steenpapierschaar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import git.olafgoud.casino.CasinoMain;
import git.olafgoud.casino.utils.CreateItemStack;

public class SPSMainScreen {

	
	public static void openInventory(Player p) {
		p.openInventory(getInventory());
	}
	
	
	public static Inventory getInventory() {
		SPSMainScreenHolder holder = new SPSMainScreenHolder();
		Inventory inv = Bukkit.createInventory(holder, 6*9, "Steen Papier Schaar");
		CreateItemStack createGreenGlass = new CreateItemStack(Material.STAINED_GLASS_PANE, 13);
		createGreenGlass.setName(ChatColor.GRAY + "[own side]");
		CreateItemStack createRedGlass = new CreateItemStack(Material.STAINED_GLASS_PANE, 14);
		createRedGlass.setName(ChatColor.GRAY + "[oponent side]");
		CreateItemStack createBlackGlass = new CreateItemStack(Material.STAINED_GLASS_PANE, 15);
		createBlackGlass.setName(ChatColor.GRAY + "[SteenPapierSchaar]");

		for(int i = 0; i < 54; i++) {
			if(i < 4 || (i > 8 && i < 13) || (i > 17 && i < 22) || (i > 26 && i < 31) || (i > 35 && i < 40) || (i > 44 && i < 49)) {
				inv.setItem(i, createGreenGlass.getItem());
			} else if( i == 4 || i == 13 || i == 22 || i == 31 || i == 40 || i == 49) {
				inv.setItem(i, createBlackGlass.getItem());
			} else {
				inv.setItem(i, createRedGlass.getItem());
			}
		}
		
		CreateItemStack createLever = new CreateItemStack(Material.WOOL, 13);
		createLever.setName(ChatColor.GREEN + "Ready");
		
		CreateItemStack createBarrier = new CreateItemStack(Material.BARRIER);
		createBarrier.setName(ChatColor.AQUA + "Place Your money here");
		
		inv.setItem(48, createLever.getItem());
		inv.setItem(39, createBarrier.getItem());
		inv.setItem(41, createBarrier.getItem());
		
		
		
		return inv;
	}


	public static void handleClickEvent(InventoryClickEvent e, SPSMainScreenHolder holder) {
		Integer slot = e.getRawSlot();
		
		if(slot > 53) {
			return;
		}
		e.setCancelled(true);
		
		
		if(e.getSlot() == 39) {
			ItemStack cursor = e.getWhoClicked().getItemOnCursor();
			ItemStack copyCursor = cursor.clone();
			copyCursor.setAmount(1);
			ItemStack copyInventoryItem = e.getInventory().getItem(slot).clone();
			copyInventoryItem.setAmount(1);
			if(cursor.getType().equals(Material.AIR) && CasinoMain.listOfCoins.containsValue(copyInventoryItem)) {
				e.getWhoClicked().setItemOnCursor(e.getInventory().getItem(slot));
				e.getInventory().setItem(slot, getInventory().getItem(slot));
				return;
			} else if (CasinoMain.listOfCoins.containsValue(copyCursor)) {
				e.getInventory().setItem(slot, cursor);
				e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
				return;
			}
		}
		
		
		
	}
}
