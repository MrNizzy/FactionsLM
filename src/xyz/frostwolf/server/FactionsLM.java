package xyz.frostwolf.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.Bukkit;
//Colores
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
//Libreria descripcion
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
//Libreria de JavaPlugin
import org.bukkit.plugin.java.JavaPlugin;

import xyz.frostwolf.server.commands.CommandGeneral;
import xyz.frostwolf.server.commands.CommandEvents;

public class FactionsLM extends JavaPlugin {
	
	private FileConfiguration messages = null;
	private File messagesFile = null;
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
	
	public FileConfiguration getMessages() {
		if(messages == null){
            reloadMessages();
        }
        return messages;
	}
	
	public void reloadMessages(){
        if(messages == null){
            messagesFile = new File(getDataFolder(),"messages.yml");
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        Reader defConfigStream;
        try{
            defConfigStream = new InputStreamReader(this.getResource("messages.yml"),"UTF8");
            if(defConfigStream != null){
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                messages.setDefaults(defConfig);
            }          
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }
   
    public void saveMessages(){
        try{
            messages.save(messagesFile);           
        }catch(IOException e){
            e.printStackTrace();
        }
    }
   
    public void registerMessages(){
        messagesFile = new File(this.getDataFolder(),"messages.yml");
        if(!messagesFile.exists()){
            this.getMessages().options().copyDefaults(true);
            saveMessages();
        }
    }
	
}
