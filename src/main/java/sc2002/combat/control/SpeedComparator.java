package sc2002.combat.control;

import java.util.List;
import sc2002.combat.core.entities.Entity;

public class SpeedComparator implements TurnOrderStrategy {
    
    @Override
    public void sort(List<Entity> entities) {
        entities.sort((e1, e2) -> Integer.compare(e2.getSpeed(), e1.getSpeed()));
    }
}
