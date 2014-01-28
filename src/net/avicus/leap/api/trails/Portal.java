package net.avicus.leap.api.trails;

import net.avicus.api.utils.ParticleUtil;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Portal extends Trail {

	public Portal() {
		super("portal");
	}

	@Override
	public void play(Location l) {
		ParticleUtil.PORTAL.animateAtLocation(l, getParticleAmount(), 1F);
	}
	
	@Override
	public int getParticleAmount() {
		return 20;
	}

}
