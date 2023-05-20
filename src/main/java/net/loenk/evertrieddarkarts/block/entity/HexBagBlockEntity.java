package net.loenk.evertrieddarkarts.block.entity;


import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;


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


    public void writeNbt(NbtCompound nbt) {
        if (!this.serializeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory, false);
        }
        nbt.putInt("evertrieddarkarts.hexbagspellpower", SpellPower);
        nbt.putInt("evertrieddarkarts.hexbagspellid", SpellID);

        super.writeNbt(nbt);
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
