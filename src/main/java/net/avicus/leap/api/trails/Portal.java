package net.avicus.leap.api.trails;

import net.avicus.leap.api.ParticleEffect;
import net.avicus.leap.api.Trail;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Portal extends Trail {

	public Portal() {
		super("portal");
	}

	@Override
	public void play(Location location) {
		for (Player player : Bukkit.getOnlinePlayers()) {
            ParticleEffect.PORTAL.animateToPlayer(player, location, getParticleAmount(), 1F);
        }
	}
	
	@Override
	public int getParticleAmount() {
		return 20;
	}

}
