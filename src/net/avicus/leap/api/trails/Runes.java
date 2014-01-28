package net.avicus.leap.api.trails;

import net.avicus.api.utils.ParticleUtil;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Runes extends Trail {

	public Runes() {
		super("runes");
	}

	@Override
	public void play(Location l) {
		ParticleUtil.ENCHANTMENT_TABLE.animateAtLocation(l, getParticleAmount(), 1F);
	}
	
	@Override
	public int getParticleAmount() {
		return 5;
	}

}
