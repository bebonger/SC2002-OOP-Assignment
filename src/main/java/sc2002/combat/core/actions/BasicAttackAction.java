package sc2002.combat.core.actions;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.IBattleObserver;

public class BasicAttackAction implements IAction {
    @Override
    public void execute(Entity attacker, Entity target, IBattleObserver observer) {
        // Entity.takeDamage applies defense and incoming-damage effects.
        int damage = Math.max(1, attacker.getEffectiveAttack());
        
        if (observer != null) {
            observer.onActionExecuted(attacker, "Basic Attack", target);
        }
        
        target.takeDamage(damage);
    }
}