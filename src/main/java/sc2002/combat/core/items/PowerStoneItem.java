package sc2002.combat.core.items;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.utils.BattleContext;
import sc2002.combat.core.utils.ITargetable;

public class PowerStoneItem implements IItem, ITargetable {
    @Override
    public void use(Player user, Entity target, BattleContext context) {
        // trigger skill immediately without cooldown reset
        context.getObserver().onItemUsed(user, "Power Stone", target);
        user.getSpecialSkill().execute(user, target, context);
    }

    @Override
    public String getName() {
        return "Power Stone";
    }
}