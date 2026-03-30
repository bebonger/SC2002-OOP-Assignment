package sc2002.combat.core.actions;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.utils.BattleContext;

public class ItemAction implements IAction {
    private final int slotIndex;

    public ItemAction(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    @Override
    public void execute(Entity attacker, Entity target, BattleContext context) {
        if (attacker instanceof Player p) {
            p.useItem(slotIndex, target, context);
        }
    }
}