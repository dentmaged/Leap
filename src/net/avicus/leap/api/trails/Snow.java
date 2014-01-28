package net.avicus.leap.api.trails;

import net.avicus.leap.api.ParticleEffect;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Snow extends Trail {

	public Snow() {
		super("snow");
	}

	@Override
	public void play(Location l) {
		ParticleEffect.SNOW_SHOVEL.animateAtLocation(l, getParticleAmount(), 1F);
	}
	
	@Override
	public int getParticleAmount() {
		return 5;
	}

}
