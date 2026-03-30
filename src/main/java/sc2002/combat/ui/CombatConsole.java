package sc2002.combat.ui;

import sc2002.combat.core.entities.Entity;

public class CombatConsole implements BattleObserver {
    private static final String DIVIDER = "--------------------------------------------------";

    @Override
    public void onRoundStart(int roundNumber) {
        System.out.println();
        System.out.println(DIVIDER);
        System.out.println("ROUND " + roundNumber);
        System.out.println(DIVIDER);
    }

    @Override
    public void onTurnStart(Entity activeEntity) {
        if (activeEntity == null) {
            return;
        }

        System.out.println();
        System.out.println("Turn: " + activeEntity.getName() + " (HP: " + activeEntity.getHp() + ")");
    }

    @Override
    public void onDamageDealt(Entity target, int damage, int currentHP, boolean isEliminated) {
        if (target == null) {
            return;
        }

        System.out.println(
            target.getName()
            + " takes "
            + damage
            + " damage. HP now: "
            + currentHP
        );

        if (isEliminated) {
            System.out.println(target.getName() + " is eliminated.");
        }
    }

    @Override
    public void onActionExecuted(Entity attacker, String actionName, Entity target) {
        String attackerName = attacker == null ? "Unknown" : attacker.getName();
        String targetName = target == null ? "Unknown" : target.getName();
        String action = actionName == null || actionName.isBlank() ? "Action" : actionName;

        System.out.println(attackerName + " uses " + action + " on " + targetName + ".");
    }

    @Override
    public void onGameOver(boolean playerAlive, int roundCount, int remainingDetail) {
        System.out.println();
        System.out.println(DIVIDER);
        System.out.println("BATTLE OVER");
        System.out.println(DIVIDER);

        if (playerAlive) {
            System.out.println("Congratulations, you have defeated all your enemies.");
            System.out.println("Statistics: Remaining HP: " + remainingDetail + " | Total Rounds: " + roundCount);
        } else {
            System.out.println("Defeated. Don't give up, try again!");
            System.out.println("Statistics: Enemies remaining: " + remainingDetail + " | Total Rounds Survived: " + roundCount);
        }
    }

    @Override
    public void onStatusEffectApplied(Entity entity, String effectName, int duration) {
        if (entity == null) {
            return;
        }

        String effect = effectName == null || effectName.isBlank() ? "Status Effect" : effectName;
        System.out.println("ITEM | " + entity.getName() + " gains " + effect + " for " + duration + " turn(s).");
    }

    @Override
    public void onStatusEffectExpired(Entity entity, String effectName) {
        if (entity == null) {
            return;
        }

        String effect = effectName == null || effectName.isBlank() ? "Status Effect" : effectName;
        System.out.println("EFFECT | " + effect + " on " + entity.getName() + " has expired.");
    }

    @Override
    public void onItemUsed(Entity user, String itemName, Entity target) {
        if (user == null) {
            return;
        }

        String item = itemName == null || itemName.isBlank() ? "an item" : itemName;
        System.out.println("ITEM | " + user.getName() + " used " + item + "on " + target.getName());
    }

    @Override
    public void displayMessage(String message) {
        if (message == null || message.isBlank()) {
            return;
        }

        System.out.println(message);
    }

}