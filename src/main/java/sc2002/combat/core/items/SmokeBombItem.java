package sc2002.combat.core.items;

import sc2002.combat.core.effects.SmokeBombEffect;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.ui.IBattleObserver;

public class SmokeBombItem implements IItem {
    @Override
    public void use(Player user, Entity target, IBattleObserver observer) {
        user.addStatusEffect(new SmokeBombEffect(2));
        observer.onItemUsed(user, "Smoke Bomb", target);
    }

    @Override
    public String getName() { return "Smoke Bomb"; }
}