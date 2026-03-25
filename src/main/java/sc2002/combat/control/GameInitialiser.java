package sc2002.combat.control;

import sc2002.combat.core.entities.Player;
import sc2002.combat.ui.BattleObserver;

public class GameInitialiser {
    private BattleController engine;
    private BattleObserver observer;

    public GameInitialiser(BattleController engine, BattleObserver observer) {
        this.engine = engine;
        this.observer = observer;
    }

    public void start() {
        Player player = setupPlayer();
        setupLevel("Easy");
    }
    
    //TODO: only after core is more or less settled 
    private Player setupPlayer() {
        // Setup player logic as per game flow requirements
        return null;
    }

    //TODO: only after core is more or less settled 
    private void setupLevel(String difficulty) {
        // Logic to setup level and enemies based on difficulty
        // List<Entity> enemies = new ArrayList<>();
        // engine.startBattle(player, enemies);
    }
}
