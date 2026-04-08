package sc2002.combat.core.utils;

import java.util.Collections;
import java.util.List;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.ICombatBoundary;

public class BattleContext {
    private final List<Entity> entityList;
    private final ICombatBoundary boundary;

    public BattleContext(List<Entity> entityList, ICombatBoundary boundary) {
        this.entityList = entityList;
        this.boundary = boundary;
    }

    // DO NOT MESS WITH BATTLECONTROLLER LIST STRUCTURE
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entityList);
    }

    public ICombatBoundary getBoundary() {
        return boundary;
    }
}