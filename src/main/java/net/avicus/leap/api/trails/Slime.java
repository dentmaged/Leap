package net.avicus.leap.api.trails;

import net.avicus.leap.api.ParticleEffect;
import net.avicus.leap.api.Trail;

import org.bukkit.Location;

public class Slime extends Trail {

    public Slime() {
        super("slime");
    }

    @Override
    public void play(Location l) {
        ParticleEffect.SLIME.animateAtLocation(l, getParticleAmount(), 1F);
    }

    @Override
    public int getParticleAmount() {
        return 3;
    }

}
