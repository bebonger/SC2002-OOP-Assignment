package sc2002.combat.core.actions;

import java.util.List;
import sc2002.combat.core.entities.Enemy;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Wizard;
import sc2002.combat.ui.IBattleObserver;

public class ArcaneBlastSkill implements ISpecialSkillAction {
    private List<Entity> entityList;

    public void setTargets(List<Entity> enemies) {
        this.entityList = enemies;
    }

    @Override
    public void execute(Entity attacker, Entity ignored, IBattleObserver observer) {
        int damage = 50;
        
        for (Entity e : entityList) {
            if (!(e instanceof Enemy)) continue;

            if (e.isAlive()) {
                if (observer != null) {
                    observer.onActionExecuted(attacker, "Arcane Blast", e);
                }
                e.takeDamage(damage);
            }
        }

        if (attacker instanceof Wizard wizard) {
            wizard.increaseBaseAttack(10);
            if (observer != null) {
                observer.displayMessage(attacker.getName() + " gains +10 ATK from the blast!");
            }
        }
    }
}