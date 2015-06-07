package net.avicus.leap.api.trails;

import net.avicus.leap.api.ParticleEffect;
import net.avicus.leap.api.Trail;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Ember extends Trail {

    public Ember() {
        super("ember");
    }

    @Override
    public void play(Location location) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ParticleEffect.LAVA.animateToPlayer(player, location, getParticleAmount(), 1F);
        }
    }

    @Override
    public int getParticleAmount() {
        return 4;
    }

}
