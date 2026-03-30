package sc2002.combat.core.actions;

import sc2002.combat.core.entities.Enemy;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.utils.BattleContext;

public class ArcaneBlastSkill implements ISpecialSkillAction {

    @Override
    public void execute(Entity attacker, Entity ignored, BattleContext context) {
        for (Entity e : context.getEntities()) {
            if (!(e instanceof Enemy)) continue;

            if (e.isAlive()) {
                int damage = Math.max(1, attacker.getEffectiveAttack() - e.getEffectiveDefense());

                e.takeDamage(damage);

                context.getObserver().onActionExecuted(attacker, "Arcane Blast", e);
                context.getObserver().onDamageDealt(e, damage, e.getHp(), !e.isAlive());

                if (!e.isAlive()) {
                    attacker.increaseBaseAttack(10);
                    context.getObserver().displayMessage(attacker.getName() + " gains +10 ATK from the blast!");
                }
            }
        }
    }
}