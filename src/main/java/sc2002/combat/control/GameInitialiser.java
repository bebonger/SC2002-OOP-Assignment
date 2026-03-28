package sc2002.combat.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
import sc2002.combat.ui.BattleObserver;

public class GameInitialiser {
    private static final Scanner INPUT = new Scanner(System.in);

    private final BattleController engine;
    private final BattleObserver observer;

    public GameInitialiser(BattleController engine, BattleObserver observer) {
        this.engine = engine;
        this.observer = observer;
    }

    public void start() {
        String playerName = askNonEmpty("Enter your player name:");
        String classChoice = askClassChoice();
        String difficulty = askDifficulty();

        Player player = setupPlayer(playerName, classChoice);
        grantStarterItems(player);
        List<Enemy> enemies = setupLevel(difficulty);

        List<Entity> battleEnemies = new ArrayList<>(enemies);
        engine.startBattle(player, battleEnemies);
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

    private void grantStarterItems(Player player) {
        player.getInventory().add(new PotionItem());
        player.getInventory().add(new PowerStoneItem());
        player.getInventory().add(new SmokeBombItem());
    }

    private List<Enemy> setupLevel(String difficulty) {
        List<Enemy> enemies = new ArrayList<>();

        if ("Hard".equalsIgnoreCase(difficulty)) {
            enemies.add(new Goblin("Goblin 1"));
            enemies.add(new Goblin("Goblin 2"));
            enemies.add(new Wolf("Wolf 1"));
            enemies.add(new Wolf("Wolf 2"));
        } else if ("Medium".equalsIgnoreCase(difficulty)) {
            enemies.add(new Goblin("Goblin 1"));
            enemies.add(new Wolf("Wolf 1"));
            enemies.add(new Wolf("Wolf 2"));
        } else {
            enemies.add(new Goblin("Goblin 1"));
            enemies.add(new Wolf("Wolf 1"));
        }

        for (Enemy enemy : enemies) {
            enemy.setObserver(observer);
        }

        return enemies;
    }

    private String askClassChoice() {
        while (true) {
            display("Choose class:");
            display("1. Warrior");
            display("2. Wizard");
            String input = INPUT.nextLine().trim();

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
            String input = INPUT.nextLine().trim();

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
            String input = INPUT.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            display("Input cannot be empty.");
        }
    }

    private void display(String message) {
        if (observer != null) {
            observer.displayMessage(message);
            return;
        }
        System.out.println(message);
    }
}
