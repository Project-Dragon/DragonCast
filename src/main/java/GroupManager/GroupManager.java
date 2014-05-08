package GroupManager;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.Main;

public class GroupManager {
	FileConfiguration cfg;
	File file;
	
	public GroupManager(){
		file = new File(Main.getInstance().getDatafolder(), "Groups.dc");
		load();
		saveConf();	
	}
	
	public void load() { 
		if (!file.exists()) {
			try {
					file.createNewFile();
		} catch (IOException ex) {
		}
		}
		this.file = new File(Main.getInstance().getDatafolder(), "Groups.dc");
		this.cfg = YamlConfiguration.loadConfiguration(file);
		}
		
	
	
 public void saveConf() {
	 try {
		 this.cfg.save(file);
		   	} catch (IOException ex) {
		   	}
		 }
 
 public void addGroup(String name) {
	 load();
	 String c = "Groups.";
	 cfg.set(c + name , name);
	saveConf();
} 		  

public String getGroupChannel(String Group){
	load();
	 String c = "Groups.";
	
	return (String) cfg.get(c + Group);
}
public Set<String> channelList(){
	Set<String> list = cfg.getConfigurationSection("Groups").getKeys(false);
	return list;
}
}
