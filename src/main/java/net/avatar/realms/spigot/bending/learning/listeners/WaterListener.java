package net.avatar.realms.spigot.bending.learning.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.avatar.realms.spigot.bending.abilities.Abilities;
import net.avatar.realms.spigot.bending.abilities.BendingPlayer;
import net.avatar.realms.spigot.bending.abilities.BendingType;
import net.avatar.realms.spigot.bending.controller.ConfigManager;
import net.avatar.realms.spigot.bending.event.AbilityCooldownEvent;
import net.avatar.realms.spigot.bending.learning.BendingLearning;
import net.avatar.realms.spigot.bending.utils.EntityTools;
import net.avatar.realms.spigot.bending.utils.PluginTools;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class WaterListener implements Listener {
	private BendingLearning plugin;
	private Map<UUID, Long> waterManipulationlastTime = new HashMap<UUID, Long>();
	private Map<UUID, Integer> waterManipulationSucceded= new HashMap<UUID, Integer>();
	
	private Map<UUID, Integer> surge = new HashMap<UUID, Integer>();

	public WaterListener(BendingLearning plugin) {
		this.plugin = plugin;
		
	}
	
	@EventHandler
	public void unlockTorrent(AbilityCooldownEvent event) {
		BendingPlayer bPlayer = event.getBender();
		if(bPlayer != null) {
			if(bPlayer.isBender(BendingType.Water) && event.getAbility().equals(Abilities.Torrent)) {
				List<LivingEntity> entities = EntityTools.getLivingEntitiesAroundPoint(bPlayer.getPlayer().getLocation(), 10);
				
				for(Entity entity : entities) {
					if(entity instanceof Player) {
						Player p = (Player)entity;
						BendingPlayer trainee = BendingPlayer.getBendingPlayer(p);
						if(trainee.isBender(BendingType.Water) && !trainee.isBender(BendingType.ChiBlocker)) {
							if(p.hasLineOfSight(bPlayer.getPlayer())) {
								if(plugin.addPermission(p, Abilities.Torrent)) {
									ChatColor color = PluginTools.getColor(ConfigManager.getColor("Water"));
									String message = "You saw "+bPlayer.getPlayer().getName()+" bending a continuous torrent of water, and you copied it's mouvement";
									p.sendMessage(color+message);
									message = "Congratulations, you have unlocked "+Abilities.Torrent.name();
									p.sendMessage(color+message);
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void unlockWaterBubble(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player && event.getCause().equals(DamageCause.DROWNING)) {
			Player p = (Player) event.getEntity();
			BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(p);
			if(bPlayer != null) {
				if(bPlayer.isBender(BendingType.Water) && !bPlayer.isBender(BendingType.ChiBlocker)) {
					if(plugin.addPermission(p, Abilities.WaterBubble)) {
						ChatColor color = PluginTools.getColor(ConfigManager.getColor("Water"));
						String message = "Your suffocation made your realize that you can bend water arround you to prevent your own death";
						p.sendMessage(color+message);
						message = "Congratulations, you have unlocked "+Abilities.WaterBubble.name();
						p.sendMessage(color+message);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void unlockSurge(AbilityCooldownEvent event) {
		BendingPlayer bPlayer = event.getBender();
		if(bPlayer != null) {
			if(bPlayer.isBender(BendingType.Water) && !bPlayer.isBender(BendingType.ChiBlocker) && event.getAbility().equals(Abilities.WaterManipulation)) {
				int blasted = 0;
				Player p = bPlayer.getPlayer();
				if(surge.containsKey(p.getUniqueId())) {
					blasted = surge.get(p.getUniqueId());
				}
				blasted = blasted + 1;
				if(blasted >= 250) {
					if(plugin.addPermission(p, Abilities.Surge)) {
						ChatColor color = PluginTools.getColor(ConfigManager.getColor("Water"));
						String message = "Your water manipulation has improved enough for you to concentrate multiple water source at the same time";
						p.sendMessage(color+message);
						message = "Congratulations, you have unlocked "+Abilities.Surge.name();
						p.sendMessage(color+message);
					}
					surge.remove(p.getUniqueId());
				} else {
					surge.put(p.getUniqueId(), blasted);
				}
			}
		}
	}
	
	@EventHandler
	public void unlockIceSpike(AbilityCooldownEvent event) {
		BendingPlayer bPlayer = event.getBender();
		if(bPlayer != null) {
			//Check if player has unlocked PhaseChange here
			if(bPlayer.isBender(BendingType.Water) && !bPlayer.isBender(BendingType.ChiBlocker) && event.getAbility().equals(Abilities.WaterManipulation)) {
				Player p = bPlayer.getPlayer();
				if(EntityTools.hasAbility(p, Abilities.PhaseChange)) {
					if(plugin.addPermission(p, Abilities.IceSpike)) {
						ChatColor color = PluginTools.getColor(ConfigManager.getColor("Water"));
						String message = "Turning water into water also made you realize that you can bend directly ice";
						p.sendMessage(color+message);
						message = "Congratulations, you have unlocked "+Abilities.IceSpike.name();
						p.sendMessage(color+message);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void unlockOctopusForm(AbilityCooldownEvent event) {
		BendingPlayer bPlayer = event.getBender();
		if(bPlayer != null) {
			//Check if player knows surge
			Player p = bPlayer.getPlayer();
			if(bPlayer.isBender(BendingType.Water) && !bPlayer.isBender(BendingType.ChiBlocker) && EntityTools.hasAbility(p, Abilities.Surge)) {
				if(event.getAbility().equals(Abilities.WaterManipulation)) {
					long lastTime = -1;
					long currentTime = System.currentTimeMillis();
					if(waterManipulationlastTime .containsKey(p.getUniqueId())) {
						lastTime = waterManipulationlastTime.get(p.getUniqueId());
					}
					
					if(currentTime - lastTime > 10000) {
						waterManipulationSucceded.remove(p.getUniqueId());
					}
					
					int success = 0;
					if(waterManipulationSucceded.containsKey(p.getUniqueId())) {
						success = waterManipulationSucceded.get(p.getUniqueId());
					}
					success = success + 1;
					
					if(success > 25) {
						if(plugin.addPermission(p, Abilities.OctopusForm)) {
							ChatColor color = PluginTools.getColor(ConfigManager.getColor("Water"));
							String message = "By launching so much water manipulation, you think you are able to manage both defense and attack by combining a water wall on yourself, and multiple water manipulation at the same time";
							p.sendMessage(color+message);
							message = "Congratulations, you have unlocked "+Abilities.OctopusForm.name();
							p.sendMessage(color+message);
						}
						
						waterManipulationSucceded.remove(p.getUniqueId());
						waterManipulationlastTime.remove(p.getUniqueId());
					} else {
						waterManipulationSucceded.put(p.getUniqueId(), success);
						waterManipulationlastTime.put(p.getUniqueId(), currentTime);
					}
				}
			}
		}
	}
}