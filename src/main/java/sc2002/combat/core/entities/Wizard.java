package sc2002.combat.core.entities;

import sc2002.combat.core.actions.ArcaneBlastSkill;

public class Wizard extends Player {
    public Wizard(String name) {
        super(name, 200, 50, 10, 20);
        this.specialSkill = new ArcaneBlastSkill();
    }
}