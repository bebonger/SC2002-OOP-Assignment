package sc2002.combat.core.actions;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.IBattleObserver;

public abstract class SpecialSkillAction implements IAction {
    protected int currentCooldown = 0;
    protected final int maxCooldown;
    protected String skillName;

    public SpecialSkillAction(String skillName, int maxCooldown) {
        this.skillName = skillName;
        this.maxCooldown = maxCooldown;
    }

    public void tick() {
        if (currentCooldown > 0) currentCooldown--;
    }

    public void startCooldown() {
        this.currentCooldown = maxCooldown;
    }

    public boolean isReady() {
        return currentCooldown == 0;
    }

    public int getRemainingTurns() {
        return currentCooldown;
    }

    public String getName() {
        return skillName;
    }

    // subclasses still must implement the unique 'execute' logic
    @Override
    public abstract void execute(Entity attacker, Entity target, IBattleObserver observer);
}