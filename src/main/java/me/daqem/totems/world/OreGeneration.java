package me.daqem.totems.world;

import me.daqem.totems.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGeneration {

    public static void generateOres(final BiomeLoadingEvent event) {
        if (!event.getCategory().equals(Biome.Category.NETHER) ||
                !event.getCategory().equals(Biome.Category.THEEND)) {
            event.getGeneration().withFeature(
                    GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.EMERALD_ORE.withConfiguration(
                            new ReplaceBlockConfig(
                                    Blocks.STONE.getDefaultState(),
                                    ModBlocks.TOTEM_GEM_ORE.asBlock().getDefaultState()))
                            .withPlacement(
                                    Placement.EMERALD_ORE
                                            .configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        }
    }
}
