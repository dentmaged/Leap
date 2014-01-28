package net.avicus.leap.api.trails;

import net.avicus.api.utils.ParticleUtil;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Heart extends Trail {

	public Heart() {
		super("heart");
	}

	@Override
	public void play(Location l) {
		ParticleUtil.HEART.animateAtLocation(l, getParticleAmount(), 1F);
	}
	
	@Override
	public int getParticleAmount() {
		return 4;
	}

}
