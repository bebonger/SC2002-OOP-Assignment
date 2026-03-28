package sc2002.combat.core.effects;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.BattleObserver;

public class StunEffect extends StatusEffect {
    public StunEffect(int duration) { super("Stunned", duration); }

    @Override
    public void onTurnStart(Entity owner, BattleObserver observer) {
        super.onTurnStart(owner, observer);
        owner.setCanTakeAction(false);
    }
}