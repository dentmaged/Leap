package net.avicus.leap.api.trails;

import net.avicus.api.utils.ParticleUtil;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Cloud extends Trail {

	public Cloud() {
		super("cloud");
	}

	@Override
	public void play(Location l) {
		ParticleUtil.EXPLODE.animateAtLocation(l, getParticleAmount(), 1F);
	}
	
	@Override
	public int getParticleAmount() {
		return 3;
	}

}
