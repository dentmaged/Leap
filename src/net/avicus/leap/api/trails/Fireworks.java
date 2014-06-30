package net.avicus.leap.api.trails;

import net.avicus.api.player.Gamer;
import net.avicus.api.player.ToggleType;
import net.avicus.api.utils.FireworkPlayer;
import net.avicus.api.utils.RandomUtil;
import net.avicus.leap.api.Trail;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;

public class Fireworks extends Trail {
	
	static Color[] COLORS = new Color[] {Color.RED, Color.WHITE, Color.BLUE};

	public Fireworks() {
		super("fireworks");
	}

	@Override
	public void play(Location l) {
		Location loc = l.clone().add(RandomUtil.nextDouble() - 0.5, 0, RandomUtil.nextDouble() - 0.5);
		FireworkEffect effect = FireworkEffect.builder().withColor(COLORS[RandomUtil.between(0, COLORS.length - 1)]).with(Type.BURST).build();
		
		for (Gamer g : Gamer.getList()) {
			if (ToggleType.TRAILS.getValue(g) == 0)
				FireworkPlayer.playToPlayer(g.getPlayer(), loc, effect);
		}
	}
	
	@Override
	public int getParticleAmount() {
		return 1;
	}

}
