package Messager;

import java.util.List;

import org.bukkit.entity.Player;

import CMS.CMS;
import Main.Main;
import Utils.General;
import Utils.Utils;

public class TickerMessager
  implements Runnable
{
  boolean Delay = false;
  private Main plugin;
  private Player p;
  private CMS cms;
  StringBuilder Scroll = new StringBuilder();
  int Counter = 0;
  int Display = 0;
  int Size = 0;
  int Current = 0;
  int Lenght = 0;
  int Max = 40;
  boolean a = false;

  public TickerMessager(Main plugin, Player pl, CMS cms) {
    this.plugin = plugin;
    this.p = pl;
    this.cms = cms;
  }

  public void run() {	
	  if(Main.disabled.contains(p)){
		  return;
	  }
	  @SuppressWarnings("unchecked")
	List<String> messages = (List<String>) cms.getChannelData(Main.CM.get(p.getUniqueId()) , "Messages");
	@SuppressWarnings("unchecked")
	List<String> Worlds = (List<String>) cms.getChannelData(Main.CM.get(p.getUniqueId()) , "Worlds");
	if(messages.size() == 0){
		General.setStatus(p, "§cNo messages set!", 100);
	}
	if(cms.getChannelBoolean(Main.CM.get(p.getUniqueId()), "PerWorld") == true){
		if(Worlds.contains(p.getWorld().getName())){
			
		}else{
			return;
		}
	}
    if (this.p != null) {
      try {
  		String message = messages.get(Counter);
        Utils u = new Utils();
        message = replaceColors(message);
        message = u.Variables(message, this.p, this.plugin);
        this.Size = message.length();
        String Color = "§$1";
        if (this.Size != this.Current) {

          if ((message.charAt(this.Current) == Color.charAt(0)) || (message.charAt(this.Current) == Color.charAt(1))) {
            this.Scroll.append(message.charAt(this.Current));
            this.Current += 1;
            Lenght ++;
            this.Display += 1;
            this.Scroll.append(message.charAt(this.Current));
            this.Current += 1;
            this.Display += 1;
            this.Scroll.append(message.charAt(this.Current));
            this.Current += 1;
            this.Display += 1;
          }

          this.Scroll.append(message.charAt(this.Current));
          this.Current += 1;
          Lenght ++;
          this.Display += 1;

          General.setStatus(p, Scroll.toString(), 100);
        }
        if (this.Size == this.Current) {            
        this.Scroll.append(" ");
        this.Display --;
        Lenght ++;
       
          General.setStatus(p, Scroll.toString(), 100);
          if (this.Display == 0) {
            this.Current = 0;
            this.Size = 0;
            this.Max = 40;
            this.Counter += 1;
            this.Display = 0;
          }
        }


if(Scroll.length() == cms.getChannelInt(Main.CM.get(p.getUniqueId()), "Tickerlenght")){
	Scroll.deleteCharAt(0); 
	Lenght --;
}
if(Scroll.length() >= cms.getChannelInt(Main.CM.get(p.getUniqueId()), "Tickerlenght")){
	int max = cms.getChannelInt(Main.CM.get(p.getUniqueId()), "Tickerlenght");
	int c = Scroll.length();
	int r = c - max;
	while(r != 0){
	Scroll.deleteCharAt(0); 
	r --;
	}
}
        if (this.Counter < messages.size()) return;
        this.Counter = 0;
        this.Current = 0;
        this.Size = 0;
        this.Max = 40;
        this.Display = 0;
      }
      catch (Exception exc)
      {
          this.Current = 0;
          this.Size = 0;
          this.Counter = 0;
          this.Max = 40;
          this.Display = 0;
      }
    }
    else;
  }

  public String replaceColors(String message)
  {
    return message.replaceAll("(?i)&([a-f0-9lmnok])", "§$1");
  }
}