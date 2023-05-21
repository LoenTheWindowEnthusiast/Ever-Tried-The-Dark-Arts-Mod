package net.loenk.evertrieddarkarts.block.entity;


import net.loenk.evertrieddarkarts.util.HexBagIdAndPowerManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;


public class HexBagBlockEntity extends LootableContainerBlockEntity {

    public int SpellID = 0;
    public int SpellPower = 0;
    public String HexBagOwners = null;

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

    @Override
    public int size() {
        return 9;
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    public HexBagBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HexBag, pos, state);
    }

    public float cooldown = -1;


    public void writeNbt(NbtCompound nbt) {
        if (!this.serializeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory, false);
        }
        nbt.putInt("evertrieddarkarts.hexbagspellpower", SpellPower);
        nbt.putInt("evertrieddarkarts.hexbagspellid", SpellID);

        super.writeNbt(nbt);
    }

    public static void tick(World world, BlockPos pos, BlockState state, HexBagBlockEntity hexBagBlockEntity) {
        --hexBagBlockEntity.cooldown;
        if (hexBagBlockEntity.needsCooldown()) {
            hexBagBlockEntity.setCooldown(16);
        } else {
            if (hexBagBlockEntity.SpellID != 0 && !world.isClient()) {
                switch (hexBagBlockEntity.SpellID) {
                    case HexBagIdAndPowerManager.HEALING_SPELL:
                        applyStatusEffectToEntitiesInRange(world, pos, 3,new StatusEffectInstance(StatusEffects.REGENERATION, 50, hexBagBlockEntity.SpellPower - 1));
                        break;
                }
            }
        }
    }

    static void applyStatusEffectToEntitiesInRange(World world, BlockPos pos, float maxDistance, StatusEffectInstance effect) {
        TypeFilter filter = TypeFilter.instanceOf(LivingEntity.class);
        Box box = new Box(pos.getX()-maxDistance,pos.getY()-maxDistance,pos.getZ()-maxDistance,pos.getX()+maxDistance,pos.getY()+maxDistance,pos.getZ()+maxDistance);
        List<LivingEntity> entities = world.getEntitiesByType(filter, box, EntityPredicates.VALID_LIVING_ENTITY);

        for (LivingEntity livingEntity: entities) {
            livingEntity.addStatusEffect(effect);
        }
    }

    public boolean needsCooldown() {
        return cooldown < 0;
    }

    public void setCooldown(int i) {
        this.cooldown = i;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory);
        }

        SpellPower = nbt.getInt("evertrieddarkarts.hexbagspellpower");
        SpellID = nbt.getInt("evertrieddarkarts.hexbagspellid");

        super.readNbt(nbt);
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    protected Text getContainerName() {
        return new LiteralText("Hex Bag");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new Generic3x3ContainerScreenHandler(syncId, playerInventory, this);
    }

}
