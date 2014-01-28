package net.avicus.leap.api.trails;

import net.avicus.leap.api.ParticleEffect;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Critical extends Trail {

	public Critical() {
		super("critical");
	}

	@Override
	public void play(Location l) {
		ParticleEffect.CRIT.animateAtLocation(l, getParticleAmount(), 1F);
	}
	
	@Override
	public int getParticleAmount() {
		return 5;
	}

}
