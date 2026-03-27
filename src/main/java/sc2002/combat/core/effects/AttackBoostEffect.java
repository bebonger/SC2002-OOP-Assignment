package sc2002.combat.core.effects;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.BattleObserver;

public class AttackBoostEffect extends StatusEffect {
    private int boostAmount;

    public AttackBoostEffect(int duration, int boostAmount) {
        super("Attack Boost", duration);
        this.boostAmount = boostAmount;
    }

    @Override
    public int applyAttackModifier(int currentAtk) {
        return currentAtk + boostAmount;
    }

    @Override
    public void onTurnStart(Entity owner, BattleObserver observer) {
        super.onTurnStart(owner, observer);
        if (observer != null && duration > 0) {
            observer.displayMessage(owner.getName() + " feels a surge of strength! (+" + boostAmount + " ATK)");
        }
    }
}