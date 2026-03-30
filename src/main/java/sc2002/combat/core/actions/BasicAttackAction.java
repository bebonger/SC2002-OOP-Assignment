package sc2002.combat.core.actions;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.utils.BattleContext;

public class BasicAttackAction implements IAction {
    @Override
    public void execute(Entity attacker, Entity target, BattleContext context) {
        // ATK - DEF
        // Using getEffective stats to account for buffs/debuffs
        int damage = Math.max(1, attacker.getEffectiveAttack() - target.getEffectiveDefense());
        
        target.takeDamage(damage);

        if (context != null) {
            context.getObserver().onActionExecuted(attacker, "Basic Attack", target);
            context.getObserver().onDamageDealt(target, damage, target.getHp(), !target.isAlive());
        }
    }
}