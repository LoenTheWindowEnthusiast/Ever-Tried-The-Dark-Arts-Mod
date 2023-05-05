package net.loenk.evertrieddarkarts.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.loenk.evertrieddarkarts.EverTriedDarkArtsMod;
import net.loenk.evertrieddarkarts.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {

    public static BlockEntityType<HexBagBlockEntity> HexBag;

    public static void registerAllBlockEntities() {
        HexBag = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(EverTriedDarkArtsMod.MOD_ID, "hex_bag"),
                FabricBlockEntityTypeBuilder.create(HexBagBlockEntity::new, ModBlocks.HEX_BAG).build(null));


    }
}
