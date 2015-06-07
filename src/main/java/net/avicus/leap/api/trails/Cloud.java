package net.avicus.leap.api.trails;

import net.avicus.leap.api.ParticleEffect;
import net.avicus.leap.api.Trail;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Cloud extends Trail {

    public Cloud() {
        super("cloud");
    }

    @Override
    public void play(Location location) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ParticleEffect.EXPLODE.animateToPlayer(player, location, getParticleAmount(), 1F);
        }
    }

    @Override
    public int getParticleAmount() {
        return 3;
    }

}
