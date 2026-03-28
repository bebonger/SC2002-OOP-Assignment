package sc2002.combat.control;

import java.util.ArrayList;
import java.util.List;
import sc2002.combat.core.actions.Action;
import sc2002.combat.core.actions.BasicAttackAction;
import sc2002.combat.core.actions.DefendAction;
import sc2002.combat.core.actions.ItemAction;
import sc2002.combat.core.entities.Enemy;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.items.Item;
import sc2002.combat.ui.BattleObserver;

public class BattleController {
    private final List<Entity> entities;
    private final BattleObserver observer;
    private final TurnOrderStrategy turnStrategy;
    private int roundCount;

    public BattleController(BattleObserver observer) {
        this.observer = observer;
        this.entities = new ArrayList<>();
        this.turnStrategy = new SpeedComparator();
        this.roundCount = 0;
    }

    public void startBattle(Player player, List<Entity> enemies) {
        this.entities.clear();
        this.entities.add(player);
        this.entities.addAll(enemies);
        this.roundCount = 0;
        
        runBattleLoop();
    }

    public void runBattleLoop() {
        boolean isBattleOngoing = true;
        
        while (isBattleOngoing) {
            roundCount++;
            if (observer != null) {
                observer.onRoundStart(roundCount);
            }
            
            turnStrategy.sort(entities);
            
            for (Entity current : entities) {
                if (!current.isAlive()) {
                    continue;
                }
                
                processTurn(current);
                
                // Check win/loss conditions
                boolean playerAlive = false;
                boolean enemiesAlive = false;
                for (Entity e : entities) {
                    if (e == null) {
                        continue;
                    }
                    if (e instanceof Player && e.isAlive()) playerAlive = true;
                    if (!(e instanceof Player) && e.isAlive()) enemiesAlive = true;
                }
                
                if (!playerAlive || !enemiesAlive) {
                    isBattleOngoing = false;
                    if (observer != null) {
                        int remainingDetail = 0;
                        if (playerAlive) {
                            for (Entity e : entities) {
                                if (e == null) {
                                    continue;
                                }
                                if (e instanceof Player) remainingDetail = e.getHp(); 
                            }
                        } else {
                            for (Entity e : entities) {
                                if (e == null) {
                                    continue;
                                }
                                if (!(e instanceof Player) && e.isAlive()) remainingDetail++; 
                            }
                        }
                        observer.onGameOver(playerAlive, roundCount, remainingDetail);
                    }
                    break;
                }
            }
        }
    }

    private void processTurn(Entity current) {
        if (current == null || !current.isAlive()) {
            return;
        }

        current.setCanTakeAction(true);
        current.updateStatusEffects();
        if (!current.canTakeAction() || !current.isAlive()) {
            if (observer != null) {
                observer.displayMessage(current.getName() + " is unable to act this turn.");
            }
            if (current instanceof Player player) {
                player.updateCooldown();
            }
            return;
        }

        if (observer != null) {
            observer.onTurnStart(current);
        }

        if (current instanceof Player player) {
            processPlayerTurn(player);
            return;
        }

        if (current instanceof Enemy enemy) {
            Player target = findFirstAlivePlayer();
            if (target == null) {
                return;
            }

            Action action = enemy.decideAction(target);
            if (action != null) {
                action.execute(enemy, target, observer);
            }
        }
    }

    private void processPlayerTurn(Player player) {
        player.updateCooldown();

        while (true) {
            if (observer != null) {
                observer.displayMessage("Choose action:");
                observer.displayMessage("1. Basic Attack");
                observer.displayMessage("2. Defend");
                observer.displayMessage("3. Special Skill (Cooldown: " + player.getCurrentCooldown() + ")");
                observer.displayMessage("4. Use Item");
            }

            int actionChoice = readInt(1, 4);
            switch (actionChoice) {
                case 1:
                    {
                        Entity target = chooseEnemyTarget();
                        if (target != null) {
                            new BasicAttackAction().execute(player, target, observer);
                            return;
                        }       break;
                    }
                case 2:
                    new DefendAction().execute(player, player, observer);
                    return;
                case 3:
                    {
                        if (player.getSpecialSkill() == null) {
                            if (observer != null) {
                                observer.displayMessage("No special skill available.");
                            }
                            continue;
                        }       if (player.getCurrentCooldown() > 0) {
                            if (observer != null) {
                                observer.displayMessage("Special skill is on cooldown.");
                            }
                            continue;
                        }       Entity target = chooseEnemyTarget();
                        if (target != null) {
                            player.useSpecialSkill(target);
                            return;
                        }       break;
                    }
                default:
                    {
                        if (player.getInventory().isEmpty()) {
                            if (observer != null) {
                                observer.displayMessage("No items in inventory.");
                            }
                            continue;
                        }       int itemIndex = chooseItemIndex(player);
                        Entity target = chooseItemTarget();
                        if (target != null) {
                            new ItemAction(itemIndex).execute(player, target, observer);
                            return;
                        }       break;
                    }
            }
        }
    }

    private Entity chooseEnemyTarget() {
        List<Entity> aliveEnemies = getAliveEnemies();
        if (aliveEnemies.isEmpty()) {
            if (observer != null) {
                observer.displayMessage("No enemy targets available.");
            }
            return null;
        }

        if (observer != null) {
            observer.displayMessage("Choose target:");
            for (int i = 0; i < aliveEnemies.size(); i++) {
                Entity enemy = aliveEnemies.get(i);
                observer.displayMessage((i + 1) + ". " + enemy.getName() + " (HP: " + enemy.getHp() + ")");
            }
        }

        int targetChoice = readInt(1, aliveEnemies.size());
        return aliveEnemies.get(targetChoice - 1);
    }

    private int chooseItemIndex(Player player) {
        List<Item> inventory = player.getInventory();
        if (observer != null) {
            observer.displayMessage("Choose item:");
            for (int i = 0; i < inventory.size(); i++) {
                observer.displayMessage((i + 1) + ". " + inventory.get(i).getName());
            }
        }

        int itemChoice = readInt(1, inventory.size());
        return itemChoice - 1;
    }

    private Entity chooseItemTarget() {
        Player player = findFirstAlivePlayer();
        if (player == null) {
            return null;
        }

        List<Entity> aliveEnemies = getAliveEnemies();
        if (observer != null) {
            observer.displayMessage("Choose item target:");
            observer.displayMessage("1. " + player.getName() + " (Self)");
            for (int i = 0; i < aliveEnemies.size(); i++) {
                Entity enemy = aliveEnemies.get(i);
                observer.displayMessage((i + 2) + ". " + enemy.getName() + " (HP: " + enemy.getHp() + ")");
            }
        }

        int targetChoice = readInt(1, aliveEnemies.size() + 1);
        if (targetChoice == 1) {
            return player;
        }
        return aliveEnemies.get(targetChoice - 2);
    }

    private List<Entity> getAliveEnemies() {
        List<Entity> aliveEnemies = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity != null && !(entity instanceof Player) && entity.isAlive()) {
                aliveEnemies.add(entity);
            }
        }
        return aliveEnemies;
    }

    private int readInt(int min, int max) {
        while (true) {
            String line = UserInput.SCANNER.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // continue to invalid input message
            }

            if (observer != null) {
                observer.displayMessage("Invalid input. Enter a number from " + min + " to " + max + ".");
            }
        }
    }

    private Player findFirstAlivePlayer() {
        for (Entity entity : entities) {
            if (entity instanceof Player player && player.isAlive()) {
                return player;
            }
        }
        return null;
    }

}
