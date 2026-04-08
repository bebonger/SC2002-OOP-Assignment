package sc2002.combat.core.actions;

import sc2002.combat.control.CombatContext;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.utils.TargetRequirement;

public interface IAction {
    void execute(Entity attacker, Entity target, CombatContext context);
    TargetRequirement getTargetRequirement();
    default boolean requiresCooldown() {
        return false;
    }
}