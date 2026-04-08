package sc2002.combat.control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import sc2002.combat.core.actions.IAction;
import sc2002.combat.core.entities.Enemy;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.utils.BattleContext;
import sc2002.combat.core.utils.TargetRequirement;
import sc2002.combat.ui.ICombatBoundary;

public class CombatController {

    private enum BattleOutcome {
        ONGOING,
        PLAYER_WIN,
        ENEMY_WIN
    }

    private final List<Entity> entities;
    private final List<Entity> backupEnemies;
    private final ICombatBoundary boundary;
    private final ITurnOrderStrategy turnStrategy;
    private int roundCount;
    private boolean backupSpawned;

    public CombatController(ICombatBoundary boundary) {
        this.boundary = boundary;
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

            BattleContext context = new BattleContext(entities, this.boundary);
            boundary.onRoundStart(roundCount);

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
                    if (boundary != null) {
                        boundary.displayMessage("Backup enemies have spawned!");
                    }

                    for (Entity backup : backupEnemies) {
                        backup.setCanTakeAction(false);
                    }

                    break;
                }

                if (outcome != BattleOutcome.ONGOING) {
                    isBattleOngoing = false;
                    int remainingDetail = 0;
                    boolean playerAlive = outcome == BattleOutcome.PLAYER_WIN;
                    if (playerAlive) {
                        for (Entity e : entities) {
                            if (e == null) {
                                continue;
                            }
                            if (e instanceof Player) {
                                remainingDetail = e.getHp();
                            }
                        }
                    } else {
                        for (Entity e : entities) {
                            if (e == null) {
                                continue;
                            }
                            if (!(e instanceof Player) && e.isAlive()) {
                                remainingDetail++;
                            }
                        }
                    }
                    boundary.onGameOver(playerAlive, roundCount, remainingDetail);
                    break;
                }

                // Sleep
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException ex) {
                    System.getLogger(CombatController.class.getName()).log(System.Logger.Level.ERROR, (String) null,
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
            boundary.displayMessage(current.getName() + " is unable to act this turn.");
            return;
        }

        boundary.onTurnStart(current);

        if (current instanceof Player player) {
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
        player.updateCooldown();
        boolean validAction = false;

        while (!validAction) {
            IAction action = context.getBoundary().promptForActionSelection(player, context);
            if (action.requiresCooldown()) {
                if (player.getCurrentCooldown() > 0) {
                    boundary.displayMessage("Action is on cooldown.");
                    continue;
                }
            }

            if (action.getTargetRequirement() != TargetRequirement.NONE) {
                Entity target = context.getBoundary().promptForTargetSelection(context.getEntities(), true, false);
                if (target == null) continue;

                action.execute(player, target, context);
            } else {
                action.execute(player, null, context);
            }
            validAction = true;
            if (action.requiresCooldown()) player.startCooldown();
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
