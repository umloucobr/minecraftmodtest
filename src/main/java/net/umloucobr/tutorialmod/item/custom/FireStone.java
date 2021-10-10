package net.umloucobr.tutorialmod.item.custom;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.umloucobr.tutorialmod.util.TutorialTags;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class FireStone extends Item {
    public FireStone(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getWorld();

        if(!world.isRemote) {
            PlayerEntity playerEntity = Objects.requireNonNull(context.getPlayer());
            BlockState clickedBlock = world.getBlockState(context.getPos());

            rightClickOnCertainBlockState(clickedBlock, context, playerEntity);
            stack.damageItem(1, playerEntity, player -> player.sendBreakAnimation(context.getHand()));
        }

        return super.onItemUseFirst(stack, context);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        if(Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("tooltip.tutorialmod.firestone_shift"));
        } else {
            tooltip.add(new TranslationTextComponent("tooltip.tutorialmod.firestone"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    private void rightClickOnCertainBlockState(BlockState blockClicked, ItemUseContext context, PlayerEntity playerEntity) {
        boolean playerIsNotOnFire = !playerEntity.isBurning();

        // 50% of the time lights the ground on fire other time you get fire resistance
        if(random.nextFloat() > 0.5f) {
            lightEntityOnFire(playerEntity, 6);
        } else if(playerIsNotOnFire && blockIsValidForResistance(blockClicked)) {
            gainFireResistanceAndDestroyBlock(playerEntity, context.getWorld(), context.getPos());
        } else {
            lightGroundOnFire(context);
        }
    }

    private boolean blockIsValidForResistance(BlockState clickedBlock) {
        return clickedBlock.isIn(TutorialTags.Blocks.FIRESTONE_CLICKABLE_BLOCKS);
    }

    private void gainFireResistanceAndDestroyBlock(PlayerEntity playerEntity, World world, BlockPos pos) {
        gainFireResistance(playerEntity);
        world.destroyBlock(pos, false);
    }

    public static void lightGroundOnFire(ItemUseContext context) {
        PlayerEntity playerentity = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockPosForFire = context.getPos().offset(context.getFace());

        if (AbstractFireBlock.canLightBlock(world, blockPosForFire, context.getPlacementHorizontalFacing())) {
            world.playSound(playerentity, blockPosForFire, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS,
                    1.0F, random.nextFloat() * 0.4F + 0.8F);

            BlockState blockstate = AbstractFireBlock.getFireForPlacement(world, blockPosForFire);
            world.setBlockState(blockPosForFire, blockstate, 11);
        }
    }

    public static void gainFireResistance(PlayerEntity playerIn) {
        playerIn.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 200));
    }

    public static void lightEntityOnFire(Entity entity, int duration) {
        entity.setFire(duration);
    }
}