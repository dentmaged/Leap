package net.avicus.leap;

import java.util.HashMap;

import net.avicus.api.events.PlayerDamageEvent;
import net.avicus.api.events.PlayerOnGroundEvent;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class LeapListener implements Listener {

	private Leap plugin;
	
	private static double MULTIPLY = 2.0;
	private static double HEIGHT = 1.5;
	
	private HashMap<String, Boolean> using = new HashMap<String, Boolean>();
	
	public LeapListener(Leap plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		using.put(event.getPlayer().getName(), false);
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
		
		if (event.getOnGround()) {
			using.put(event.getPlayer().getName(), false);
			event.getPlayer().setAllowFlight(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onToggleFlight(PlayerToggleFlightEvent event) {
		if (event.getPlayer().hasPermission("leap.use") == false)
			return;
		
		Player p = event.getPlayer();
		
		if (event.isFlying()) {
			using.put(p.getName(), true);
			
			event.setCancelled(true);
			p.setAllowFlight(false);
			
			Vector newVelocity = p.getLocation().getDirection().multiply(1.0D * MULTIPLY).setY(1.0 * HEIGHT);
			p.setVelocity(newVelocity);

			for (Effect effect : plugin.getEffects())
				p.getWorld().playEffect(p.getLocation(), effect, effect.getId());

			for (Sound sound : plugin.getSounds())
				p.getWorld().playSound(p.getLocation(), sound, 1.0F, -5.0F);
		}
	}
	
}
