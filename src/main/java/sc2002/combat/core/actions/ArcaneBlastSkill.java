package sc2002.combat.core.actions;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Wizard;
import sc2002.combat.ui.BattleObserver;

public class ArcaneBlastSkill implements SpecialSkillAction {
    @Override
    public void execute(Entity attacker, Entity target, BattleObserver observer) {
        int damage = 50;
        target.takeDamage(damage);

        // +10 to base attack
        if (attacker instanceof Wizard wizard) {
            wizard.increaseBaseAttack(10); 
            
            if (observer != null) {
                observer.displayMessage(wizard.getName() + "'s magical power surges! (+10 ATK)");
            }
        }

        if (observer != null) {
            observer.onActionExecuted(attacker, "Arcane Blast", target);
        }
    }
}