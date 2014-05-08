package CMS;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import Main.Main;

public class CMS {
	FileConfiguration cfg;
	File file;
	
	public CMS(){
		file = new File(Main.getInstance().getDatafolder(), "Channels.dc");
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
		this.file = new File(Main.getInstance().getDatafolder(), "Channels.dc");
		this.cfg = YamlConfiguration.loadConfiguration(file);
		}
		
	
	
 public void saveConf() {
	 try {
		 this.cfg.save(file);
		   	} catch (IOException ex) {
		   	}
		 }
		  
		  
		  
		 
 public void addChannel(String name) {
	 String c = "Channels.";
	 cfg.set("Channels." + name + ".Name", name);
	 cfg.set("Channels." + name + ".Messages", new String[]{
			 "&aYou successfully installed DragonCast!",
			 "&cThis is the new anwesome Ticker feature",
			 "&bType in /dc and see what's in"
	 });
	 cfg.set(c + name + ".Countdown", true);
	 cfg.set(c + name + ".Ticker", true);
	 cfg.set(c + name + ".Tickerspeed", 1);
	 cfg.set(c + name + ".Tickerlenght", 400);
	 cfg.set(c + name + ".Delay", 0);
	 cfg.set(c + name + ".Stay", 10);
	 cfg.set(c + name + ".PerWorld", false);
	 cfg.set(c + name + ".Worlds", new String[]{
			 "World 1",
			 "World 2"
	 });
	saveConf();
} 
 
public String getChannel(String name){
	load();
	 String c = "Channels.";
	
	return (String) cfg.get(c + name + ".Name");
}

public Object getChannelData(String Channel, Object ChannelData){
	load();
	 String c = "Channels.";
	
	return cfg.get(c + Channel + "." + ChannelData );
}
public Integer getChannelInt(String Channel, Object ChannelData){
	load();
	 String c = "Channels.";
	
	return (Integer) cfg.get(c + Channel + "." + ChannelData );
}

public boolean getChannelBoolean(String Channel, Object ChannelData){
	load();
	 String c = "Channels.";
	
	return cfg.getBoolean(c + Channel + "." + ChannelData );
}

public void SwitchChannel(Player p, String name){
	load();
	Main.CM.remove(p.getUniqueId());
	Main.CM.put(p.getUniqueId(), name);
}

public void DeleteChannel(String name){
	load();
cfg.set("Channels." + name, null);
saveConf();
}

public void addMessage(String Channel, String Message){
	load();
 @SuppressWarnings("unchecked")
List<String> m = (List<String>) getChannelData(Channel, "Messages");
 m.add(Message);
 cfg.set("Channels." + Channel + ".Messages", m);
 saveConf();
}

public void addWorld(String Channel, String World){
	load();
 @SuppressWarnings("unchecked")
List<String> m = (List<String>) getChannelData(Channel, "Worlds");
 m.add(World);
 cfg.set("Channels." + Channel + ".Worlds", m);
 saveConf();
}

public void removeMessage(String Channel, String Message){
	load();
 @SuppressWarnings("unchecked")
List<String> m = (List<String>) getChannelData(Channel, "Messages");
 int num = Integer.parseInt(Message);
m.remove(num);
 cfg.set("Channels." + Channel + ".Messages", m);
 saveConf();
}


public void removeWorld(String Channel, String World){
	load();
 @SuppressWarnings("unchecked")
List<String> m = (List<String>) getChannelData(Channel, "Worlds");
 int num = Integer.parseInt(World);
m.remove(num);
 cfg.set("Channels." + Channel + ".Worlds", m);
 saveConf();
}


public void SetChannelData(String Channel, Object ChannelData, String Value){
	load();
	String c = "Channels.";
	if(cfg.get(c + Channel + "." + ChannelData) instanceof Boolean){
		cfg.set(c + Channel + "." + ChannelData, Boolean.valueOf(Value));
		saveConf();
		return;
	}
	if(cfg.get(c + Channel + "." + ChannelData) instanceof Integer){
		cfg.set(c + Channel + "." + ChannelData, Integer.valueOf(Value));
		saveConf();
		return;
	}
	cfg.set(c + Channel + "." + ChannelData, Value);
	saveConf();
}

public Set<String> channelList(){
	Set<String> list = cfg.getConfigurationSection("Channels").getKeys(false);
	return list;
}
}
