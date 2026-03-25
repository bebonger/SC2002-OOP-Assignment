package sc2002.combat.ui;

import sc2002.combat.core.entities.Entity;

public interface BattleObserver {
    void onRoundStart(int roundNumber);
    void onTurnStart(Entity activeEntity);

    void onDamageDealt(Entity target, int damage, int currentHP, boolean isEliminated);

    void onGameOver(boolean playerAlive, int roundCount, int remainingDetail);
        
}