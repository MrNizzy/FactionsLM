package xyz.frostwolf.server.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class Chat implements Listener {

	@EventHandler
	public void ModifiedChat(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		String message = event.getMessage();
		
		event.setMessage(message);
		
		event.setFormat(ChatColor.translateAlternateColorCodes('&', "&l&a[User] &6"+player.getName()+":&f "+message+"."));
	}
}
