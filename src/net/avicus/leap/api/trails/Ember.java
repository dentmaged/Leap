package net.avicus.leap.api.trails;

import net.avicus.api.utils.ParticleUtil;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Ember extends Trail {

	public Ember() {
		super("ember");
	}

	@Override
	public void play(Location l) {
		ParticleUtil.LAVA.animateAtLocation(l, getParticleAmount(), 1F);
	}
	
	@Override
	public int getParticleAmount() {
		return 4;
	}

}
