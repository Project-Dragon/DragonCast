package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import DragonCore.DragonCore;
import Main.Main;

public class Utils{

	public String colorize(String msg)
    {
		msg = msg.replace('&', '§');
		return msg.replace('&', '§');
    }
	public String Variables(String message, Player p, Main plugin){
		message = message.replace("%Player%", p.getName());
		message = message.replace("%player%", p.getName());
		StringBuilder Online = new StringBuilder();
		Online.append("");
		Online.append(Bukkit.getOnlinePlayers().length);
		String on = Online.toString();
		message = message.replace("%Online%", on);
		message = message.replace("%online%", on);
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append(Bukkit.getMaxPlayers());
		String slots = sb.toString();
		message = message.replace("%Slots%", slots);
		message = message.replace("%slots%", slots);
		
		message = message.replace("%Displayname%", p.getDisplayName());
		message = message.replace("%displayname%", p.getDisplayName());
		
		message = message.replace("%Playerip%", p.getAddress().getAddress().toString());
		message = message.replace("%playerip%", p.getAddress().getAddress().toString());
		
		message = message.replace("%MOTD%", DragonCore.plugin.getServer().getMotd());
		message = message.replace("%motd%", DragonCore.plugin.getServer().getMotd());
		
		SimpleDateFormat day = new SimpleDateFormat("dd");
		SimpleDateFormat month = new SimpleDateFormat("M");
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		SimpleDateFormat hours = new SimpleDateFormat("hh");
		SimpleDateFormat minutes = new SimpleDateFormat("mm");
		SimpleDateFormat seconds = new SimpleDateFormat("ss");
		Date date = new Date();
		
		message = message.replace("%day%", day.format(date));
		message = message.replace("%month%", month.format(date));
		message = message.replace("%year%", year.format(date));
		message = message.replace("%hours%", hours.format(date));
		message = message.replace("%minutes%", minutes.format(date));
		message = message.replace("%seconds%", seconds.format(date));
		
		return message;
	}
	
}
