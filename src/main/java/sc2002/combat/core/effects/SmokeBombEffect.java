package sc2002.combat.core.effects;

import java.util.Random;

public class SmokeBombEffect extends StatusEffect {
    private Random rand = new Random();
    public SmokeBombEffect(int duration) { super("Smoked", duration); }

    @Override
    public int applyIncomingDamageModifier(int damage) {
        return 0;
    }
}