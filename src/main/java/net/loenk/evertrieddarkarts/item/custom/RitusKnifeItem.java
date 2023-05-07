package net.loenk.evertrieddarkarts.item.custom;


import net.loenk.evertrieddarkarts.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;

public class RitusKnifeItem extends SwordItem {

    public RitusKnifeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.getType().getSpawnGroup().equals(SpawnGroup.MONSTER)) {
            Random rnd = new Random();
            if (rnd.nextFloat() > 0.95f) {
                ItemStack drop = new ItemStack(ModItems.NORMAL_BLOOD_VIAL, 1);
                attacker.getWorld().playSound(null, target.getBlockPos(), SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.NEUTRAL, 0.7f, 1.2f);
                ItemScatterer.spawn(attacker.getWorld(), target.getPos().getX(), target.getPos().getY(), target.getPos().getZ(), drop);
            }
        } else if (target.isUndead()) {
            Random rnd = new Random();
            if (rnd.nextFloat() > 0.95f) {
                ItemStack drop = new ItemStack(ModItems.UNDEAD_ESSENCE_VIAL, 1);
                attacker.getWorld().playSound(null, target.getBlockPos(), SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.NEUTRAL, 0.7f, 1.2f);
                ItemScatterer.spawn(attacker.getWorld(), target.getPos().getX(), target.getPos().getY(), target.getPos().getZ(), drop);
            }
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!hand.equals(Hand.MAIN_HAND)) return super.use(world, user, hand);

        if (!user.getOffHandStack().isEmpty()) {
            if (user.getOffHandStack().getItem() == ModItems.KINDLED_FORTUNE_BERRY) {
                user.sendMessage(new LiteralText("KINDLED FORTUNE BERRY DETECTED !"), false);
                user.damage(DamageSource.MAGIC, 2f);
                return TypedActionResult.success(user.getMainHandStack(), world.isClient());
            }

        } else {
            user.damage(DamageSource.MAGIC, 1f);
            return TypedActionResult.success(user.getMainHandStack(), world.isClient());
        }

        return super.use(world, user, hand);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        player.damage(DamageSource.MAGIC, 2f);
        super.onCraft(stack, world, player);
    }
}
