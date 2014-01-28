package net.avicus.leap.api.trails;

import net.avicus.api.utils.ParticleUtil;
import net.avicus.api.utils.RandomUtil;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Rainbow extends Trail {

	public Rainbow() {
		super("rainbow");
	}

	@Override
	public void play(Location l) {
		for (int i = 0; i < 3; i++)
			ParticleUtil.MOB_SPELL.animateAtLocation(l.clone().add(RandomUtil.nextDouble() - 0.5, 0, RandomUtil.nextDouble() - 0.5), getParticleAmount(), 1F);
	}
	
	@Override
	public int getParticleAmount() {
		return 5;
	}

}
