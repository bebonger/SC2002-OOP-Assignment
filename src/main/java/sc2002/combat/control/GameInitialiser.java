package sc2002.combat.control;

import java.util.ArrayList;
import java.util.List;
import sc2002.combat.core.entities.Enemy;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Goblin;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.entities.Warrior;
import sc2002.combat.core.entities.Wizard;
import sc2002.combat.core.entities.Wolf;
import sc2002.combat.core.items.PotionItem;
import sc2002.combat.core.items.PowerStoneItem;
import sc2002.combat.core.items.SmokeBombItem;
import sc2002.combat.ui.IBattleObserver;

public class GameInitialiser {
    private final BattleController engine;
    private final IBattleObserver observer;
    
    // Store settings for replay
    private String lastPlayerName;
    private String lastClassChoice;
    private final List<Integer> lastItemChoices;
    private String lastDifficulty;

    public GameInitialiser(BattleController engine, IBattleObserver observer) {
        this.engine = engine;
        this.observer = observer;
        this.lastItemChoices = new ArrayList<>();
    }

    public void start() {
        displayLoadingScreenDetails();

        lastPlayerName = askNonEmpty("Enter your player name:");
        lastClassChoice = askClassChoice();

        Player player = setupPlayer(lastPlayerName, lastClassChoice);
        chooseStarterItem(player, false);

        lastDifficulty = askDifficulty();
        List<Enemy> initialEnemies = setupLevel(lastDifficulty);
        List<Enemy> backupEnemies = setupBackupLevel(lastDifficulty);

        List<Entity> battleInitialEnemies = new ArrayList<>(initialEnemies);
        List<Entity> battleBackupEnemies = new ArrayList<>(backupEnemies);
        
        engine.startBattle(player, battleInitialEnemies, battleBackupEnemies);
    }
    
    public void startReplay() {
        Player player = setupPlayer(lastPlayerName, lastClassChoice);
        chooseStarterItem(player, true);

        List<Enemy> initialEnemies = setupLevel(lastDifficulty);
        List<Enemy> backupEnemies = setupBackupLevel(lastDifficulty);

        List<Entity> battleInitialEnemies = new ArrayList<>(initialEnemies);
        List<Entity> battleBackupEnemies = new ArrayList<>(backupEnemies);
        
        engine.startBattle(player, battleInitialEnemies, battleBackupEnemies);
    }
    
    public boolean askPostGameAction() {
        while (true) {
            display("What would you like to do next?");
            display("1. Replay with the same settings");
            display("2. Start a new game (return to home screen)");
            display("3. Exit");
            
            String input = UserInput.SCANNER.nextLine().trim();
            if (null != input) switch (input) {
                case "1" -> {
                    startReplay();
                    return true;
                }
                case "2" -> {
                    return true; // Returns true to trigger a normal start() again
                }
                case "3" -> {
                    display("Exiting game. Thanks for playing!");
                    return false;
                }
            }
            display("Invalid choice. Enter 1, 2, or 3.");
        }
    }
    
    private Player setupPlayer(String playerName, String classChoice) {
        Player player;
        if ("2".equals(classChoice)) {
            player = new Wizard(playerName);
        } else {
            player = new Warrior(playerName);
        }

        player.setObserver(observer);
        return player;
    }

    private void chooseStarterItem(Player player, boolean isReplay) {
        if (!isReplay) {
            lastItemChoices.clear();
            display("Choose 2 starter items (Duplicates are allowed):");
            display("1. Health Potion - Restore 100 HP");
            display("2. Power Stone - Trigger special skill immediately");
            display("3. Smoke Bomb - Dodge attacks for 2 turns");

            for (int i = 1; i <= 2; i++) {
                display("Select Item " + i + ":");
                int choice = readIntInRange(1, 3);
                lastItemChoices.add(choice);
                addItemToInventory(player, choice);
            }
        } else {
            // Restore items for replay
            for (int choice : lastItemChoices) {
                addItemToInventory(player, choice);
            }
        }
    }
    
    private void addItemToInventory(Player player, int choice) {
        switch (choice) {
            case 1 -> {
                player.getInventory().add(new PotionItem());
                display("Health Potion added to inventory.");
            }
            case 2 -> {
                player.getInventory().add(new PowerStoneItem());
                display("Power Stone added to inventory.");
            }
            default -> {
                player.getInventory().add(new SmokeBombItem());
                display("Smoke Bomb added to inventory.");
            }
        }
    }

