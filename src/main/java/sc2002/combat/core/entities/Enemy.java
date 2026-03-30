package sc2002.combat.core.entities;

import sc2002.combat.core.actions.BasicAttackAction;
import sc2002.combat.core.actions.IAction;

public abstract class Enemy extends Entity {
    public Enemy(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
    }

    public IAction decideAction(Player player) {
        if (!player.isAlive())
            return null;
        return new BasicAttackAction();
    }
}