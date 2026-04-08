package sc2002.combat.core.items;

import sc2002.combat.control.CombatContext;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.utils.TargetRequirement;
import sc2002.combat.ui.ICombatObserver;

public class PotionItem implements IItem {
    @Override
    public void use(Player user, Entity target, CombatContext context) {
        int healAmount = user.heal(100);
        ICombatObserver observer = context.getObserver();

        observer.onItemUsed(user, "Health Potion", target);
        observer.displayMessage(user.getName() + " heals " + healAmount +" hp");
        observer.displayMessage("New HP: " + user.getHp());
    }

    @Override
    public String getName() {
        return "Health Potion";
    }

    @Override 
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.NONE;
    }
}