package net.avicus.leap.listeners;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.avicus.api.events.GamerJoinEvent;
import net.avicus.api.events.PermissionModifyEvent;
import net.avicus.api.events.PlayerDamageEvent;
import net.avicus.api.events.PlayerOnGroundEvent;
import net.avicus.api.tools.Schedule;
import net.avicus.leap.Leap;
import net.avicus.leap.api.Trail;
import net.gravitydevelopment.anticheat.api.AntiCheatAPI;
import net.gravitydevelopment.anticheat.check.CheckType;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class LeapListener implements Listener {

	private Leap plugin;

	private HashMap<String, Boolean> using = new HashMap<String, Boolean>();
	private HashMap<String, Date> lastUse = new HashMap<String, Date>();
	
	public LeapListener(Leap plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onGamerJoin(GamerJoinEvent event) {
		using.put(event.getGamer().getName(), false);
		event.getGamer().setAllowFlight(event.getGamer().hasPermission("leap.use"));
		
		List<Trail> trails = plugin.getTrails(event.getGamer());
		Collections.shuffle(trails);
		
		for (Trail trail : plugin.getTrails(event.getGamer())) {
			plugin.setTrail(event.getGamer(), trail);
			break;
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		using.remove(event.getPlayer().getName());
	}
	
	@EventHandler
	public void onPermissionModify(PermissionModifyEvent event) {
		if (event.getPermission().equals("leap.use") == false)
			return;
		event.getGamer().setAllowFlight(event.isEnabled());
	}
	
	@EventHandler
	public void onPlayerDamage(PlayerDamageEvent event) {
		if (event.getCause() == DamageCause.FALL == false)
			return;
		
		if (using.containsKey(event.getTarget().getName()))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onGround(PlayerOnGroundEvent event) {
		if (event.getPlayer().hasPermission("leap.use") == false)
			return;
		
		if (using.get(event.getPlayer().getName()) == false)
			return;
		
		if (event.getOnGround()) {
			using.put(event.getPlayer().getName(), false);
			event.getPlayer().setAllowFlight(true);

			try {
				Class.forName("net.gravitydevelopment.anticheat.api.AntiCheatAPI");
				toggleAntiCheat(event.getPlayer(), true);
			} catch (Exception e) {
				// AntiCheat not installed on server
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onToggleFlight(PlayerToggleFlightEvent event) {
		if (event.getPlayer().hasPermission("leap.use") == false)
			return;
		
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
			return;
		
		if (event.isFlying()) {
			
			final Player p = event.getPlayer();
			
			/*
			 * AntiCheat?
			 */
			
			try {
				
				Class.forName("net.gravitydevelopment.anticheat.api.AntiCheatAPI");
				toggleAntiCheat(event.getPlayer(), false);

				final Date now = new Date();
				
				new Schedule() {

					@Override
					public void run() {
						if (lastUse.get(p.getName()) != now)
							return;

						toggleAntiCheat(p, true);
					}
					
				}.laterAsync((int) Math.ceil(20 * 4 * plugin.getElevation()));
				
				lastUse.put(p.getName(), now);
				
			} catch (Exception e) {
				// AntiCheat not installed on server
			}

			/* 
			 * Give player velocity and elevation
			 */
			
			using.put(p.getName(), true);
			
			event.setCancelled(true);
			p.setAllowFlight(false);
			
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
			
			new Schedule() {

				@Override
				public void run() {
					if (p == null || p.isOnline() == false || using.get(p.getName()) == false) {
						cancel();
						return;
					}
					
					for (int i = 0; i < trail.getParticleAmount(); i++)
						trail.play(p.getLocation());
				}
				
			}.repeat(0, 2);
		}
	}
	
	private void toggleAntiCheat(Player player, boolean enableAntiCheat) {
		if (enableAntiCheat) {
			AntiCheatAPI.unexemptPlayer(player, CheckType.FLY);
			AntiCheatAPI.unexemptPlayer(player, CheckType.SPEED);
			AntiCheatAPI.unexemptPlayer(player, CheckType.WATER_WALK);
		}
		else {
			AntiCheatAPI.exemptPlayer(player, CheckType.FLY);
			AntiCheatAPI.exemptPlayer(player, CheckType.SPEED);
			AntiCheatAPI.exemptPlayer(player, CheckType.WATER_WALK);
		}
	}
	
}