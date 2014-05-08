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

public class ColorMessager implements Runnable {
	public static boolean Broadcast = false;
	CMS cms;
	Main plugin;
	Player p;
	
	public ColorMessager(CMS cms, Main plugin, Player p){
		this.cms = cms;
		this.plugin = plugin;
		this.p = p;
	}
	Utils u = new Utils();
	HashMap<UUID, String> CM = Main.CM;
	int Counter = 0;
	public void run() {
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) cms.getChannelData(CM.get(p.getUniqueId()) , "Messages");
		@SuppressWarnings("unchecked")
		List<String> Worlds = (List<String>) cms.getChannelData(CM.get(p.getUniqueId()) , "Worlds");
		
		if(cms.getChannelBoolean(CM.get(p.getUniqueId()), "PerWorld") == true){
			if(Worlds.contains(p.getWorld().getName())){
				
			}else{
				return;
			}
		}
		
		
		try{
		String m = messages.get(Counter);
		if(cms.getChannelBoolean(CM.get(p.getUniqueId()), "Countdown") == true){
			Countdown c = new Countdown(plugin,p,cms.getChannelInt(CM.get(p.getUniqueId()), "Stay"), m);
			int Task = Bukkit.getScheduler().scheduleSyncRepeatingTask(DragonCore.plugin, new Countdown(plugin, p, cms.getChannelInt(CM.get(p.getUniqueId()), "Stay"), messages.get(Counter)), 0, 1*20);
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
			Countdown c = new Countdown(plugin,p,cms.getChannelInt(CM.get(p.getUniqueId()), "Stay"), m);
			int Task = Bukkit.getScheduler().scheduleSyncRepeatingTask(DragonCore.plugin, new Countdown(plugin, p, cms.getChannelInt(CM.get(p.getUniqueId()), "Stay"), messages.get(Counter)), 0, 1*20);
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
