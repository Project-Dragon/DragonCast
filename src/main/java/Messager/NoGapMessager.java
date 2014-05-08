package Messager;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import CMS.CMS;
import DragonCore.DragonCore;
import Main.Main;
import Utils.Countdown;
import Utils.General;
import Utils.Utils;

public class NoGapMessager implements Runnable {
	public static boolean Broadcast = false;
	CMS cms;
	Main plugin;
	Player p;
	
	
	public NoGapMessager(CMS cms, Main plugin, Player pl){
		this.cms = cms;
		this.plugin = plugin;
		this.p = pl;
	}
	Utils u = new Utils();
	HashMap<UUID, String> CM = Main.CM;
	int Counter = 0;
	public void run() {
		  if(Main.disabled.contains(p)){
			  return;
		  }
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) cms.getChannelData(CM.get(p.getUniqueId()) , "Messages");
		@SuppressWarnings("unchecked")
		List<String> Worlds = (List<String>) cms.getChannelData(CM.get(p.getUniqueId()) , "Worlds");
		if(messages.size() == 0){
			General.setStatus(p, "§cNo messages set!", 100);
		}
		if(cms.getChannelBoolean(CM.get(p.getUniqueId()), "PerWorld") == true){
			if(Worlds.contains(p.getWorld().getName())){
				
			}else{
				return;
			}
		}
		
		
		try{
		String m = messages.get(Counter);
		if(cms.getChannelBoolean(CM.get(p.getUniqueId()), "Countdown") == true){
			Countdown c = new Countdown(plugin,p,15*4, m);
			int Task = Bukkit.getScheduler().scheduleSyncRepeatingTask(DragonCore.plugin, new Countdown(plugin, p, 15*4, messages.get(Counter)), 0, 1);
			c.setId(Task);
		}else{
			m = u.colorize(m);
			m = u.Variables(m, p, plugin);
			General.setStatus(p, m, 100);
		}
		Counter ++;
	}catch(Exception e){
		Counter = 0;
		String m = messages.get(Counter);
		if(cms.getChannelBoolean(CM.get(p.getUniqueId()), "Countdown") == true){
			Countdown c = new Countdown(plugin,p,15*4, m);
			int Task = Bukkit.getScheduler().scheduleSyncRepeatingTask(DragonCore.plugin, new Countdown(plugin, p, 15*4, messages.get(Counter)), 0, 1);
			c.setId(Task);
		}else{
			m = u.colorize(m);
			m = u.Variables(m, p, plugin);
			General.setStatus(p, m, 100);
		}
		Counter ++;
	}

	}
}
