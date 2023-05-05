package net.loenk.evertrieddarkarts.item.custom;


import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.world.World;

import java.util.Random;


public class KindledFortuneBerryItem extends Item{

    public KindledFortuneBerryItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);

        world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_MEDIUM_AMETHYST_BUD_BREAK, SoundCategory.NEUTRAL, 1f, 1f);
        fortuneBerryEvent(world, user);

        return stack;
    }

    private void fortuneBerryEvent(World world, LivingEntity user) {
        if (!user.isPlayer() || world.isClient) {
            return;
        }

        Random rand = new Random();
        int randNumber = rand.nextInt(6);

        switch (randNumber) {
            case 0:
                user.sendSystemMessage(new LiteralText("You feel a little lightheaded"), Util.NIL_UUID);
                break;
            case 1:
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 3000, 3));
                user.sendSystemMessage(new LiteralText("Your legs are positively quivering"), Util.NIL_UUID);
                break;
            case 2:
                ZombieEntity zombie = new ZombieEntity(world);
                zombie.setCanPickUpLoot(true);
                zombie.setHealth(40f);
                zombie.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_PICKAXE));
                zombie.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0f);
                zombie.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
                zombie.setEquipmentDropChance(EquipmentSlot.CHEST, 0f);
                zombie.setPosition(user.getBlockX() + rand.nextInt(2), user.getBlockY(), user.getBlockZ() + rand.nextInt(2));
                world.spawnEntity(zombie);
                break;
            case 3:
                TntEntity tnt = new TntEntity(EntityType.TNT ,world);
                tnt.setPos(user.getBlockX(), user.getBlockY(), user.getBlockZ());
                tnt.setFuse(60);
                world.spawnEntity(tnt);
                break;
            case 4:
                TntEntity tnt2 = new TntEntity(EntityType.TNT ,world);
                tnt2.setPos(user.getBlockX(), user.getBlockY(), user.getBlockZ());
                tnt2.setFuse(1);
                world.spawnEntity(tnt2);
                break;
            case 5:
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightning.setPos(user.getBlockX(), user.getBlockY(), user.getBlockZ());
                world.spawnEntity(lightning);

        }
    }

}
