package sc2002.combat.core.actions;

import java.util.List;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.utils.BattleContext;

public interface IAction {
    void execute(Entity attacker, Entity target, BattleContext context);

    default void executeAoE(Entity attacker, List<Entity> entityList, BattleContext context) {
        // do nothing
    }
}