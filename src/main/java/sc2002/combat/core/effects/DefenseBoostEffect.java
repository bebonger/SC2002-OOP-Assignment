package sc2002.combat.core.effects;

public class DefenseBoostEffect extends StatusEffect {
      public DefenseBoostEffect() {
        super("Defense Boost", 2); // current round + next round
    }

    @Override
    public int applyDamageModifier(int damage) {
        return damage - 10;
    }
}