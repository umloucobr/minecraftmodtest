package net.minecraft.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;

public class DamageSourceProperties implements ILootCondition {
   private final DamageSourcePredicate predicate;

   private DamageSourceProperties(DamageSourcePredicate predicate) {
      this.predicate = predicate;
   }

   public LootConditionType getConditionType() {
      return LootConditionManager.DAMAGE_SOURCE_PROPERTIES;
   }

   public Set<LootParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootParameters.ORIGIN, LootParameters.DAMAGE_SOURCE);
   }

   public boolean test(LootContext p_test_1_) {
      DamageSource damagesource = p_test_1_.get(LootParameters.DAMAGE_SOURCE);
      Vector3d vector3d = p_test_1_.get(LootParameters.ORIGIN);
      return vector3d != null && damagesource != null && this.predicate.test(p_test_1_.getWorld(), vector3d, damagesource);
   }

   public static ILootCondition.IBuilder builder(DamageSourcePredicate.Builder builder) {
      return () -> {
         return new DamageSourceProperties(builder.build());
      };
   }

   public static class Serializer implements ILootSerializer<DamageSourceProperties> {
      public void serialize(JsonObject p_230424_1_, DamageSourceProperties p_230424_2_, JsonSerializationContext p_230424_3_) {
         p_230424_1_.add("predicate", p_230424_2_.predicate.serialize());
      }

      public DamageSourceProperties deserialize(JsonObject p_230423_1_, JsonDeserializationContext p_230423_2_) {
         DamageSourcePredicate damagesourcepredicate = DamageSourcePredicate.deserialize(p_230423_1_.get("predicate"));
         return new DamageSourceProperties(damagesourcepredicate);
      }
   }
}