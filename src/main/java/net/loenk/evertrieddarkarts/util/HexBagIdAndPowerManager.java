package net.loenk.evertrieddarkarts.util;

import net.loenk.evertrieddarkarts.item.ModItems;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;


import java.util.Set;

public class HexBagIdAndPowerManager {

    public static final int HEALING_SPELL = 1;
    public static final int POISON_SPELL = 2;



    public static int getHexBagSpellID(Inventory inventory) {
        if (HexBagIdAndPowerManager.containsItems(inventory, Set.of(Items.GOLDEN_APPLE, Items.GLISTERING_MELON_SLICE, Items.AMETHYST_SHARD, Items.POPPY, Items.DIAMOND_CHESTPLATE, ModItems.NORMAL_BLOOD_VIAL))) return HEALING_SPELL;
        if (HexBagIdAndPowerManager.containsItems(inventory, Set.of(Items.ROTTEN_FLESH, Items.EMERALD, Items.BONE, Items.SPIDER_EYE, Items.DIAMOND_CHESTPLATE, ModItems.UNDEAD_ESSENCE_VIAL))) return POISON_SPELL;

        return 0;
    }

    public static int getHexBagSpellPower(Inventory inventory) {
        int highestFound = 1;
        if (HexBagIdAndPowerManager.containsItems(inventory, Set.of(Items.APPLE))) highestFound = 2;


        return highestFound;
    }

    public static String getHexBagOwners(Inventory inventory) {
        if (!(inventory.containsAny(Set.of(Items.PAPER)))) return "#NoOwner";
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.getItem().equals(Items.PAPER) && stack.getName().asString() != Items.PAPER.getName().asString()) {
                return stack.getName().asString();
            }
        }
        return "#NoOwner";
    }

    public static boolean containsItems(Inventory inv, Set<Item> items) {
        int xNeeded = items.size();
        int xFound = 0;
        for (int i = 0; i < inv.size(); ++i) {
            ItemStack itemStack = inv.getStack(i);
            if (items.contains(itemStack.getItem())) xFound += 1;
        }
        if (xNeeded == xFound) return true;
        else return false;
    }


}
