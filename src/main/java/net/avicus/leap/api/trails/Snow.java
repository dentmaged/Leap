package net.avicus.leap.api.trails;

import net.avicus.leap.api.ParticleEffect;
import net.avicus.leap.api.Trail;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Snow extends Trail {

    public Snow() {
        super("snow");
    }

    @Override
    public void play(Location l) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ParticleEffect.SNOW_SHOVEL.animateToPlayer(player, l, getParticleAmount(), 1F);
        }
    }

    @Override
    public int getParticleAmount() {
        return 5;
    }

}
