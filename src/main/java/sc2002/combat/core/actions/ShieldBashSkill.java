package sc2002.combat.core.actions;

import sc2002.combat.core.effects.StunEffect;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.BattleObserver;

public class ShieldBashSkill implements SpecialSkillAction {
    @Override
    public void execute(Entity attacker, Entity target, BattleObserver observer) {
        int damage = 35;
        target.takeDamage(damage);

        // stun for 2 turns
        target.addStatusEffect(new StunEffect(2));

        if (observer != null) {
            observer.onActionExecuted(attacker, "Shield Bash", target);
        }
    }
}