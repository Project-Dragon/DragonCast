package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import AddonManager.DragonPlugin;
import CMS.CMS;
import DragonCore.DragonCore;
import GroupManager.GroupManager;
import Messager.NoGapMessager;
import Messager.TickerMessager;
import Utils.General;

public class Main extends DragonPlugin implements Listener {
	public static Main pl;
	public void onLoad(){
		pl = this;
		continueLoad();
	}
	
	CMS cms = null;
	GroupManager gm;
	public static HashMap<UUID, String> CM = new HashMap<UUID, String>();
	public HashMap<Player, Integer> msg = new HashMap<Player, Integer>();
	public static ArrayList<Player> disabled = new ArrayList<Player>();
    public static Permission perms = null;
	
	public void continueLoad(){
		setupPermissions();
		cms = new CMS();
		gm = new GroupManager();
		if(cms.getChannel("Default") == null){
			cms.addChannel("Default");
			gm.addGroup("Default");
		}
		registerEvent(this);
		for(Player p : Bukkit.getOnlinePlayers()){
			General.displayDragonTextBar(DragonCore.plugin, "§aLoading ...", p, 10, 100);

			String group = perms.getPrimaryGroup(p);
			String channel = gm.getGroupChannel(group);
			if(channel == null){
				CM.put(p.getUniqueId(), "Default");
			}else{
				CM.put(p.getUniqueId(), channel);
			}
			
			
			
			
				if(cms.getChannelBoolean(CM.get(p.getUniqueId()), "Ticker")){
					int Task = Bukkit.getScheduler().scheduleSyncRepeatingTask(DragonCore.plugin , new TickerMessager(this, p, cms), 0*20, cms.getChannelInt(CM.get(p.getUniqueId()), "Tickerspeed"));	
					msg.put(p, Task);
					return;
				}
				if(cms.getChannelInt(CM.get(p.getUniqueId()), "Delay") == 0){
					int Task = Bukkit.getScheduler().scheduleSyncRepeatingTask(DragonCore.plugin,new NoGapMessager(cms, this, p), 0* 20,15*10);
					msg.put(p, Task);
					return;
					}
		}
	}
	
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = DragonCore.plugin.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    
    
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		General.displayDragonTextBar(DragonCore.plugin, "§aLoading ...", e.getPlayer(), 10, 100);
		Player p  = e.getPlayer();
		String group = perms.getPrimaryGroup(p);
		String channel = gm.getGroupChannel(group);
		if(channel == null){
			CM.put(p.getUniqueId(), "Default");
		}else{
			CM.put(p.getUniqueId(), channel);
		}
		
		
		
		
			if(cms.getChannelBoolean(CM.get(p.getUniqueId()), "Ticker")){
				int Task = Bukkit.getScheduler().scheduleSyncRepeatingTask(DragonCore.plugin, new TickerMessager(this, p, cms), 0*20, cms.getChannelInt(CM.get(p.getUniqueId()), "Tickerspeed"));	
				msg.put(p, Task);
				return;
			}
			if(cms.getChannelInt(CM.get(p.getUniqueId()), "Delay") == 0){
				int Task = Bukkit.getScheduler().scheduleSyncRepeatingTask(DragonCore.plugin,new NoGapMessager(cms, this, p), 0* 20,15*10);
				msg.put(p, Task);
				return;
				}
	}
	
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e){
		Player p = e.getPlayer();
		General.removeBar(p);
		General.displayDragonTextBar(DragonCore.plugin,"§aLoading ..." , p, 10, 100);
	}


