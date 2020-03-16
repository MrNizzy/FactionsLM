package xyz.frostwolf.server.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import xyz.frostwolf.server.FactionsLM;

public class CommandEvents implements Listener{
	
	private FactionsLM plugin;
	
	public CommandEvents(FactionsLM plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void spawner(PlayerJoinEvent event) {
		Player jugador = event.getPlayer();
		FileConfiguration config = plugin.getConfig();
		
		
		if(config.contains("Config.Spawn.x")) {
			Location principal_location = new Location(
					plugin.getServer().getWorld(config.getString("Config.Spawn.world")),
					Double.valueOf(config.getString("Config.Spawn.x")), 
					Double.valueOf(config.getString("Config.Spawn.y")), 
					Double.valueOf(config.getString("Config.Spawn.z")), 
					Float.valueOf(config.getString("Config.Spawn.yaw")), 
					Float.valueOf(config.getString("Config.Spawn.pitch"))); // (Mundo,x,y,z,yaw,pitch)
																							// -268,5 77 -141,5
																							// -0,7 3.6
			jugador.teleport(principal_location);
		}
		
		String welcome = "Config.mensaje-bienvenida";
		
		if(config.getString(welcome).equals("true")) {
			String welcome_text = "Config.mensaje-bienvenida-texto";
			jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString(welcome_text).replaceAll("%player%", jugador.getName())));
			
		}
		
		return;
	}
	
}
