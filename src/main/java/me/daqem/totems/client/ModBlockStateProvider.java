package me.daqem.totems.client;

import me.daqem.totems.Totems;
import me.daqem.totems.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(final DataGenerator gen, final ExistingFileHelper exFileHelper) {
        super(gen, Totems.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.TOTEM_GEM_ORE.get());
    }
}
