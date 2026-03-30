package sc2002.combat.core.effects;

public class DefenseBoostEffect extends StatusEffect {
    public DefenseBoostEffect() { super("Defending", 1); }

    @Override
    public int applyIncomingDamageModifier(int damage) {
        return damage / 2; // 50% reduction
    }
}