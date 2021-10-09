package net.umloucobr.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.umloucobr.tutorialmod.item.custom.FireStone;

public class FireStoneBlock extends Block {
    public FireStoneBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
                                             Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote()) {
            if(handIn == Hand.MAIN_HAND) {
                System.out.println("I right-clicked a FireStoneBlock. This is called for the Main Hand! D:");
            }
            if(handIn == Hand.OFF_HAND) {
                System.out.println("I right-clicked a FireStoneBlock. This is called for the Off Hand! D:");
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if(!worldIn.isRemote()) {
            System.out.println("I left-clicked a FireStoneBlock!");
        }
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        FireStone.lightEntityOnFire(entityIn, 5);
        super.onEntityWalk(worldIn, pos, entityIn);
    }
}