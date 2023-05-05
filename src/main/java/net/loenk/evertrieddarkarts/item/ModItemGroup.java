package net.loenk.evertrieddarkarts.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.loenk.evertrieddarkarts.EverTriedDarkArtsMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup ETTDA_GROUP = FabricItemGroupBuilder.build(new Identifier(EverTriedDarkArtsMod.MOD_ID, "ettda_itemgroup"),
            () -> new ItemStack(ModItems.RIB_BONE));
}
