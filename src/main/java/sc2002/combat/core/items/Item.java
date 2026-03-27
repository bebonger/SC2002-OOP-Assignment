package sc2002.combat.core.items;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.ui.BattleObserver;

public interface Item {
    void use(Player user, Entity target, BattleObserver observer);
    String getName();
}