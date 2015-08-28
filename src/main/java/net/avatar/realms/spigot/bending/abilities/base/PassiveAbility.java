package net.avatar.realms.spigot.bending.abilities.base;

import org.bukkit.entity.Player;

import net.avatar.realms.spigot.bending.abilities.AbilityState;
import net.avatar.realms.spigot.bending.utils.ProtectionManager;

public abstract class PassiveAbility extends Ability{

	public PassiveAbility (Player player, Ability parent) {
		super(player, parent);
		if (canBeInitialized()) {
			this.startedTime = System.currentTimeMillis();	
			setState(AbilityState.CanStart);
		}
		else {
			setState(AbilityState.CannotStart);
		}
	}

	@Override
	public boolean canBeInitialized () {
		if (!super.canBeInitialized()) {
			return false;
		}

		if (ProtectionManager.isRegionProtectedFromBendingPassives(this.player, this.player.getLocation())) {
			return false;
		}

		return true;
	}

	public abstract void start();

	@Override
	public boolean progress () {
		if (!super.progress()) {
			return false;
		}

		if (ProtectionManager.isRegionProtectedFromBendingPassives(this.player, this.player.getLocation())) {
			return false;
		}

		if (!this.state.equals(AbilityState.Progressing)) {
			return false;
		}

		return true;
	}

	@Override
	protected final long getMaxMillis() {
		return 0;
	}

}