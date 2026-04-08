package sc2002.combat.core.items;

import sc2002.combat.control.BattleContext;
import sc2002.combat.core.actions.ISpecialSkillAction;
import sc2002.combat.core.entities.Entity;
import sc2002.combat.core.entities.Player;
import sc2002.combat.core.utils.TargetRequirement;

public class PowerStoneItem implements IItem {
    private final ISpecialSkillAction imbuedSkill;

    public PowerStoneItem(ISpecialSkillAction skill) {
        this.imbuedSkill = skill;
    }

    @Override
    public void use(Player user, Entity target, BattleContext context) {
        // trigger skill immediately without cooldown reset
        context.getObserver().onItemUsed(user, "Power Stone", target);
        imbuedSkill.execute(user, target, context);
    }

    @Override
    public String getName() {
        return "Power Stone";
    }

    @Override 
    public TargetRequirement getTargetRequirement() {
        return imbuedSkill.getTargetRequirement();
    }
}