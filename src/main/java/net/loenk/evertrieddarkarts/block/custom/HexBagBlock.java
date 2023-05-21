package net.loenk.evertrieddarkarts.block.custom;

import net.loenk.evertrieddarkarts.block.ModBlocks;
import net.loenk.evertrieddarkarts.block.entity.HexBagBlockEntity;
import net.loenk.evertrieddarkarts.block.entity.ModBlockEntities;
import net.loenk.evertrieddarkarts.util.HexBagIdAndPowerManager;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;

import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;

import net.minecraft.util.TypeFilter;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;


public class HexBagBlock extends BlockWithEntity {



    public HexBagBlock(Settings settings) {
        super(settings);
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.HexBag, (world1, pos, state1, be) -> HexBagBlockEntity.tick(world1, pos, state1, be));
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof HexBagBlockEntity) {
            HexBagBlockEntity hexBagBlockEntity = (HexBagBlockEntity) blockEntity;
            if (itemStack.hasNbt()) {
                hexBagBlockEntity.SpellID = itemStack.getNbt().getInt("evertrieddarkarts.hexbagspellid");
                hexBagBlockEntity.SpellPower = itemStack.getNbt().getInt("evertrieddarkarts.hexbagspellpower");
                hexBagBlockEntity.HexBagOwners = itemStack.getNbt().getString("evertrieddarkarts.hexbagowners");
            }
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.isClient) return;
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof  HexBagBlockEntity) {
            HexBagBlockEntity hexBagBlockEntity = (HexBagBlockEntity) blockEntity;
            ItemStack drop = new ItemStack(ModBlocks.HEX_BAG.asItem(), 1);

            if (hexBagBlockEntity.SpellID == 0) {
                if (((Inventory)hexBagBlockEntity).isEmpty()){
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), drop);

                    player.sendMessage(new LiteralText("1"), false); // REMOVE
                }else {
                    NbtCompound nbtData = new NbtCompound();
                    nbtData.putInt("evertrieddarkarts.hexbagspellid", HexBagIdAndPowerManager.getHexBagSpellID((Inventory)hexBagBlockEntity));
                    nbtData.putInt("evertrieddarkarts.hexbagspellpower", HexBagIdAndPowerManager.getHexBagSpellPower((Inventory)hexBagBlockEntity));
                    nbtData.putString("evertrieddarkarts.hexbagowners", HexBagIdAndPowerManager.getHexBagOwners((Inventory)hexBagBlockEntity));
                    if (HexBagIdAndPowerManager.getHexBagSpellID((Inventory)hexBagBlockEntity) != 0){
                        drop.setNbt(nbtData);
                    } else {
                        ItemScatterer.spawn(world, pos, (Inventory) hexBagBlockEntity);
                    }

                    player.sendMessage(new LiteralText("2"), false); // REMOVE
                }
            } else {
                drop.setNbt(new NbtCompound());
                drop.getNbt().putInt("evertrieddarkarts.hexbagspellid", hexBagBlockEntity.SpellID);
                drop.getNbt().putInt("evertrieddarkarts.hexbagspellpower", hexBagBlockEntity.SpellPower);
                drop.getNbt().putString("evertrieddarkarts.hexbagowners", hexBagBlockEntity.HexBagOwners);
                player.sendMessage(new LiteralText("3"), false); // REMOVE
            }

            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), drop);
        }

        super.onBreak(world, pos, state, player);
    }



    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // change it to so it only opens for owner if (!(((HexBagBlockEntity)(world.getBlockEntity(pos))).HexBagSpellID == 0)) return ActionResult.FAIL;

        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);
        player.sendMessage(new LiteralText("HexBagSpellID: " + ((HexBagBlockEntity)blockEntity).SpellID), false);
        player.sendMessage(new LiteralText("HexBagSpellPower: " + ((HexBagBlockEntity)blockEntity).SpellPower), false);
        player.sendMessage(new LiteralText("HexBagOwners: " + ((HexBagBlockEntity)blockEntity).HexBagOwners), false);

        if (blockEntity instanceof  HexBagBlockEntity) {
            player.openHandledScreen((HexBagBlockEntity)blockEntity);
        }


        return ActionResult.CONSUME;
    }




    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HexBagBlockEntity(pos, state);
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.combineAndSimplify(Block.createCuboidShape(6, 0, 6, 10, 4, 10), Block.createCuboidShape(7, 3, 7, 9, 4.2, 9), BooleanBiFunction.OR);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Random rnd = new Random();

        world.addParticle(ParticleTypes.PORTAL, pos.getX(), pos.getY(), pos.getZ(), rnd.nextFloat(-10f, 10f), rnd.nextFloat(-10f, 10f), rnd.nextFloat(-10f, 10f));
    }
}
