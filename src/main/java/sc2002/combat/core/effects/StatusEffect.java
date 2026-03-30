package sc2002.combat.core.effects;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.IBattleObserver;

public abstract class StatusEffect {
    protected String name;
    protected int duration; // in turns

    public StatusEffect(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public void onTurnStart(Entity owner, IBattleObserver observer) {
        duration--;
    }

    public int applyAttackModifier(int currentAtk) { return currentAtk; }
    public int applyIncomingDamageModifier(int incomingDamage) { return incomingDamage; }
    public int applyDefenseModifier(int currentDef) { return currentDef; }

    public boolean isExpired() {
        return duration < 0;
    }

    public String getName() { return name; }
    public int getDuration() { return duration; }
}