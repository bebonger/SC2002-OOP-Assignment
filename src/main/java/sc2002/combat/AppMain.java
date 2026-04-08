package sc2002.combat;

import sc2002.combat.control.CombatController;
import sc2002.combat.control.GameInitialiser;
import sc2002.combat.ui.CombatConsole;
import sc2002.combat.ui.ICombatBoundary;

public class AppMain {
    public static void main(String[] args) {
        // Instantiate Boundary/UI
        ICombatBoundary boundary = new CombatConsole();

        // Instantiate Controller components as per diagram
        CombatController controller = new CombatController(boundary);
        GameInitialiser manager = new GameInitialiser(controller, boundary);

        // Start the game loop
        boolean isAppRunning = true;
        while (isAppRunning) {
            manager.start();
            isAppRunning = manager.askPostGameAction();
        }
    }
}