    private List<Enemy> setupLevel(String difficulty) {
        List<Enemy> enemies = new ArrayList<>();

        switch (difficulty.toLowerCase()) {
            case "hard":
                enemies.add(new Goblin("Goblin 1"));
                enemies.add(new Goblin("Goblin 2"));
                // Note: Backup Spawn is 1 Goblin, 2 Wolf (to be handled by BattleController logic later)
                break;
            case "medium":
                enemies.add(new Goblin("Goblin 1"));
                enemies.add(new Wolf("Wolf 1"));
                // Note: Backup Spawn is 2 Wolf (to be handled by BattleController logic later)
                break;
            case "easy":
            default:
                enemies.add(new Goblin("Goblin 1"));
                enemies.add(new Goblin("Goblin 2"));
                enemies.add(new Goblin("Goblin 3"));
                break;
        }

        for (Enemy enemy : enemies) {
            enemy.setObserver(observer);
        }

        return enemies;
    }

    private List<Enemy> setupBackupLevel(String difficulty) {
        List<Enemy> backupEnemies = new ArrayList<>();

        switch (difficulty.toLowerCase()) {
            case "hard":
                backupEnemies.add(new Goblin("Backup Goblin 1"));
                backupEnemies.add(new Wolf("Backup Wolf 1"));
                backupEnemies.add(new Wolf("Backup Wolf 2"));
                break;
            case "medium":
                backupEnemies.add(new Wolf("Backup Wolf 1"));
                backupEnemies.add(new Wolf("Backup Wolf 2"));
                break;
            case "easy":
            default:
                // No backup spawn for easy
                break;
        }

        for (Enemy enemy : backupEnemies) {
            enemy.setObserver(observer);
        }

        return backupEnemies;
    }

    private String askClassChoice() {
        while (true) {
            display("Choose class:");
            display("1. Warrior");
            display("2. Wizard");
            String input = UserInput.SCANNER.nextLine().trim();

            if ("1".equals(input) || "2".equals(input)) {
                return input;
            }

            display("Invalid class choice. Enter 1 or 2.");
        }
    }

    private String askDifficulty() {
        while (true) {
            display("Choose difficulty:");
            display("1. Easy");
            display("2. Medium");
            display("3. Hard");
            String input = UserInput.SCANNER.nextLine().trim();

            if ("1".equals(input)) {
                return "Easy";
            }
            if ("2".equals(input)) {
                return "Medium";
            }
            if ("3".equals(input)) {
                return "Hard";
            }

            display("Invalid difficulty choice. Enter 1, 2, or 3.");
        }
    }

    private String askNonEmpty(String prompt) {
        while (true) {
            display(prompt);
            String input = UserInput.SCANNER.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            display("Input cannot be empty.");
        }
    }

    private int readIntInRange(int min, int max) {
        while (true) {
            String line = UserInput.SCANNER.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // handled by retry message below
            }
            display("Invalid input. Enter a number from " + min + " to " + max + ".");
        }
    }

    private void displayLoadingScreenDetails() {
        display("================ LOADING SCREEN ================");
        display("Available Players:");
        displayPlayerAttributes();
        display(" \nAvailable Enemies:");
        displayEnemyAttributes();
        displayDifficultyDetails();
    }

    private void displayPlayerAttributes() {
        Player warrior = new Warrior("Warrior");
        Player wizard = new Wizard("Wizard");

        display(formatEntityStats("Warrior", warrior));
        display("  Special Skill: Shield Bash");
        display(formatEntityStats("Wizard", wizard));
        display("  Special Skill: Arcane Blast");
    }

    private void displayEnemyAttributes() {
        Enemy goblin = new Goblin("Goblin");
        Enemy wolf = new Wolf("Wolf");

        display(formatEntityStats("Goblin", goblin));
        display(formatEntityStats("Wolf", wolf));
    }

    private String formatEntityStats(String label, Entity entity) {
        return label
            + " - HP: " + entity.getMaxHp()
            + ", ATK: " + entity.getAttack()
            + ", DEF: " + entity.getDefense()
            + ", SPD: " + entity.getSpeed();
    }

    private void displayDifficultyDetails() {
        display("\nDifficulty Overview:");
        display("Easy   - Initial: 3 Goblins");
        display("Medium - Initial: 1 Goblin, 1 Wolf | Backup: 2 Wolf");
        display("Hard   - Initial: 2 Goblins | Backup: 1 Goblin, 2 Wolf");
    }

    private void display(String message) {
        if (observer != null) {
            observer.displayMessage(message);
            return;
        }
        System.out.println(message);
    }
}
