package sc2002.combat.control;

import java.util.ArrayList;
import java.util.List;

import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.ui.BattleObserver;

public class BattleController {
    private List<Entity> entities;
    private BattleObserver observer;
    private TurnOrderStrategy turnStrategy;
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
                    if (e instanceof Player && e.isAlive()) playerAlive = true;
                    if (!(e instanceof Player) && e.isAlive()) enemiesAlive = true;
                }
                
                if (!playerAlive || !enemiesAlive) {
                    isBattleOngoing = false;
                    if (observer != null) {
                        int remainingDetail = 0;
                        if (playerAlive) {
                            for (Entity e : entities) {
                                if (e instanceof Player) remainingDetail = e.getHp(); 
                            }
                        } else {
                            for (Entity e : entities) {
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

    //TODO: only after core is more or less settled 
    private void processTurn(Entity current) {
        if (current.canTakeAction()) {
            // Processing actions performed during each turn as per PDF logic
            // Enemies perform BasicAttack, Player chooses an action
            // Detailed logic goes here, bounded by Controller constraints
        }
    }
}
