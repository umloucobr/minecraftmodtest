package net.umloucobr.tutorialmod.item.custom;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.umloucobr.tutorialmod.item.ModItems;

public class ModItemGroup {

    public static final ItemGroup TUTORIAL_GROUP = new ItemGroup("tutorialModTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.AMETHYST.get());
        }
    };
}
