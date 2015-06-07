package net.avicus.leap.api.trails;

import net.avicus.leap.api.ParticleEffect;
import net.avicus.leap.api.Trail;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Runes extends Trail {

	public Runes() {
		super("runes");
	}

	@Override
	public void play(Location location) {
		for (Player player : Bukkit.getOnlinePlayers()) {
            ParticleEffect.ENCHANTMENT_TABLE.animateToPlayer(player, location, getParticleAmount(), 1F);
        }
	}
	
	@Override
	public int getParticleAmount() {
		return 5;
	}

}
