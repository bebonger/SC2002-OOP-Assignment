package sc2002.combat;

import sc2002.combat.control.BattleController;
import sc2002.combat.control.GameInitialiser;
import sc2002.combat.ui.CombatConsole;
import sc2002.combat.ui.IBattleObserver;

public class AppMain {
    public static void main(String[] args) {
        // Instantiate Boundary/UI
        CombatConsole console = new CombatConsole();

        // Instantiate Controller components as per diagram
        BattleController engine = new BattleController(console);
        GameInitialiser manager = new GameInitialiser(engine, console);

        // Start the game loop
        boolean isAppRunning = true;
        while (isAppRunning) {
            manager.start();
            isAppRunning = manager.askPostGameAction();
        }
    }
}
