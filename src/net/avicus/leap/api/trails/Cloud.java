package net.avicus.leap.api.trails;

import net.avicus.api.player.Gamer;
import net.avicus.api.player.ToggleType;
import net.avicus.api.utils.ParticleUtil;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Cloud extends Trail {

	public Cloud() {
		super("cloud");
	}

	@Override
	public void play(Location l) {
		for (Gamer g : Gamer.getList()) {
			if (ToggleType.TRAILS.getValue(g) == 0)
				ParticleUtil.EXPLODE.animateToPlayer(g.getPlayer(), l, getParticleAmount(), 1F);
		}
	}
	
	@Override
	public int getParticleAmount() {
		return 3;
	}

}
