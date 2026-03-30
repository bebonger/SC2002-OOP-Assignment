package sc2002.combat.core.items;

import sc2002.combat.core.effects.SmokeBombEffect;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.ui.BattleObserver;

public class SmokeBombItem implements Item {
    @Override
    public void use(Player user, Entity target, BattleObserver observer) {
        target.addStatusEffect(new SmokeBombEffect(2));
        observer.onItemUsed(user, "Smoke Bomb", target);
    }

    @Override
    public String getName() { return "Smoke Bomb"; }
}