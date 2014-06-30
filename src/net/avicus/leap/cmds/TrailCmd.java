package net.avicus.leap.cmds;

import net.avicus.api.command.SimpleCommand;
import net.avicus.api.locale.T;
import net.avicus.api.locale.W;
import net.avicus.api.player.Gamer;
import net.avicus.api.utils.Chat;
import net.avicus.leap.Leap;
import net.avicus.leap.api.Trail;

public class TrailCmd extends SimpleCommand {

	private Leap plugin;
	
	public TrailCmd(Leap plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public Result onCommand(Gamer g, String[] args) {		
		if (args.length != 1) {
			String trails = "";
			for (Trail trail : Trail.getList()) {
				if (trail.isEnabled()) {
					String color = trail.hasPermission(g) ? "&a" : "&6";
					trails += color + trail.getName() + "&e, ";
				}
			}
			
			try {
				trails = Chat.chomp(trails, 4);
			} catch (Exception e) {
				g.message("&c{0}", new T("hub/trails/noTrails"));
				return null;
			}
			
			g.message("&e{0}: &f{1}", new W("trail").caps().plural(), trails);
			return null;
		}

		if (g.isConsole())
			return Result.INVALID_PLAYER;
		
		String input = args[0].toLowerCase();
		Trail trail = Trail.getByName(input);
		
		if (trail == null || trail.isEnabled() == false) {
			g.message("&c{0}", new T("hub/trails/noMatch"));
			return null;
		}
		
		if (plugin.getTrails(g).contains(trail) == false) {
			g.message("&c{0}", new T("hub/trails/locked"));
			return null;
		}

		plugin.setTrail(g, trail);
		g.message("&e{0}", new T("hub/trails/success", "&6" + trail.getName() + "&e"));
		return null;
	}

	@Override
	public String getNode() {
		return null;
	}

	@Override
	public String getUsage() {
		return "/trail <name>";
	}

}