public boolean onCommand(CommandSender sender, Command cmd, String label,
		String[] args) {
	if(sender.hasPermission("dc.commands")){
	if (cmd.getName().equalsIgnoreCase("dc")) {
		Player p = (Player) sender;
	    if (args.length == 0){

			sender.sendMessage("§7=====§6DragonCast§7=====§r");
			p.sendMessage("§3/dc CMS §7: §aDisplays the help for all CMS commands");
			p.sendMessage("§3/dc Config §7: §aDisplays the help for all config commands");
			p.sendMessage("§3/dc System §7: §aDisplays the help for all System commands.");
			return true;
	    }
	    if(args[0].equalsIgnoreCase("Config")){
			sender.sendMessage("§7=====§6DragonCast§7=====§r");
			p.sendMessage("§3/dc add <Channel> [Message]§7: §aAdd's a Message to specified Channel. If no Channel is specified the actual one will used");
			p.sendMessage("§3/dc remove <Channel> [Number] §7: §aRemove a message");
			p.sendMessage("§3/dc removeworld <Channel> [Number] §7: §aRemove a world");
			p.sendMessage("§3/dc list <Channel> [Messages/Worlds] §7: §aView all messages/worlds for a Channel");
			p.sendMessage("§3/dc set <Channel> [ConfigNode] [Value]§7: §aEdit the Config");
			p.sendMessage("§3/dc view <Channel> [ConfigNode] §7: §aView a ConfigSetting");
			p.sendMessage("§a The fields marked with [ ] are required");
			return true;
	    }
	    if(args[0].equalsIgnoreCase("CMS")){
			sender.sendMessage("§7=====§6DragonCast§7=====§r");
			p.sendMessage("§3/dc create [ChannelName]§7: §aCreate a new channel");
			p.sendMessage("§3/dc delete [ChannelName] §7: §aDelete a Channel");
			p.sendMessage("§3/dc switch [ChannelName] §7: §aDelete a Channel");
			p.sendMessage("§a The fields marked with [ ] are required");
			return true;
	    }
	    if(args[0].equalsIgnoreCase("System")){
			sender.sendMessage("§7=====§6DragonCast§7=====§r");
			p.sendMessage("§3/dc update §7: §aUpdates the Plugin.");
			p.sendMessage("§3/dc check §7: §aCheck the Plugin and Config.");
			p.sendMessage("§3/dc sendData §7: §aSends Error Data to Developer.");
			p.sendMessage("§3/dc debug §7: §cFOR DEVELOPER ONLY.");
			return true;
	    }      
	  
	    if(args[0].equalsIgnoreCase("list")){
	    	try{
	    	if(args.length == 2){
	    		if(cms.getChannel(args[1]) == null){
		    		@SuppressWarnings("unchecked")
					List<String> m = (List<String>) cms.getChannelData(CM.get(p.getUniqueId()), "Messages");
		    		p.sendMessage("§aMessages:");
		    		int Counter = 1;
		    		for(String me : m){
		    			me = m.get(Counter);
		    			p.sendMessage("§a" + Counter + ". §r" + me);
		    			Counter ++;
		    		}
		    		Counter = 0;
		    		p.sendMessage("§aWorlds");
		    		@SuppressWarnings("unchecked")
					List<String> w = (List<String>) cms.getChannelData(CM.get(p.getUniqueId()), "Worlds");
		    		for(String we : w){
		    			we = w.get(Counter);
		    			p.sendMessage("§a" + Counter + ". §c" + we);
		    			Counter ++;
		    		}
		    		return true;
		    		
		    	}
		    	if(cms.getChannel(args[1]) != null){
		    		@SuppressWarnings("unchecked")
					List<String> m = (List<String>) cms.getChannelData(args[1], "Messages");
		    		p.sendMessage("§aMessages:");
		    		int Counter = 1;
		    		for(String me : m){
		    			me = m.get(Counter);
		    			p.sendMessage("§a" + Counter + ". §r" + me);
		    			Counter ++;
		    		}
		    		Counter = 1;
		    		p.sendMessage("§aWorlds");
		    		@SuppressWarnings("unchecked")
					List<String> w = (List<String>) cms.getChannelData(args[1], "Worlds");
		    		for(String we : w){
		    			we = w.get(Counter);
		    			p.sendMessage("§a" + Counter + ". §c" + we);
		    			Counter ++;
		    		}
		    		return true;
		    	}
	    	}
	    	@SuppressWarnings("unchecked")
			List<String> m = (List<String>) cms.getChannelData(CM.get(p.getUniqueId()), "Messages");
    		p.sendMessage("§aMessages:");
    		int Counter = 1;
    		for(String me : m){
    			me = m.get(Counter);
    			p.sendMessage("§a" + Counter + ". §r" + me);
    			Counter ++;
    		}
    		Counter = 1;
    		p.sendMessage("§aWorlds");
    		@SuppressWarnings("unchecked")
			List<String> w = (List<String>) cms.getChannelData(CM.get(p.getUniqueId()), "Worlds");
    		for(String we : w){
    			we = w.get(Counter);
    			p.sendMessage("§a" + Counter + ". §c" + we);
    			Counter ++;
    		}
    		return true;
    		
	    	
	    }catch (Exception e){
	    	p.sendMessage("§cThere is some error. Check your command");
	    }
	    }
	    if(args[0].equalsIgnoreCase("removeworld")){
	    	try{
	    	if(cms.getChannel(args[1]) == null){
	    	cms.removeWorld(CM.get(p.getUniqueId()), args[1]);
	    	}
	    	if(cms.getChannel(args[1]) != null){
	    	cms.removeWorld(args[1], args[2]);
	    	}	   
	    	}catch (Exception e){
		    	p.sendMessage("§cThere is some error. Check your command");
		    }
	    }
	    if(args[0].equalsIgnoreCase("remove")){
	    	try{
	    	if(cms.getChannel(args[1]) == null){
	    		int c = Integer.valueOf(args[1]);
	    		c --;
	    	cms.removeMessage(CM.get(p.getUniqueId()), String.valueOf(c));
	    	p.sendMessage("§aMessage removed");
	    	}
	    	if(cms.getChannel(args[1]) != null){
	    		int c = Integer.valueOf(args[2]);
	    		c --;
	    	cms.removeMessage(args[1], String.valueOf(c));
	    	p.sendMessage("§aMessage removed");
	    	}
		    }catch (Exception e){
		    	p.sendMessage("§cThere is some error. Check your command");
		    }
	    }
	    	
	    if(args[0].equalsIgnoreCase("view")){
try{
	    	if(cms.getChannel(args[1]) == null){
	    		p.sendMessage("§aValue: §c" + cms.getChannelData(CM.get(p.getUniqueId()), args[1]));
	    		return true;
	    	}
	    	if(cms.getChannel(args[1]) != null ){
	    		p.sendMessage("§aValue: §c" + cms.getChannelData(args[1], args[2]));
	    		return true;
	    	}
}catch (Exception e){
	p.sendMessage("§cThere is some error. Check your command");
}
	    } 
	    if(args[0].equalsIgnoreCase("set")){
try{
	    	if(cms.getChannel(args[1]) == null){
	    		cms.SetChannelData(CM.get(p.getUniqueId()), args[1], args[2]);
	    		p.sendMessage("§aNode Set");
	    		return true;
	    	}
	    	if(cms.getChannel(args[1]) != null ){
	    		cms.SetChannelData(args[1], args[2], args[3]);
	    		p.sendMessage("§aNode Set");
	    		return true;
	    	}
}catch (Exception e){
	p.sendMessage("§cThere is some error. Check your command");
}
	    }
	    if(args[0].equalsIgnoreCase("add")){
	    	try{
	    	if(cms.getChannel(args[1]) == null){
	    		String message = args[1];
	              if (args.length > 2) {
	                for (int i = 2; i < args.length; i++) {
	                  message = message + " " + args[i];
	                }
	              }
	              cms.addMessage(CM.get(p.getUniqueId()), message);
	              sender.sendMessage("§aMessage successfully added!");
	              return true;
	    	}
	    	if(cms.getChannel(args[1]) != null){
	    		String message = args[2];
	              if (args.length > 2) {
	                for (int i = 2; i < args.length; i++) {
	                  message = message + " " + args[i];
	                }
	              }
	              cms.addMessage(args[1], message);
	              sender.sendMessage("§aMessage successfully added!");
	              return true;
	    	}
		    }catch (Exception e){
		    	p.sendMessage("§cThere is some error. Check your command");
		    }
	    }
	    if(args[0].equalsIgnoreCase("switch")){
	    	try{
	    	if(cms.getChannel(args[1]) != null ){
	    		cms.SwitchChannel(p, args[1]);
	    		p.sendMessage("§aChannel Switched");
	    		return true;
	    	}
		    }catch (Exception e){
		    	p.sendMessage("§cThere is some error. Check your command");
		    }
	    }
	    
	    if(args[0].equalsIgnoreCase("create")){
	    	try{
	    		if(cms.getChannel(args[1]) == null){
	    			cms.addChannel(args[1]);
		    		p.sendMessage("§aChannel Created");
		    		return true;
	    		}
		    }catch (Exception e){
		    	p.sendMessage("§cThere is some error. Check your command");
		    }
	    	}
	    if(args[0].equalsIgnoreCase("delete")){
	    	try{
    		if(cms.getChannel(args[1]) != null){
    			cms.DeleteChannel(args[1]);
	    		p.sendMessage("§aChannel Removed");
	    		
	    		return true;
	    		
    		}
		    }catch (Exception e){
		    	p.sendMessage("§cThere is some error. Check your command");
		    }
    	}
	}
	
	    }
	if (cmd.getName().equalsIgnoreCase("dc")) {
		
	    if (args.length == 0){
			return true;
	    }
		if(args[0].equalsIgnoreCase("enable")){
	    	disabled.remove(((Player)sender));
	    	sender.sendMessage("§aSuccessfully enabled DragonCast!");
	    }     
	    if(args[0].equalsIgnoreCase("disable")){
	    	disabled.add(((Player)sender));
	    	sender.sendMessage("§aSuccessfully disabled DragonCast!");
	    }
	    
	}
	  
	return true;
}

