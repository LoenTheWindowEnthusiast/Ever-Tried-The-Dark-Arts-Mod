package net.loenk.evertrieddarkarts.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ChargedInfusedDiamondItem extends Item {


    public ChargedInfusedDiamondItem(Settings settings) {
        super(settings);
    }


    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
