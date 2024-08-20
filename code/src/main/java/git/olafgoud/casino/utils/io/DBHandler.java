package git.olafgoud.casino.utils.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.ChatColor;

import git.olafgoud.casino.CasinoMain;

public class DBHandler {
	
	private static CasinoMain server = new CasinoMain();
	private static String url = "jdbc:mysql://localhost/stat_tracker";
	
	private static String user = "root";
	private static String password = "";

	
	public static boolean dataBaseConnection() {
			
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
			String sqlQuerry = "CREATE TABLE IF NOT EXISTS roulette_player_stats(name int, input int, output int, data DATE)";
			statement.execute(sqlQuerry);
			statement.close();
			databaseConnection.close();
		} catch (SQLException e) {
			server.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Casino] Something went wrong with creating table of roulette");
			return false;

		}
		return true;
		
	}
	
	public static void addRouletteValues() {
		Connection databaseConnection = null;
		try {
			databaseConnection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			server.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Casino] Cann't connect to database");
			return;
		}
		try {
			Statement statement = databaseConnection.createStatement();
			String sqlQuerry = "CREATE TABLE IF NOT EXISTS roulette_player_stats(name int, input int, output int, data DATE)";
			statement.execute(sqlQuerry);
			statement.close();
			databaseConnection.close();
		} catch (SQLException e) {
			server.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Casino] Something went wrong with creating table of roulette");
			return;
		}
		return;
	}
	
}
