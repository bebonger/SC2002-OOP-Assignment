package sc2002.combat.core.items;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.ui.IBattleObserver;

public class PotionItem implements IItem {
    @Override
    public void use(Player user, Entity target, IBattleObserver observer) {
        user.heal(100);
        observer.onItemUsed(user, "Health Potion", target);
    }

    @Override
    public String getName() { return "Health Potion"; }
}