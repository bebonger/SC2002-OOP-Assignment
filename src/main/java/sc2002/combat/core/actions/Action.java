package sc2002.combat.core.actions;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.BattleObserver;

public interface Action {
    void execute(Entity attacker, Entity target, BattleObserver observer);
}