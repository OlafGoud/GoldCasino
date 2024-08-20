package git.olafgoud.casino.games.roulette;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import git.olafgoud.casino.CasinoMain;
import git.olafgoud.casino.utils.CasinoSlot;
import git.olafgoud.casino.utils.CreateItemStack;

public class Roulette {
	
	private boolean running;
	private static ArrayList<ItemStack> numberList = new ArrayList<>();
	private static ArrayList<Integer> redList = new ArrayList<>();
	private static ArrayList<Integer> blackList = new ArrayList<>();
	public ArrayList<CasinoSlot> rouletteSlots = new ArrayList<>();
	private static ArrayList<Integer> aantal1tot18 = new ArrayList<>();
	private static ArrayList<Integer> aantal19tot36 = new ArrayList<>();
	private static ArrayList<Integer> aantaleven = new ArrayList<>();
	private static ArrayList<Integer> aantaloneven = new ArrayList<>();
	private static ArrayList<Integer> aantal1tot12 = new ArrayList<>();
	private static ArrayList<Integer> aantal13tot24 = new ArrayList<>();
	private static ArrayList<Integer> aantal25tot36 = new ArrayList<>();
	public ArrayList<Integer> fullList = new ArrayList<>();
	public Integer pastNumber = 0;
	public Integer number = 0;
	public Inventory inventory;	
	
