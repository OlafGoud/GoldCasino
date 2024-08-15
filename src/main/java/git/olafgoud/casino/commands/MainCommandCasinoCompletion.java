package git.olafgoud.casino.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class MainCommandCasinoCompletion implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// TODO Auto-generated method stub
		/*
		 * format:
		 	/factory <subcommand>
		 	 * createmachine
		 	 * addrecipe
		 	 * listrecipe
		 	 * addmachine
		 	 * deletemachine
		 	 * removemachine
		 	 * help
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		if (!(sender instanceof Player)) {
			return null;
		}
		
		
		if(args.length == 1) {
			List<String> list = new ArrayList<>();
			list.add("coinflip");
			list.add("roulette");
			list.add("steenpapierschaar");
			list.add("coin");
			list.add("addcoin");
			
			if(args[0] == "") {
				return list;
			}
			
			List<String> list2 = new ArrayList<>();
			list2.addAll(list);
			
			for(String string : list) {
				if(!string.startsWith(args[0])) {
					list2.remove(string);
				}
			}
			
			return list2;
		}
		return null;
	}
}
