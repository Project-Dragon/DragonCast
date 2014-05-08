package Messager;

import java.util.List;

import org.bukkit.entity.Player;

import CMS.CMS;
import Main.Main;
import Utils.General;
import Utils.Utils;

public class Scrollmessager
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
  int Max = 40;
  boolean a = false;

  public Scrollmessager(Main plugin, Player p, CMS cms) {
    this.plugin = plugin;
    this.p = p;
    this.cms = cms;
  }

  public void run() {		@SuppressWarnings("unchecked")
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
          if (this.a) {
            this.Scroll.deleteCharAt(0);
            this.a = false;
          }

          if ((message.charAt(this.Current) == Color.charAt(0)) || (message.charAt(this.Current) == Color.charAt(1))) {
            this.Scroll.append(message.charAt(this.Current));
            this.Current += 1;
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
          this.Display += 1;
          if (this.Display == this.Max) {
            if ((this.Scroll.charAt(0) == Color.charAt(0)) || (this.Scroll.charAt(0) == Color.charAt(1))) {
              this.Scroll.deleteCharAt(2);
              this.Display -= 1;
            }
            if ((this.Scroll.charAt(2) == Color.charAt(0)) || (this.Scroll.charAt(2) == Color.charAt(1))) {
              this.Scroll.deleteCharAt(0);
              this.Scroll.deleteCharAt(0);

              this.Display -= 1;
              this.Display -= 1;
            }
          }

          General.setStatus(p, Scroll.toString(), 100);
        }
        if (this.Size == this.Current) {
          if ((this.Scroll.charAt(0) == Color.charAt(0)) || (this.Scroll.charAt(0) == Color.charAt(1))) {
            this.Scroll.deleteCharAt(2);
            this.Display -= 1;
          }
          if ((this.Scroll.charAt(2) == Color.charAt(0)) || (this.Scroll.charAt(2) == Color.charAt(1))) {
            this.Scroll.deleteCharAt(0);
            this.Scroll.deleteCharAt(0);

            this.Display -= 1;
            this.Display -= 1;
          }
          if (this.Scroll.length() == 3) {
            this.Scroll.deleteCharAt(0);
            this.Scroll.deleteCharAt(0);
            this.Scroll.deleteCharAt(0);

            this.Display -= 1;
            this.Display -= 1;
            this.Display -= 1;
            this.Scroll.append(" ");
            this.a = true;
          }
          General.setStatus(p, Scroll.toString(), 100);
          if (this.Display == 0) {
            this.Display = 0;
            this.Current = 0;
            this.Size = 0;
            this.Max = 40;
            this.Counter += 1;
          }
        }
        if (this.Counter < messages.size()) return;
        this.Counter = 0;
        this.Display = 0;
        this.Current = 0;
        this.Size = 0;
        this.Max = 40;
      }
      catch (Exception exc)
      {
    	  this.Display = 0;
          this.Current = 0;
          this.Size = 0;
          this.Counter = 0;
          this.Max = 40;
      }
    }
    else;
  }

  public String replaceColors(String message)
  {
    return message.replaceAll("(?i)&([a-f0-9lmnok])", "§$1");
  }
}