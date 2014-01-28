package net.avicus.leap;

import java.util.HashMap;

import net.avicus.api.events.GamerJoinEvent;
import net.avicus.api.events.PermissionModifyEvent;
import net.avicus.api.events.PlayerDamageEvent;
import net.avicus.api.events.PlayerOnGroundEvent;
import net.gravitydevelopment.anticheat.api.AntiCheatAPI;
import net.gravitydevelopment.anticheat.check.CheckType;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class LeapListener implements Listener {

	private Leap plugin;
	
	private HashMap<String, Boolean> using = new HashMap<String, Boolean>();
	
	public LeapListener(Leap plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onGamerJoin(GamerJoinEvent event) {
		using.put(event.getGamer().getName(), false);
		event.getGamer().setAllowFlight(event.getGamer().hasPermission("leap.use"));
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
		
		if (event.getOnGround()) {
			using.put(event.getPlayer().getName(), false);
			event.getPlayer().setAllowFlight(true);

			try {
			    Class.forName("net.gravitydevelopment.anticheat.api.AntiCheatAPI");
			    AntiCheatAPI.activateCheck(CheckType.FLY);
			} catch(Exception e) {
				
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
			Player p = event.getPlayer();
			
			try {
			    Class.forName("net.gravitydevelopment.anticheat.api.AntiCheatAPI");
			    AntiCheatAPI.deactivateCheck(CheckType.FLY);
			} catch(Exception e) {
				
			}
			
			using.put(p.getName(), true);
			
			event.setCancelled(true);
			p.setAllowFlight(false);
			
			Vector newVelocity = p.getLocation().getDirection().multiply(1.0D * plugin.getVelocity()).setY(1.0 * plugin.getElevation());
			p.setVelocity(newVelocity);

			for (Effect effect : plugin.getEffects().keySet())
				if (plugin.hasEffect(p, effect))
					p.getWorld().playEffect(p.getLocation(), effect, effect.getId());

			for (Sound sound : plugin.getSounds())
				p.getWorld().playSound(p.getLocation(), sound, 1.0F, -5.0F);
		}
	}
	
}
