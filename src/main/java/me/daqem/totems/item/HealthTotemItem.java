package me.daqem.totems.item;

import me.daqem.totems.Totems;
import net.minecraft.item.Item;

public class HealthTotemItem extends Item {

    public HealthTotemItem() {
        super(new Properties().group(Totems.TAB).maxStackSize(1));
    }
}
