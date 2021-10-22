package net.umloucobr.tutorialmod.world;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.umloucobr.tutorialmod.TutorialMod;
import net.umloucobr.tutorialmod.world.gen.ModFlowerGeneration;
import net.umloucobr.tutorialmod.world.gen.ModOreGeneration;
import net.umloucobr.tutorialmod.world.gen.ModTreeGeneration;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID)
public class ModWorldEvents {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        ModOreGeneration.generateOres(event);
        ModTreeGeneration.generateTrees(event);
        ModFlowerGeneration.generateFlowers(event);
        }
}

