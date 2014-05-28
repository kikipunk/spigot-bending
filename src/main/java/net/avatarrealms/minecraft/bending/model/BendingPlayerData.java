package net.avatarrealms.minecraft.bending.model;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;

public class BendingPlayerData {
	private String playerName;
	private List<BendingLevelData> bendings;
	private String language;
	private boolean bendToItem;
	private Map<Integer, Abilities> slotAbilities;
	private Map<Material, Abilities> itemAbilities;
	private boolean permaRemoved;
	private long lastTime;
	
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public List<BendingLevelData> getBendings() {
		return bendings;
	}
	public void setBendings(List<BendingLevelData> bending) {
		this.bendings = bending;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isBendToItem() {
		return bendToItem;
	}
	public void setBendToItem(boolean bendToItem) {
		this.bendToItem = bendToItem;
	}
	public Map<Integer, Abilities> getSlotAbilities() {
		return slotAbilities;
	}
	public void setSlotAbilities(Map<Integer, Abilities> slotAbilities) {
		this.slotAbilities = slotAbilities;
	}
	public Map<Material, Abilities> getItemAbilities() {
		return itemAbilities;
	}
	public void setItemAbilities(Map<Material, Abilities> itemAbilities) {
		this.itemAbilities = itemAbilities;
	}
	public boolean isPermaRemoved() {
		return permaRemoved;
	}
	public void setPermaRemoved(boolean permaRemoved) {
		this.permaRemoved = permaRemoved;
	}
	public long getLastTime() {
		return lastTime;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
}
