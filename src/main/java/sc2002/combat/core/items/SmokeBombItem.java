package sc2002.combat.core.items;

import sc2002.combat.core.effects.SmokeBombEffect;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.utils.BattleContext;
import sc2002.combat.core.utils.TargetRequirement;

public class SmokeBombItem implements IItem {
    @Override
    public void use(Player user, Entity target, BattleContext context) {
        user.addStatusEffect(new SmokeBombEffect(2));
        context.getObserver().onItemUsed(user, "Smoke Bomb", target);
    }

    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    @Override 
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.NONE;
    }
}