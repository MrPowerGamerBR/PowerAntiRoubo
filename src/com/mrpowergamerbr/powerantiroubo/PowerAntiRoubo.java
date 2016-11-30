package com.mrpowergamerbr.powerantiroubo;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import net.md_5.bungee.api.ChatColor;

public class PowerAntiRoubo extends JavaPlugin implements Listener {		
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		/*
		 * Carregar as configurações
		 */
		Bukkit.getPluginManager().registerEvents(this, this);

		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Login.Client.START) {
			public void onPacketReceiving(final PacketEvent e) {
				try {
					if (!e.getPlayer().hasPermission("PowerAntiRoubo.Bypass")) {
						WrapperPlayClientTabComplete wpctc = new WrapperPlayClientTabComplete(e.getPacket());

						for (String cmd : (ArrayList<String>) getConfig().getStringList("ComandosBloqueados")) {
							if (wpctc.getText().toLowerCase().startsWith(cmd.toLowerCase() + " ") || wpctc.getText().equalsIgnoreCase(cmd)) {
								e.setCancelled(true);
								break;
							}
						}
					}
				} catch (Exception ex) {
				}
			}
		});
	}

	@Override
	public void onDisable() {
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPreprocess(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equalsIgnoreCase("/par")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§6§lPowerAntiRoubo §8- §3Criado por §bMrPowerGamerBR");
			e.getPlayer().sendMessage("§eWebsite:§6 http://mrpowergamerbr.blogspot.com.br/");
			e.getPlayer().sendMessage("§eSparklyPower:§6 http://sparklypower.net/");
			return;
		}
		for (String cmd : getConfig().getStringList("ComandosBloqueados")) {
			if (!e.getPlayer().hasPermission("PowerAntiRoubo.Bypass")) {
				if (e.getMessage().toLowerCase().startsWith(cmd + " ") || e.getMessage().toLowerCase().equals(cmd)) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("MensagemFilosofica")));
					break;
				}
			}
		}
	}

	public PowerAntiRoubo getMe() {
		return this;
	}
}

