package sc2002.combat.core.actions;

import sc2002.combat.core.effects.DefenseBoostEffect;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.IBattleObserver;

public class DefendAction implements IAction {
    @Override
    public void execute(Entity attacker, Entity target, IBattleObserver observer) {
        attacker.addStatusEffect(new DefenseBoostEffect());
        
        if (observer != null) {
            observer.displayMessage(attacker.getName() + " takes a defensive stance!");
        }
    }
}