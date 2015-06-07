package net.avicus.leap.listeners;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.avicus.leap.Leap;
import net.avicus.leap.api.Trail;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.hooks.NCPExemptionManager;

public class LeapListener implements Listener {

    private Leap plugin;

    private HashMap<String, Boolean> using = new HashMap<String, Boolean>();
    private HashMap<String, Date> lastUse = new HashMap<String, Date>();

    public LeapListener(Leap plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        using.put(event.getPlayer().getName(), false);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setAllowFlight(player.hasPermission("leap.use"));

        List<Trail> trails = plugin.getTrails(player);
        Collections.shuffle(trails);

        if (trails.size() > 0)
            plugin.setTrail(player, trails.get(0));
    }    

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        using.remove(event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerGamemodeSwitch(PlayerGameModeChangeEvent event) {
        if (event.getNewGameMode() == GameMode.SURVIVAL || event.getNewGameMode() == GameMode.ADVENTURE) {
            event.getPlayer().setAllowFlight(event.getPlayer().hasPermission("leap.use"));
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        if (event.getCause() != DamageCause.FALL)
            return;

        if (using.containsKey(((Player) event.getEntity()).getName()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onGround(PlayerMoveEvent event) {
        if (!((Entity) event.getPlayer()).isOnGround())
            return;

        if (!event.getPlayer().hasPermission("leap.use"))
            return;

        if (!using.get(event.getPlayer().getName()))
            return;

        using.put(event.getPlayer().getName(), false);
        event.getPlayer().setAllowFlight(true);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent event) {
        if (!event.getPlayer().hasPermission("leap.use"))
            return;

        if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;

        if (event.isFlying()) {
            final Player p = event.getPlayer();
            event.setCancelled(true);
            p.setAllowFlight(false);

            /*
             * AntiCheat?
             */

            if (plugin.hasNCP()) {
                toggleNCP(event.getPlayer(), false);

                final Date now = new Date();

                new BukkitRunnable() {

                    public void run() {
                        if (lastUse.get(p.getName()) != now)
                            return;

                        toggleNCP(p, true);
                        using.put(p.getName(), false);
                        p.setAllowFlight(true);
                    }

                }.runTaskLaterAsynchronously(plugin, (int) Math.ceil(20 * 4 * plugin.getElevation()));

                lastUse.put(p.getName(), now);
            }

            /*
             * Give player velocity and elevation
             */

            using.put(p.getName(), true);

            Vector newVelocity = p.getLocation().getDirection().multiply(1.0D * plugin.getVelocity()).setY(1.0 * plugin.getElevation());
            p.setVelocity(newVelocity);

            /*
             * Effects and Sounds
             */

            for (Effect effect : plugin.getEffects().keySet())
                if (plugin.hasEffect(p, effect))
                    p.getWorld().playEffect(p.getLocation(), effect, effect.getId());

            for (Sound sound : plugin.getSounds())
                p.getWorld().playSound(p.getLocation(), sound, 1.0F, -5.0F);

            /*
             * Particle Trails
             */

            final Trail trail = plugin.getTrail(p.getName());
            if (trail == null)
                return;

            new BukkitRunnable() {

                public void run() {
                    if (p == null || p.isOnline() == false || using.get(p.getName()) == false) {
                        cancel();
                        return;
                    }

                    for (int i = 0; i < trail.getParticleAmount(); i++)
                        trail.play(p.getLocation());
                }

            }.runTaskTimer(plugin, 0, 3);
        }
    }

    private void toggleNCP(Player player, boolean enableAntiCheat) {
        if (enableAntiCheat) {
            NCPExemptionManager.unexempt(player, CheckType.ALL);
        } else {
            NCPExemptionManager.exemptPermanently(player, CheckType.ALL);
        }
    }

}
