package me.daqem.totems.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;

public interface IBlockProvider extends IItemProvider {

    Block asBlock();

    @Override
    default Item asItem() {
        return asBlock().asItem();
    }
}