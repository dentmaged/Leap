package net.avicus.leap.api.trails;

import net.avicus.api.player.Gamer;
import net.avicus.api.player.ToggleType;
import net.avicus.api.utils.ParticleUtil;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Ember extends Trail {

	public Ember() {
		super("ember");
	}

	@Override
	public void play(Location l) {
		for (Gamer g : Gamer.getList()) {
			if (ToggleType.TRAILS.getValue(g) == 0)
				ParticleUtil.LAVA.animateToPlayer(g.getPlayer(), l, getParticleAmount(), 1F);
		}
	}
	
	@Override
	public int getParticleAmount() {
		return 4;
	}

}
