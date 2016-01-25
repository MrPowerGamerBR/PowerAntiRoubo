package com.mrpowergamerbr.powerantiroubo;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import com.mrpowergamerbr.powerantiroubo.utils.AsrielConfig;
import com.mrpowergamerbr.powerantiroubo.utils.TemmieUpdater;

public class PowerAntiRoubo extends JavaPlugin implements Listener {
	ArrayList<String> blockedCommands = new ArrayList<String>();
	String blockedMessage = "";
	public AsrielConfig asriel;
	
	public static final String version = "v1.1.0";
	public static final String pluginName = "PowerAntiRoubo";
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		asriel = new AsrielConfig(this);
		
		/*
		 * Carregar as configurações
		 */
		blockedCommands = (ArrayList<String>) getConfig().getStringList("ComandosBloqueados");
		String message = getConfig().getString("MensagemFilosofica");
		/*
		 * Sim, eu poderia usar .replace, mas usar translateAlternateColorCodes é bem melhor
		 */
		message = ChatColor.translateAlternateColorCodes('&', message);
		blockedMessage = message;

		Bukkit.getPluginManager().registerEvents(this, this);

		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Login.Client.START) {
			public void onPacketReceiving(final PacketEvent e) {
				try {
					if (!e.getPlayer().hasPermission("PowerAntiRoubo.Bypass")) {
						WrapperPlayClientTabComplete wpctc = new WrapperPlayClientTabComplete(e.getPacket());

						for (String cmd : blockedCommands) {
							if (wpctc.getText().toLowerCase().startsWith(cmd + " ") || wpctc.getText().toLowerCase().equals(cmd)) {
								e.setCancelled(true);
								break;
							}
						}
					}
				} catch (Exception ex) {
				}
			}
		});
		
		if ((boolean) asriel.get("TemmieUpdater.VerificarUpdates")) {
			new TemmieUpdater(this);
		}
	}

	@Override
	public void onDisable() {
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPreprocess(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equalsIgnoreCase("/par")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§6§lPowerAntiRoubo §6v1.1.0 §8- §3Criado por §bMrPowerGamerBR");
			e.getPlayer().sendMessage("§eWebsite:§6 http://mrpowergamerbr.blogspot.com.br/");
			e.getPlayer().sendMessage("§eSparklyPower:§6 http://sparklypower.net/");
			return;
		}
		for (String cmd : blockedCommands) {
			if (!e.getPlayer().hasPermission("PowerAntiRoubo.Bypass")) {
				if (e.getMessage().toLowerCase().startsWith(cmd + " ") || e.getMessage().toLowerCase().equals(cmd)) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(blockedMessage);
					break;
				}
			}
		}
	}

	public PowerAntiRoubo getMe() {
		return this;
	}
}

