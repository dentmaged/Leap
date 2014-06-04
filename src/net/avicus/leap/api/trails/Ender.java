package net.avicus.leap.api.trails;

import net.avicus.api.player.Gamer;
import net.avicus.api.player.ToggleType;
import net.avicus.leap.api.Trail;

import org.bukkit.Effect;
import org.bukkit.Location;

public class Ender extends Trail {

	public Ender() {
		super("ender");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void play(Location l) {
		for (Gamer g : Gamer.getList()) {
			if (ToggleType.TRAILS.getValue(g) == 0)
				g.getPlayer().playEffect(l, Effect.ENDER_SIGNAL, 0);
		}
	}
	
	@Override
	public int getParticleAmount() {
		return 3;
	}

}
