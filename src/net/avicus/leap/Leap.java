package net.avicus.leap;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import net.avicus.api.config.Config;
import net.avicus.api.utils.Chat;
import net.avicus.api.utils.LogLevel;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

public class Leap extends JavaPlugin {
	
	@Getter List<Effect> effects = new ArrayList<Effect>();
	@Getter List<Sound> sounds = new ArrayList<Sound>();
	@Getter double velocity;
	@Getter double elevation;
	
	public void onEnable() {
		Config config = new Config(this);
		for (String raw : config.getStringList("effects")) {
			try {
				Effect effect = Effect.valueOf(raw.toUpperCase().replace(" ", "_"));
				effects.add(effect);
			} catch (Exception e) {
				Chat.log(LogLevel.WARNING, "Invalid effect name: \"%s\"", raw);
			}
		}
		
		for (String raw : config.getStringList("sounds")) {
			try {
				Sound sound = Sound.valueOf(raw.toUpperCase().replace(" ", "_"));
				sounds.add(sound);
			} catch (Exception e) {
				Chat.log(LogLevel.WARNING, "Invalid effect name: \"%s\"", raw);
			}
		}
		
		velocity = config.getDouble("velocity");
		elevation = config.getDouble("elevation");
		
		Bukkit.getPluginManager().registerEvents(new LeapListener(this), this);
	}
	
}
