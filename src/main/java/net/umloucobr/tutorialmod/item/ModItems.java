package net.umloucobr.tutorialmod.item;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.umloucobr.tutorialmod.TutorialMod;
import net.umloucobr.tutorialmod.item.custom.FireStone;



public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TutorialMod.MOD_ID);

    public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst",
            () ->  new Item(new Item.Properties().group(ModItemGroup.TUTORIAL_GROUP)));

    public static final RegistryObject<Item> FIRESTONE = ITEMS.register("firestone",
            () -> new FireStone(new Item.Properties().group(ModItemGroup.TUTORIAL_GROUP).maxDamage(8)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
