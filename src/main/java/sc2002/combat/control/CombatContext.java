package sc2002.combat.control;

import java.util.Collections;
import java.util.List;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.ICombatBoundary;
import sc2002.combat.ui.ICombatInterface;
import sc2002.combat.ui.ICombatObserver;

public class CombatContext {
    private final List<Entity> entityList;
    private final ICombatBoundary boundary;

    public CombatContext(List<Entity> entityList, ICombatBoundary boundary) {
        this.entityList = entityList;
        this.boundary = boundary;
    }

    // DO NOT MESS WITH BATTLECONTROLLER LIST STRUCTURE
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entityList);
    }

    // explicit getters so that objects do not have to see functions they are not allowed to see
    public ICombatObserver getObserver() {
        return this.boundary; 
    }
    
    // Private so only classes in the same package can touch this
    ICombatInterface getInterface() {
        return this.boundary;
    }
}