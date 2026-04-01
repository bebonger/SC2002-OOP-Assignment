package sc2002.combat.control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import sc2002.combat.core.actions.BasicAttackAction;
import sc2002.combat.core.actions.DefendAction;
import sc2002.combat.core.actions.IAction;
import sc2002.combat.core.actions.ItemAction;
import sc2002.combat.core.entities.Enemy;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.items.IItem;
import sc2002.combat.core.items.ITargetable;
import sc2002.combat.core.utils.BattleContext;
import sc2002.combat.ui.IBattleObserver;

public class BattleController {
    private enum BattleOutcome {
        ONGOING,
        PLAYER_WIN,
        ENEMY_WIN
    }

    private final List<Entity> entities;
    private final List<Entity> backupEnemies;
    private final IBattleObserver observer;
    private final ITurnOrderStrategy turnStrategy;
    private int roundCount;
    private boolean backupSpawned;

    public BattleController(IBattleObserver observer) {
        this.observer = observer;
        this.entities = new ArrayList<>();
        this.backupEnemies = new ArrayList<>();
        this.turnStrategy = new SpeedComparator();
        this.roundCount = 0;
        this.backupSpawned = false;
    }

    public void startBattle(Player player, List<Entity> initialEnemies, List<Entity> backupEnemies) {
        this.entities.clear();
        this.backupEnemies.clear();

        this.entities.add(player);
        this.entities.addAll(initialEnemies);

        if (backupEnemies != null) {
            this.backupEnemies.addAll(backupEnemies);
        }

        this.roundCount = 0;
        this.backupSpawned = false;

        runBattleLoop();
    }

