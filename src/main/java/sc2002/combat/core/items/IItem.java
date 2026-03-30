package sc2002.combat.core.items;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.ui.IBattleObserver;

public interface IItem {
    void use(Player user, Entity target, IBattleObserver observer);
    String getName();
}