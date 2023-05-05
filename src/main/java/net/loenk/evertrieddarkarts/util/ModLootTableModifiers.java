package net.loenk.evertrieddarkarts.util;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.loenk.evertrieddarkarts.item.ModItems;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;


public class ModLootTableModifiers {

    // Mobs
    private static final Identifier SKELETON_ID = new Identifier("minecraft", "entities/skeleton");

    // Blocks
    private static final Identifier GRASS_ID = new Identifier("minecraft", "blocks/grass");

    public static void modifyLootTables() {
        // RIB BONE drops
        LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) -> {
            if (SKELETON_ID.equals(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.25f)).with(ItemEntry.builder(ModItems.RIB_BONE)) // 25 % Drop Chance
                        .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 2f)).build()); // Between 1 and 2
                supplier.withPool(poolBuilder.build());
            }
        });

        // FORTUNE BERRY drops
        LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) -> {
            if (GRASS_ID.equals(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.04f)).with(ItemEntry.builder(ModItems.FORTUNE_BERRY)) // 4 % Drop Chance
                        .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)).build()); // Between 1 and 3
                supplier.withPool(poolBuilder.build());
            }
        });

    }

}
