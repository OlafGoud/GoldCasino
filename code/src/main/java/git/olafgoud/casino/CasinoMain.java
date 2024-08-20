package git.olafgoud.casino;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import git.olafgoud.casino.commands.MainCommandCasino;
import git.olafgoud.casino.commands.MainCommandCasinoCompletion;
import git.olafgoud.casino.games.roulette.Roulette;
import git.olafgoud.casino.listener.ChatListener;
import git.olafgoud.casino.listener.InventoryClick;
import git.olafgoud.casino.listener.InventoryClose;
import git.olafgoud.casino.steenpapierschaar.SPSQueeScreen;
import git.olafgoud.casino.utils.io.DBHandler;
import git.olafgoud.casino.utils.io.MainConfig;

public class CasinoMain extends JavaPlugin{

	public static HashMap<Integer, ItemStack> listOfCoins = new HashMap<>(); //value, coin
	private static Plugin plugin;
	private static boolean dbEnabled;
	
	public void onEnable() {
		plugin = this;
		
		dbEnabled = DBHandler.dataBaseConnection();
		
		
		
		//config file
		MainConfig.reloadConfig();
	
		//events
		getServer().getPluginManager().registerEvents(new InventoryClick(), this);
		getServer().getPluginManager().registerEvents(new InventoryClose(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);

		// main command
		getCommand("casino").setExecutor(new MainCommandCasino());
		getCommand("casino").setTabCompleter(new MainCommandCasinoCompletion());
		
		
		//setting all the arrays for the roulette table
		Roulette.setNumberList();
		//init SPS lobby
		SPSQueeScreen.enableSPSLobby();
	}

	public static Plugin getPlugin() {
		return plugin;
	}


	public static boolean isDbEnabled() {
		return dbEnabled;
	}

	public static void setDbEnabled(boolean dbEnabled) {
		CasinoMain.dbEnabled = dbEnabled;
	}


}
