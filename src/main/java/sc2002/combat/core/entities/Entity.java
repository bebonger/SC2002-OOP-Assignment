package sc2002.combat.core.entities;

import java.util.ArrayList;
import java.util.List;
import sc2002.combat.core.effects.StatusEffect;
import sc2002.combat.ui.IBattleObserver;

public abstract class Entity {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int defense;
    protected int speed;
    
    protected List<StatusEffect> statusEffects;
    protected IBattleObserver observer;
    protected boolean canTakeAction = true;

    public Entity(String name, int hp, int attack, int defense, int speed) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.statusEffects = new ArrayList<>();
    }

    // Observer subscription
    public void setObserver(IBattleObserver observer) {
        this.observer = observer;
    }

    public void takeDamage(int rawDamage) {
        int finalDamage = rawDamage;

        // let status effects modify incoming damage
        for (StatusEffect effect : statusEffects) {
            finalDamage = effect.applyDamageModifier(finalDamage);
        }

        // clamp hp to 0
        this.hp = Math.max(0, this.hp - finalDamage);

        if (observer != null) observer.onDamageDealt(this, finalDamage, this.hp, !isAlive());
    }

    public void heal(int amount) {
        this.hp = Math.min(maxHp, this.hp + amount);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    // Status Effect Lifecycle
    public void updateStatusEffects() {
        for (int i = statusEffects.size() - 1; i >= 0; i--) {
            StatusEffect effect = statusEffects.get(i);
            effect.onTurnStart(this, observer);
            
            if (effect.isExpired()) {
                statusEffects.remove(i);
                if (observer != null) observer.onStatusEffectExpired(this, effect.getName());
            }
        }
    }

    public void addStatusEffect(StatusEffect effect) {
        this.statusEffects.add(effect);
        if (observer != null) {
            observer.onStatusEffectApplied(this, effect.getName(), effect.getDuration());
        }
    }

    public int getEffectiveAttack() {
        int current = this.attack;
        for (StatusEffect e : statusEffects) current = e.applyAttackModifier(current);
        return current;
    }

    public int getEffectiveDefense() {
        int current = this.defense;
        for (StatusEffect e : statusEffects) current = e.applyDefenseModifier(current);
        return current;
    }

    public void increaseBaseAttack(int amount) {
        this.attack += amount;
    }

    // Getters and Setters
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public boolean canTakeAction() { return canTakeAction; }
    public void setCanTakeAction(boolean state) { this.canTakeAction = state; }
}