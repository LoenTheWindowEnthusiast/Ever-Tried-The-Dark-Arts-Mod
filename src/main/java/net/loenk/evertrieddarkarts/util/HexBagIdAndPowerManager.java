package net.loenk.evertrieddarkarts.util;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;


import java.util.Set;

public class HexBagIdAndPowerManager {

    public static final int HEALING_SPELL = 1;



    public static int getHexBagSpellID(Inventory inventory) {
        if (HexBagIdAndPowerManager.containsItems(inventory, Set.of(Items.EMERALD))) return HEALING_SPELL;

        return 0;
    }

    public static int getHexBagSpellPower(Inventory inventory) {
        int highestFound = 1;
        if (HexBagIdAndPowerManager.containsItems(inventory, Set.of(Items.APPLE))) highestFound = 2;


        return highestFound;
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
