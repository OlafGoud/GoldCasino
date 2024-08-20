package git.olafgoud.casino.steenpapierschaar;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import git.olafgoud.casino.CasinoMain;
import git.olafgoud.casino.utils.CreateItemStack;

public class SPSMainScreen {

	
	public static void openInventory(Player p, SPSGame game) {
		p.openInventory(getInventory(p, game));
	}
	
	public static Inventory getInventory(Player p, SPSGame game) {
		SPSMainScreenHolder holder = new SPSMainScreenHolder();
		holder.player = new SPSPlayerObject(p);
		holder.game = game;
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
		CreateItemStack createWoolRed = new CreateItemStack(Material.WOOL, 14);
		createWoolRed.setName(ChatColor.GREEN + "Not Ready");
		
		CreateItemStack createBarrier = new CreateItemStack(Material.BARRIER);
		createBarrier.setName(ChatColor.AQUA + "Place Your money here");
		
		inv.setItem(48, createLever.getItem());
		inv.setItem(50, createWoolRed.getItem());
		inv.setItem(39, createBarrier.getItem());
		inv.setItem(41, createBarrier.getItem());
		
		CreateItemStack createStone = new CreateItemStack(Material.STONE);
		createStone.setName(ChatColor.AQUA + "Stone");
		CreateItemStack createPaper = new CreateItemStack(Material.PAPER);
		createPaper.setName(ChatColor.AQUA + "Paper");
		CreateItemStack createShears = new CreateItemStack(Material.SHEARS);
		createShears.setName(ChatColor.AQUA + "Shears");
		
		inv.setItem(10, createStone.getItem());
		inv.setItem(16, createStone.getItem());
		inv.setItem(19, createPaper.getItem());
		inv.setItem(25, createPaper.getItem());
		inv.setItem(28, createShears.getItem());
		inv.setItem(34, createShears.getItem());
		
		
		
		return inv;
	}


	public static void handleClickEvent(InventoryClickEvent e, SPSMainScreenHolder holder) {
		Integer slot = e.getRawSlot();
		
		if(slot > 53) {
			return;
		}
		e.setCancelled(true);

		if(holder.game.isDone()) {
			return;
		}
		
		if(slot == 48) {
			if(holder.player.isDone()) {
				holder.player.setDone(false);
				changeInventoryToFalse(holder, (Player) e.getWhoClicked());
				return;
			}
			
			if(holder.player.getInzetItem().getType().equals(Material.AIR)) {
				e.getWhoClicked().sendMessage(ChatColor.RED + "You must put money in the machine");
				return;
			}
			
			if(holder.player.getInzet() == null) {
				e.getWhoClicked().sendMessage(ChatColor.RED + "You must choose an option");
				return;
			}
			
			if(holder.game.getOtherSPSPlayer((Player) e.getWhoClicked()) == null) {
				e.getWhoClicked().sendMessage(ChatColor.AQUA + "Waiting for other player to join the lobby");
			} else if(!holder.game.getOtherSPSPlayer((Player) e.getWhoClicked()).isDone()) {
				e.getWhoClicked().sendMessage(ChatColor.AQUA + "Waiting for other player to be done");
			} else if (holder.game.getOtherSPSPlayer((Player) e.getWhoClicked()).isDone()) {
				changeInventoryToTrue(holder, (Player) e.getWhoClicked());
				e.getWhoClicked().sendMessage("winnar word bepaald");
				checkForWin(holder, (Player) e.getWhoClicked());
			}
			holder.game.getSPSPlayer((Player) e.getWhoClicked()).setDone(true);
			changeInventoryToTrue(holder, (Player) e.getWhoClicked());

		}
		
		if(holder.player.isDone()) {
			return;
		}
		
		
		if(slot == 10) {
			holder.player.setInzet("steen");
			e.getInventory().getItem(10).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			e.getInventory().getItem(19).removeEnchantment(Enchantment.DURABILITY);
			e.getInventory().getItem(28).removeEnchantment(Enchantment.DURABILITY);
		} else if (slot == 19) {
			holder.player.setInzet("papier");
			e.getInventory().getItem(19).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			e.getInventory().getItem(10).removeEnchantment(Enchantment.DURABILITY);
			e.getInventory().getItem(28).removeEnchantment(Enchantment.DURABILITY);
		} else if (slot == 28) {
			holder.player.setInzet("schaar");
			e.getInventory().getItem(28).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			e.getInventory().getItem(19).removeEnchantment(Enchantment.DURABILITY);
			e.getInventory().getItem(10).removeEnchantment(Enchantment.DURABILITY);
		}
		
		
		if(slot == 39) {
			ItemStack cursor = e.getWhoClicked().getItemOnCursor();
			ItemStack copyCursor = cursor.clone();
			copyCursor.setAmount(1);
			ItemStack copyInventoryItem = e.getInventory().getItem(slot).clone();
			copyInventoryItem.setAmount(1);
			if(cursor.getType().equals(Material.AIR) && CasinoMain.listOfCoins.containsValue(copyInventoryItem)) {
				e.getWhoClicked().setItemOnCursor(e.getInventory().getItem(slot));
				SPSPlayerObject p2 = holder.game.getOtherSPSPlayer((Player) e.getWhoClicked());
				if(p2 != null) {
					p2.getPlayer().getOpenInventory().getTopInventory().setItem(slot + 2, e.getInventory().getItem(slot + 2));
				}
				e.getInventory().setItem(slot, getInventory().getItem(slot));
				SPSPlayerObject obj = holder.player;
				obj.setInzetItem(new ItemStack(Material.AIR));
				return;
			} else if (CasinoMain.listOfCoins.containsValue(copyCursor)) {
				e.getWhoClicked().sendMessage("hoi");
				e.getInventory().setItem(slot, cursor);
				SPSPlayerObject p2 = holder.game.getOtherSPSPlayer((Player) e.getWhoClicked());
				if(p2 != null) {
					p2.getPlayer().getOpenInventory().getTopInventory().setItem(slot + 2, cursor);
				}
				e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
				SPSPlayerObject obj = holder.player;
				obj.setInzetItem(cursor);
				return;
			}
		}
		
		
		
	}

