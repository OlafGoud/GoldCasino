package git.olafgoud.casino.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import git.olafgoud.casino.CasinoMain;
import git.olafgoud.casino.games.roulette.RouletteMainScreen;
import git.olafgoud.casino.utils.config.MainConfig;

public class MainCommandCasino implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// TODO Auto-generated method stub
		
		//checking of a player has done the command
		if(!(sender instanceof Player)) {
			sender.sendMessage("You are not a player");
			return true;
		}
		Player p = (Player) sender; 
		
		//checking for length of the subcommands and else giving a help message
		try {
			if (args.length < 1) {
				sendMessageCasinoHelp(p);
				return true;
			}
		} catch (Exception e) {
			sendMessageCasinoHelp(p);
			return true;
		}
		
		//checking subcommand
		if (args[0].equals("roulette")) {
			//open roulette gui
			RouletteMainScreen.getScreen(p);
		} else if (args[0].equals("coin")) {
			//giving the user the coins that are used
			for (Integer value : CasinoMain.listOfCoins.keySet()) {
				p.getInventory().addItem(CasinoMain.listOfCoins.get(value));
			}
		} else if (args[0].equals("addcoin")) {
			//adding coins
			//checking for an item and value
			if(p.getInventory().getItemInMainHand() == null) {
				p.sendMessage(ChatColor.RED + "You must hold an item");
				return true;
			}
			if (args.length < 2) {
				p.sendMessage(ChatColor.RED + "You must give a value for the coin");
				return true;
			}			
			try{
				Integer i = Integer.valueOf(args[1]); 
				//checking if the value is already in the config. thats a problem because there can not be 2 the same values.
				if(CasinoMain.listOfCoins.containsKey(i)) {
					p.sendMessage(ChatColor.RED + "This value already exists");
					return false;
				}
				
				ItemStack coin = p.getInventory().getItemInMainHand().clone();
				coin.setAmount(1); //seting the amount to 1 for better handling
				
				CasinoMain.listOfCoins.put(i, coin); //adding the coins to the session storage
				MainConfig.saveCoins(); //saving session coins to config
				p.sendMessage(ChatColor.YELLOW + "Successfully added a coin");

			} catch(Exception a) {
				p.sendMessage(ChatColor.RED + "The value must be an intager");
				return true;
			}
			
		} else if (args[0].equals("reloadconfig")) {
			//reloading config
			MainConfig.reloadConfig();
			p.sendMessage(ChatColor.YELLOW + "Reload Complete");
		}
		
		return false;
	}
	
	private void sendMessageCasinoHelp(Player p) {
		//help menu
		p.sendMessage(ChatColor.GOLD + "-================================-");
		p.sendMessage(ChatColor.YELLOW + "Gold Casino");
		p.sendMessage(ChatColor.YELLOW + " - Made By: " + ChatColor.GOLD + "OlafGoud");
		p.sendMessage(ChatColor.YELLOW + "");
		p.sendMessage(ChatColor.YELLOW + "Commands:");
		p.sendMessage(ChatColor.GOLD+ " - /casino roulette");
		p.sendMessage(ChatColor.YELLOW + "   - Opens the menu for the roulette");
		p.sendMessage(ChatColor.GOLD + " - /casino coin");
		p.sendMessage(ChatColor.YELLOW + "   - Gives all the coins that are in the config");
		p.sendMessage(ChatColor.GOLD + " - /casino addcoin <value>");
		p.sendMessage(ChatColor.YELLOW + "   - Add a coin with a value to all the casino's");
		p.sendMessage(ChatColor.GOLD + " - /casino reloadconfig");
		p.sendMessage(ChatColor.YELLOW + "   - Reloads the config");
		p.sendMessage(ChatColor.GOLD + "-================================-");




	}

}
