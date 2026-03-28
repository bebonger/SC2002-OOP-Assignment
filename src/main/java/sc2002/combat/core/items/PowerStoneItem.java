package sc2002.combat.core.items;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.ui.BattleObserver;

public class PowerStoneItem implements Item {
    @Override
    public void use(Player user, Entity target, BattleObserver observer) {
        // trigger skill immediately without cooldown reset
        user.getSpecialSkill().execute(user, target, observer);
        observer.onItemUsed(user, "Power Stone");
    }

    @Override
    public String getName() { return "Power Stone"; }
}