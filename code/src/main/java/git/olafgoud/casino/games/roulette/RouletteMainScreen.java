package git.olafgoud.casino.games.roulette;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import git.olafgoud.casino.CasinoMain;
import git.olafgoud.casino.games.roulette.events.RouletteWinEvent;
import git.olafgoud.casino.listener.ChatListener;
import git.olafgoud.casino.utils.CasinoSlot;

public class RouletteMainScreen {

	
	public static HashMap<Player, Integer> ID = new HashMap<>();
	
	public static void getScreen(Player p) {
		RouletteMainScreenHolder holder = new RouletteMainScreenHolder();
		holder.roulette = new Roulette();
		
		
		
		
		holder.roulette.createInventory(holder);
		Inventory inv = holder.roulette.inventory;
		p.openInventory(inv);
		
				
	}
	
	
	public static void onClick(InventoryClickEvent e, RouletteMainScreenHolder holder) {
		Integer slot = e.getRawSlot();
		
		
		if(slot > 53) {
			return;
		}
		Player p = (Player) e.getWhoClicked();
		e.setCancelled(true);
		
		Roulette roulette = holder.roulette;

		if(roulette.isRunning()) {
			e.setCancelled(true);
		}
		
		
		if(slot == 29) {
			boolean inzet = false;
			for(CasinoSlot casinoSlot : roulette.rouletteSlots) {
				ItemStack inzetItem = e.getInventory().getItem(casinoSlot.getSlot()).clone();
				inzetItem.setAmount(1);
				if(CasinoMain.listOfCoins.containsValue(inzetItem)) {
					casinoSlot.setInput(e.getInventory().getItem(casinoSlot.getSlot()));
					inzet = true;
				} else {
					casinoSlot.setInput(new ItemStack(Material.AIR));
				}
				
			}
			
			if(inzet == false) {
				p.sendMessage(ChatColor.RED + "You need to place a bet");
				return;
			}
			
			Random rand = new Random();
			Integer randomNumber = rand.nextInt(37);
		
			Integer output = roulette.checkForWin(randomNumber);
			holder.roulette.setRunning(true);
			ID.put(p, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(CasinoMain.getPlugin(), new Runnable() {
				
				public Integer number = holder.roulette.pastNumber;
				
				@Override
				public void run() {
					
					if(number < 0) {
						number += 37;
					} else if (number > 36) {
						number -= 37;
					}
					
					
					ArrayList<ItemStack> numberList = holder.roulette.getNumbers(number);
					e.getInventory().setItem(1, numberList.get(0));
					e.getInventory().setItem(1 + 9, numberList.get(1));
					e.getInventory().setItem(1 + 18, numberList.get(2));
					e.getInventory().setItem(1 + 27, numberList.get(3));
					e.getInventory().setItem(1 + 36, numberList.get(4));
					e.getInventory().setItem(1 + 45, numberList.get(5));
					if(randomNumber == number) {
						
						for(CasinoSlot casinoSlot : roulette.rouletteSlots) {
							ItemStack stack = e.getInventory().getItem(casinoSlot.getSlot()).clone();
							stack.setAmount(1);
							if(CasinoMain.listOfCoins.containsValue(stack)) {
								e.getInventory().setItem(casinoSlot.getSlot(), roulette.getInventory(holder).getItem(casinoSlot.getSlot()));
							}
						}
						
						RouletteWinEvent winEvent = new RouletteWinEvent(p, output);
						Bukkit.getPluginManager().callEvent(winEvent);
						
						if (winEvent.isCancelled()) {
							return;
						}
						
						p.sendMessage(ChatColor.YELLOW + "You won: " + ChatColor.GOLD + output);
						
						// coins uitgeven
						Integer output1 = output;
						Set<Integer> set = CasinoMain.listOfCoins.keySet();
						int[] ints = Arrays.stream(set.toArray()).mapToInt(o -> (int)o).toArray();
						ArrayList<Integer> sortedList = insertionSort(ints);
						Collections.reverse(sortedList);
						Integer i = 0;
						while (output1 > 0 && i < sortedList.size()) {
							
							if (output1 - sortedList.get(i) >= 0) {
								p.getInventory().addItem(CasinoMain.listOfCoins.get(sortedList.get(i)));
								output1 -= sortedList.get(i);
							} if(output1 < sortedList.get(i)) {
								i++;
							}
						}
						
						
						
						
						
						
						holder.roulette.setRunning(false);
						Bukkit.getScheduler().cancelTask(ID.get(p));
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 2, 2);
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BELL, 2, 2);
						holder.roulette.pastNumber = number;

					}
					
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 2, 2);
					number++;
					
				}
				
			}, 0, 5));
		}
		
		if(slot == 53) {
			ChatListener.players.put(p, e.getInventory());
			p.closeInventory();
			p.sendMessage(ChatColor.GOLD + "Type the number in chat.");
			
		}	
		
		if(roulette.checkForRouletteSlots(slot)) {
			
			ItemStack cursor = e.getWhoClicked().getItemOnCursor();
			ItemStack copyCursor = cursor.clone();
			copyCursor.setAmount(1);
			ItemStack copyInventoryItem = e.getInventory().getItem(slot).clone();
			copyInventoryItem.setAmount(1);
			if(cursor.getType().equals(Material.AIR) && CasinoMain.listOfCoins.containsValue(copyInventoryItem)) {
				e.getWhoClicked().setItemOnCursor(e.getInventory().getItem(slot));
				e.getInventory().setItem(slot, holder.roulette.getInventory(holder).getItem(slot));
				return;
			} else if (CasinoMain.listOfCoins.containsValue(copyCursor)) {
				e.getInventory().setItem(slot, cursor);
				e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
				return;
			}
			
			
		}
		
		
	}
	
	public static void onClose(InventoryCloseEvent e, RouletteMainScreenHolder holder) {
		if(holder.roulette.isRunning()) {
			return;
		}
		Roulette roulette = holder.roulette;

		for(CasinoSlot casinoSlot : roulette.rouletteSlots) {
			ItemStack inzetItem = e.getInventory().getItem(casinoSlot.getSlot()).clone();
			inzetItem.setAmount(1);
			if(CasinoMain.listOfCoins.containsValue(inzetItem)) {
				e.getPlayer().getInventory().addItem(e.getInventory().getItem(casinoSlot.getSlot()));
			} 
			
		}
		
	}
	
	private static ArrayList<Integer> insertionSort(int... ints) {
	    int n = ints.length;
	    for (int i = 1; i < n; i++) {
	        int key = ints[i];
	        int j = i - 1;
	        while (j >= 0 && ints[j] > key) {
	            ints[j + 1] = ints[j];
	            j--;
	        }
	        ints[j + 1] = key;
	    }
	    ArrayList<Integer> list = new ArrayList<>();
	    for(int a = 0; a < ints.length; a++) {
	    	list.add(ints[a]);
	    }
	    
	    return list;
	}

}
