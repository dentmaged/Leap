package net.avicus.leap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import net.avicus.api.config.Config;
import net.avicus.api.player.Gamer;
import net.avicus.api.utils.Chat;
import net.avicus.api.utils.LogLevel;
import net.avicus.leap.api.Trail;
import net.avicus.leap.cmds.TrailCmd;
import net.avicus.leap.listeners.LeapListener;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Leap extends JavaPlugin {
	
	@Getter HashMap<Effect, String> effects = new HashMap<Effect, String>();
	@Getter List<Sound> sounds = new ArrayList<Sound>();
	@Getter double velocity;
	@Getter double elevation;
	
	private boolean anticheat;
	
	private HashMap<String, Trail> trails = new HashMap<String, Trail>();
	
	public void onEnable() {
		Config config = new Config(this);
		
		for (String raw : config.getStringList("effects")) {
			try {
				String[] parts = raw.split(":");
				
				Effect effect = Effect.valueOf(parts[0].toUpperCase().replace(" ", "_"));
				String perm = parts.length == 1 ? null : parts[1];
				
				effects.put(effect, perm);
			} catch (Exception e) {
				Chat.log(LogLevel.WARNING, "Invalid effect name: \"%s\"", raw);
			}
		}
		
		for (String raw : config.getStringList("sounds")) {
			try {
				Sound sound = Sound.valueOf(raw.toUpperCase().replace(" ", "_"));
				sounds.add(sound);
			} catch (Exception e) {
				Chat.log(LogLevel.WARNING, "Invalid effect name: \"%s\"", raw);
			}
		}
		
		for (String input : config.getStringList("trails")) {
			Trail trail = Trail.getByName(input);
			if (trail != null)
				trail.setEnabled(true);
		}
		
		velocity = config.getDouble("velocity");
		elevation = config.getDouble("elevation");
		
		anticheat = Bukkit.getServer().getPluginManager().getPlugin("AntiCheat") != null;
		
		if (anticheat)
			Chat.log(LogLevel.NOTICE, "Connected to AntiCheat!");
		
		Bukkit.getPluginManager().registerEvents(new LeapListener(this), this);
		getCommand("trail").setExecutor(new TrailCmd(this));
	}
	
	/**
	 * @param player
	 * @param effect
	 * @return If the player has permission to use the effect
	 */
	public boolean hasEffect(Player player, Effect effect) {
		String perm = effects.get(effect);
		
		if (perm == null)
			return true;
		
		return player.hasPermission(perm);
	}
	
	/**
	 * @return If the current server is running the AntiCheat plugin
	 */
	public boolean hasAntiCheat() {
		return anticheat;
	}
	
	/**
	 * @param gamer
	 * @return List of trails that this gamer has permission to use
	 */
	public List<Trail> getTrails(Gamer gamer) {
		List<Trail> trails = new ArrayList<Trail>();
		
		for (Trail trail : Trail.getList()) {
			if (trail.isEnabled() == false)
				continue;
			
			if (trail.hasPermission(gamer))
				trails.add(trail);
		}
		
		return trails;
	}
	
	/**
	 * @param username
	 * @return The trail that the specified user is currently using. Returns null if none.
	 */
	public Trail getTrail(String username) {
		if (trails.containsKey(username) == false)
			return null;
		return trails.get(username);
	}
	
	/**
	 * Set the trail of a gamer
	 * @param g The gamer
	 * @param trail The trail to set
	 */
	public void setTrail(Gamer g, Trail trail) {
		trails.put(g.getName(), trail);
	}
	
}
