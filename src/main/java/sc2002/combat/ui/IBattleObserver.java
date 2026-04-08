package sc2002.combat.ui;

import java.util.List;
import sc2002.combat.core.actions.IAction;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.items.IItem;
import sc2002.combat.core.utils.BattleContext;

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

    IAction promptForActionSelection(Player player, BattleContext context);
    int promptForClassSelection();
    int promptForDifficultySelection();
    int promptForStarterItemSelection(int itemNumber);
    Entity promptForTargetSelection(List<Entity> enemies, boolean allowBack, boolean targetSelf);
    int promptForItemSelection(List<IItem> items, boolean allowBack);
}