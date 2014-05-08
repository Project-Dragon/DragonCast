package Messager;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import CMS.CMS;
import DragonCore.DragonCore;
import Main.Main;
import Utils.DelayCountdown;
import Utils.General;
import Utils.Utils;

public class Messager implements Runnable {
	private Main plugin;
	private Player p;
	boolean Delay = false;
	CMS cms;
	static boolean Broadcast = false;
	Utils u = new Utils();

	public Messager(Main plugin, Player p, CMS cms) {
		this.plugin = plugin;
		this.p = p;
		this.cms = cms;
	}

	private int Counter = 0;

	public void run() {
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) cms.getChannelData(Main.CM.get(p.getUniqueId()), "Messages");
		if (p != null) {
			try {
				if(Delay == true){
					if(cms.getChannelInt(Main.CM.get(p.getUniqueId()), "Delay") != 0){
						General.setStatus(p, " ", 0);
					Bukkit.getScheduler().scheduleSyncDelayedTask(DragonCore.plugin, new Runnable(){

						public void run() {
							Delay = false;
						}
						
					}, cms.getChannelInt(Main.CM.get(p.getUniqueId()), "Delay") * 20);
					return;
					}else{
						Delay = false;
					}
				}
				String message = messages.get(Counter);
				message = u.colorize(message);
				message = u.Variables(message, p, plugin);
				if(cms.getChannelBoolean(Main.CM.get(p.getUniqueId()), "Countdown") == true){
					DelayCountdown c = new DelayCountdown(plugin, p, cms.getChannelInt(Main.CM.get(p.getUniqueId()), "Stay"), messages.get(Counter));
					int Task = Bukkit.getScheduler().scheduleSyncRepeatingTask(DragonCore.plugin, new DelayCountdown(plugin, p, cms.getChannelInt(Main.CM.get(p.getUniqueId()), "Stay"), messages.get(Counter)), 0, 1*20);
					c.setId(Task);
				}else{
					General.setStatus(p, message, 100);
				}
				Counter++;
				Delay = true;
				
			} catch (IndexOutOfBoundsException exc) {
				if(Delay == true){
					if(cms.getChannelInt(Main.CM.get(p.getUniqueId()), "Delay") != 0){
						General.setStatus(p, " ", 0);
					Bukkit.getScheduler().scheduleSyncDelayedTask(DragonCore.plugin, new Runnable(){

						public void run() {
							Delay = false;
						}
						
					}, cms.getChannelInt(Main.CM.get(p.getUniqueId()), "Delay") * 20);
					return;
					}else{
						Delay = true;
					}
				}
				Counter = 0;
				String message = messages.get(Counter);
				message = u.colorize(message);
				message = u.Variables(message, p, plugin);
				if(cms.getChannelBoolean(Main.CM.get(p.getUniqueId()), "Countdown") == true){
					DelayCountdown c = new DelayCountdown(plugin, p, cms.getChannelInt(Main.CM.get(p.getUniqueId()), "Stay"), messages.get(Counter));
					int Task = Bukkit.getScheduler().scheduleSyncRepeatingTask(DragonCore.plugin, new DelayCountdown(plugin, p, cms.getChannelInt(Main.CM.get(p.getUniqueId()), "Stay"), messages.get(Counter)), 0, 1*20);
					c.setId(Task);
				}else{
					General.setStatus(p, message, 100);
				}
				Counter++;
				Delay = true;
			}
		} else {
			return;
		}
		}
}