public void permJoin(Player p){
	for(String s : cms.channelList()){
		p.sendMessage(s);
		if(p.hasPermission("dc.Channel." + s)){
			CM.put(p.getUniqueId(), s);
		}
	}
}

@EventHandler
public void onDeath(PlayerRespawnEvent e){
	final Player p = e.getPlayer();
	Bukkit.getScheduler().scheduleSyncDelayedTask(DragonCore.plugin, new Runnable(){

		@Override
		public void run() {
			General.displayDragonTextBar(DragonCore.plugin, "§aLoading ...", p, 10, 100);

			String group = perms.getPrimaryGroup(p);
			String channel = gm.getGroupChannel(group);
			if(channel == null){
				CM.put(p.getUniqueId(), "Default");
			}else{
				CM.put(p.getUniqueId(), channel);
			}
			
			
			
			
				if(cms.getChannelBoolean(CM.get(p.getUniqueId()), "Ticker")){
					Bukkit.getScheduler().scheduleSyncRepeatingTask(DragonCore.plugin, new TickerMessager(pl, p, cms), 0*20, cms.getChannelInt(CM.get(p.getUniqueId()), "Tickerspeed"));		
					return;
				}
				if(cms.getChannelInt(CM.get(p.getUniqueId()), "Delay") == 0){
					Bukkit.getScheduler().scheduleSyncRepeatingTask(DragonCore.plugin,new NoGapMessager(cms, pl, p), 0* 20,cms.getChannelInt(CM.get(p.getUniqueId()), "Stay")* 20);
					return;
					}
		}
		
		
	});
}

@EventHandler
public void onDeath(PlayerDeathEvent e){
Bukkit.getScheduler().cancelTask(msg.get(e.getEntity()));
}

public static Main getInstance(){
	return pl;
}
}