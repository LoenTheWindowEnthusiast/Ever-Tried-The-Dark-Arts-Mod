package net.loenk.evertrieddarkarts.block.custom;

import net.loenk.evertrieddarkarts.block.ModBlocks;
import net.loenk.evertrieddarkarts.block.entity.HexBagBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;

import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;

import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;



public class HexBagBlock extends BlockWithEntity {



    public HexBagBlock(Settings settings) {
        super(settings);
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof HexBagBlockEntity) {
            HexBagBlockEntity hexBagBlockEntity = (HexBagBlockEntity) blockEntity;
            if (itemStack.hasNbt()) {
                hexBagBlockEntity.HexBagSpellID = itemStack.getNbt().getInt("evertrieddarkarts.hexbagspellid");
                hexBagBlockEntity.HexBagSpellPower = itemStack.getNbt().getInt("evertrieddarkarts.hexbagspellpower");
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

            if (hexBagBlockEntity.HexBagSpellPower == 0 && hexBagBlockEntity.HexBagSpellID == 0) {
                if (((Inventory)hexBagBlockEntity).isEmpty()){
                    NbtCompound nbtData = new NbtCompound();
                    nbtData.putInt("evertrieddarkarts.hexbagspellpower", 5);
                    drop.setNbt(nbtData);
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), drop);

                    player.sendMessage(new LiteralText("1"), false); // REMOVE
                }else {
                    NbtCompound nbtData = new NbtCompound();
                    nbtData.putInt("evertrieddarkarts.hexbagspellpower", 5);
                    drop.setNbt(nbtData);

                    player.sendMessage(new LiteralText("2"), false); // REMOVE
                    // Check for Items and assign spell and spell power
                }
            } else {
                drop.setNbt(new NbtCompound());
                drop.getNbt().putInt("evertrieddarkarts.hexbagspellid", hexBagBlockEntity.HexBagSpellID);
                drop.getNbt().putInt("evertrieddarkarts.hexbagspellpower", hexBagBlockEntity.HexBagSpellPower);
                player.sendMessage(new LiteralText("3"), false); // REMOVE
            }

            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), drop);
        }

        super.onBreak(world, pos, state, player);
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }


        BlockEntity blockEntity = world.getBlockEntity(pos);
        player.sendMessage(new LiteralText("HexBagelSpellPower: " + ((HexBagBlockEntity)blockEntity).HexBagSpellPower), false);

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
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Random rnd = new Random();

        world.addParticle(ParticleTypes.PORTAL, pos.getX(), pos.getY(), pos.getZ(), rnd.nextFloat(-10f, 10f), rnd.nextFloat(-10f, 10f), rnd.nextFloat(-10f, 10f));
    }
}
