package net.avicus.leap.api.trails;

import net.avicus.api.utils.ParticleUtil;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Fire extends Trail {

	public Fire() {
		super("fire");
	}

	@Override
	public void play(Location l) {
		ParticleUtil.FLAME.animateAtLocation(l, getParticleAmount(), 1F);
	}
	
	@Override
	public int getParticleAmount() {
		return 4;
	}

}
