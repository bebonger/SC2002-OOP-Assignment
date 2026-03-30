package sc2002.combat.core.entities;

import java.util.ArrayList;
import java.util.List;
import sc2002.combat.core.actions.ISpecialSkillAction;
import sc2002.combat.core.items.IItem;
import sc2002.combat.core.utils.BattleContext;

public abstract class Player extends Entity {
    protected List<IItem> inventory;
    protected ISpecialSkillAction specialSkill;
    protected int currentCooldown = 0;
    protected final int MAX_COOLDOWN = 3;

    public Player(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
        this.inventory = new ArrayList<>();
    }

    public void useItem(int index, Entity target, BattleContext context) {
        if (index >= 0 && index < inventory.size()) {
            IItem item = inventory.remove(index);
            item.use(this, target, context);
        }
    }

    public void useSpecialSkill(Entity target, BattleContext context) {
        if (this.currentCooldown == 0) {
            this.specialSkill.execute(this, target, context);
            this.currentCooldown = MAX_COOLDOWN;
        }
    }

    public void updateCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }

    public void startCooldown() {
        this.currentCooldown = MAX_COOLDOWN;
    }

    // Getters and Setters
    public List<IItem> getInventory() {
        return inventory;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public ISpecialSkillAction getSpecialSkill() {
        return specialSkill;
    }
}
