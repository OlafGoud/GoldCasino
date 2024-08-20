package git.olafgoud.casino.utils.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import git.olafgoud.casino.CasinoMain;

public class DBHandler {
	
	private static org.bukkit.plugin.Plugin server = CasinoMain.getPlugin();
	private static String url = "jdbc:mysql://localhost/stat_tracker";
	
	private static String user = "root";
	private static String password = "";

	
	public static boolean dataBaseConnection() {
		//database setup
		Connection databaseConnection = null;
		try {
			databaseConnection = DriverManager.getConnection(url, user, password);
			
			server.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Casino] Connected to Database");
		} catch (SQLException e) {
			server.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Casino] Cann't connect to database");
			return false;
		}
		
		try {
			Statement statement = databaseConnection.createStatement();
			String sqlQuerry = "CREATE TABLE IF NOT EXISTS roulette_player_stats(name VARCHAR(20), input int, output int, data DATE)";
			statement.execute(sqlQuerry);
			statement.close();
			databaseConnection.close();
		} catch (SQLException e) {
			server.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Casino] Something went wrong with creating table of roulette");
			return false;

		}
		return true;
		
	}
	
	public static void addRouletteValues(String name, Integer inputAmount, Integer outputAmount) {
		//roulette bijhouden
        Bukkit.getScheduler().runTaskAsynchronously(CasinoMain.getPlugin(), new Runnable() {

		
			@Override
			public void run() {
				Connection databaseConnection = null;
				try {
					databaseConnection = DriverManager.getConnection(url, user, password);
				} catch (SQLException e) {
					server.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Casino] Cann't connect to database");
					return;
				}
				try {
					Statement statement = databaseConnection.createStatement();
					String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
					String sqlQuerry = "INSERT INTO roulette_player_stats (name, input, output, data) VALUES ('" + name + "', '" + inputAmount + "', '" + outputAmount + "', '" + timeStamp + "')";
					statement.execute(sqlQuerry.toString());
					statement.close();
					databaseConnection.close();
				} catch (SQLException e) {
					server.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Casino] Something went wrong with creating table of roulette");
					e.printStackTrace();
					return;
				}
				
			}
        });
        return;
	}

	public static void getRouletteStats(Player p) {
		// roulette results terugsturen
		Bukkit.getScheduler().runTaskAsynchronously(CasinoMain.getPlugin(), new Runnable() {

			
			@Override
			public void run() {
				Connection databaseConnection = null;
				try {
					databaseConnection = DriverManager.getConnection(url, user, password);
				} catch (SQLException e) {
					server.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Casino] Cann't connect to database");
					return;
				}
				try {
					Statement statement = databaseConnection.createStatement();
					String sqlQuerry = "SELECT name, SUM(input), SUM(output), data FROM roulette_player_stats GROUP BY name, data";
					ResultSet res = statement.executeQuery(sqlQuerry);
					p.sendMessage("Name, input, output, data");
					while (res.next()) {
						String result = "";
						result += res.getString(1) + ", ";
						result += res.getInt(2)+ ", ";
						result += res.getInt(3)+ ", ";
						result += res.getString(4)+ ", ";
						
						p.sendMessage(result);
					}
					statement.close();
					databaseConnection.close();
				} catch (SQLException e) {
					server.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Casino] Something went wrong with creating table of roulette");
					e.printStackTrace();
					return;
				}
				
			}
        });
	}
	
}
