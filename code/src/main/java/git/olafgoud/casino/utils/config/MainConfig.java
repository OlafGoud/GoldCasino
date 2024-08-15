package git.olafgoud.casino.utils.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import git.olafgoud.casino.CasinoMain;
import git.olafgoud.casino.utils.CreateItemStack;

public class MainConfig {

	public static void createConfigIfNotExists() {
		
		/*
		 * locaties in gui van inzetten
		 */
		
		File path = new File("plugins/GoldCasino");
		File file = new File("plugins/GoldCasino/CasinoConfig.yml");
		YamlConfiguration config;
		if (!file.exists()) {
			path.mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return;
		}
		config = YamlConfiguration.loadConfiguration(file);
		
		
		
		config.createSection("coins");
		
		for (int i = 0; i < 1; i++) {
		
			CreateItemStack coin = new CreateItemStack(Material.CLAY_BRICK);
			coin.setKey("casinocoin", "50");
			coin.setName("Coin");
			coin.setLore("This coin is 50 worth");
			
			
			config.getConfigurationSection("coins").set("Coin50", coin.getItem().serialize());
		}
		
		
		
		//roulette
		config.createSection("roulette");
		
		
		
		
		/*
		 * steenpapierschaar:
		 */
		
		config.createSection("steenpapierschaar");
		
		/*
		 * coinflip:
		 */
		
		config.createSection("coinflip");
		
		
		
		try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }	
		
	}
	
	public static void loadCoins() {
		File file = new File("plugins/GoldCasino/CasinoConfig.yml");
		YamlConfiguration config;
		config = YamlConfiguration.loadConfiguration(file);
		HashMap<Integer, ItemStack> list1 = new HashMap<>();
		ConfigurationSection coins = config.getConfigurationSection("coins");
		for(String item : coins.getKeys(false)) {
			try {
				ItemStack stack = ItemStack.deserialize(coins.getConfigurationSection(item).getValues(false));
				
				list1.put(Integer.valueOf(item.replace("Coin", "")), stack);
			} catch (Exception e) {
				System.out.println("Failed to add item: " + item);
			}
			
		}
		
		
		CasinoMain.listOfCoins = list1;
		
	
			
	
	}
	
	public static void saveCoins() {
		File file = new File("plugins/GoldCasino/CasinoConfig.yml");
		YamlConfiguration config;
		config = YamlConfiguration.loadConfiguration(file);
		for(Integer value : CasinoMain.listOfCoins.keySet()) {
			config.getConfigurationSection("coins").set("Coin" + value.toString(), CasinoMain.listOfCoins.get(value).serialize());
		}
		
		try {
			config.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void reloadConfig() {
		MainConfig.createConfigIfNotExists();
		MainConfig.loadCoins();

	}
	

	
}
