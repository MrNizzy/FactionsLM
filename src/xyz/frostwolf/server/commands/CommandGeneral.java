package xyz.frostwolf.server.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import xyz.frostwolf.server.FactionsLM;

public class CommandGeneral implements CommandExecutor {

	private FactionsLM plugin;

	public CommandGeneral(FactionsLM plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {

		FileConfiguration config = plugin.getConfig();
		FileConfiguration messages = plugin.getMessages();
		if (!(sender instanceof Player)) {
			if (args[0].equalsIgnoreCase("reload")) {
				// Recargar plugin
				plugin.reloadConfig();
				Bukkit.getConsoleSender().sendMessage(plugin.nombre + " "+ messages.getString("Messages.t-reload-console"));
			}else {
				Bukkit.getConsoleSender().sendMessage(plugin.nombre + " "+ messages.getString("Messages.t-console-exist"));
			}
			
			
			return false;
		} else {
			Player jugador = (Player) sender;
			if (args.length > 0) {
				// Version del plugin
				if (args[0].equalsIgnoreCase("version")) {
					jugador.sendMessage(
							plugin.nombre + ChatColor.GREEN + " Version: " + ChatColor.GOLD + plugin.version);
					return true;
				}
				// Ver comandos en ayuda
				else if (args[0].equalsIgnoreCase("help")) {
					
					jugador.sendMessage(plugin.nombre + ChatColor.translateAlternateColorCodes('&',
							messages.getString("Messages.t-help").replaceAll("%player%", jugador.getName())));
					
					jugador.sendMessage(ChatColor.GOLD+"/flm version " + ChatColor.translateAlternateColorCodes('&',
							messages.getString("Messages.t-help-version").replaceAll("%player%", jugador.getName())));
					
					jugador.sendMessage(ChatColor.GOLD+"/flm reload " + ChatColor.translateAlternateColorCodes('&',
							messages.getString("Messages.t-help-reload").replaceAll("%player%", jugador.getName())));
					
					jugador.sendMessage(ChatColor.GOLD+"/flm setspawn " + ChatColor.translateAlternateColorCodes('&',
							messages.getString("Messages.t-help-setspawn").replaceAll("%player%", jugador.getName())));
					
					jugador.sendMessage(ChatColor.GOLD+"/flm spawn " + ChatColor.translateAlternateColorCodes('&',
							messages.getString("Messages.t-help-spawn").replaceAll("%player%", jugador.getName())));
					return true;
				}
				// Teleportarse al spawn
				else if (args[0].equalsIgnoreCase("spawn")) {


					if (config.contains("Config.Spawn.x")) {
						Location principal_location = new Location(
								plugin.getServer().getWorld(config.getString("Config.Spawn.world")),
								Double.valueOf(config.getString("Config.Spawn.x")),
								Double.valueOf(config.getString("Config.Spawn.y")),
								Double.valueOf(config.getString("Config.Spawn.z")),
								Float.valueOf(config.getString("Config.Spawn.yaw")),
								Float.valueOf(config.getString("Config.Spawn.pitch"))); // (Mundo,x,y,z,yaw,pitch)
																						// -268,5 77 -141,5
																						// -0,7 3.6
						jugador.sendMessage(plugin.nombre + ChatColor.translateAlternateColorCodes('&',
								messages.getString("Messages.t-spawn").replaceAll("%player%", jugador.getName())));
						jugador.teleport(principal_location);
					} else {
						jugador.sendMessage(plugin.nombre + ChatColor.translateAlternateColorCodes('&',
								messages.getString("Messages.t-spawn-exist").replaceAll("%player%", jugador.getName())));
						return true;
					}

					return true;
				} else if (args[0].equalsIgnoreCase("setspawn")) {
					Location principal_location = jugador.getLocation();

					config.set("Config.Spawn.x", principal_location.getX());
					config.set("Config.Spawn.y", principal_location.getY());
					config.set("Config.Spawn.z", principal_location.getZ());
					config.set("Config.Spawn.yaw", principal_location.getYaw());
					config.set("Config.Spawn.pitch", principal_location.getPitch());
					config.set("Config.Spawn.world", principal_location.getWorld().getName());
					plugin.saveConfig();
					jugador.sendMessage(plugin.nombre + ChatColor.translateAlternateColorCodes('&',
							messages.getString("Messages.t-setspawn").replaceAll("%player%", jugador.getName())));

					return true;
				} else if (args[0].equalsIgnoreCase("reload")) {
					// Recargar plugin
					plugin.reloadConfig();
					jugador.sendMessage(plugin.nombre + ChatColor.translateAlternateColorCodes('&',
							messages.getString("Messages.t-reload").replaceAll("%player%", jugador.getName())));
					return true;
				} else {
					jugador.sendMessage(plugin.nombre + ChatColor.translateAlternateColorCodes('&',
							messages.getString("Messages.t-exist").replaceAll("%player%", jugador.getName())));
					return true;
				}
			} else {
				jugador.sendMessage(plugin.nombre + ChatColor.translateAlternateColorCodes('&',
						messages.getString("Messages.t-none").replaceAll("%player%", jugador.getName())));
				return true;

			}
		}

	}

}
