package xyz.frostwolf.server;

import java.io.File;

import org.bukkit.Bukkit;
//Colores
import org.bukkit.ChatColor;
//Libreria descripcion
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
//Libreria de JavaPlugin
import org.bukkit.plugin.java.JavaPlugin;

import xyz.frostwolf.server.commands.CommandGeneral;
import xyz.frostwolf.server.commands.CommandEvents;

public class FactionsLM extends JavaPlugin {

	public String rutaConfig;
	public String rutaMessages;
	PluginDescriptionFile PDFile = getDescription();
	public String version = PDFile.getVersion();
	public String nombre = ChatColor.GOLD + "[" + PDFile.getName() + "] ";

	public void onEnable() {

		registerConfig();
		registerMessages();
		asignacionComandos();
		registerEvents();
		Bukkit.getConsoleSender().sendMessage("-----------------------------------------------------");
		Bukkit.getConsoleSender().sendMessage(
				ChatColor.DARK_AQUA + "El plugin " + nombre + ChatColor.DARK_AQUA + " se activo correctamente");
		Bukkit.getConsoleSender().sendMessage("-----------------------------------------------------");
	}

	public void onDisable() {

		Bukkit.getConsoleSender().sendMessage(
				ChatColor.DARK_AQUA + "El plugin " + nombre + ChatColor.DARK_AQUA + " se desactivo correctamente");
	}
	
	public void asignacionComandos() {
		this.getCommand("FLM").setExecutor(new CommandGeneral(this));
	}
	
	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new CommandEvents(this), this);
	}
	
	public void registerConfig() {
		File config = new File(this.getDataFolder(),"config.yml");
		rutaConfig = config.getPath();
		
		if(!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
	
	public void registerMessages() {
		File config = new File(this.getDataFolder(),"messages.yml");
		rutaMessages = config.getPath();
		
		if(!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
}
