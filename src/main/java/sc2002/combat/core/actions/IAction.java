package sc2002.combat.core.actions;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.IBattleObserver;

public interface IAction {
    void execute(Entity attacker, Entity target, IBattleObserver observer);
}