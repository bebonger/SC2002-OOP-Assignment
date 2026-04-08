package sc2002.combat.core.effects;

import sc2002.combat.control.CombatContext;
import sc2002.combat.core.entities.Entity;

public class StunEffect extends StatusEffect {
    public StunEffect(int duration) {
        super("Stunned", duration);
    }

    @Override
    public void onTurnStart(Entity owner, CombatContext context) {
        super.onTurnStart(owner, context);
        owner.setCanTakeAction(false);
    }
}