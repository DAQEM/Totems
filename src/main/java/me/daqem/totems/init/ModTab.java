package me.daqem.totems.init;

import me.daqem.totems.Totems;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ModTab extends BlockItem {

    public ModTab(Block blockIn, Properties builder) {
        super(blockIn, (new Item.Properties().group(Totems.TAB)));
    }
}
