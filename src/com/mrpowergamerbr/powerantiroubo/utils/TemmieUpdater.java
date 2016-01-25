package com.mrpowergamerbr.powerantiroubo.utils;

import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import com.mrpowergamerbr.powerantiroubo.PowerAntiRoubo;

public class TemmieUpdater implements Listener {
	/*
	 * TemmieUpdater - hOI! Im TEMMIE!!!
	 * 
	 * Created by MrPowerGamerBR 2016
	 */
	PowerAntiRoubo m;
	String newVersion = "";

	public TemmieUpdater(PowerAntiRoubo m) {
		this.m = m;
		Bukkit.getPluginManager().registerEvents(this, m);
		startUpdater();
	}

	public void startUpdater() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					Scanner ipChecker;
					if ((boolean) m.asriel.get("TemmieUpdater.TemmieMetrics")) {
						ipChecker = new Scanner((new URL("http://plugins.mrpowergamerbr.com/updater?plugin=" + PowerAntiRoubo.pluginName + "&port=" + Bukkit.getPort() + "&ver=" + getVersion() + "&plugins=" + getPlugins())).openStream());
					} else {
						ipChecker = new Scanner((new URL("http://plugins.mrpowergamerbr.com/updater?plugin=" + PowerAntiRoubo.pluginName + "&port=" + Bukkit.getPort())).openStream());
					}
					Bukkit.getLogger().log(Level.INFO, "[" + PowerAntiRoubo.pluginName + "] Verificando atualizações...");

					boolean update = false;
					while (ipChecker.hasNextLine()) {
						String line = ipChecker.nextLine();
						if (line.equals(PowerAntiRoubo.version)) {
							break;
						} else {
							update = true;
							newVersion = line;
						}
					}
					// System.out.println("HATE. " + powerHateListIPs.toString());
					ipChecker.close();

					if (update) {
						Bukkit.getLogger().log(Level.INFO, "[" + PowerAntiRoubo.pluginName + "] Uma nova atualização para o " + PowerAntiRoubo.pluginName + " está disponível! (" + newVersion + ")");
					} else {
						Bukkit.getLogger().log(Level.INFO, "[" + PowerAntiRoubo.pluginName + "] Você está na última versão do " + PowerAntiRoubo.pluginName);
					}
				} catch (Exception e) {
					Bukkit.getLogger().log(Level.INFO, "[" + PowerAntiRoubo.pluginName + "] Um problema ocorreu ao tentar verificar updates.");
				}
			}
		});
		t.start();
	}

	public static String getVersion() {
		final String packageName = Bukkit.getServer().getClass().getPackage().getName();

		return packageName.substring(packageName.lastIndexOf('.') + 1);
	}

	public static String getPlugins() {
		String plugins = "";
		for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
			if (plugins.equals("")) {
				plugins = p.getName();
			} else {
				plugins = plugins + "," + p.getName();
			}
		}
		return plugins;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission(PowerAntiRoubo.pluginName + ".ReloadConfig")) {
			if (!newVersion.equals("")) {
				e.getPlayer().sendMessage("§7[§b§l" + PowerAntiRoubo.pluginName + "§7] §6Uma nova atualização para o " + PowerAntiRoubo.pluginName + " está disponível! (" + newVersion + ")");
			}
		}
	}
}