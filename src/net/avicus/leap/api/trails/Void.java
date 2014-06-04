package net.avicus.leap.api.trails;

import net.avicus.api.player.Gamer;
import net.avicus.api.player.ToggleType;
import net.avicus.api.utils.ParticleUtil;
import net.avicus.api.utils.RandomUtil;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Void extends Trail {

	public Void() {
		super("void");
	}

	@Override
	public void play(Location l) {
		for (Gamer g : Gamer.getList()) {
			if (ToggleType.TRAILS.getValue(g) == 0)
				for (int i = 0; i < 3; i++)
					ParticleUtil.TOWN_AURA.animateToPlayer(g.getPlayer(), l.clone().add(RandomUtil.nextDouble() - 0.5, 0, RandomUtil.nextDouble() - 0.5), getParticleAmount(), 1F);
		}
	}
	
	@Override
	public int getParticleAmount() {
		return 3;
	}

}
