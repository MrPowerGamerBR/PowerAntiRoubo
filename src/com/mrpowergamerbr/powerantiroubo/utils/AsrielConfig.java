package com.mrpowergamerbr.powerantiroubo.utils;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import com.mrpowergamerbr.powerantiroubo.PowerAntiRoubo;

public class AsrielConfig {
	/*
	 * Asriel "Dreemurr" Config - A way "like a dream" to load values from config without setting values!
	 * 
	 * Created by MrPowerGamerBR 2016
	 */
	PowerAntiRoubo m;
	
	public AsrielConfig(PowerAntiRoubo m) {
		this.m = m;
	}
	
	HashMap<String, Object> cache = new HashMap<String, Object>();
	
	public Object get(String value) {
		if (cache.containsKey(value)) {
			return cache.get(value);
		} else {
			cache.put(value, m.getConfig().get(value));
			return cache.get(value);
		}
	}

	public Object get(String value, FileConfiguration fc) {
		if (cache.containsKey(value)) {
			return cache.get(value);
		} else {
			cache.put(value, fc.get(value));
			return cache.get(value);
		}
	}
	
	public String getChanged(String value) {
		if (cache.containsKey(value)) {
			return ChatColor.translateAlternateColorCodes('&', (String) cache.get(value));
		} else {
			cache.put(value, m.getConfig().get(value));
			return ChatColor.translateAlternateColorCodes('&', (String) cache.get(value));
		}
	}
	
	public String getChanged(String value, FileConfiguration fc) {
		if (cache.containsKey(value)) {
			return ChatColor.translateAlternateColorCodes('&', (String) cache.get(value));
		} else {
			cache.put(value, fc.get(value));
			return ChatColor.translateAlternateColorCodes('&', (String) cache.get(value));
		}
	}
	
	public void resetToReload() {
		m.reloadConfig();
		
		cache.clear();
	}
}
