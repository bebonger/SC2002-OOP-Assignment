package sc2002.combat.ui;

import sc2002.combat.core.entities.Entity;

public interface BattleObserver {
    void onRoundStart(int roundNumber);
    void onTurnStart(/*Entity activeEntity*/);

    // other events
    void onDamageDealt(Entity target, int damage, int currentHP, boolean isEliminated);
}