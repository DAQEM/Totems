package me.daqem.totems.events;

import me.daqem.totems.init.ModItems;
import me.daqem.totems.item.TotemBagItem;
import me.daqem.totems.item.TotemItem;
import me.daqem.totems.util.modifier.ModModifiers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Collection;

public class EventPlayerTick {

    private final Collection<ItemStack> totemsOfHealth = new ArrayList<>();
    private final Collection<ItemStack> totemsOfFlight = new ArrayList<>();
    private final Collection<ItemStack> totemsOfArmor = new ArrayList<>();

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

        if (event.phase == TickEvent.Phase.START) {
            PlayerEntity player = (PlayerEntity) event.player.getEntity();
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                Item stackItem = stack.getItem();
                if (stackItem instanceof TotemBagItem) {
                    IItemHandler itemHandler = ((TotemBagItem) stackItem).getInventory(stack);
                    for (int x = 0; x < itemHandler.getSlots(); ++x) {
                        ItemStack bagStack = itemHandler.getStackInSlot(x);
                        addTotemToList(bagStack);
                    }
                }
                addTotemToList(stack);
            }
            if (!totemsOfHealth.isEmpty()) {
                int highestLevel = 0;
                for (ItemStack totemOfHealth : totemsOfHealth) {
                    int level = ((TotemItem) totemOfHealth.getItem()).getLevel(totemOfHealth);
                    if (level > highestLevel) highestLevel = level;
                }
                ModModifiers.applyMaxHealthModifier(player, highestLevel);
            } else {
                ModModifiers.applyMaxHealthModifier(player, 0);
            }
            if (!totemsOfArmor.isEmpty()) {
                int highestLevel = 0;
                for (ItemStack totemOfArmor : totemsOfArmor) {
                    int level = ((TotemItem) totemOfArmor.getItem()).getLevel(totemOfArmor);
                    if (level > highestLevel) highestLevel = level;
                }
                ModModifiers.applyArmourModifier(player, highestLevel);
            } else {
                ModModifiers.applyArmourModifier(player, 0);
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
        totemsOfArmor.clear();
        totemsOfFlight.clear();
        totemsOfHealth.clear();
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

    public void addTotemToList(ItemStack stack) {
        Item stackItem = stack.getItem();
        if (stackItem instanceof TotemItem) {
            if (stackItem == ModItems.totemOfHealth) totemsOfHealth.add(stack);
            if (stackItem == ModItems.totemOfFlight) totemsOfFlight.add(stack);
            if (stackItem == ModItems.totemOfArmor) totemsOfArmor.add(stack);
        }
    }
}
