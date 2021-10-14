package net.umloucobr.tutorialmod.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import static net.umloucobr.tutorialmod.item.custom.FireStone.lightEntityOnFire;


public class SwordEffects extends SwordItem {

    public SwordEffects(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        lightEntityOnFire(target, 3);
        return true;
    }
}

