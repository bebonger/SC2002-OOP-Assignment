package sc2002.combat.core.effects;

import sc2002.combat.control.CombatContext;
import sc2002.combat.core.entities.Entity;

public abstract class StatusEffect {
    protected String name;
    protected int duration; // in turns

    public StatusEffect(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public void onTurnStart(Entity owner, CombatContext context) {
        duration--;
    }

    public int applyAttackModifier(int currentAtk) {
        return currentAtk;
    }

    public int applyDefenseModifier(int currentDef) {
        return currentDef;
    }

    public int applyDamageModifier(int incomingDamage) {
        return incomingDamage;
    }

    public boolean isExpired() {
        return duration <= 0;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }
}