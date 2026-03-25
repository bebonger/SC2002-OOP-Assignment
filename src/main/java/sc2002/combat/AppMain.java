package sc2002.combat;

import sc2002.combat.control.BattleController;
import sc2002.combat.control.GameInitialiser;
import sc2002.combat.ui.BattleObserver;
import sc2002.combat.ui.CombatConsole;
// TODO: add combatConsole class

public class AppMain {
    public static void main(String[] args) {
        // Instantiate Boundary/UI
        BattleObserver observer = new CombatConsole();
        
        // Instantiate Controller components as per diagram
        BattleController engine = new BattleController(observer);
        GameInitialiser manager = new GameInitialiser(engine, observer);
        
        // Start the game
        manager.start();
    }
}
