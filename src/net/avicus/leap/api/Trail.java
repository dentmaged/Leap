package net.avicus.leap.api;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.avicus.api.permissions.Permissible;
import net.avicus.leap.api.trails.Cloud;
import net.avicus.leap.api.trails.Critical;
import net.avicus.leap.api.trails.Ember;
import net.avicus.leap.api.trails.Ender;
import net.avicus.leap.api.trails.Explosion;
import net.avicus.leap.api.trails.Fire;
import net.avicus.leap.api.trails.Heart;
import net.avicus.leap.api.trails.Portal;
import net.avicus.leap.api.trails.Rainbow;
import net.avicus.leap.api.trails.Runes;
import net.avicus.leap.api.trails.Slime;
import net.avicus.leap.api.trails.Snow;
import net.avicus.leap.api.trails.Void;

import org.bukkit.Location;

public abstract class Trail extends Permissible {

	@Getter static List<Trail> list = new ArrayList<Trail>();
	
	static {
		list.add(new Void());
		list.add(new Ember());
		list.add(new Ender());
		list.add(new Snow());
		list.add(new Heart());
		list.add(new Fire());
		list.add(new Rainbow());
		list.add(new Explosion());
		list.add(new Runes());
		list.add(new Slime());
		list.add(new Critical());
		list.add(new Cloud());
		list.add(new Portal());
	}
	
	@Getter String name;
	@Getter @Setter boolean enabled;
	
	public Trail(String name) {
		this.name = name;
	}
	
	@Override
	public String getNode() {
		return "leap.trail." + name;
	}
	
	/**
	 * @param name
	 * @return Trail with the specified name
	 */
	public static Trail getByName(String name) {
		for (Trail trail : list)
			if (trail.getName().equalsIgnoreCase(name))
				return trail;
		return null;
	}
	
	/**
	 * Plays the effect to all nearby players at the location
	 * @param location
	 */
	public abstract void play(Location location);
	
	/**
	 * @return Amount of particles
	 */
	public abstract int getParticleAmount();
	
}
