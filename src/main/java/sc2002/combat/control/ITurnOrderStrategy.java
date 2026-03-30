package sc2002.combat.control;

import java.util.List;
import sc2002.combat.core.entities.Entity;

public interface ITurnOrderStrategy {
    void sort(List<Entity> entities);
}
