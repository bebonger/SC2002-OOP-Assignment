package sc2002.combat.ui;

import sc2002.combat.core.entities.Entity;

public class CombatConsole implements BattleObserver {

    // @Bryan implement these to however you see fit!

    @Override
    public void onRoundStart(int roundNumber) {
    }

    @Override
    public void onTurnStart(Entity activeEntity) {
    }

    @Override
    public void onDamageDealt(Entity target, int damage, int currentHP, boolean isEliminated) {
    }

    @Override
    public void onActionExecuted(Entity attacker, String actionName, Entity target) {
    }

    @Override
    public void onGameOver(boolean playerAlive, int roundCount, int remainingDetail) {
    }

    @Override
    public void onStatusEffectApplied(Entity entity, String effectName, int duration) {
    }

    @Override
    public void onStatusEffectExpired(Entity entity, String effectName) {
    }

    @Override
    public void onItemUsed(Entity user, String itemName) {
    }

    @Override
    public void displayMessage(String message) {
    }

}