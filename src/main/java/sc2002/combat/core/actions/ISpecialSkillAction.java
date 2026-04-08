package sc2002.combat.core.actions;

public interface ISpecialSkillAction extends IAction {
    @Override
    default boolean requiresCooldown() {
        return true;
    }
}