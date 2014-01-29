package net.avicus.leap.cmds;

import net.avicus.api.command.SimpleCommand;
import net.avicus.api.player.Gamer;
import net.avicus.api.utils.Chat;
import net.avicus.leap.Leap;
import net.avicus.leap.api.Trail;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrailCmd extends SimpleCommand {

	private Leap plugin;
	
	public TrailCmd(Leap plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		
		if (args.length != 1) {
			String trails = "";
			for (Trail trail : Trail.getList())
				if (trail.isEnabled())
					trails += "&6" + trail.getName() + "&e, ";
			
			try {
				trails = Chat.chomp(trails, 4);
			} catch (Exception e) {
				Chat.player(sender, "&cThere are no trails enabled.");
				return true;
			}
			
			Chat.player(sender, "&cUsage: /trail <name>");
			Chat.player(sender, "&eTrails: " + trails);
			return true;
		}

		if (sender instanceof Player == false) {
			Chat.player(sender, "&cMust be a player in order to use trails.");
			return true;
		}
		
		Gamer g = Gamer.get(sender);
		
		String input = args[0].toLowerCase();
		Trail trail = Trail.getByName(input);
		
		if (trail == null || trail.isEnabled() == false) {
			g.sendMessage("&cNo trail matched your query. Type /trails to see a list.");
			return true;
		}
		
		if (plugin.getTrails(g).contains(trail) == false) {
			g.sendMessage("&cYou haven't unlocked that trail!");
			return true;
		}
		
		g.sendMessage("&eYou are now using the trail, &6" + trail.getName().toUpperCase() + "&e!");
		plugin.setTrail(g, trail);
		return true;
	}

	@Override
	public String getNode() {
		return null;
	}

}
