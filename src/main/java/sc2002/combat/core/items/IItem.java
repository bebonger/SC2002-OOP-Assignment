package sc2002.combat.core.items;

import sc2002.combat.control.CombatContext;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.utils.TargetRequirement;

public interface IItem {
    void use(Player user, Entity target, CombatContext context);

    String getName();
    TargetRequirement getTargetRequirement();
}