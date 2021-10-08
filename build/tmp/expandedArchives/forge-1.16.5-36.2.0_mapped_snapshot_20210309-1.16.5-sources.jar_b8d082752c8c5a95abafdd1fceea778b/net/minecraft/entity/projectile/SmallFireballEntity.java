package net.minecraft.entity.projectile;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class SmallFireballEntity extends AbstractFireballEntity {
   public SmallFireballEntity(EntityType<? extends SmallFireballEntity> p_i50160_1_, World p_i50160_2_) {
      super(p_i50160_1_, p_i50160_2_);
   }

   public SmallFireballEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
      super(EntityType.SMALL_FIREBALL, shooter, accelX, accelY, accelZ, worldIn);
   }

   public SmallFireballEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
      super(EntityType.SMALL_FIREBALL, x, y, z, accelX, accelY, accelZ, worldIn);
   }

   /**
    * Called when the arrow hits an entity
    */
   protected void onEntityHit(EntityRayTraceResult result) {
      super.onEntityHit(result);
      if (!this.world.isRemote) {
         Entity entity = result.getEntity();
         if (!entity.isImmuneToFire()) {
            Entity entity1 = this.getShooter();
            int i = entity.getFireTimer();
            entity.setFire(5);
            boolean flag = entity.attackEntityFrom(DamageSource.causeOnFireDamage(this, entity1), 5.0F);
            if (!flag) {
               entity.forceFireTicks(i);
            } else if (entity1 instanceof LivingEntity) {
               this.applyEnchantments((LivingEntity)entity1, entity);
            }
         }

      }
   }

   protected void func_230299_a_(BlockRayTraceResult result) {
      super.func_230299_a_(result);
      if (!this.world.isRemote) {
         Entity entity = this.getShooter();
         if (entity == null || !(entity instanceof MobEntity) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.getEntity())) {
            BlockPos blockpos = result.getPos().offset(result.getFace());
            if (this.world.isAirBlock(blockpos)) {
               this.world.setBlockState(blockpos, AbstractFireBlock.getFireForPlacement(this.world, blockpos));
            }
         }

      }
   }

   /**
    * Called when this EntityFireball hits a block or entity.
    */
   protected void onImpact(RayTraceResult result) {
      super.onImpact(result);
      if (!this.world.isRemote) {
         this.remove();
      }

   }

   /**
    * Returns true if other Entities should be prevented from moving through this Entity.
    */
   public boolean canBeCollidedWith() {
      return false;
   }

   /**
    * Called when the entity is attacked.
    */
   public boolean attackEntityFrom(DamageSource source, float amount) {
      return false;
   }
}
