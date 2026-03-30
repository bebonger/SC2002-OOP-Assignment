package sc2002.combat.core.effects;

public class SmokeBombEffect extends StatusEffect {
    public SmokeBombEffect(int duration) { super("Smoked", duration); }

    @Override
    public int applyAttackModifier(int currentAtk) {
        return 0;
    }
}