package net.avicus.leap.api.trails;

import net.avicus.leap.api.Trail;
import net.avicus.leap.util.FireworkPlayer;
import net.avicus.leap.util.RandomUtils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Fireworks extends Trail {

    static Color[] COLORS = new Color[] { Color.RED, Color.WHITE, Color.BLUE };

    public Fireworks() {
        super("fireworks");
    }

    @Override
    public void play(Location location) { // TODO FIX!!!!
        Location loc = location.clone().add(RandomUtils.nextDouble() - 0.5, 0, RandomUtils.nextDouble() - 0.5);
        FireworkEffect effect = FireworkEffect.builder().withColor(COLORS[RandomUtils.between(0, COLORS.length - 1)]).with(Type.BURST).build();

        for (Player player : Bukkit.getOnlinePlayers()) {
            FireworkPlayer.playToPlayer(player, loc, effect);
        }
    }

    @Override
    public int getParticleAmount() {
        return 1;
    }

}
