package net.loenk.evertrieddarkarts.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent FORTUNE_BERRY = new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 150, 1), 0.15f).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 0), 0.02f).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1), 0.02f).statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 300, 1), 0.02f).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 150, 0), 0.02f).alwaysEdible().build();
    public static final FoodComponent KINDLED_FORTUNE_BERRY = new FoodComponent.Builder().hunger(5).saturationModifier(0.3f).alwaysEdible().build();
}
