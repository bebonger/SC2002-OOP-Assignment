package sc2002.combat.core.actions;

import sc2002.combat.control.BattleContext;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.utils.TargetRequirement;

public class BasicAttackAction implements IAction {
    @Override
    public void execute(Entity attacker, Entity target, BattleContext context) {
        int damage = Math.max(1, attacker.getEffectiveAttack());
        int finalDamage = target.takeDamage(damage);

        context.getObserver().onActionExecuted(attacker, "Basic Attack", target);
        context.getObserver().onDamageDealt(target, finalDamage, target.getHp(), !target.isAlive());
    }

    @Override 
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.SINGLE;
    }
}