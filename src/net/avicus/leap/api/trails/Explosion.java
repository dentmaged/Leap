package net.avicus.leap.api.trails;

import net.avicus.api.utils.ParticleUtil;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Explosion extends Trail {

	public Explosion() {
		super("explosion");
	}

	@Override
	public void play(Location l) {
		ParticleUtil.HUGE_EXPLOSION.animateAtLocation(l, getParticleAmount(), 1F);
	}
	
	@Override
	public int getParticleAmount() {
		return 1;
	}

}
