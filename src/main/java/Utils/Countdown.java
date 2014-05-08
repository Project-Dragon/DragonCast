package Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import Main.Main;

public class Countdown implements Runnable{
	public static boolean Broadcast = false;
	Main plugin;
	String m;
	Utils u = new Utils();
	Player p;
	int delay;
	int health = 100;
	int c = 0;
	
	private int id = 0;
	 
	public void setId(int n) {
	  this.id = n;
	}
	public Countdown(Main plugin, Player p, int delay, String m)
	{
		this.plugin = plugin;
		this.p = p;
		this.delay = delay;
		this.m = m;
	}
	public void run() {
		if(Broadcast != true){
		if(health != c){
			int d = health/delay;
			c = c + d;
			General.setStatus(p, u.Variables(u.colorize(m), p, plugin), c);
		}
		if(c == health){
			Bukkit.getScheduler().cancelTask(id);
		}
	}
	}
	

}
