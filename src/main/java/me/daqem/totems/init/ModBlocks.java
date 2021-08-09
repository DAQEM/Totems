package me.daqem.totems.init;

import me.daqem.totems.Totems;
import me.daqem.totems.block.TotemGemOreBlock;
import me.daqem.totems.util.registry.BlockRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {

    public static final BlockRegistryObject<TotemGemOreBlock> TOTEM_GEM_ORE = register("totem_gem_ore", TotemGemOreBlock::new);

    static void register() {}

    private static <T extends Block> BlockRegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return new BlockRegistryObject<>(Registration.BLOCKS.register(name, block));
    }

    private static <T extends Block> BlockRegistryObject<T> register(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::defaultItem);
    }

    private static <T extends Block> BlockRegistryObject<T> register(String name, Supplier<T> block, Function<BlockRegistryObject<T>, Supplier<? extends BlockItem>> item) {
        BlockRegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, item.apply(ret));
        return ret;
    }

    public static <T extends Block> Supplier<BlockItem> defaultItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(Totems.TAB));
    }

}
