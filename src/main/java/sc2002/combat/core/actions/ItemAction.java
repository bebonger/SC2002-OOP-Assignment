package sc2002.combat.core.actions;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.items.IItem;
import sc2002.combat.core.utils.BattleContext;
import sc2002.combat.core.utils.TargetRequirement;

public class ItemAction implements IAction {
    private final int slotIndex;
    private final IItem item;

    public ItemAction(int slotIndex, IItem item) {
        this.slotIndex = slotIndex;
        this.item = item;
    }

    @Override
    public void execute(Entity attacker, Entity target, BattleContext context) {
        if (attacker instanceof Player p) {
            p.useItem(slotIndex, target, context);
        }
    }

    @Override 
    public TargetRequirement getTargetRequirement() {
        return item.getTargetRequirement();
    }
}