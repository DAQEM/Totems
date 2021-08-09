package me.daqem.totems.client;

import me.daqem.totems.init.ModItems;
import me.daqem.totems.item.TotemBagItem;
import net.minecraftforge.client.event.ColorHandlerEvent;

public class ColorHandlers {

    public static void registerItemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register(TotemBagItem::getItemColor, ModItems.totemBag);
    }
}
