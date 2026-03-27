package sc2002.combat.core.effects;

import java.util.Random;

public class SmokeBombEffect extends StatusEffect {
    private Random rand = new Random();
    public SmokeBombEffect(int duration) { super("Smoked", duration); }

    @Override
    public int applyDamageModifier(int damage) {
        if (rand.nextBoolean()) { // 50% chance
            return 0; // dodged
        }
        return damage;
    }
}