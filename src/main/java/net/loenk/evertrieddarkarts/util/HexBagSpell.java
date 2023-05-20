package net.loenk.evertrieddarkarts.util;

import net.minecraft.item.Item;

import java.util.Set;

public class HexBagSpell {

    public HexBagSpell(Set<Item> items, int SpellId) {
        this.RequiredItems = items;
        this.SpellId = SpellId;

    }
    public Set<Item> RequiredItems;

    public int SpellId;
}
