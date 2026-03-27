package sc2002.combat.core.actions;

import sc2002.combat.core.effects.DefenseBoostEffect;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.BattleObserver;

public class DefendAction implements Action {
    @Override
    public void execute(Entity attacker, Entity target, BattleObserver observer) {
        attacker.addStatusEffect(new DefenseBoostEffect());
        
        if (observer != null) {
            observer.displayMessage(attacker.getName() + " takes a defensive stance!");
        }
    }
}