	public Roulette() {
		running = false;
		fullList.add(number);
		rouletteSlots.add(new CasinoSlot(13, "red", new ItemStack(Material.AIR), 2f, redList));
		rouletteSlots.add(new CasinoSlot(14, "1-18", new ItemStack(Material.AIR), 2f, aantal1tot18));
		rouletteSlots.add(new CasinoSlot(15, "even", new ItemStack(Material.AIR), 2f, aantaleven));
		rouletteSlots.add(new CasinoSlot(22, "black", new ItemStack(Material.AIR), 2f, blackList));
		rouletteSlots.add(new CasinoSlot(23, "19-36", new ItemStack(Material.AIR), 2f, aantal19tot36));
		rouletteSlots.add(new CasinoSlot(24, "oneven", new ItemStack(Material.AIR), 2f, aantaloneven));
		rouletteSlots.add(new CasinoSlot(40, "1-12", new ItemStack(Material.AIR), 3f, aantal1tot12));
		rouletteSlots.add(new CasinoSlot(41, "13-24", new ItemStack(Material.AIR), 3f, aantal13tot24));
		rouletteSlots.add(new CasinoSlot(42, "25-36", new ItemStack(Material.AIR), 3f, aantal25tot36));
		rouletteSlots.add(new CasinoSlot(52, "number", new ItemStack(Material.AIR), 36f, fullList));
		

		
		
		
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	
	public static ArrayList<ItemStack> getNumberlist() {
		return numberList;
	}

	public static void setNumberlist(ArrayList<ItemStack> numberlist) {
		numberList = numberlist;
	}
	
	public static void addNumberToList(ItemStack number) {
		numberList.add(number);
	}
	
	public void createInventory(RouletteMainScreenHolder holder) {
		inventory = getInventory(holder);

	}
	
	
	
	public static void setNumberList() {
		
		for(int i = 1; i < 19; i++) {
			aantal1tot18.add(i);
		}
		for(int i = 19; i < 37; i++) {
			aantal19tot36.add(i);
		}
		for(int i = 1; i < 13; i++) {
			aantal1tot12.add(i);
		}
		for(int i = 13; i < 25; i++) {
			aantal13tot24.add(i);
		}
		for(int i = 25; i < 37; i++) {
			aantal25tot36.add(i);
		}
		
		for(int i = 2; i < 37; i += 2) {
			aantaleven.add(i);
		}
		for(int i = 1; i < 36; i += 2) {
			aantaloneven.add(i);
		}
		
		
		
		
		
		
		
		Integer[] list1 = {1, 3, 5, 7, 9, 12, 14,16,18, 19,21,23,25,27,30,32,34,36};
		for(int i = 0; i < list1.length; i++) {
			redList.add(list1[i]);
		}
		Integer[] list2 = {2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,31,33,35};
		for(int i = 0; i < list2.length; i++) {
			blackList.add(list2[i]);
		}
		CreateItemStack red = new CreateItemStack(Material.WOOL, 14);
		CreateItemStack black = new CreateItemStack(Material.WOOL, 15);
		CreateItemStack green = new CreateItemStack(Material.WOOL, 13);
		for(int i = 0; i < 37; i++) {
			if(i == 0) {
				green.setName(ChatColor.GOLD + "> " + i + " <");
				addNumberToList(green.getItem().clone());
			} 
			if (redList.contains(i)) {
				red.setName(ChatColor.GOLD + "> " + i + " <");
				addNumberToList(red.getItem().clone());
			}
			if(blackList.contains(i)) {
				black.setName(ChatColor.GOLD + "> " + i + " <");
				addNumberToList(black.getItem().clone());
			}
		}
	}    
	
	public ArrayList<ItemStack> getNumbers(int i) {
		i -= 2;
		if(i < 0) {
			i += 37;
		}
		ArrayList<ItemStack> list = new ArrayList<>();
		for(int n = 0; n < 6; n++) {
			if(i + n > 36) {
				list.add(numberList.get(i + n - 37));
			} else {
				list.add(numberList.get(i + n));
			}
		}
		return list;
	}
	
	public Inventory getInventory(RouletteMainScreenHolder holder) {
		Inventory inv = Bukkit.createInventory(holder, 6 * 9, "Roulette");
		
		
		CreateItemStack createGlass = new CreateItemStack(Material.STAINED_GLASS_PANE, 15);
		createGlass.setName(ChatColor.GRAY + "[Roulette]");
		ItemStack glass = createGlass.getItem();
		for(int i = 0; i < 54; i++) {
			inv.setItem(i, glass);
		}
		
		
		CreateItemStack createLever = new CreateItemStack(Material.LEVER);
		createLever.setName(ChatColor.GREEN + "> Bet <");
		ItemStack lever = createLever.getItem();
		inv.setItem(29, lever);
		
		
		CreateItemStack createGlassGreen = new CreateItemStack(Material.STAINED_GLASS_PANE, 5);
		createGlassGreen.setName("&6Winning number");
		ItemStack glassGreen = createGlassGreen.getItem();
		inv.setItem(18, glassGreen);
		inv.setItem(20, glassGreen);
		
		ArrayList<ItemStack> numberList = holder.roulette.getNumbers(0);
		inv.setItem(1, numberList.get(0));
		inv.setItem(1 + 9, numberList.get(1));
		inv.setItem(1 + 18, numberList.get(2));
		inv.setItem(1 + 27, numberList.get(3));
		inv.setItem(1 + 36, numberList.get(4));
		inv.setItem(1 + 45, numberList.get(5));
		
		
		CreateItemStack createWoolRed = new CreateItemStack(Material.WOOL, 15);
		createWoolRed.setName(ChatColor.DARK_AQUA + "> Black <");
		ItemStack woolRed = createWoolRed.getItem();
		CreateItemStack createWoolBlack = new CreateItemStack(Material.WOOL, 14);
		createWoolBlack.setName(ChatColor.DARK_AQUA + "> Red <");
		ItemStack woolBlack = createWoolBlack.getItem();

		inv.setItem(13, woolBlack);
		inv.setItem(13 + 9, woolRed);
		
		
		CreateItemStack createPaper = new CreateItemStack(Material.PAPER);
		createPaper.setName(ChatColor.DARK_AQUA + "> 1-12 <");
		inv.setItem(40, createPaper.getItem());
		createPaper.setName(ChatColor.DARK_AQUA + "> 13-24 <");
		inv.setItem(41, createPaper.getItem());
		createPaper.setName(ChatColor.DARK_AQUA + "> 25-36 <");
		inv.setItem(42, createPaper.getItem());
		createPaper.setName(ChatColor.DARK_AQUA + "> 1-18 <");
		inv.setItem(14, createPaper.getItem());
		createPaper.setName(ChatColor.DARK_AQUA + "> 19-36 <");
		inv.setItem(14 + 9, createPaper.getItem());

		
		createPaper.setName(ChatColor.DARK_AQUA + "> Even <");
		inv.setItem(15, createPaper.getItem());
		createPaper.setName(ChatColor.DARK_AQUA + "> Oneven <");
		inv.setItem(15 + 9, createPaper.getItem());
		
		CreateItemStack paperCreate = new CreateItemStack(Material.PAPER);
		paperCreate.setName(ChatColor.DARK_AQUA + number.toString());
		paperCreate.setLore(ChatColor.DARK_AQUA + "Click here to change the number");
		inv.setItem(53, paperCreate.getItem());
		
		CreateItemStack createBarrier = new CreateItemStack(Material.BARRIER);
		createBarrier.setName(ChatColor.DARK_AQUA + "Single number bet place");
		createBarrier.setLore(ChatColor.DARK_AQUA + "This is the place for the single number bet");
		ItemStack barrier = createBarrier.getItem();
		inv.setItem(52, barrier);
		
		
		
		return inv;
		
		
	}
	
	public boolean checkForRouletteSlots(Integer integer) {
		for(CasinoSlot slot : rouletteSlots) {
			if (slot.getSlot().equals(integer)) {
				return true;
			}
		}
		return false;
	}
	
	public Integer checkForWin(Integer winningNumber) {
		Integer output = 0;
		for (CasinoSlot slot : rouletteSlots) {
			if(slot.getInput().getType().equals(Material.AIR)) {
				continue;
			}

			if (slot.getWinningNumbers().contains(winningNumber)) {
				for(Integer keys : CasinoMain.listOfCoins.keySet()) {
					ItemStack input = slot.getInput().clone();
					input.setAmount(1);
					if(input.equals(CasinoMain.listOfCoins.get(keys))) {
						output += (int) (keys * slot.getModifier() * slot.getInput().getAmount());
					}
				}
			} else {
				return 0;
			}
		}
		
		
		
		return output;
	}
	
	
	public Integer getInput(Integer winningNumber) {
		Integer output = 0;
		for (CasinoSlot slot : rouletteSlots) {
			if(slot.getInput().getType().equals(Material.AIR)) {
				continue;
			}

			for(Integer keys : CasinoMain.listOfCoins.keySet()) {
				ItemStack input = slot.getInput().clone();
				input.setAmount(1);
				if(input.equals(CasinoMain.listOfCoins.get(keys))) {
					output += (int) (keys * slot.getInput().getAmount());
				}
			}
		}
		
		
		
		return output;
	}
	
}
