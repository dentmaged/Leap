package net.avicus.leap.api.trails;

import net.avicus.leap.api.ParticleEffect;
import net.avicus.leap.api.Trail;
import net.avicus.leap.util.RandomUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Void extends Trail {

    public Void() {
        super("void");
    }

    @Override
    public void play(Location l) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < 3; i++)
                ParticleEffect.TOWN_AURA.animateToPlayer(player, l.clone().add(RandomUtils.nextDouble() - 0.5, 0, RandomUtils.nextDouble() - 0.5), getParticleAmount(), 1F);
        }
    }

    @Override
    public int getParticleAmount() {
        return 3;
    }

}
