package sc2002.combat.control;

import sc2002.combat.core.actions.BasicAttackAction;
import sc2002.combat.core.actions.DefendAction;
import sc2002.combat.core.actions.IAction;
import sc2002.combat.core.actions.ItemAction;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.items.IItem;

public class ActionFactory {

    public IAction createAction(int choice, Player player) {
        switch (choice){
            case 1 -> {
                return new BasicAttackAction();
            }
            case 2 -> {
                return new DefendAction();
            }
            case 3 -> {
                return player.getSpecialSkill();
            }
        }
        return null;
    }

    public IAction createItemAction(int itemIndex, Player player) {
        IItem item = player.getInventory().get(itemIndex);
        return new ItemAction(itemIndex, item);
    }
}