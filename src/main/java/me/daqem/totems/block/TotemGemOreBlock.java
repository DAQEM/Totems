package me.daqem.totems.block;

import me.daqem.totems.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

import java.util.Collections;
import java.util.List;

public class TotemGemOreBlock extends Block {

    public TotemGemOreBlock() {
        super(Properties.create(Material.ROCK)
                .setRequiresTool()
                .hardnessAndResistance(3.0F, 3.0F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(2));
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silkTouch) {
        return silkTouch == 0 ? MathHelper.nextInt(RANDOM, 3, 7) : 0;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return Collections.singletonList(new ItemStack(ModItems.totemGem));
    }
}
