package me.daqem.totems.events;

import me.daqem.totems.item.HealthTotemItem;
import me.daqem.totems.item.TotemBagItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;

public class EventPlayerTick {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            PlayerEntity player = event.player;
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (stack.getItem() instanceof TotemBagItem) {
                    IItemHandler itemHandler = ((TotemBagItem) stack.getItem()).getInventory(stack);
                    for (int x = 0; x < itemHandler.getSlots(); ++x) {
                        ItemStack bagStack = itemHandler.getStackInSlot(x);
                        if (bagStack.getItem() instanceof HealthTotemItem) {
                            //TODO: Write some code here. ((HealthTotemItem) bagStack.getItem()).getLevel(bagStack)
                        }
                    }
                }
            }
        }
    }
}
