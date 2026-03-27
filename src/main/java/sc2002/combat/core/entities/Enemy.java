package sc2002.combat.core.entities;

import sc2002.combat.core.actions.Action;
import sc2002.combat.core.actions.BasicAttackAction;

public abstract class Enemy extends Entity {
    public Enemy(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
    }

    public Action decideAction(Player player) {
    if (!player.isAlive()) return null;
    return new BasicAttackAction();
}
}