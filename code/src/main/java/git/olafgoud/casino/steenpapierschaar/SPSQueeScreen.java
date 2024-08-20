package git.olafgoud.casino.steenpapierschaar;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import git.olafgoud.casino.utils.CreateItemStack;

public class SPSQueeScreen {

	public static ArrayList<SPSGame> gameList = new ArrayList<>();	
	//deze is static omdat hij bij iedereen tegelijk geupdate word
	public static Inventory lobbyInventory;
	
	//enabled de games
	public static void enableSPSLobby() {
		setGameList();
		lobbyInventory = getScreen();
		updateScreen();
	}
	
	//maakt de games
	public static void setGameList() {
		gameList.add(new SPSGame(0, 10));

		gameList.add(new SPSGame(1, 28));

	}
	
	//geeft het scherm
	public static Inventory getScreen() {
		SPSQueeScreenHolder holder = new SPSQueeScreenHolder();
		Inventory inv = Bukkit.createInventory(holder, 5*9, "Steen Papier Schaar Lobby");
		CreateItemStack glassBlack = new CreateItemStack(Material.STAINED_GLASS_PANE, 15);
		glassBlack.setName(ChatColor.GRAY + "[Steen Papier Schaar]");
		for (int i = 0; i < 9 * 5; i++) {
			inv.setItem(i, glassBlack.getItem());
		}
		
		CreateItemStack glassGreen = new CreateItemStack(Material.STAINED_GLASS_PANE, 13);
		glassGreen.setName(ChatColor.AQUA + "Click to join");
		CreateItemStack glassRed = new CreateItemStack(Material.STAINED_GLASS_PANE, 14);
		glassRed.setName(ChatColor.AQUA + "This space is full");

		
		return inv;
	}
	
	
	//handeld de klik events
	public static void handleClickEvents(InventoryClickEvent e) {
		Integer slot = e.getRawSlot();
		
		//buiten bovenste inv
		if(slot > (5*9)-1) {
			return;
		}
		e.setCancelled(true);
		
		Player p = (Player) e.getWhoClicked();
		
		//kijken voor de lobbys
		for(SPSGame game : gameList) {
			if(game.getPlayer().size() > 1) {
				
				continue;
			}
			if(slot == game.getBeginSlot() || slot == game.getBeginSlot() + 1) {
				game.addPlayer(p);
				updateScreen();
				SPSMainScreen.openInventory(p, game);
				return;
			}
		}
		
	}
	
	//updates alle screens
	public static void updateScreen() {
		CreateItemStack glassGreen = new CreateItemStack(Material.STAINED_GLASS_PANE, 13);
		glassGreen.setName(ChatColor.AQUA + "Click to join");
		CreateItemStack glassRed = new CreateItemStack(Material.STAINED_GLASS_PANE, 14);
		glassRed.setName(ChatColor.AQUA + "This space is full");
		for(int i = 0; i < gameList.size(); i++) {
			Integer playerAmount = gameList.get(i).getPlayer().size();
			if(playerAmount == 1) {
				lobbyInventory.setItem(gameList.get(i).getBeginSlot(), glassRed.getItem());
				lobbyInventory.setItem(gameList.get(i).getBeginSlot() + 1, glassGreen.getItem());
			} else if (playerAmount == 2) {
				lobbyInventory.setItem(gameList.get(i).getBeginSlot(), glassRed.getItem());
				lobbyInventory.setItem(gameList.get(i).getBeginSlot() + 1, glassRed.getItem());
			} else {
				lobbyInventory.setItem(gameList.get(i).getBeginSlot(), glassGreen.getItem());
				lobbyInventory.setItem(gameList.get(i).getBeginSlot() + 1, glassGreen.getItem());
			}
		}
	}

	
	
}
