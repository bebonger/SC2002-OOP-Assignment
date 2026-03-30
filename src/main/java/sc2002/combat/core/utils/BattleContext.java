package sc2002.combat.core.utils;

import java.util.Collections;
import java.util.List;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.ui.IBattleObserver;

public class BattleContext {
    private final List<Entity> entityList;
    private final IBattleObserver observer;

    public BattleContext(List<Entity> entityList, IBattleObserver observer) {
        this.entityList = entityList;
        this.observer = observer;
    }

    // DO NOT MESS WITH BATTLECONTROLLER LIST STRUCTURE
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entityList);
    }

    public IBattleObserver getObserver() {
        return observer;
    }
}