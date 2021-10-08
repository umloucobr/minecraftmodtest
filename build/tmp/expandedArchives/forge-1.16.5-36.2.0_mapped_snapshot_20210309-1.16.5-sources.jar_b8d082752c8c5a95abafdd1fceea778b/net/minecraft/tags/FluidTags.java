package net.minecraft.tags;

import java.util.List;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;

public final class FluidTags {
   protected static final TagRegistry<Fluid> REGISTRY = TagRegistryManager.create(new ResourceLocation("fluid"), ITagCollectionSupplier::getFluidTags);
   public static final ITag.INamedTag<Fluid> WATER = makeWrapperTag("water");
   public static final ITag.INamedTag<Fluid> LAVA = makeWrapperTag("lava");

   public static ITag.INamedTag<Fluid> makeWrapperTag(String id) {
      return REGISTRY.createTag(id);
   }

   public static net.minecraftforge.common.Tags.IOptionalNamedTag<Fluid> createOptional(ResourceLocation name) {
       return createOptional(name, null);
   }

   public static net.minecraftforge.common.Tags.IOptionalNamedTag<Fluid> createOptional(ResourceLocation name, @javax.annotation.Nullable java.util.Set<java.util.function.Supplier<Fluid>> defaults) {
      return REGISTRY.createOptional(name, defaults);
   }

   public static List<? extends ITag.INamedTag<Fluid>> getAllTags() {
      return REGISTRY.getTags();
   }

   //Forge: Readd this stripped getter like the other tag classes
   public static ITagCollection<Fluid> getCollection() {
       return REGISTRY.getCollection();
   }
}
