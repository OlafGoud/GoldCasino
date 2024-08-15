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
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("You are not a player");
			return true;
		}
		Player p = (Player) sender; 
		try {
			if (args.length < 1) {
				p.sendMessage(ChatColor.RED + "for help: /casino help");
				return true;
			}
		} catch (Exception e) {
			p.sendMessage(ChatColor.RED + "for help: /casino help");
			return true;
		}
		
		if (args[0].equals("roulette")) {
			RouletteMainScreen.getScreen(p);
		} else if (args[0].equals("coin")) {
			for (Integer value : CasinoMain.listOfCoins.keySet()) {
				p.getInventory().addItem(CasinoMain.listOfCoins.get(value));
			}
		} else if (args[0].equals("addcoin")) {
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
				
				ItemStack coin = p.getInventory().getItemInMainHand().clone();
				coin.setAmount(1);
				
				CasinoMain.listOfCoins.put(i, coin);
				MainConfig.saveCoins();
			} catch(Exception a) {
				p.sendMessage(ChatColor.RED + "The value must be an intager");
				return true;
			}
			
		}
		
		return false;
	}

}
