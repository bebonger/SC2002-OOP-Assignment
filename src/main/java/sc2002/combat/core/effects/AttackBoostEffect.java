package sc2002.combat.core.effects;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.utils.BattleContext;

// we technically don't need this class because ArcaneBlastSkill changes ONLY Wizard's base attack
// however this could be potentially implemented through an item like an AttackBoostPotion that affects all types of entities.
public class AttackBoostEffect extends StatusEffect {
    private final int boostAmount;

    public AttackBoostEffect(int duration, int boostAmount) {
        super("Attack Boost", duration);
        this.boostAmount = boostAmount;
    }

    @Override
    public int applyAttackModifier(int currentAtk) {
        return currentAtk + boostAmount;
    }

    @Override
    public void onTurnStart(Entity owner, BattleContext context) {
        super.onTurnStart(owner, context);
        if (context != null && duration > 0) {
            context.getObserver()
                    .displayMessage(owner.getName() + " feels a surge of strength! (+" + boostAmount + " ATK)");
        }
    }
}