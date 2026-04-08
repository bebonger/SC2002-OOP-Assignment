package sc2002.combat.core.items;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.utils.BattleContext;
import sc2002.combat.core.utils.TargetRequirement;

public class PotionItem implements IItem {
    @Override
    public void use(Player user, Entity target, BattleContext context) {
        user.heal(100);
        context.getBoundary().onItemUsed(user, "Health Potion", target);
    }

    @Override
    public String getName() {
        return "Health Potion";
    }

    @Override 
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.NONE;
    }
}