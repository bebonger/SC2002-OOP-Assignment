package sc2002.combat.core.entities;

import sc2002.combat.core.actions.ShieldBashSkill;

public class Warrior extends Player {
    public Warrior(String name) {
        super(name, 260, 40, 20, 30);
        this.specialSkill = new ShieldBashSkill();
    }
}