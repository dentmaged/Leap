package net.avicus.leap.api.trails;

import net.avicus.leap.api.Trail;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Ender extends Trail {

	public Ender() {
		super("ender");
	}

	@Override
	public void play(Location location) {
		for (Player player : Bukkit.getOnlinePlayers()) {
				player.getWorld().playEffect(location, Effect.ENDER_SIGNAL, 0);
		}
	}
	
	@Override
	public int getParticleAmount() {
		return 3;
	}

}
