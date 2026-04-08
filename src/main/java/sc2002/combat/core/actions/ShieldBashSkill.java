package sc2002.combat.core.actions;

import sc2002.combat.control.BattleContext;
import sc2002.combat.core.effects.StunEffect;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.utils.TargetRequirement;

public class ShieldBashSkill implements ISpecialSkillAction {

    @Override
    public void execute(Entity attacker, Entity target, BattleContext context) {
        int damage = Math.max(1, attacker.getEffectiveAttack());
        int finalDamage = target.takeDamage(damage);

        // stun for 2 turns
        target.addStatusEffect(new StunEffect(2));

        context.getObserver().onActionExecuted(attacker, "Shield Bash", target);
        context.getObserver().onDamageDealt(target, finalDamage, target.getHp(), !target.isAlive());
        context.getObserver().displayMessage(target.getName() + " is stunned for 2 rounds");
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.SINGLE;
    }
}