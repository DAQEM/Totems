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
import java.util.HashMap;
import java.util.Map;

public class EventPlayerTick {

    private final Collection<ItemStack> totemsOfHealth = new ArrayList<>();
    private final Collection<ItemStack> totemsOfFlight = new ArrayList<>();
    private final Collection<ItemStack> totemsOfArmor = new ArrayList<>();
    private final Collection<ItemStack> totemsOfSpeed = new ArrayList<>();
    private final Collection<ItemStack> totemsOfHunger = new ArrayList<>();

    private final HashMap<PlayerEntity, Integer> hungerList = new HashMap<>();

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
            totemOfHealthMethod(player);
            totemOfArmorMethod(player);
            if (!totemsOfSpeed.isEmpty()) {
                int highestLevel = 0;
                for (ItemStack totemOfSpeed : totemsOfSpeed) {
                    int level = ((TotemItem) totemOfSpeed.getItem()).getLevel(totemOfSpeed);
                    if (level > highestLevel) highestLevel = level;
                }
                ModModifiers.applyMovementSpeedModifier(player, highestLevel);
                ModModifiers.applyFlyingSpeedModifier(player, highestLevel);
            } else {
                ModModifiers.applyMovementSpeedModifier(player, 0F);
                ModModifiers.applyFlyingSpeedModifier(player, 0F);
            }
            totemOfFlightMethod(player);
            totemOfHungerMethod(player);
        }
        clearArrays();
    }

    private void clearArrays() {
        totemsOfArmor.clear();
        totemsOfFlight.clear();
        totemsOfHealth.clear();
        totemsOfSpeed.clear();
        totemsOfHunger.clear();
    }

    private void totemOfHungerMethod(PlayerEntity player) {
        if (!totemsOfHunger.isEmpty()) {
            for (Map.Entry entry : hungerList.entrySet()) {
                if (entry.getKey() == player) {
                    if (player.getFoodStats().getFoodLevel() < (Integer) entry.getValue()) {
                        int highestLevel = 0;
                        for (ItemStack totemOfHunger : totemsOfHunger) {
                            int level = ((TotemItem) totemOfHunger.getItem()).getLevel(totemOfHunger);
                            if (level > highestLevel) highestLevel = level;
                        }
                        double random = Math.random();
                        if (random < highestLevel / 10D) {
                            player.getFoodStats().setFoodLevel((Integer) entry.getValue());
                        }
                    }
                } else {
                    hungerList.put(player, player.getFoodStats().getFoodLevel());
                }
            }
        }
    }

    private void totemOfFlightMethod(PlayerEntity player) {
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

    private void totemOfSpeedMethod(PlayerEntity player) {
        if (!totemsOfSpeed.isEmpty()) {
            int highestLevel = 0;
            for (ItemStack totemOfSpeed : totemsOfSpeed) {
                int level = ((TotemItem) totemOfSpeed.getItem()).getLevel(totemOfSpeed);
                if (level > highestLevel) highestLevel = level;
            }
            ModModifiers.applyMovementSpeedModifier(player, highestLevel);
            ModModifiers.applyFlyingSpeedModifier(player, highestLevel);
        } else {
            ModModifiers.applyMovementSpeedModifier(player, 0F);
            ModModifiers.applyFlyingSpeedModifier(player, 0F);
        }
    }

    private void totemOfArmorMethod(PlayerEntity player) {
        if (!totemsOfArmor.isEmpty()) {
            int highestLevel = 0;
            for (ItemStack totemOfArmor : totemsOfArmor) {
                int level = ((TotemItem) totemOfArmor.getItem()).getLevel(totemOfArmor);
                if (level > highestLevel) highestLevel = level;
            }
            ModModifiers.applyArmourModifier(player, highestLevel);
        } else {
            ModModifiers.applyArmourModifier(player, 0F);
        }
    }

    private void totemOfHealthMethod(PlayerEntity player) {
        if (!totemsOfHealth.isEmpty()) {
            int highestLevel = 0;
            for (ItemStack totemOfHealth : totemsOfHealth) {
                int level = ((TotemItem) totemOfHealth.getItem()).getLevel(totemOfHealth);
                if (level > highestLevel) highestLevel = level;
            }
            ModModifiers.applyMaxHealthModifier(player, highestLevel);
        } else {
            ModModifiers.applyMaxHealthModifier(player, 0F);
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

    public void addTotemToList(ItemStack stack) {
        Item stackItem = stack.getItem();
        if (stackItem instanceof TotemItem) {
            if (stackItem == ModItems.totemOfHealth) totemsOfHealth.add(stack);
            if (stackItem == ModItems.totemOfFlight) totemsOfFlight.add(stack);
            if (stackItem == ModItems.totemOfArmor) totemsOfArmor.add(stack);
            if (stackItem == ModItems.totemOfSpeed) totemsOfSpeed.add(stack);
            if (stackItem == ModItems.totemOfHunger) totemsOfHunger.add(stack);
        }
    }
}
