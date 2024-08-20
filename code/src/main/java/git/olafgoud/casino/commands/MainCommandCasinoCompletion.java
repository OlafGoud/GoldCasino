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
		
		//checking if the sender is a player
		if (!(sender instanceof Player)) {
			return null;
		}
		
		//checking for 1st argument
		if(args.length == 1) {
			//creating list
			List<String> list = new ArrayList<>();
			list.add("roulette");
			list.add("coin");
			list.add("addcoin");
			list.add("reloadconfig");
			list.add("steenpapierschaar");
			list.add("getstats");
			
			if(args[0] == "") {
				return list;
			}
			// autocompletion
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