	private static void checkForWin(SPSMainScreenHolder holder, Player p) {
		SPSPlayerObject p2 = holder.game.getOtherSPSPlayer(p);
		SPSPlayerObject p1 = holder.game.getSPSPlayer(p);
		Bukkit.getScheduler().runTaskLaterAsynchronously(CasinoMain.getPlugin(), new Runnable() {

			@Override
			public void run() {			
				p1.getPlayer().closeInventory();
				p2.getPlayer().closeInventory();
				
				
				p1.getPlayer().openInventory(SPSQueeScreen.lobbyInventory);
				p2.getPlayer().openInventory(SPSQueeScreen.lobbyInventory);
				holder.game.reset();
			} 
			
		}, 20*5);
		
		String p1Inzet = p1.getInzet();
		String p2Inzet = p2.getInzet();
		
		//setting other view for the reward
		if(p1.getInzet() == "schaar") {
			p2.getPlayer().getOpenInventory().getTopInventory().getItem(34).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		}
		if(p1.getInzet() == "schaar") {
			p2.getPlayer().getOpenInventory().getTopInventory().getItem(25).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		}
		if(p1.getInzet() == "schaar") {
			p2.getPlayer().getOpenInventory().getTopInventory().getItem(16).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		}
		
		if(p2.getInzet() == "schaar") {
			p1.getPlayer().getOpenInventory().getTopInventory().getItem(34).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		}
		if(p2.getInzet() == "schaar") {
			p1.getPlayer().getOpenInventory().getTopInventory().getItem(25).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		}
		if(p1.getInzet() == "schaar") {
			p2.getPlayer().getOpenInventory().getTopInventory().getItem(16).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		}
		
		//check if same option
		if(p1Inzet.equals(p2Inzet)) {
			p1.getPlayer().sendMessage("Equal");
			p2.getPlayer().sendMessage("Equal");
			p1.getPlayer().getInventory().addItem(p1.getInzetItem());
			p2.getPlayer().getInventory().addItem(p2.getInzetItem());

		}
		
		//check different options
		if(p1Inzet == "steen") {
			if(p2Inzet == "schaar") {
				p2.getPlayer().sendMessage("You lost");
				p1.getPlayer().sendMessage("You won");
				p1Won(p1, p2);

			}
			if(p2Inzet == "papier") {
				p2.getPlayer().sendMessage("You won");
				p1.getPlayer().sendMessage("You lost");
				p2Won(p1, p2);


			}
		}
		if(p1Inzet == "papier") {
			if(p2Inzet == "steen") {
				p2.getPlayer().sendMessage("You lost");
				p1.getPlayer().sendMessage("You won");
				p1Won(p1, p2);

			}
			if(p2Inzet == "schaar") {
				p2.getPlayer().sendMessage("You won");
				p1.getPlayer().sendMessage("You lost");
				p2Won(p1, p2);

			}
		}
		if(p1Inzet == "schaar") {
			if(p2Inzet == "papier") {
				p2.getPlayer().sendMessage("You lost");
				p1.getPlayer().sendMessage("You won");
				p1Won(p1, p2);

			}
			if(p2Inzet == "steen") {
				p2.getPlayer().sendMessage("You won");
				p1.getPlayer().sendMessage("You lost");
				p2Won(p1, p2);
			}
		}
	}
	
	
	private static void p1Won(SPSPlayerObject p1, SPSPlayerObject p2) {
		p1.getPlayer().getInventory().addItem(p1.getInzetItem());
		p1.getPlayer().getInventory().addItem(p2.getInzetItem());
	}
	
