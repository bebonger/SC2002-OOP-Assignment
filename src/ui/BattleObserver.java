public interface BattleObserver {
    void onRoundStart(int roundNumber);
    void onTurnStart(/*Entity activeEntity*/);

    // other events
}