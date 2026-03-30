package sc2002.combat.core.actions;

import sc2002.combat.core.effects.StunEffect;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.IBattleObserver;

public class ShieldBashSkill implements ISpecialSkillAction {

    @Override
    public void execute(Entity attacker, Entity target, IBattleObserver observer) {
        int damage = 35;
        target.takeDamage(damage);

        // stun for 2 turns
        target.addStatusEffect(new StunEffect(2));

        if (observer != null) {
            observer.onActionExecuted(attacker, "Shield Bash", target);
        }
    }
}