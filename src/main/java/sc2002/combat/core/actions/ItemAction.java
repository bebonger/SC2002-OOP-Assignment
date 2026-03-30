package sc2002.combat.core.actions;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.ui.IBattleObserver;

public class ItemAction implements IAction {
    private int slotIndex;

    public ItemAction(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    @Override
    public void execute(Entity attacker, Entity target, IBattleObserver observer) {
        if (attacker instanceof Player p) {
            p.useItem(slotIndex, target);
        }
    }
}