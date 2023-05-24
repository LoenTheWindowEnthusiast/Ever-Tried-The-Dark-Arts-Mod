package net.loenk.evertrieddarkarts.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.loenk.evertrieddarkarts.EverTriedDarkArtsMod;
import net.loenk.evertrieddarkarts.item.custom.KindledFortuneBerryItem;
import net.loenk.evertrieddarkarts.item.custom.RitusKnifeItem;
import net.loenk.evertrieddarkarts.item.toolmaterial.HexToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;


public class ModItems {

    public static final Item RIB_BONE = registerItem("rib_bone", new Item(new FabricItemSettings().group(ModItemGroup.ETTDA_GROUP)));
    public static final Item NORMAL_BLOOD_VIAL = registerItem("normal_blood_vial", new Item(new FabricItemSettings().group(ModItemGroup.ETTDA_GROUP)));
    public static final Item UNDEAD_ESSENCE_VIAL = registerItem("undead_essence_vial", new Item(new FabricItemSettings().group(ModItemGroup.ETTDA_GROUP)));


    public static final Item INFUSED_DIAMOND = registerItem("infused_diamond", new Item(new FabricItemSettings().group(ModItemGroup.ETTDA_GROUP)));
    public static final Item CHARGED_INFUSED_DIAMOND = registerItem("charged_infused_diamond", new Item(new FabricItemSettings().group(ModItemGroup.ETTDA_GROUP)));
    public static final Item RITUS_KNIFE = registerItem("ritus_knife", new RitusKnifeItem(HexToolMaterial.INSTANCE, 3, -2.4f, new FabricItemSettings().group(ModItemGroup.ETTDA_GROUP).rarity(Rarity.UNCOMMON)));

    public static final Item FORTUNE_BERRY = registerItem("fortune_berry", new Item(new FabricItemSettings().group(ModItemGroup.ETTDA_GROUP).food(ModFoodComponents.FORTUNE_BERRY)));
    public static final Item KINDLED_FORTUNE_BERRY = registerItem("kindled_fortune_berry", new KindledFortuneBerryItem(new FabricItemSettings().group(ModItemGroup.ETTDA_GROUP).food(ModFoodComponents.KINDLED_FORTUNE_BERRY).rarity(Rarity.UNCOMMON)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(EverTriedDarkArtsMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        EverTriedDarkArtsMod.LOGGER.info("Registering Mod Items for " + EverTriedDarkArtsMod.MOD_ID);
    }
}
