package net.avatar.realms.spigot.bending.abilities.air;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.avatar.realms.spigot.bending.abilities.Abilities;
import net.avatar.realms.spigot.bending.abilities.Ability;
import net.avatar.realms.spigot.bending.abilities.AbilityState;
import net.avatar.realms.spigot.bending.abilities.BendingAbility;
import net.avatar.realms.spigot.bending.abilities.BendingType;
import net.avatar.realms.spigot.bending.abilities.energy.AvatarState;
import net.avatar.realms.spigot.bending.abilities.multi.Bubble;
import net.avatar.realms.spigot.bending.controller.ConfigurationParameter;

@BendingAbility(name="Air Bubble", element=BendingType.Air)
public class AirBubble extends Bubble {

	@ConfigurationParameter("Radius")
	private static double DEFAULT_RADIUS = 4;

	@ConfigurationParameter("Max-Duration")
	private static long MAX_DURATION = 60 * 10 * 1000;

	public AirBubble (Player player) {
		this(player, null);
	}

	public AirBubble (Player player, Ability parent) {
		super(player, parent);

		if (this.state.isBefore(AbilityState.CanStart)) {
			return;
		}

		this.radius = DEFAULT_RADIUS;

		if (AvatarState.isAvatarState(player)) {
			this.radius = AvatarState.getValue(this.radius);
		}

		this.pushedMaterials.add(Material.WATER);
		this.pushedMaterials.add(Material.STATIONARY_WATER);
	}

	@Override
	public Abilities getAbilityType () {
		return Abilities.AirBubble;
	}

	@Override
	protected long getMaxMillis () {
		return MAX_DURATION;
	}

}
