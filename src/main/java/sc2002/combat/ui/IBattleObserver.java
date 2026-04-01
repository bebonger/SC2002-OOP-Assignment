package sc2002.combat.ui;

import java.util.List;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.items.IItem;

public interface IBattleObserver {
    void onRoundStart(int roundNumber);

    void onTurnStart(Entity activeEntity);

    void onDamageDealt(Entity target, int damage, int currentHP, boolean isEliminated);

    void onActionExecuted(Entity attacker, String actionName, Entity target);

    void onGameOver(boolean playerAlive, int roundCount, int remainingDetail);

    void onStatusEffectApplied(Entity entity, String effectName, int duration);

    void onStatusEffectExpired(Entity entity, String effectName);

    void onItemUsed(Entity user, String itemName, Entity target);

    void displayMessage(String message);

    String readLineTrim();
    String readNonEmpty(String prompt);
    int readIntInRange(int min, int max);

    int promptForActionSelection(int cooldown);
    int promptForClassSelection();
    int promptForDifficultySelection();
    int promptForStarterItemSelection(int itemNumber);
    int promptForTargetSelection(List<Entity> enemies, boolean allowBack);
    int promptForItemSelection(List<IItem> items, boolean allowBack);
    int promptForItemTargetSelection(Entity self, List<Entity> enemies, boolean allowBack);
}