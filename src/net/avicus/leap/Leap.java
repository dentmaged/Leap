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
	
	Config configuration;
	@Getter List<Effect> effects = new ArrayList<Effect>();
	@Getter List<Sound> sounds = new ArrayList<Sound>();
	
	public void onEnable() {
		
		configuration = new Config(this);
		

		for (String raw : configuration.getStringList("effects")) {
			try {
				Effect effect = Effect.valueOf(raw.toUpperCase().replace(" ", "_"));
				effects.add(effect);
			} catch (Exception e) {
				Chat.log(LogLevel.WARNING, "Invalid effect name: \"%s\"", raw);
			}
		}
		
		for (String raw : configuration.getStringList("sounds")) {
			try {
				Sound sound = Sound.valueOf(raw.toUpperCase().replace(" ", "_"));
				sounds.add(sound);
			} catch (Exception e) {
				Chat.log(LogLevel.WARNING, "Invalid effect name: \"%s\"", raw);
			}
		}
		
		
		Bukkit.getPluginManager().registerEvents(new LeapListener(this), this);
	}
	
}
