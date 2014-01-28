package net.avicus.leap.api.trails;

import net.avicus.leap.api.Trail;

import org.bukkit.Effect;
import org.bukkit.Location;

public class Ender extends Trail {

	public Ender() {
		super("ender");
	}

	@Override
	public void play(Location l) {
		l.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
	}
	
	@Override
	public int getParticleAmount() {
		return 1;
	}

}
