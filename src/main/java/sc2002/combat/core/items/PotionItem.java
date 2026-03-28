package sc2002.combat.core.items;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.ui.BattleObserver;

public class PotionItem implements Item {
    @Override
    public void use(Player user, Entity target, BattleObserver observer) {
        user.heal(100);
        observer.onItemUsed(user, "Health Potion");
    }

    @Override
    public String getName() { return "Health Potion"; }
}