	private static void p2Won(SPSPlayerObject p1, SPSPlayerObject p2) {
		p2.getPlayer().getInventory().addItem(p1.getInzetItem());
		p2.getPlayer().getInventory().addItem(p2.getInzetItem());

	}
	
	

	public static void onClose(InventoryCloseEvent e, SPSMainScreenHolder holder) {
		e.getPlayer().sendMessage("closed");
		for(SPSGame game : SPSQueeScreen.gameList) {
			ArrayList<SPSPlayerObject> objList = game.getPlayer();
			for(SPSPlayerObject obj : objList) {
				if(obj.getPlayer().equals((Player) e.getPlayer())){
					game.removePlayer((Player) e.getPlayer());
					SPSQueeScreen.updateScreen();
					return;
				}
			}
		}
	}
	
	
	private static void changeInventoryToTrue(SPSMainScreenHolder holder, Player p) {
		SPSPlayerObject p2 = holder.game.getOtherSPSPlayer(p);
		SPSPlayerObject p1 = holder.player;
		CreateItemStack createWoolGreen = new CreateItemStack(Material.WOOL, 13);
		createWoolGreen.setName(ChatColor.GREEN + "Done");
		CreateItemStack createWoolRed = new CreateItemStack(Material.WOOL, 14);
		createWoolRed.setName(ChatColor.GREEN + "Not Ready");
		if(p2 != null) {
			p2.getPlayer().getOpenInventory().getTopInventory().setItem(50, createWoolGreen.getItem());
		}
		p1.getPlayer().getOpenInventory().getTopInventory().setItem(48, createWoolRed.getItem());
	}
	
	private static void changeInventoryToFalse(SPSMainScreenHolder holder, Player p) {
		SPSPlayerObject p2 = holder.game.getOtherSPSPlayer(p);
		SPSPlayerObject p1 = holder.player;
		CreateItemStack createWoolGreen = new CreateItemStack(Material.WOOL, 13);
		createWoolGreen.setName(ChatColor.GREEN + "Done");
		CreateItemStack createWoolRed = new CreateItemStack(Material.WOOL, 14);
		createWoolRed.setName(ChatColor.GREEN + "Not Ready");
		if(p2 != null) {
			p2.getPlayer().getOpenInventory().getTopInventory().setItem(50, createWoolRed.getItem());

		}
		p1.getPlayer().getOpenInventory().getTopInventory().setItem(48, createWoolGreen.getItem());
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
		CreateItemStack createWoolRed = new CreateItemStack(Material.WOOL, 14);
		createWoolRed.setName(ChatColor.GREEN + "Not Ready");
		
		CreateItemStack createBarrier = new CreateItemStack(Material.BARRIER);
		createBarrier.setName(ChatColor.AQUA + "Place Your money here");
		
		inv.setItem(48, createLever.getItem());
		inv.setItem(50, createWoolRed.getItem());
		inv.setItem(39, createBarrier.getItem());
		inv.setItem(41, createBarrier.getItem());
		
		CreateItemStack createStone = new CreateItemStack(Material.STONE);
		createStone.setName(ChatColor.AQUA + "Stone");
		CreateItemStack createPaper = new CreateItemStack(Material.PAPER);
		createPaper.setName(ChatColor.AQUA + "Paper");
		CreateItemStack createShears = new CreateItemStack(Material.SHEARS);
		createShears.setName(ChatColor.AQUA + "Shears");
		
		inv.setItem(10, createStone.getItem());
		inv.setItem(16, createStone.getItem());
		inv.setItem(19, createPaper.getItem());
		inv.setItem(25, createPaper.getItem());
		inv.setItem(28, createShears.getItem());
		inv.setItem(34, createShears.getItem());
		
		
		
		return inv;
	}

}
