package sc2002.combat.control;

import java.util.List;
import sc2002.combat.core.entities.Entity;

public interface TurnOrderStrategy {
    void sort(List<Entity> entities);
}
