package sc2002.combat.core.entities;

import java.util.ArrayList;
import java.util.List;
import sc2002.combat.core.actions.SpecialSkillAction;
import sc2002.combat.core.items.Item;

public abstract class Player extends Entity {
    protected List<Item> inventory;
    protected SpecialSkillAction specialSkill;
    protected int currentCooldown = 0;
    protected final int MAX_COOLDOWN = 3;

    public Player(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
        this.inventory = new ArrayList<>();
    }

    public void useItem(int index, Entity target) {
        if (index >= 0 && index < inventory.size()) {
            Item item = inventory.remove(index);
            item.use(this, target, observer);
        }
    }

    public void useSpecialSkill(Entity target) {
    if (this.currentCooldown == 0) {
            this.specialSkill.execute(this, target, this.observer);
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
    public List<Item> getInventory() { return inventory; }
    public int getCurrentCooldown() { return currentCooldown; }
    public SpecialSkillAction getSpecialSkill() { return specialSkill; }
}