    public void runBattleLoop() {
        boolean isBattleOngoing = true;

        while (isBattleOngoing) {
            roundCount++;

            BattleContext context = new BattleContext(entities, this.observer);
            if (observer != null) {
                observer.onRoundStart(roundCount);
            }

            turnStrategy.sort(entities);

            List<Entity> currentRoundEntities = new ArrayList<>(entities);

            for (Entity current : currentRoundEntities) {
                if (!current.isAlive()) {
                    continue;
                }

                processRound(current, context);

                BattleOutcome outcome = evaluateBattleOutcome(current);
                if (outcome == BattleOutcome.PLAYER_WIN && !backupSpawned && !backupEnemies.isEmpty()) {
                    // Trigger Backup Spawn
                    entities.addAll(backupEnemies);
                    backupSpawned = true;
                    if (observer != null) {
                        observer.displayMessage("Backup enemies have spawned!");
                    }

                    for (Entity backup : backupEnemies) {
                        backup.setCanTakeAction(false);
                    }

                    break;
                }

                if (outcome != BattleOutcome.ONGOING) {
                    isBattleOngoing = false;
                    if (observer != null) {
                        int remainingDetail = 0;
                        boolean playerAlive = outcome == BattleOutcome.PLAYER_WIN;
                        if (playerAlive) {
                            for (Entity e : entities) {
                                if (e == null) {
                                    continue;
                                }
                                if (e instanceof Player)
                                    remainingDetail = e.getHp();
                            }
                        } else {
                            for (Entity e : entities) {
                                if (e == null) {
                                    continue;
                                }
                                if (!(e instanceof Player) && e.isAlive())
                                    remainingDetail++;
                            }
                        }
                        observer.onGameOver(playerAlive, roundCount, remainingDetail);
                    }
                    break;
                }

                // Sleep
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException ex) {
                    System.getLogger(BattleController.class.getName()).log(System.Logger.Level.ERROR, (String) null,
                            ex);
                }

            }
        }
    }

    private BattleOutcome evaluateBattleOutcome(Entity actingEntity) {
        boolean playerAlive = false;
        boolean enemiesAlive = false;

        for (Entity e : entities) {
            if (e == null) {
                continue;
            }

            if (e instanceof Player && e.isAlive()) {
                playerAlive = true;
            }
            if (!(e instanceof Player) && e.isAlive()) {
                enemiesAlive = true;
            }
        }

        if (playerAlive && enemiesAlive) {
            return BattleOutcome.ONGOING;
        }
        if (playerAlive) {
            return BattleOutcome.PLAYER_WIN;
        }
        if (enemiesAlive) {
            return BattleOutcome.ENEMY_WIN;
        }

        if (actingEntity instanceof Player) {
            return BattleOutcome.PLAYER_WIN;
        }
        return BattleOutcome.ENEMY_WIN;
    }

    private void processRound(Entity current, BattleContext context) {
        if (current == null || !current.isAlive()) {
            return;
        }

        current.setCanTakeAction(true);
        current.updateStatusEffects(context);
        if (!current.canTakeAction() || !current.isAlive()) {
            if (observer != null) {
                observer.displayMessage(current.getName() + " is unable to act this turn.");
            }
            return;
        }

        if (observer != null) {
            observer.onTurnStart(current);
        }

        if (current instanceof Player player) {
            player.updateCooldown();
            processPlayerTurn(player, context);
            return;
        }

        if (current instanceof Enemy enemy) {
            Player target = findFirstAlivePlayer();
            if (target == null) {
                return;
            }

            IAction action = enemy.decideAction(target);
            if (action != null) {
                action.execute(enemy, target, context);
            }
        }
    }

    private void processPlayerTurn(Player player, BattleContext context) {
        int actionChoice = observer.promptForActionSelection(player.getCurrentCooldown());
        handlePlayerActionChoice(player, context, actionChoice);
    }

    private boolean handlePlayerActionChoice(Player player, BattleContext context, int actionChoice) {
        switch (actionChoice) {
            case 1 -> {
                Entity target = chooseEnemyTarget();
                if (target != null) {
                    new BasicAttackAction().execute(player, target, context);
                    return true;
                }
                return false;
            }
            case 2 -> {
                new DefendAction().execute(player, player, context);
                return true;
            }
            case 3 -> {
                if (player.getSpecialSkill() == null) {
                    if (observer != null) {
                        observer.displayMessage("No special skill available.");
                    }
                    return false;
                }

                if (player.getCurrentCooldown() > 0) {
                    if (observer != null) {
                        observer.displayMessage("Special skill is on cooldown.");
                    }
                    return false;
                }

                Entity target = chooseEnemyTarget();
                if (target != null) {
                    player.useSpecialSkill(target, context);
                    return true;
                }
                return false;
            }
            default -> {
                if (player.getInventory().isEmpty()) {
                    if (observer != null) {
                        observer.displayMessage("No items in inventory.");
                    }
                    return false;
                }

                int itemIndex = chooseItemIndex(player);
                if (itemIndex < 0) {
                    return false;
                }

                IItem item = player.getInventory().get(itemIndex);
                if (item instanceof ITargetable) {
                    Entity target = chooseEnemyTarget();
                    if (target != null) {
                        new ItemAction(itemIndex).execute(player, target, context);
                        return true;
                    }
                    return false;
                } else {
                    new ItemAction(itemIndex).execute(player, player, context);
                    return true;
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

        int targetChoice = observer.promptForTargetSelection(aliveEnemies, true);
        if (targetChoice == aliveEnemies.size() + 1) {
            return null;
        }
        return aliveEnemies.get(targetChoice - 1);
    }

    private int chooseItemIndex(Player player) {
        List<IItem> inventory = player.getInventory();

        int itemChoice = observer.promptForItemSelection(inventory, true);
        if (itemChoice == inventory.size() + 1) {
            return -1;
        }
        return itemChoice - 1;
    }

    // Unused function since we don't have items that should be allowed to cast on both enemies and player.
    private Entity chooseItemTarget() {
        Player player = findFirstAlivePlayer();
        if (player == null) {
            return null;
        }

        List<Entity> aliveEnemies = getAliveEnemies();

        int targetChoice = observer.promptForItemTargetSelection(player, aliveEnemies, true);
        if (targetChoice == aliveEnemies.size() + 2) {
            return null;
        }
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


    private Player findFirstAlivePlayer() {
        for (Entity entity : entities) {
            if (entity instanceof Player player && player.isAlive()) {
                return player;
            }
        }
        return null;
    }

}
