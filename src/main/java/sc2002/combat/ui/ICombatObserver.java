package sc2002.combat.ui;

import sc2002.combat.core.entities.Entity;

public interface ICombatObserver {
    void onRoundStart(int roundNumber);
    void onTurnStart(Entity activeEntity);
    void onDamageDealt(Entity target, int damage, int currentHP, boolean isEliminated);
    void onActionExecuted(Entity attacker, String actionName, Entity target);
    void onGameOver(boolean playerAlive, int roundCount, int remainingDetail);
    void onStatusEffectApplied(Entity entity, String effectName, int duration);
    void onStatusEffectExpired(Entity entity, String effectName);
    void onItemUsed(Entity user, String itemName, Entity target);
    void displayMessage(String message);
}