package sc2002.combat.ui;

import java.util.List;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.items.IItem;

public interface ICombatInterface extends IUIOutput {
    String readLineTrim();
    String readNonEmpty(String prompt);
    int readIntInRange(int min, int max);

    int promptForActionSelection(Player player);
    int promptForClassSelection();
    int promptForDifficultySelection();
    int promptForStarterItemSelection(int itemNumber);
    Entity promptForTargetSelection(List<Entity> enemies, boolean allowBack, boolean targetSelf);
    int promptForItemSelection(List<IItem> items, boolean allowBack);
}