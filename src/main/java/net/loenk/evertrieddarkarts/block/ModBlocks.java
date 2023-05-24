package net.loenk.evertrieddarkarts.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.loenk.evertrieddarkarts.EverTriedDarkArtsMod;
import net.loenk.evertrieddarkarts.block.custom.HexBagBlock;
import net.loenk.evertrieddarkarts.item.ModItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModBlocks {


    public static final Block HEX_BAG = registerBlock("hex_bag", new HexBagBlock(FabricBlockSettings.of(Material.WOOL).nonOpaque().strength(2f).luminance(state -> 4).sounds(BlockSoundGroup.WOOL)), ModItemGroup.ETTDA_GROUP);
    public static final Block SACRIFICUS_ALTAR = registerBlock("sacrificus_altar", new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.ANCIENT_DEBRIS).luminance(1).nonOpaque().strength(5.0f, 30.0f)), ModItemGroup.ETTDA_GROUP);

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(EverTriedDarkArtsMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM,  new Identifier(EverTriedDarkArtsMod.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(group)));
    }


    public static void registerModBlocks() {
        EverTriedDarkArtsMod.LOGGER.info("Registering ModBlocks for " + EverTriedDarkArtsMod.MOD_ID);
    }
}
