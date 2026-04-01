package sc2002.combat.ui;

import java.util.Scanner;
import java.util.List;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.items.IItem;

public class CombatConsole implements IBattleObserver {
    private static final String DIVIDER = "--------------------------------------------------";
    private final Scanner scanner = new Scanner(System.in);

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
                        + currentHP);

        if (isEliminated) {
            System.out.println(target.getName() + " is eliminated.");
        }
    }

    @Override
    public void onActionExecuted(Entity attacker, String actionName, Entity target) {
        String attackerName = attacker == null ? "Unknown" : attacker.getName();
        String targetName = target == null ? "Unknown" : target.getName();
        String action = actionName == null || actionName.isBlank() ? "Action" : actionName;
        
        System.out.println(
            attackerName + 
            " uses " + 
            action + 
            (target == null ? "" : (" on " + targetName + "."))
        );
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
            System.out.println(
                    "Statistics: Enemies remaining: " + remainingDetail + " | Total Rounds Survived: " + roundCount);
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
        System.out.println("ITEM | " + user.getName() + " used " + item + " on " + target.getName());
    }

    @Override
    public void displayMessage(String message) {
        if (message == null || message.isBlank()) {
            return;
        }

        System.out.println(message);
    }

    @Override
    public String readLineTrim() {
        return scanner.nextLine().trim();
    }

    @Override
    public String readNonEmpty(String prompt) {
        while (true) {
            displayMessage(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            displayMessage("Input cannot be empty.");
        }
    }

    @Override
    public int readIntInRange(int min, int max) {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            displayMessage("Invalid input. Enter a number from " + min + " to " + max + ".");
        }
    }

    @Override
    public int promptForActionSelection(int cooldown) {
        displayMessage("Choose action:");
        displayMessage("1. Basic Attack");
        displayMessage("2. Defend");
        displayMessage("3. Special Skill (Cooldown: " + cooldown + ")");
        displayMessage("4. Use Item");
        return readIntInRange(1, 4);
    }

    @Override
    public int promptForClassSelection() {
        displayMessage("Choose class:");
        displayMessage("1. Warrior");
        displayMessage("2. Wizard");
        return readIntInRange(1, 2);
    }

    @Override
    public int promptForDifficultySelection() {
        displayMessage("Choose difficulty:");
        displayMessage("1. Easy");
        displayMessage("2. Medium");
        displayMessage("3. Hard");
        return readIntInRange(1, 3);
    }

    @Override
    public int promptForStarterItemSelection(int itemNumber) {
        displayMessage("Select Item " + itemNumber + ":");
        return readIntInRange(1, 3);
    }

    @Override
    public int promptForTargetSelection(List<Entity> enemies, boolean allowBack) {
        if (enemies == null || enemies.isEmpty()) {
            return -1;
        }

        displayMessage("Choose target:");
        for (int i = 0; i < enemies.size(); i++) {
            Entity enemy = enemies.get(i);
            displayMessage((i + 1) + ". " + enemy.getName() + " (HP: " + enemy.getHp() + ")");
        }

        int max = enemies.size();
        if (allowBack) {
            displayMessage((enemies.size() + 1) + ". Back");
            max++;
        }

        return readIntInRange(1, max);
    }

    @Override
    public int promptForItemSelection(List<IItem> items, boolean allowBack) {
        if (items == null || items.isEmpty()) {
            return -1;
        }

        displayMessage("Choose item:");
        for (int i = 0; i < items.size(); i++) {
            displayMessage((i + 1) + ". " + items.get(i).getName());
        }

        int max = items.size();
        if (allowBack) {
            displayMessage((items.size() + 1) + ". Back");
            max++;
        }

        return readIntInRange(1, max);
    }

    @Override
    public int promptForItemTargetSelection(Entity self, List<Entity> enemies, boolean allowBack) {
        displayMessage("Choose item target:");
        displayMessage("1. " + self.getName() + " (Self)");

        for (int i = 0; i < enemies.size(); i++) {
            Entity enemy = enemies.get(i);
            displayMessage((i + 2) + ". " + enemy.getName() + " (HP: " + enemy.getHp() + ")");
        }

        int max = enemies.size() + 1;
        if (allowBack) {
            displayMessage((enemies.size() + 2) + ". Back");
            max++;
        }

        return readIntInRange(1, max);
    }
}