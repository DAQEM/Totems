package me.daqem.totems.events;

import me.daqem.totems.item.HealthTotemItem;
import me.daqem.totems.item.TotemBagItem;
import me.daqem.totems.util.modifier.ModHealthModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Collection;

public class EventPlayerTick {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Collection<ItemStack> healthTotems = new ArrayList<>();
        if (event.phase == TickEvent.Phase.START) {
            PlayerEntity player = (PlayerEntity) event.player.getEntity();
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (stack.getItem() instanceof TotemBagItem) {
                    IItemHandler itemHandler = ((TotemBagItem) stack.getItem()).getInventory(stack);
                    for (int x = 0; x < itemHandler.getSlots(); ++x) {
                        ItemStack bagStack = itemHandler.getStackInSlot(x);
                        if (bagStack.getItem() instanceof HealthTotemItem) {
                            healthTotems.add(bagStack);
                        }
                    }
                }
                if (stack.getItem() instanceof HealthTotemItem) {
                    healthTotems.add(stack);
                }
            }
            int highestHealthLevel = 0;
            if (!healthTotems.isEmpty()) {
                for (ItemStack healthTotem : healthTotems) {
                    int level = ((HealthTotemItem) healthTotem.getItem()).getLevel(healthTotem);
                    if (level > highestHealthLevel) {
                        highestHealthLevel = level;
                    }
                }
            } else {
                ModHealthModifier.applyMaxHealthModifier(player, 0);
            }
            ModHealthModifier.applyMaxHealthModifier(player, highestHealthLevel);
        }
    }
}
