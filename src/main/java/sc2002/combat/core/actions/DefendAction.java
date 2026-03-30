package sc2002.combat.core.actions;

import sc2002.combat.core.effects.DefenseBoostEffect;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.utils.BattleContext;

public class DefendAction implements IAction {
    @Override
    public void execute(Entity attacker, Entity target, BattleContext context) {
        attacker.addStatusEffect(new DefenseBoostEffect());
        
        context.getObserver().displayMessage(attacker.getName() + " takes a defensive stance!");
    }
}