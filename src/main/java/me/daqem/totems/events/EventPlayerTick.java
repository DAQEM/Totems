package me.daqem.totems.events;

import me.daqem.totems.init.ModItems;
import me.daqem.totems.item.TotemBagItem;
import me.daqem.totems.item.TotemItem;
import me.daqem.totems.util.modifier.ModHealthModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Collection;

public class EventPlayerTick {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Collection<ItemStack> totemsOfHealth = new ArrayList<>();
        Collection<ItemStack> totemsOfFlight = new ArrayList<>();
        if (event.phase == TickEvent.Phase.START) {
            PlayerEntity player = (PlayerEntity) event.player.getEntity();
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (stack.getItem() instanceof TotemBagItem) {
                    IItemHandler itemHandler = ((TotemBagItem) stack.getItem()).getInventory(stack);
                    for (int x = 0; x < itemHandler.getSlots(); ++x) {
                        ItemStack bagStack = itemHandler.getStackInSlot(x);
                        if (bagStack.getItem() == ModItems.totemOfHealth) totemsOfHealth.add(bagStack);
                        if (bagStack.getItem() == ModItems.totemOfFlight) totemsOfFlight.add(bagStack);
                    }
                }
                if (stack.getItem() == ModItems.totemOfHealth) totemsOfHealth.add(stack);
                if (stack.getItem() == ModItems.totemOfFlight) totemsOfFlight.add(stack);
            }
            if (!totemsOfHealth.isEmpty()) {
                int highestLevel = 0;
                for (ItemStack totemOfHealth : totemsOfHealth) {
                    int level = ((TotemItem) totemOfHealth.getItem()).getLevel(totemOfHealth);
                    if (level > highestLevel) highestLevel = level;
                }
                ModHealthModifier.applyMaxHealthModifier(player, highestLevel);
            } else {
                ModHealthModifier.applyMaxHealthModifier(player, 0);
            }
            if (!totemsOfFlight.isEmpty()) {
                int highestLevel = 0;
                for (ItemStack totemOfFlight : totemsOfFlight) {
                    int level = ((TotemItem) totemOfFlight.getItem()).getLevel(totemOfFlight);
                    if (level > highestLevel) highestLevel = level;
                }
                DimensionType dimensionType = player.getEntityWorld().getDimensionType();
                if (dimensionType.hasSkyLight()) {
                    allowFlight(player);
                } else if (dimensionType.getHasCeiling()) {
                    if (highestLevel > 1) allowFlight(player);
                    else preventFlight(player);
                } else if (dimensionType.doesHasDragonFight()) {
                    if (highestLevel > 2) allowFlight(player);
                    else preventFlight(player);
                } else {
                    allowFlight(player);
                }
            } else {
                if (player.getPersistentData().getBoolean("totems_allow_flight")) {
                    preventFlight(player);
                }
            }
        }
    }

    public static void allowFlight(PlayerEntity player) {
        player.abilities.allowFlying = true;
        player.sendPlayerAbilities();
        player.getPersistentData().putBoolean("totems_allow_flight", true);
    }

    public static void preventFlight(PlayerEntity player) {
        player.abilities.allowFlying = false;
        player.abilities.isFlying = false;
        player.sendPlayerAbilities();
        player.getPersistentData().putBoolean("totems_allow_flight", false);
    }

}
